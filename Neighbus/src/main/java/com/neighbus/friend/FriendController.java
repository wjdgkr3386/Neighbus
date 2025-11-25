package com.neighbus.friend;

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
	
	@GetMapping("/list")
	public String friendList(
		Model model,
		@AuthenticationPrincipal AccountDTO user
	) {
		String myUUID = friendMapper.getMyUuid(user.getId());
		int cnt = friendMapper.checkUuid("35bc20fb-682a-4cb3-84bc-a94a5b83921a");
		
		System.out.println(myUUID);
		System.out.println(cnt);
		
		
		model.addAttribute("myUUID", myUUID);
		
		return "friend/friendlist";
	}
}
