package com.notificationservice.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.notificationservice.modal.MailResponse;
import com.notificationservice.modal.OrderEmail;

import freemarker.template.Configuration;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RefreshScope
public class OrderEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration config;
	
	@Value("${order.subject}")
	String subject;

	/*
	 * In this method we passed the email address of receiver and body to be sent to
	 * Recipient
	 */
	public MailResponse sendEmail(OrderEmail request, Map<String, Object> model) {
		MailResponse response = new MailResponse();
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Template template = config.getTemplate("orderemail-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			helper.setTo(request.getEmailId());
			helper.setSubject(subject);
			helper.setText(html, true);
			log.info("order confirmation email sent successfully");
			mailSender.send(message);
			response.setMessage("mail send to : " + request.getEmailId());
			response.setStatus(Boolean.TRUE);
		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			log.error("Error in sending email order confirmation ");
			response.setStatus(Boolean.FALSE);
		}
		return response;
	}
}
