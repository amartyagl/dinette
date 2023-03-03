package com.menuservice.service;

import java.util.List;

import com.menuservice.dto.MenuDto;

public interface MenuService {
	
	MenuDto addMenu(MenuDto menuDto);
	List<MenuDto> getAllMenus();
	MenuDto updateMenu(String foodItemId, MenuDto menuDto);
	void deleteMenuById(String foodItemId);
	MenuDto getMenuByName(String foodName);
	MenuDto getMenuById(String foodItemId);
	List<MenuDto> getMenyBySubsciptionCategory(String category);
}
