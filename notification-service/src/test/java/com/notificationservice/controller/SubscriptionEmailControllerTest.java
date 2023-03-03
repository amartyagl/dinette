package com.notificationservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.notificationservice.modal.MailResponse;
import com.notificationservice.modal.SubscriptionEmail;
import com.notificationservice.service.SubscriptionEmailService;

@WebMvcTest(value = SubscriptionEmailController.class)
class SubscriptionEmailControllerTest {

	@MockBean
	SubscriptionEmailService emailService;

	@InjectMocks
	SubscriptionEmailController controller;

	@Autowired
	MockMvc mockMvc;

	
	SubscriptionEmail subscriptionEmail;

	MailResponse mailResponse;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		subscriptionEmail = new SubscriptionEmail();
		subscriptionEmail.setEmailId("amartya@gmail.com");
		subscriptionEmail.setName("Amartya");
		subscriptionEmail.setSubscriptionDescription("You will get more benefits and more priority");
		subscriptionEmail.setSubscriptionId("100");
		subscriptionEmail.setSubscriptionName("Premium");
		subscriptionEmail.setSubscriptionImage("abc");
		subscriptionEmail.setSubscriptionPrice(150);
		subscriptionEmail.setEndDate(LocalDate.of(2022, 10, 21));

	}

	public static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().registerModule(new JavaTimeModule())
					.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writeValueAsString(obj);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void sendSubscriptionEmailSuccess() throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("name", subscriptionEmail.getName());
		model.put("subscriptionId", subscriptionEmail.getSubscriptionId());
		model.put("subscriptionName", subscriptionEmail.getSubscriptionName());
		model.put("subscriptionDesc", subscriptionEmail.getSubscriptionDescription());
		model.put("subscriptionImage", subscriptionEmail.getSubscriptionImage());
		model.put("subscriptionPrice", subscriptionEmail.getSubscriptionPrice());
		model.put("endDate", subscriptionEmail.getEndDate());
		mailResponse = new MailResponse();
		mailResponse.setMessage("email sent");
		mailResponse.setStatus(true);
		when(emailService.sendEmail(subscriptionEmail, model)).thenReturn(mailResponse);
		int status = mockMvc.perform(post("/api/v1/sendSubscriptionEmail").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(subscriptionEmail))).andReturn().getResponse().getStatus();

		assertEquals(200, status);

	}

}
