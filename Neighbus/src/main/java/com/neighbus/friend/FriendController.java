package com.neighbus.friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.neighbus.chat.ChatMapper;
import com.neighbus.chat.ChatMessageDTO;
import com.neighbus.chat.ChatRoomDTO;

@Controller
@RequestMapping(value = "/friend")
public class FriendController {

	@Autowired
	FriendMapper friendMapper;
	@Autowired
	FriendService friendService;
	@Autowired
	ChatMapper chatMapper;
	

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
	@ResponseBody
    @PostMapping("/chat/room")
    public Map<String, Object> getOrCreateChatRoom(
            @AuthenticationPrincipal AccountDTO user,
            @RequestParam("friendId") int friendId
    ) {
        Map<String, Object> result = new HashMap<>();
        
        int myId = user.getId();
        
        // 1. 방 ID 생성 규칙: "friend_작은ID_큰ID"
        // 이렇게 하면 누가 요청하든 항상 같은 방 ID가 나옵니다.
        int minId = Math.min(myId, friendId);
        int maxId = Math.max(myId, friendId);
        String roomId = minId +""+ maxId;
        
        // 2. 방이 DB에 있는지 확인
        ChatRoomDTO room = chatMapper.findByRoomId(roomId);
        
        // 3. 없으면 방 생성 (최초 1회)
        if (room == null) {
            ChatRoomDTO newRoom = new ChatRoomDTO();
            newRoom.setRoomId(roomId);
            newRoom.setRoomName("1:1 Chat"); // 이름은 크게 중요하지 않음
            chatMapper.insertRoom(newRoom);
        }
        
        // 4. 기존 대화 내역 가져오기
        List<ChatMessageDTO> history = chatMapper.findMessagesByRoomId(roomId);
        
        result.put("roomId", roomId);
        result.put("history", history);
        result.put("myId", user.getUsername()); // 메시지 보낼 때 내 아이디(또는 이름) 필요
        
        return result;
    }
}