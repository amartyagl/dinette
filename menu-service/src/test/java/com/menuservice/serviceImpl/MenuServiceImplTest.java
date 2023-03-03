package com.menuservice.serviceImpl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.menuservice.dto.MenuDto;
import com.menuservice.exception.FoodNameAlreadyExistException;
import com.menuservice.exception.ResourceNotFoundException;
import com.menuservice.model.Menu;
import com.menuservice.model.Menu.SubscriptionCategory;
import com.menuservice.repository.MenuRepository;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {
	
	
	@Mock
	MenuRepository menuRepository;
	
	@Mock
	Menu menu;
	
	@Mock
	MenuDto menuDto;
	
	@InjectMocks
	MenuServiceImpl menuServiceImpl;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		menu = new Menu("1","Pizza","pizzaPicture",true,"Herbed Onion & Green Capsicum, Sweet Corn",null,null,Stream.of(SubscriptionCategory.GOLD).collect(Collectors.toList()));
	}
	
	@Test
	void addMenu_success() {
		List<Menu> allMenu = Arrays.asList(menu);
		Iterator<Menu> iterator = allMenu.iterator();
		MenuDto menuDto = new MenuDto("2","Burger","burgerPicture",true,"Herbed Onion & Green Capsicum, Sweet Corn",null,null,Stream.of(SubscriptionCategory.GOLD).collect(Collectors.toList()));
		when(menuRepository.findAll()).thenReturn(allMenu);
		assertTrue(iterator.hasNext());
		assertNotEquals(iterator.next().getFoodName(),menuDto.getFoodName());
		Menu expectedMenu = new Menu("2","Burger","burgerPicture",true,"Herbed Onion & Green Capsicum, Sweet Corn",null,null,Stream.of(SubscriptionCategory.GOLD).collect(Collectors.toList()));
		when(menuRepository.save(expectedMenu)).thenReturn(expectedMenu);
		MenuDto actualDto = menuServiceImpl.addMenu(menuDto);
		assertEquals(actualDto.getFoodName(), expectedMenu.getFoodName());
	}
	
	@Test
	void addMenu_Failure() {
		List<Menu> allMenu = Arrays.asList(menu);
		Iterator<Menu> iterator = allMenu.iterator();
		MenuDto menuDto = new MenuDto("2","Pizza","pizzaPicture",true,"Herbed Onion & Green Capsicum, Sweet Corn",null,null,Stream.of(SubscriptionCategory.GOLD).collect(Collectors.toList()));
		when(menuRepository.findAll()).thenReturn(allMenu);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next().getFoodName(),menuDto.getFoodName());
		assertThrows(FoodNameAlreadyExistException.class, () -> menuServiceImpl.addMenu(menuDto));
	}
	
	@Test
	void getAllMenu_success() {
		List<Menu> allMenu = Arrays.asList(menu);
		when(menuRepository.findAll()).thenReturn(allMenu);
		List<MenuDto> allMenus = menuServiceImpl.getAllMenus();
		assertThat(allMenus).isNotNull();
	}
	
	@Test
	void getMenuByName_success() {
		when(menuRepository.findByName(anyString())).thenReturn(Optional.of(menu));
		MenuDto menuByName = menuServiceImpl.getMenuByName("Pizza");
		assertThat(menuByName).isNotNull();
	}
	
	@Test
	void getMenuByName_Failure() {
		when(menuRepository.findByName(anyString())).thenReturn(Optional.empty());
		assertFalse(Optional.empty().isPresent());
		assertThrows(ResourceNotFoundException.class, () -> menuServiceImpl.getMenuByName(anyString()));
	}
	
	@Test
	void getMenuById_success() {
		when(menuRepository.findById(anyString())).thenReturn(Optional.of(menu));
		MenuDto menuByName = menuServiceImpl.getMenuById("1");
		assertThat(menuByName).isNotNull();
	}
	
	@Test
	void getMenuById_Failure() {
		when(menuRepository.findById(anyString())).thenReturn(Optional.empty());
		assertFalse(Optional.empty().isPresent());
		assertThrows(ResourceNotFoundException.class, () -> menuServiceImpl.getMenuById(anyString()));
	}
	
	@Test
	void getMenyBySubsciptionCategory_success() {
		List<Menu> allMenu = Arrays.asList(menu);
		when(menuRepository.findBySubscriptionCategory(anyString())).thenReturn(allMenu);
		List<MenuDto> menuByName = menuServiceImpl.getMenyBySubsciptionCategory("GOLD");
		assertThat(menuByName).isNotNull();
	}
	
	@Test
	void updateMenu_success() {
		when(menuRepository.findById(anyString())).thenReturn(Optional.of(menu));
		when(menuRepository.save(menu)).thenReturn(menu);
		MenuDto updateDto = new MenuDto();
		updateDto.setFoodItemId("1");
		updateDto.setFoodName(menu.getFoodName().toLowerCase());
		updateDto.setFoodPicture("updated Pizza Picture");
		updateDto.setFoodAvailability(menu.isFoodAvailability());
		updateDto.setFoodDescription(menu.getFoodDescription());
		updateDto.setFoodCategory(menu.getFoodCategory());
		updateDto.setFoodSize(menu.getFoodSize());
		updateDto.setSubscriptionCategory(menu.getSubscriptionCategory());
		MenuDto updateMenu = menuServiceImpl.updateMenu("1", updateDto);
		assertEquals("updated Pizza Picture", updateMenu.getFoodPicture());
		
	}
	
	@Test
	void updateMenu_Failure() {
		when(menuRepository.findById(anyString())).thenReturn(Optional.empty());
		assertFalse(Optional.empty().isPresent());
		assertThrows(ResourceNotFoundException.class, () -> menuServiceImpl.updateMenu(anyString(),menuDto));
	}
	
	@Test
	void deleteMenu_success() {
		when(menuRepository.existsById(anyString())).thenReturn(Boolean.TRUE);
		menuServiceImpl.deleteMenuById("1");
		verify(menuRepository, times(1)).deleteById(anyString());
		
		
	}
	
}
