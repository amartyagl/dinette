package com.paymentservice.service;

import java.util.List;

import com.paymentservice.dto.PaymentDto;
import com.paymentservice.model.Payment;
import com.razorpay.RazorpayException;

public interface PaymentService {

	String addPayment(PaymentDto paymentDto) throws RazorpayException;
	
	PaymentDto updatePayment(PaymentDto paymentDto);

	List<PaymentDto> getAllPayments();

	List<Payment> getPaymentByEmail(String email);


}
