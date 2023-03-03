package com.stackroute.userservice.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.exception.CardNumberExistException;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.modal.Address;
import com.stackroute.userservice.modal.CardDetails;
import com.stackroute.userservice.modal.User;
import com.stackroute.userservice.repository.UserRepository;
import com.stackroute.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RefreshScope
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	/*
	 * This method is used add new user and if user already exists it will throw
	 * UserAlreadyexist exception
	 */
	@Override
	public UserDto addUser(UserDto userDto) throws UserAlreadyExistException {
		if (userRepository.existsById(userDto.getEmail())) {
			log.error("User already exist it can not be added");
			throw new UserAlreadyExistException("SERVICE.USERALREADYEXIST");
		} else {
			User newUser = new User();
			newUser.setName(userDto.getName());
			newUser.setEmail(userDto.getEmail());
			newUser.setCreditPoints(userDto.getCreditPoints());
			newUser.setMobileNumber(userDto.getMobileNumber());
			newUser.setProfilePicture(userDto.getProfilePicture());

			userRepository.save(newUser);
			log.info("Adding user to database");
			return userDto;
		}
	}

	/*
	 * This method is used update existing user and if user not exists it will throw
	 * UserNotFound exception
	 */
	@Override
	public UserDto updateUser(UserDto userDto) throws UserNotFoundException {
		User updatedUser = new User();
		Optional<User> findById = userRepository.findById(userDto.getEmail());
		if (findById.isPresent()) {
			User userToUpdate = findById.get();
			updatedUser.setName(userDto.getName());
			updatedUser.setEmail(userDto.getEmail());
			updatedUser.setMobileNumber(userDto.getMobileNumber());
			updatedUser.setProfilePicture(userDto.getProfilePicture());
			updatedUser.setCreditPoints(userDto.getCreditPoints());
			updatedUser.setAddress(userToUpdate.getAddress());
			updatedUser.setCardDetails(userToUpdate.getCardDetails());
			userRepository.save(updatedUser);
			log.info("Updating user in database");
			return userDto;
		} else {
			log.error("User not found to update");
			throw new UserNotFoundException("SERVICE.USERNOTFOUND");
		}
	}

	/*
	 * This method is used get existing user and if user not exists it will throw
	 * UserNotFound exception
	 */
	@Override
	public UserDto findUserById(String emailId) throws UserNotFoundException {
		Optional<User> findById = userRepository.findById(emailId);
		if (findById.isPresent()) {
			User foundUser = findById.get();
			UserDto responseUser = new UserDto();
			responseUser.setName(foundUser.getName());
			responseUser.setEmail(emailId);
			responseUser.setMobileNumber(foundUser.getMobileNumber());
			responseUser.setProfilePicture(foundUser.getProfilePicture());
			responseUser.setCreditPoints(foundUser.getCreditPoints());
			responseUser.setAddress(foundUser.getAddress());
			responseUser.setCardDetails(foundUser.getCardDetails());
			log.info("User exist and returned");
			return responseUser;
		} else {
			log.error("User not found to return");
			throw new UserNotFoundException("SERVICE.USERNOTFOUND");
		}
	}

	/*
	 * This method is used add address for existing user taking user email for whom
	 * address needs to be added and address
	 */
	@Override
	public Address addAddress(String emailId, Address address) {
		List<Address> addressList;
		User user = userRepository.findById(emailId).get();
		if (user.getAddress() == null) {
			addressList = new ArrayList<>();
		} else {
			addressList = user.getAddress();
		}
		addressList.add(address);
		user.setAddress(addressList);
		log.info("Adding new address");
		userRepository.save(user);

		return address;
	}

	/*
	 * This method is used update address for existing user taking user email for
	 * whom address needs to be updated and updated address
	 */
	@Override
	public Address updateAddress(String emailId, Address address) {
		User user = userRepository.findById(emailId).get();
		List<Address> listOfAddress = user.getAddress();
		Iterator<Address> addressIterator = listOfAddress.iterator();
		while (addressIterator.hasNext()) {
			Address addressToUpdate = addressIterator.next();
			if (addressToUpdate.getAddressId() == address.getAddressId()) {
				addressToUpdate.setHouseNumber(address.getHouseNumber());
				addressToUpdate.setCity(address.getCity());
				addressToUpdate.setColony(address.getColony());
				addressToUpdate.setPincode(address.getPincode());
				addressToUpdate.setState(address.getState());
				log.info("Address found for updating");
			}
		}
		user.setAddress(listOfAddress);
		userRepository.save(user);

		return address;
	}

	/*
	 * This method is used delete address for existing user taking user email and
	 * address id which needs to be deleted
	 * 
	 */
	@Override
	public void deleteAddress(String emailId, int addressId) {
		User user = userRepository.findById(emailId).get();
		List<Address> listOfAddress = user.getAddress();
		Iterator<Address> addressIterator = listOfAddress.iterator();
		while (addressIterator.hasNext()) {
			if (addressIterator.next().getAddressId() == addressId) {
				addressIterator.remove();
				log.info("Address found for deleting");
			}
		}
		user.setAddress(listOfAddress);
		userRepository.save(user);

	}
	/*
	 * This method is used add cardDetails for existing user taking user email for
	 * whom cardDetails needs to be added and card details
	 */

	@Override
	public CardDetails addCardDetails(String emailId, CardDetails cardDetails) throws CardNumberExistException {
		List<CardDetails> cardList;
		User user = userRepository.findById(emailId).get();
		if (user.getCardDetails() == null) {
			cardList = new ArrayList<>();
		} else {
			cardList = user.getCardDetails();
			Iterator<CardDetails> cardIterator = cardList.iterator();
			while (cardIterator.hasNext()) {
				if (cardIterator.next().getCardNumber().equalsIgnoreCase(cardDetails.getCardNumber())) {
					log.error("This card exist so it can not be added again");
					throw new CardNumberExistException("SERVICE.CARDALREADYEXIST");
				}
			}
		}
		cardList.add(cardDetails);
		user.setCardDetails(cardList);
		log.info("Adding card details");
		userRepository.save(user);
		return cardDetails;
	}

	/*
	 * This method is used update card details for existing user taking user email
	 * for whom card details needs to be updated and updated card details
	 */

	@Override
	public CardDetails updateCardDetails(String emailId, CardDetails cardDetails) throws UserNotFoundException {
		 
		Optional<User> findById= userRepository.findById(emailId);
		if(findById.isPresent())
		{
		User user=findById.get();
		List<CardDetails> card = user.getCardDetails();
		Iterator<CardDetails> cardIterator = card.iterator();
		while (cardIterator.hasNext()) {
			CardDetails cardToUpdate = cardIterator.next();
			if (cardToUpdate.getCardNumber().equalsIgnoreCase(cardDetails.getCardNumber())) {
				cardToUpdate.setExpiryMonth(cardDetails.getExpiryMonth());
				cardToUpdate.setExpiryYear(cardDetails.getExpiryYear());
				cardToUpdate.setName(cardDetails.getName());
				log.info("Card number found for updating");
			}
		}
		user.setCardDetails(card);
		userRepository.save(user);

		return cardDetails;
		}
		else
		{
			log.error("Unable to add card details as user with given email not exist");
			throw new UserNotFoundException("SERVICE.USERNOTFOUND");
		}
	}

	/*
	 * This method is used delete card details for existing user taking user email
	 * and card number which needs to be deleted
	 * 
	 */
	@Override
	public void deleteCardDetails(String emailId, String cardNumber) {
		User user = userRepository.findById(emailId).get();
		List<CardDetails> card = user.getCardDetails();
		Iterator<CardDetails> cardIterator = card.iterator();
		while (cardIterator.hasNext()) {
			if (cardIterator.next().getCardNumber().equalsIgnoreCase(cardNumber)) {
				cardIterator.remove();
				log.info("Card number found for deleting");
			}
		}
		user.setCardDetails(card);
		userRepository.save(user);

	}

}
