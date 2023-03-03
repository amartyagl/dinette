package com.productwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductWebappController {

	@GetMapping("/")
	public String webapp() {
		return "index.html";
	}

}
