package com.notificationservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.modal.Food;
import com.notificationservice.modal.MailResponse;
import com.notificationservice.modal.OrderEmail;
import com.notificationservice.service.OrderEmailService;

@WebMvcTest(value = OrderEmailController.class)
class OrderEmailControllerTest {

	@MockBean
	OrderEmailService emailService;

	@InjectMocks
	OrderEmailController controller;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private OrderEmail orderEmail;

	private MailResponse mailResponse;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		orderEmail = new OrderEmail();
		orderEmail.setEmailId("amartya@gmail.com");
		orderEmail.setFirstName("Amartya");
		orderEmail.setOrderId("101");
		orderEmail.setOrderValue(100);
		orderEmail.setOrderList(new ArrayList<Food>());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void sendOrderEmailSuccess() throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("firstName", orderEmail.getFirstName());
		model.put("foodList", orderEmail.getOrderList());
		model.put("orderId", orderEmail.getOrderId());
		model.put("orderValue", orderEmail.getOrderValue());
		mailResponse = new MailResponse();
		mailResponse.setMessage("email sent");
		mailResponse.setStatus(true);
		when(emailService.sendEmail(orderEmail, model)).thenReturn(mailResponse);
		int status = mockMvc.perform(post("/api/v1/sendOrderEmail").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(orderEmail))).andReturn().getResponse().getStatus();

		assertEquals(200, status);
	}

}
