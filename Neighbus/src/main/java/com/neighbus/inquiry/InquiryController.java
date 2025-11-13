package com.neighbus.inquiry;

import com.neighbus.Util;

import jakarta.servlet.http.HttpServletRequest;

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

        // 🚨 수정된 부분: Null 체크를 통해 NullPointerException 방지
        Object usernameObj = model.getAttribute("username");
        
        // 1. username 객체가 null이 아닌 경우에만 .toString() 호출
        if (usernameObj != null) {
            String username = usernameObj.toString();
            // (username을 사용하는 추가 로직이 있다면 여기에 작성)
        }
        
		// 템플릿 반환
		return "inquiry/inquiry";
	}
}