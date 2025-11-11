package com.neighbus.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/board") // 게시판 관련 요청은 /board/write 등으로 처리하는 것이 일반적입니다.
public class PostController {
	
	// 글쓰기 폼을 보여줍니다.
	@GetMapping("/write") // URL 경로를 /board/write로 변경하는 것을 권장합니다.
	public String postForm(
	    @SessionAttribute(name = "loginUser", required = false) AccountDTO loginUser
	) {
		System.out.println("PostController - postForm");
		
		// 1. 로그인 체크: 로그인 정보가 없으면 로그인 페이지로 리다이렉트
		if (loginUser == null) {
			System.out.println("로그인 정보 없음. 로그인 페이지로 리다이렉트");
			return "redirect:/account/login";
		}
		
		// 2. 로그인되어 있다면, 템플릿 반환
		// 반환 경로를 "post/postForm"으로 수정합니다.
		// (templates/post/postForm.html을 찾게 됩니다.)
		return "post/postForm";
	}
	
}