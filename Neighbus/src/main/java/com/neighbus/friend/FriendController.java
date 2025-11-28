package com.neighbus.friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping(value = "/friend")
public class FriendController {

	@Autowired
	FriendMapper friendMapper;
	@Autowired
	FriendService friendService;

	@GetMapping("/list")
	public String friendList(Model model, @AuthenticationPrincipal AccountDTO user) {
		// [추가] 내 UUID 가져와서 모델에 담기
	    String myUuid = friendMapper.getMyUuid(user.getId());
	    model.addAttribute("myUuid", myUuid);

	    // 기존 코드 (친구 목록 조회)
	    List<AccountDTO> friendList = friendMapper.getMyFriendList(user.getId());
	    model.addAttribute("friendList", friendList);
	    // 친구 요청 목록
	    List<AccountDTO> requestList = friendMapper.getFriendRequests(user.getId());
	    model.addAttribute("requestList", requestList);

		return "friend/friendlist";
	}

	// 친구 요청 (AJAX 통신용)
	@ResponseBody
	@PostMapping("/request")
	public int requestFriend(@AuthenticationPrincipal AccountDTO user, @RequestParam("uuid") String uuid) {
		// 서비스의 friendRequest 메서드 호출 (결과값 정수 반환)
		return friendService.friendRequest(user, uuid);
	}
	
	// 2. 메서드 추가 (수락/거절 기능)
	@ResponseBody
	@PostMapping("/accept")
	public int acceptFriend(@AuthenticationPrincipal AccountDTO user, @RequestParam("friendId") int friendId) {
	    return friendService.addFriend(user, friendId);
	}

	@ResponseBody
	@PostMapping("/refuse")
	public int refuseFriend(@AuthenticationPrincipal AccountDTO user, @RequestParam("friendId") int friendId) {
	    return friendService.refuseFriend(user, friendId);
	}
}