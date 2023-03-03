package com.menuservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.menuservice.dto.MenuDto;
import com.menuservice.service.MenuService;

@RestController
@RequestMapping(value = "/v1/api")
@RefreshScope
public class MenuController {

	@Autowired
	MenuService menuService;

	/*
	 * This Method is to add the food items.
	 * @Request Body is used to map the json content to java object
	 */
	@PostMapping(value = "menu")
	public ResponseEntity<MenuDto> addMenu(@Valid @RequestBody MenuDto menuDto) {
		return new ResponseEntity<>(menuService.addMenu(menuDto), HttpStatus.ACCEPTED);
	}

	/*
	 * This Method is to get all the food item details.
	 */
	@GetMapping(value = "menu")
	public ResponseEntity<List<MenuDto>> getAllMenus() {
		return new ResponseEntity<>(menuService.getAllMenus(), HttpStatus.OK);
	}

	/*
	 * This Method is used to get the food item details By food item id.
	 * @PathVariable is used to fetch the content from the url
	 */
	@GetMapping(value = "menu/{foodItemId}")
	public ResponseEntity<MenuDto> getMenuById(@PathVariable String foodItemId) {
		return new ResponseEntity<>(menuService.getMenuById(foodItemId), HttpStatus.OK);
	}

	/*
	 * This Method is used to get the food item details by food name.
	 * @PathVariable is used to fetch the content from the url
	 */
	@GetMapping(value = "menuName/{name}")
	public ResponseEntity<MenuDto> getMenuByName(@PathVariable String name) {
		return new ResponseEntity<>(menuService.getMenuByName(name), HttpStatus.OK);
	}

	/*
	 * This Method is used to get the food item details by food category.
	 * @PathVariable is used to fetch the content from the url
	 */
	@GetMapping(value = "menuCategory/{category}")
	public ResponseEntity<List<MenuDto>> getMenuByCategory(@PathVariable String category) {
		return new ResponseEntity<>(menuService.getMenyBySubsciptionCategory(category), HttpStatus.OK);
	}

	/*
	 * This Method is used to update the food item details by food Id.
	 * @Request Body is used to map the json content to java object
	 * @PathVariable is used to fetch the content from the url
	 */
	@PutMapping(value = "menu/{foodItemId}")
	public ResponseEntity<MenuDto> updateMenuByID(@Valid @RequestBody MenuDto menuDto, @PathVariable String foodItemId) {
		return new ResponseEntity<>(menuService.updateMenu(foodItemId, menuDto), HttpStatus.CREATED);
	}

	/*
	 * This Method is used to delete the food item.
	 * @PathVariable is used to fetch the content from the url
	 */
	@DeleteMapping(value = "menu/{foodItemId}")
	public ResponseEntity<String> deleteMenuById(@PathVariable String foodItemId) {
		menuService.deleteMenuById(foodItemId);
		return new ResponseEntity<>("menu deleted successfully", HttpStatus.ACCEPTED);
	}

}
