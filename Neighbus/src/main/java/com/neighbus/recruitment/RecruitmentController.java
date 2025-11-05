package com.neighbus.recruitment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/recruitment")
public class RecruitmentController {

	@GetMapping(value={"/",""})
	public String recruitmentForm() {
		System.out.println("RecruitmentController - recruitmentForm");
		return "recruitment/recruitment";
	}
}
