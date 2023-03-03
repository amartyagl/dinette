package com.menuservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.menuservice.dto.MenuDto;
import com.menuservice.model.Menu.SubscriptionCategory;
import com.menuservice.serviceImpl.MenuServiceImpl;


@WebMvcTest(value = MenuController.class)
class MenuControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	MenuServiceImpl menuServiceImpl;
	
	@MockBean
	MenuDto menuDto;
	
	@InjectMocks
	MenuController menuController;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc=MockMvcBuilders.standaloneSetup(menuController).build();
		menuDto = new MenuDto("1","Pizza","pizzaPicture",true,"Herbed Onion & Green Capsicum, Sweet Corn",null,null,Stream.of(SubscriptionCategory.GOLD).collect(Collectors.toList()));
	}
	
	@Test
	void addUserTest_success() throws Exception{
		when(menuServiceImpl.addMenu(menuDto)).thenReturn(menuDto);
		int status = mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/menu")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(menuDto))).andReturn().getResponse()
				.getStatus();
		assertEquals(202, status);
	}
	
	@Test
	void getAllMenus_success() throws Exception{
		List<MenuDto> records = Arrays.asList(menuDto);
		when(menuServiceImpl.getAllMenus()).thenReturn(records);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/menu")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].foodName", is("Pizza")));
	}
	
	@Test
	void getMenuById_Success() throws Exception{
		when(menuServiceImpl.getMenuById(menuDto.getFoodItemId())).thenReturn(menuDto);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/menu/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.foodName", is("Pizza")));
	}
	
	@Test
	void getMenuByName_success() throws Exception{
		when(menuServiceImpl.getMenuByName(menuDto.getFoodName())).thenReturn(menuDto);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/menuName/Pizza")
				.contentType(MediaType.APPLICATION_JSON))
		   		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		   		.andExpect(MockMvcResultMatchers.jsonPath("$.foodDescription", is("Herbed Onion & Green Capsicum, Sweet Corn")));
	}
	
	@Test
	void getMenuByCategory_success() throws Exception{
		List<MenuDto> records = Arrays.asList(menuDto);
		List<SubscriptionCategory> subscriptionCategory = menuDto.getSubscriptionCategory();
		for (SubscriptionCategory category : subscriptionCategory) {
			when(menuServiceImpl.getMenyBySubsciptionCategory(category.toString())).thenReturn(records);
			mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/menuCategory/GOLD")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].foodName", is("Pizza")));
		}
		
	}
	
	@Test
	void updateMenu() throws Exception{
		MenuDto updateDto = new MenuDto("1","Pizza","pizzaPicture",true,"Herbed Onion & Green Capsicum, Sweet Corn",null,null,Stream.of(SubscriptionCategory.GOLD,SubscriptionCategory.PLATINUM).collect(Collectors.toList()));
		when(menuServiceImpl.updateMenu(menuDto.getFoodItemId(), updateDto)).thenReturn(updateDto);
		mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/menu/1")
				.content(asJsonString(updateDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.subscriptionCategory[0]", is("GOLD")));
	}
	
	@Test
	void deleteMenu() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/menu/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
	}
	
	
	public String asJsonString(final Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter objectWriter = objectMapper.writer();
		try {
			String context = objectWriter.writeValueAsString(obj);
			return context;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
