
package com.stackroute.userservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.modal.Address;
import com.stackroute.userservice.modal.CardDetails;
import com.stackroute.userservice.serviceimpl.UserServiceImpl;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

	@MockBean
	private UserServiceImpl serviceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	UserController userController;

	@MockBean
	UserDto userDto;

	Address address;

	CardDetails cardDetails;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		userDto = new UserDto("Amartya", "amartya@gmail.com", 10, "1234567890", "profilepicture","Amartya@123", null, null
				);
		address = new Address();
		address.setAddressId(1);
		address.setCity("Noida");
		address.setColony("Colony A");
		address.setHouseNumber("House number 1");
		address.setState("UttarPradesh");
		address.setPincode("123456");
		cardDetails = new CardDetails();
		cardDetails.setCardNumber("123123123123");
		cardDetails.setExpiryYear("2022");
		cardDetails.setExpiryMonth("11");
		cardDetails.setName("Amartya Singh");
	}

	@Test
	void testAddUser() throws Exception {
		when(serviceImpl.addUser(userDto)).thenReturn(userDto);
		int status = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/addUser")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDto))).andReturn().getResponse()
				.getStatus();
		assertEquals(201, status);
	}

	@Test
	void testGetUser() throws Exception {
		when(serviceImpl.findUserById("amartya@gmail.com")).thenReturn(userDto);
		int status = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getUser/amartya@gmail.com")).andReturn()
				.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void testUpdateUser() throws Exception {
		when(serviceImpl.updateUser(any())).thenReturn(userDto);
		mockMvc.perform(
				put("/api/v1/updateUser").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDto)))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testAddAddress() throws Exception {
		when(serviceImpl.addAddress("amartya@gmail.com", address)).thenReturn(address);
		int status = mockMvc.perform(post("/api/v1/addAddress/amartya@gmail.com")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(address))).andReturn().getResponse()
				.getStatus();
		assertEquals(202, status);
	}

	@Test
	void testUpdateAddress() throws Exception {
		address.setCity("Ghazipur");
		when(serviceImpl.updateAddress("amartya@gmail.com", address)).thenReturn(address);
		int status = mockMvc.perform(put("/api/v1/updateAddress/amartya@gmail.com")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(address))).andReturn().getResponse()
				.getStatus();
		assertEquals(200, status);
	}

	@Test
	void testDeleteAddress() throws Exception {
		int status = mockMvc.perform(delete("/api/v1/deleteAddress/amartya@gmail.com/1")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(address))).andReturn().getResponse()
				.getStatus();
		assertEquals(200, status);
	}

	@Test
	void testAddCardDetails() throws Exception {
		when(serviceImpl.addCardDetails("amartya@gmail.com", cardDetails)).thenReturn(cardDetails);
		int status = mockMvc.perform(post("/api/v1/addcardDetails/amartya@gmail.com")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(cardDetails))).andReturn().getResponse()
				.getStatus();
		assertEquals(202, status);
	}

	@Test
	void testUpdateCardDetails() throws Exception {
		when(serviceImpl.updateCardDetails("amartya@gmail.com", cardDetails)).thenReturn(cardDetails);
		int status = mockMvc.perform(put("/api/v1/updateCardDetails/amartya@gmail.com")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(cardDetails))).andReturn().getResponse()
				.getStatus();
		assertEquals(200, status);
	}

	@Test
	void testDeleteCardDetails() throws Exception {
		int status = mockMvc
				.perform(delete("/api/v1/deleteCardDetails/amartya@gmail.com/123123123123")
						.contentType(MediaType.APPLICATION_JSON).content(asJsonString(cardDetails)))
				.andReturn().getResponse().getStatus();
		assertEquals(200, status);
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
}
}
