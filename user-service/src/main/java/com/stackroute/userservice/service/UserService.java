package com.stackroute.userservice.service;


import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.exception.CardNumberExistException;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.modal.Address;
import com.stackroute.userservice.modal.CardDetails;

public interface UserService {

	UserDto addUser(UserDto userDto) throws UserAlreadyExistException;

	UserDto updateUser(UserDto userDto) throws UserNotFoundException;

	UserDto findUserById(String emailId) throws UserNotFoundException;

	Address addAddress(String emailId, Address address);

	Address updateAddress(String emailId, Address address);

	void deleteAddress(String emailId, int addressId);

	CardDetails addCardDetails(String emailId, CardDetails cardDetails) throws CardNumberExistException;

	CardDetails updateCardDetails(String emailId, CardDetails cardDetailss) throws UserNotFoundException;

	void deleteCardDetails(String emailId, String cardNumber);
}
