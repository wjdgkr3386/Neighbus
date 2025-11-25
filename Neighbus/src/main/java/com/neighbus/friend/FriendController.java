package com.neighbus.friend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping(value="/friend")
public class FriendController {

	@Autowired
	FriendMapper friendMapper;
	@Autowired
	FriendService friendService;
	
	@GetMapping("/list")
	public String friendList(
		Model model,
		@AuthenticationPrincipal AccountDTO user
	) {
		String friendUuid = "35bc20fb-682a-4cb3-84bc-a94a5b83921a";
		//친구 요청
		int status = friendService.friendRequest(user, friendUuid);
		model.addAttribute("status", status);
//		//친구 수락
//		int status2 = friendService.addFriend(user, friendId);
//		model.addAttribute("status2", status2);
		
//		int status3 = friendService.refuseFriend(user, friendId);
//		model.addAttribute("status3", status3);
		
//		int status4 = friendService.deleteFriend(user, friendId);
//		model.addAttribute("status4", status4);
		
		
		
		return "friend/friendlist";
	}
}
