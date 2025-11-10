package com.neighbus.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	private MyPageService myPageService;  // 서비스 주입

	// 마이페이지 메인
	@GetMapping({"", "/"})
	public String mypageForm(@SessionAttribute(name = "loginUser", required = false) AccountDTO loginUser, Model model) {
		System.out.println("MyPageController - mypageForm");
		
		// 세션에 로그인 정보가 없을 경우 로그인 페이지로 리다이렉트
		if (loginUser == null) {
			return "redirect:/account/login";
		}
		
		String username = loginUser.getUsername();
		
		// 내 정보 불러오기
		AccountDTO myInfo = myPageService.getMyPageInfo(username);
		model.addAttribute("myInfo", myInfo);
		
		// 내가 쓴 게시글
		List<Map<String, Object>> myPosts = myPageService.getMyPosts(username);
		model.addAttribute("myPosts", myPosts);
		
		// 내가 쓴 댓글
		List<Map<String, Object>> myComments = myPageService.getMyComments(username);
		model.addAttribute("myComments", myComments);
		
		// 좋아요 수
		int myLikes = myPageService.getMyLikes(username);
		model.addAttribute("myLikes", myLikes);
		
		return "mypage/mypage"; // mypage.jsp or mypage.html
	}
}
