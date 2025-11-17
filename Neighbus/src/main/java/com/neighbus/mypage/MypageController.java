package com.neighbus.mypage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MyPageService myPageService; // 서비스 주입
	@Autowired
	private MyPageMapper myPageMapper; // 서비스 주입

	/**
	 * 마이페이지 메인 화면을 표시합니다.
	 * 세션에서 로그인 사용자 정보를 가져옵니다.
	 */
	@GetMapping({"", "/"})
	public String mypageForm(
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
		
		  // 2. 내가 쓴 게시글
		model.addAttribute("myPosts", myPageService.getMyPosts(username));
		
		// 3. 내가 쓴 댓글
		model.addAttribute("myComments", myPageService.getMyComments(username));
		
		 // 4. 좋아요 수 (Mapper에서 SELECT 0으로 임시 수정된 상태를 가정합니다.)
		 model.addAttribute("myLikes", myPageService.getMyLikes(username));
		 
		 model.addAttribute("friendState", myPageMapper.getFriendState(loginUser.getId()));
		 
		return "mypage/mypage"; // mypage.jsp or mypage.html
	}
	
	@PostMapping(value="addFriend")
	public String addFriend(
		@AuthenticationPrincipal AccountDTO loginUser,
		@RequestParam("friendCode") String friendCode,
		RedirectAttributes ra
	) {
		if(loginUser.getUserUuid().equals(friendCode)) {
			//나 자신일경우
			ra.addFlashAttribute("errorMessage", "친구 요청 처리 중 오류가 발생했습니다.");
			return "redirect:/mypage";
		}
		
		int result = myPageService.addFriend(loginUser.getId(), friendCode);

		if (result == -1) {
			// -1: 유저 없음 (없는 유저)
			ra.addFlashAttribute("errorMessage", "해당 친구 코드를 가진 유저가 존재하지 않습니다.");
		} else if (result == 1) {
			// 1: 요청 성공
			ra.addFlashAttribute("successMessage", "친구 요청에 성공했습니다.");
		} else {
			// 기타 오류 (0: 이미 친구, -2: 자기 자신, 등)
			ra.addFlashAttribute("errorMessage", "친구 요청 처리 중 오류가 발생했습니다.");
		}
		return "redirect:/mypage";
	}
	
	@PostMapping(value="/handleFriendRequest")
	public String handleFriendRequest(
		@AuthenticationPrincipal AccountDTO loginUser,
		@RequestParam("action") int action, //1:수락 , 2:거절
		@RequestParam("sender") int sender
	) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", loginUser.getId());
		map.put("sender", sender);
		if(action==1) {
			myPageService.friendAccept(map);
		}else if(action==2) {
			myPageService.friendReject(map);
		}
		
		return "redirect:/mypage";
	}
}