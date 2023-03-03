package com.paymentservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.paymentservice.dto.PaymentDto;
import com.paymentservice.exception.PaymentNotFoundException;
import com.paymentservice.model.Payment;
import com.paymentservice.repository.PaymentRepository;
import com.paymentservice.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.extern.slf4j.Slf4j;

@Service
@RefreshScope
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	Payment myPayment;

	@Autowired
	PaymentDto myPaymentDto;

	@Autowired
	Environment environment;

	/*
	 * This Method is to add the payment details. By using paymentdto extracting
	 * ammount.
	 */
	@Override
	public String addPayment(PaymentDto paymentDto) throws RazorpayException {
		int amount = paymentDto.getAmount();
		amount = amount * 100;
		log.info("add payment Request {}", amount);

		// creating razor pay object to validate keys which are taken from razor pay
		RazorpayClient razorpayClient = new RazorpayClient(environment.getProperty("RAZOR_PAY_KEY_ID"),
				environment.getProperty("RAZOR_PAY_KEY_SECRET"));
		JSONObject options = new JSONObject();
		// convert amount in paisa
		options.put("amount", amount);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");

		// creating order in razor pay by adding amount, currency and receipt details
		Order orderCreated = razorpayClient.Orders.create(options);
		// mapping dto to entity
		myPayment.setEmailId(paymentDto.getEmailId());
		myPayment.setAmount(paymentDto.getAmount());
		myPayment.setOrderId(orderCreated.get("id"));
		myPayment.setStatus(orderCreated.get("status"));
		myPayment.setReceipt(orderCreated.get("receipt"));

		// By using repository saving data to database
		Payment savedPayment = paymentRepository.save(myPayment);
		myPaymentDto.setMyPaymentId(savedPayment.getMyPaymentId());

		log.info("add payment Response {}", orderCreated.toString());
		return orderCreated.toString();
	}

	@Override
	public PaymentDto updatePayment(PaymentDto paymentDto) {
		log.info("update payment Request {}", paymentDto);
		String orderId = paymentDto.getOrderId();
		Payment findByOrderId = paymentRepository.findByOrderId(orderId);
		findByOrderId.setPaymentId(paymentDto.getPaymentId());
		findByOrderId.setStatus(paymentDto.getStatus());
		findByOrderId.setAmount(paymentDto.getAmount());
		paymentDto.setMyPaymentId(findByOrderId.getMyPaymentId());
		paymentDto.setEmailId(findByOrderId.getEmailId());
		paymentDto.setAmount(findByOrderId.getAmount());
		paymentDto.setReceipt(findByOrderId.getReceipt());
		log.info("payment Response {}", findByOrderId);
		paymentRepository.save(findByOrderId);
		log.info("update payment Response {}", paymentDto);
		return paymentDto;
	}

	/*
	 * This Method is to get all the payment details.
	 */
	@Override
	public List<PaymentDto> getAllPayments() {
		log.info("Request to get all Payment Details {}");
		List<Payment> allPayments = paymentRepository.findAll();
		List<PaymentDto> allPaymentsDto = new ArrayList<>();
		for (Payment paymentEntity : allPayments) {
			myPaymentDto.setMyPaymentId(paymentEntity.getMyPaymentId());
			myPaymentDto.setEmailId(paymentEntity.getEmailId());
			myPayment.setAmount(paymentEntity.getAmount());
			myPaymentDto.setOrderId(paymentEntity.getOrderId());
			myPaymentDto.setPaymentId(paymentEntity.getPaymentId());
			myPaymentDto.setReceipt(paymentEntity.getReceipt());
			myPaymentDto.setStatus(paymentEntity.getStatus());
			allPaymentsDto.add(myPaymentDto);
		}
		log.info("get all payments Response {}", myPaymentDto);
		return allPaymentsDto;
	}

	@Override
	public List<Payment> getPaymentByEmail(String emailId) {
		log.info("Request to Get Payment By Email {}", emailId);
		List<Payment> getByEmail = paymentRepository.findByEmail(emailId.toLowerCase());
//		List<PaymentDto> allPaymentsDto = new ArrayList<>();
//		if (!getByEmail.isEmpty()) {
//			for (Payment paymentEntity : getByEmail) {
//				myPaymentDto.setMyPaymentId(paymentEntity.getMyPaymentId());
//				myPaymentDto.setEmailId(paymentEntity.getEmailId());
//				myPayment.setAmount(paymentEntity.getAmount());
//				myPaymentDto.setOrderId(paymentEntity.getOrderId());
//				myPaymentDto.setPaymentId(paymentEntity.getPaymentId());
//				myPaymentDto.setReceipt(paymentEntity.getReceipt());
//				myPaymentDto.setStatus(paymentEntity.getStatus());
//				allPaymentsDto.add(myPaymentDto);
//			}
//			log.info("get all payments Response {}", allPaymentsDto);
//			return allPaymentsDto;
//		} else {
//			throw new PaymentNotFoundException("SERVICE.PAYMENT_NOT_FOUND");
//		}
		return getByEmail;
	}

}