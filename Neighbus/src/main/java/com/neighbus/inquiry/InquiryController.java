package com.neighbus.inquiry;

import javax.servlet.http.HttpServletRequest;
import com.neighbus.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InquiryController {

	// GET 요청이 오면 inquiry/inquiry.html을 반환
	@GetMapping("/inquiry")
	public String inquiry(
			HttpServletRequest req,
			Model model
			) {
		Util.getCookie(req, model);
	String username = model.getAttribute("username").toString();
		return "inquiry/inquiry";
	}
}