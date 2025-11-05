package com.neighbus.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(value="/")
	public String mainForm() {
		System.out.println("MainController - mainForm");
		return "main/main";
	}
}
