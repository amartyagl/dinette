package com.authenticationservice.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.authenticationservice.model.UserDto;
import com.authenticationservice.service.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RefreshScope
public class Consumer {
	@Autowired
	private JwtUserDetailsService userService;

	@RabbitListener(queues = "message_queue")
	public void getUserDtoFromRabbitMq(UserDto userDto) {
		log.info("Registering after signup from userservice");
		userService.save(userDto);
	}
}
