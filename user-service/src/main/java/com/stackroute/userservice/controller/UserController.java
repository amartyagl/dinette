package com.stackroute.userservice.controller;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.exception.CardNumberExistException;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.modal.Address;
import com.stackroute.userservice.modal.CardDetails;
import com.stackroute.userservice.modal.UserAuth;
import com.stackroute.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RefreshScope
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	UserService userService;

	/*
	 * This method is used add new user and if user already exists it will throw
	 * UserAlreadyexist exception and if user is added it will give created response
	 * 
	 * @RestController is used at the class level and allows the class to handle the
	 * requests made by the client.
	 * 
	 * @Requestbody is used to take input in object from client
	 */
	@Autowired
	RabbitTemplate rabbitTemplate;

	@PostMapping("/addUser")
	ResponseEntity<String> addUser(@Valid @RequestBody UserDto userDto) throws UserAlreadyExistException {
		log.info("adding user and passing created status");
		UserAuth userAuth = new UserAuth();
		userAuth.setEmail(userDto.getEmail());
		userAuth.setPassword(userDto.getPassword());
		rabbitTemplate.convertAndSend("message_exchange", "message_routingKey", userAuth);
		userService.addUser(userDto);
		return new ResponseEntity<>("user created successfully", HttpStatus.CREATED);

	}

	/*
	 * This method is used update existing user and if user not exists it will throw
	 * UserNotFound exception and if update it will give response status ok
	 */
	@PutMapping("/updateUser")
	ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
		log.info("adding user and passing ok status");
		return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
	}

	/*
	 * This method is used get existing user and if user not exists it will throw
	 * UserNotFound exception
	 */
	@GetMapping("/getUser/{emailId}")
	ResponseEntity<UserDto> getUser(@PathVariable String emailId) throws UserNotFoundException {
		log.info("getting user and passing ok status");
		return new ResponseEntity<>(userService.findUserById(emailId), HttpStatus.OK);
	}

	/*
	 * This method is used add address for existing user taking user email for whom
	 * address needs to be added and address
	 */
	@PostMapping("/addAddress/{emailId}")
	ResponseEntity<Address> addAddress(@PathVariable String emailId, @RequestBody @Valid Address address) {
		log.info("adding user address and passing accepted status");
		return new ResponseEntity<>(userService.addAddress(emailId, address), HttpStatus.ACCEPTED);
	}

	/*
	 * This method is used update address for existing user taking user email for
	 * whom address needs to be updated and updated address
	 */
	@PutMapping("/updateAddress/{emailId}")
	ResponseEntity<Address> updateAddress(@PathVariable String emailId, @Valid @RequestBody Address address) {
		log.info("updating user address and passing ok status");
		return new ResponseEntity<>(userService.updateAddress(emailId, address), HttpStatus.OK);
	}

	/*
	 * This method is used delete address for existing user taking user email and
	 * address id which needs to be deleted
	 * 
	 */
	@DeleteMapping("/deleteAddress/{emailId}/{addressId}")
	ResponseEntity<String> deleteAddress(@PathVariable String emailId, @PathVariable int addressId) {

		userService.deleteAddress(emailId, addressId);
		return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
	}

	/*
	 * This method is used add cardDetails for existing user taking user email for
	 * whom cardDetails needs to be added and card details
	 */
	@PostMapping("/addcardDetails/{emailId}")
	ResponseEntity<CardDetails> addCardDetails(@PathVariable String emailId,
			@Valid @RequestBody CardDetails cardDetails) throws CardNumberExistException {
		log.info("adding user card details and passing accepted status");
		return new ResponseEntity<>(userService.addCardDetails(emailId, cardDetails), HttpStatus.ACCEPTED);
	}

	/*
	 * This method is used update card details for existing user taking user email
	 * for whom card details needs to be updated and updated card details
	 */
	@PutMapping("/updateCardDetails/{emailId}")
	ResponseEntity<CardDetails> updateCardDetails(@PathVariable String emailId,
			@Valid @RequestBody CardDetails cardDetails) throws UserNotFoundException {
		log.info("updating user card details and passing ok status");
		return new ResponseEntity<>(userService.updateCardDetails(emailId, cardDetails), HttpStatus.OK);

	}

	/*
	 * This method is used delete card details for existing user taking user email
	 * and card number which needs to be deleted
	 * 
	 */
	@DeleteMapping("/deleteCardDetails/{emailId}/{cardNumber}")
	ResponseEntity<String> deleteCardDetails(@PathVariable String emailId, @PathVariable String cardNumber) {
		userService.deleteCardDetails(emailId, cardNumber);
		log.info("deleting user card details and passing ok status");
		return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
	}

}
