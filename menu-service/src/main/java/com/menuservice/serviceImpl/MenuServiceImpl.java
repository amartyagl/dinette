package com.menuservice.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.menuservice.dto.MenuDto;
import com.menuservice.exception.FoodNameAlreadyExistException;
import com.menuservice.exception.ResourceNotFoundException;
import com.menuservice.model.Menu;
import com.menuservice.repository.MenuRepository;
import com.menuservice.service.MenuService;
import com.mongodb.MongoException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;


	/*
	 * This Method is to add the food items.
	 */
	@Override
	public MenuDto addMenu(MenuDto menuDto) {
		Menu menu1=new Menu();
		log.info("add menu Request {}", menuDto);
		List<Menu> allMenu = menuRepository.findAll();
		Iterator<Menu> iterator = allMenu.iterator();
		while (iterator.hasNext()) {
			Menu iteratingMenu = iterator.next();
			if (iteratingMenu.getFoodName().equalsIgnoreCase(menuDto.getFoodName()))
				throw new FoodNameAlreadyExistException("SERVICE.FOOD_NAME_ALREADY_EXIST");
		}
		menu1.setFoodName(menuDto.getFoodName().toLowerCase());
		menu1.setFoodPicture(menuDto.getFoodPicture());
		menu1.setFoodAvailability(menuDto.isFoodAvailability());
		menu1.setFoodDescription(menuDto.getFoodDescription());
		menu1.setFoodCategory(menuDto.getFoodCategory());
		menu1.setFoodSize(menuDto.getFoodSize());
		menu1.setSubscriptionCategory(menuDto.getSubscriptionCategory());
		try {
			Menu savedMenu = menuRepository.save(menu1);
			menuDto.setFoodItemId(savedMenu.getFoodItemId());
			log.info("add menu Response {}", menuDto);
		} catch (Exception e) {
			log.error("Error while adding to Menu {}", e.getMessage());
		}
		return menuDto;
	}

	/*
	 * This Method is to get all the food item details.
	 */
	@Override
	public List<MenuDto> getAllMenus() {
		log.info("Request for get all menu items");
		List<Menu> allMenus = menuRepository.findAll();
		List<MenuDto> allProductsDto = new ArrayList<>();
		for (Menu menuFromRepo : allMenus) {
			MenuDto allMenuDto = new MenuDto();
			allMenuDto.setFoodItemId(menuFromRepo.getFoodItemId());
			allMenuDto.setFoodName(menuFromRepo.getFoodName());
			allMenuDto.setFoodPicture(menuFromRepo.getFoodPicture());
			allMenuDto.setFoodAvailability(menuFromRepo.isFoodAvailability());
			allMenuDto.setFoodDescription(menuFromRepo.getFoodDescription());
			allMenuDto.setFoodCategory(menuFromRepo.getFoodCategory());
			allMenuDto.setFoodSize(menuFromRepo.getFoodSize());
			allMenuDto.setSubscriptionCategory(menuFromRepo.getSubscriptionCategory());
			allProductsDto.add(allMenuDto);
		}
		log.info("get all menu Response {}", allProductsDto);
		return allProductsDto;
	}

	/*
	 * This Method is used to get the food item details By food Name.
	 */
	@Override
	public MenuDto getMenuByName(String foodName) {
		log.info("Request for Get menu item  By Name {}", foodName);
		foodName = foodName.toLowerCase();
		Optional<Menu> findByName = menuRepository.findByName(foodName.toLowerCase());
		MenuDto menuDto = new MenuDto();
		if (findByName.isPresent()) {
			Menu menuByName = findByName.get();
			menuDto.setFoodItemId(menuByName.getFoodItemId());
			menuDto.setFoodName(menuByName.getFoodName());
			menuDto.setFoodPicture(menuByName.getFoodPicture());
			menuDto.setFoodAvailability(menuByName.isFoodAvailability());
			menuDto.setFoodDescription(menuByName.getFoodDescription());
			menuDto.setFoodCategory(menuByName.getFoodCategory());
			menuDto.setFoodSize(menuByName.getFoodSize());
			menuDto.setSubscriptionCategory(menuByName.getSubscriptionCategory());
			log.info("get menu by name Response {}", menuDto);
			return menuDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.MENU_NOT_FOUND_BY_NAME ");
		}
	}

	/*
	 * This Method is used to get the food item details By food item id.
	 */
	@Override
	public MenuDto getMenuById(String foodItemId) {
		log.info("Request for Get menu item  By ID{}", foodItemId);
		Optional<Menu> findById = menuRepository.findById(foodItemId);
		MenuDto menuDto = new MenuDto();
		if (findById.isPresent()) {
			Menu menuById = findById.get();
			menuDto.setFoodItemId(menuById.getFoodItemId());
			menuDto.setFoodName(menuById.getFoodName());
			menuDto.setFoodPicture(menuById.getFoodPicture());
			menuDto.setFoodAvailability(menuById.isFoodAvailability());
			menuDto.setFoodDescription(menuById.getFoodDescription());
			menuDto.setFoodCategory(menuById.getFoodCategory());
			menuDto.setFoodSize(menuById.getFoodSize());
			menuDto.setSubscriptionCategory(menuById.getSubscriptionCategory());
			log.info("get menu by id Response {}", menuDto);
			return menuDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.MENU_NOT_FOUND");
		}

	}

	/*
	 * This Method is used to update the food item details by food Id.
	 */
	@Override
	public MenuDto updateMenu(String foodItemId, MenuDto menuDto) {
		log.info("Request for update menu item {}", foodItemId);
		Optional<Menu> findById = menuRepository.findById(foodItemId);
		if (findById.isPresent()) {
			Menu updateProduct = findById.get();
			updateProduct.setFoodName(menuDto.getFoodName().toLowerCase());
			updateProduct.setFoodPicture(menuDto.getFoodPicture());
			updateProduct.setFoodAvailability(menuDto.isFoodAvailability());
			updateProduct.setFoodDescription(menuDto.getFoodDescription());
			updateProduct.setFoodCategory(menuDto.getFoodCategory());
			updateProduct.setFoodSize(menuDto.getFoodSize());
			updateProduct.setSubscriptionCategory(menuDto.getSubscriptionCategory());
			try {
				menuRepository.save(updateProduct);
				log.info("Response {}", menuDto);
			} catch (MongoException e) {
				log.error("Error while Updating Menu {}", e.getMessage());
			}
			return menuDto;
		} else {
			throw new ResourceNotFoundException("SERVICE.MENU_NOT_FOUND");
		}
	}

	/*
	 * This Method is used to delete the food item.
	 */
	@Override
	public void deleteMenuById(String foodItemId) {
		log.info("Request for Delete menu item {}", foodItemId);
		if (menuRepository.existsById(foodItemId)) {
			try {
				menuRepository.deleteById(foodItemId);
				log.info("Menu item deleted");
			} catch (MongoException e) {
				log.error("Error while deleting Item {}", e.getMessage());
			}
		} else {
			throw new ResourceNotFoundException("SERVICE.MENU_NOT_FOUND");
		}

	}

	/*
	 * This Method is used to get the food item details by food category.
	 */
	@Override
	public List<MenuDto> getMenyBySubsciptionCategory(String category) {
		log.info("Request for Get menu item  By Category{}", category);
		category = category.toUpperCase();
		List<Menu> findBySubscriptionCategory = menuRepository.findBySubscriptionCategory(category);
		MenuDto menuDto = new MenuDto();
		List<MenuDto> allProductsDto = new ArrayList<>();
		for (Menu menuBySubCategory : findBySubscriptionCategory) {
			menuDto.setFoodItemId(menuBySubCategory.getFoodItemId());
			menuDto.setFoodName(menuBySubCategory.getFoodName());
			menuDto.setFoodPicture(menuBySubCategory.getFoodPicture());
			menuDto.setFoodAvailability(menuBySubCategory.isFoodAvailability());
			menuDto.setFoodDescription(menuBySubCategory.getFoodDescription());
			menuDto.setFoodCategory(menuBySubCategory.getFoodCategory());
			menuDto.setFoodSize(menuBySubCategory.getFoodSize());
			menuDto.setSubscriptionCategory(menuBySubCategory.getSubscriptionCategory());
			allProductsDto.add(menuDto);
		}
		log.info("Response {}", allProductsDto);
		return allProductsDto;
	}

}
