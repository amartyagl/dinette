package com.notificationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.notificationservice.modal.MailResponse;
import com.notificationservice.modal.SubscriptionEmail;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
@ExtendWith(MockitoExtension.class)
class SubscriptionEmailServiceTest {

	@InjectMocks
	SubscriptionEmailService emailService;

	@Mock
	JavaMailSender javaMailSender;
	
	
	
	SubscriptionEmail subscriptionEmail;
	

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
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

	@Test
	void testSubscriptionEmailSuccess() throws Exception, MalformedTemplateNameException, ParseException, IOException {
		Map<String, Object> model = new HashMap<>();
		model.put("name", subscriptionEmail.getName());
		model.put("subscriptionId", subscriptionEmail.getSubscriptionId());
		model.put("subscriptionName", subscriptionEmail.getSubscriptionName());
		model.put("subscriptionDesc", subscriptionEmail.getSubscriptionDescription());
		model.put("subscriptionImage", subscriptionEmail.getSubscriptionImage());
		model.put("subscriptionPrice", subscriptionEmail.getSubscriptionPrice());
		model.put("endDate", subscriptionEmail.getEndDate());
		when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
		MailResponse mailResponse = emailService.sendEmail(subscriptionEmail, model);
		assertEquals(true, mailResponse.isStatus());

	}
	public FreeMarkerConfigurationFactoryBean factoryBean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates");
		return bean;
	}

}
