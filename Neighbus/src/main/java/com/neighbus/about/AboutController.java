package com.neighbus.about;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/about")
public class AboutController {

    // application.properties에서 카카오맵 JavaScript 키를 주입받습니다.
    @Value("${kakao.javascript.appkey}")
    private String kakaoApiKey;
    
	@GetMapping(value={"/",""})
	public String aboutForm(Model model) {
		System.out.println("AboutController - aboutForm");

        // 카카오맵 API 키를 모델에 추가하여 Thymeleaf로 전달합니다.
        model.addAttribute("kakaoApiKey", kakaoApiKey);

		return "about/About";
	}
}