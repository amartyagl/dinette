package com.authenticationservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.authenticationservice.model.JwtRequest;
import com.authenticationservice.model.UserDto;
import com.authenticationservice.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
@RestController
@RefreshScope
@RequestMapping(value = "/v1/api")
@Slf4j
public class AuthenticationController {
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		log.info(jwtRequest.toString());
		return ResponseEntity.ok(userDetailsService.authenticateUser(jwtRequest.getEmail(), jwtRequest.getPassword()));
	}
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String getUser() {
		return "Welcome to Auth controller";
	}
}







