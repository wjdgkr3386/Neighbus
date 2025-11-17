package com.neighbus.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	
	@Autowired
	private MyPageService myPageService; // 서비스 주입

	/**
	 * 마이페이지 메인 화면을 표시합니다.
	 * 세션에서 로그인 사용자 정보를 가져옵니다.
	 */
	@GetMapping({"", "/"})
	public String mypageForm(
	    // @SessionAttribute만 사용해도 로그인 정보를 가져올 수 있습니다.
			@AuthenticationPrincipal AccountDTO loginUser, 
	    Model model
	) {
		System.out.println("MyPageController - mypageForm");
		
		// 세션에 로그인 정보가 없을 경우 로그인 페이지로 리다이렉트
		if (loginUser == null) {
			return "redirect:/account/login";
		}
		
		String username = loginUser.getUsername();
		
		// 1. 내 정보 불러오기
		// DTO에 regionName, provinceName 필드가 추가되었다면 지역 이름도 함께 가져옵니다.
		Map<String, Object> myInfo = myPageService.getMyPageInfo(username);
		model.addAttribute("myInfo", myInfo);
		System.out.println(myInfo);
		
		
		  // 2. 내가 쓴 게시글
		model.addAttribute("myPosts", myPageService.getMyPosts(username));
		
		// 3. 내가 쓴 댓글
		model.addAttribute("myComments", myPageService.getMyComments(username));
		
		
		 // 4. 좋아요 수 (Mapper에서 SELECT 0으로 임시 수정된 상태를 가정합니다.)
		 model.addAttribute("myLikes", myPageService.getMyLikes(username));
		 
		 
		
		return "mypage/mypage"; // mypage.jsp or mypage.html
	}
}