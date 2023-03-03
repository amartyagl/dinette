
package com.stackroute.userservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.exception.CardNumberExistException;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.modal.Address;
import com.stackroute.userservice.modal.CardDetails;
import com.stackroute.userservice.modal.User;
import com.stackroute.userservice.repository.UserRepository;
import com.stackroute.userservice.serviceimpl.UserServiceImpl;

/**
 * @author amartya.singh
 *
 */

@ExtendWith(MockitoExtension.class)
class UserServiceTest {



	@Mock
	UserRepository userRepository;
	@InjectMocks
	UserServiceImpl serviceImpl;
	private User user;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User("Amartya", "amartya@gmail.com", "1234567890", 10, "profilepicture", null, null);

	}

	@Test
	void addUserSuccess() throws UserAlreadyExistException {
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setCreditPoints(user.getCreditPoints());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setProfilePicture(user.getProfilePicture());
		when(userRepository.existsById(anyString())).thenReturn(Boolean.FALSE);
		when(userRepository.save((User) any())).thenReturn(user);
		UserDto userSaved = serviceImpl.addUser(userDto);
		assertEquals(userDto, userSaved);

	}

	@Test
	void addUserFailure() throws UserAlreadyExistException {
		when(userRepository.existsById(anyString())).thenReturn(Boolean.TRUE);
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setCreditPoints(user.getCreditPoints());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setProfilePicture(user.getProfilePicture());
		assertThrows(UserAlreadyExistException.class, () -> serviceImpl.addUser(userDto));

	}

	@Test
	void findUserByIdSuccess() throws UserNotFoundException {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		UserDto userDto = serviceImpl.findUserById("amartya@gmail.com");
		assertThat(userDto).isNotNull();
	}

	@Test
	void findUserByIdFailure() {
		when(userRepository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> serviceImpl.findUserById("amartya@gmail.com"));
	}

	@Test
	void updateUserSuccess() throws UserNotFoundException {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		UserDto userDto = new UserDto();
		userDto.setName("Ayush");
		userDto.setEmail(user.getEmail());
		userDto.setCreditPoints(user.getCreditPoints());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setProfilePicture(user.getProfilePicture());
		user.setName(userDto.getName());
		UserDto updatedUser = serviceImpl.updateUser(userDto);
		assertEquals("Ayush", updatedUser.getName());
	}

	@Test
	void updateUserFailure() {
		when(userRepository.findById(anyString())).thenReturn(Optional.empty());
		UserDto userDto = new UserDto();
		userDto.setName("Ayush");
		userDto.setEmail("ayush@gmail.com");
		userDto.setCreditPoints(user.getCreditPoints());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setProfilePicture(user.getProfilePicture());
		assertThrows(UserNotFoundException.class, () -> serviceImpl.updateUser(userDto));
	}

	@Test
	void addAddressSuccess() {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		List<Address> list = new ArrayList<>();
		list.add(getNewAddress());
		Address addedAddress = serviceImpl.addAddress("amartya@gmail.com", getNewAddress());
		assertEquals(addedAddress, getNewAddress());

	}

	@Test
	void updateAddressSuccess() {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		List<Address> list = new ArrayList<>();
		list.add(getNewAddress());
		user.setAddress(list);
		Address newAddress = new Address();
		newAddress.setAddressId(1);
		newAddress.setCity("Delhi");
		newAddress.setColony("Sector-135");
		newAddress.setHouseNumber("101");
		newAddress.setState("Delhi");
		newAddress.setPincode("123789");
		Address updatedAddress = serviceImpl.updateAddress("amartya@gmail.com", newAddress);
		assertEquals("Delhi", updatedAddress.getCity());

	}

	@Test
	void deleteAddressSuccess() {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		List<Address> list = new ArrayList<>();
		list.add(getNewAddress());
		user.setAddress(list);
		serviceImpl.deleteAddress("amartya@gmail.com", 1);
		assertEquals(0, user.getAddress().size());
	}

	@Test
	void addCardDetailsSuccess() throws CardNumberExistException {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		List<CardDetails> list = new ArrayList<>();
		list.add(getNewCard());
		CardDetails addedcardDetails = serviceImpl.addCardDetails("amartya@gmail.com", getNewCard());
		assertEquals(addedcardDetails, getNewCard());
	}

	@Test
	void addCardDetailsFailure() {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		List<CardDetails> list = new ArrayList<>();
		list.add(getNewCard());
		user.setCardDetails(list);
		assertThrows(CardNumberExistException.class,
				() -> serviceImpl.addCardDetails("amartya@gmail.com", getNewCard()));

	}

	@Test
	void updateCardDetailsSuccess() throws UserNotFoundException {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		List<CardDetails> list = new ArrayList<>();
		list.add(getNewCard());
		user.setCardDetails(list);
		CardDetails newcardDetails = new CardDetails();
		newcardDetails.setCardNumber("1234567890");
		newcardDetails.setExpiryMonth("11");
		newcardDetails.setExpiryYear("2022");
		newcardDetails.setName("HDFC Bank");
		CardDetails updatedCard = serviceImpl.updateCardDetails("amartya@gmail.com", newcardDetails);
		assertEquals("HDFC Bank", updatedCard.getName());

	}

	@Test
	void updateCardDetailsFailure() {
		when(userRepository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> serviceImpl.updateCardDetails("singh@gmail.com", any()));
	}

	@Test
	void deleteCardDetailsSuccess() {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		List<CardDetails> list = new ArrayList<>();
		list.add(getNewCard());
		user.setCardDetails(list);
		serviceImpl.deleteCardDetails("amartya@gmail.com", "1234567890");
		assertEquals(0, user.getCardDetails().size());
	}

	Address getNewAddress() {
		Address address = new Address();
		address.setAddressId(1);
		address.setCity("Noida");
		address.setColony("Sector-135");
		address.setHouseNumber("101");
		address.setState("Uttar Pradesh");
		address.setPincode("123456");
		return address;
	}

	CardDetails getNewCard() {
		CardDetails cardDetails = new CardDetails();
		cardDetails.setCardNumber("1234567890");
		cardDetails.setExpiryMonth("11");
		cardDetails.setExpiryYear("2022");
		cardDetails.setName("Bank of India");
		return cardDetails;
	}
}
