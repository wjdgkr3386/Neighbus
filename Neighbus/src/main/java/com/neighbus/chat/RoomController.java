package com.neighbus.chat;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.neighbus.account.AccountDTO;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/chat")
public class RoomController {

    private final ChatMapper chatMapper;

    public RoomController(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    // 1. 채팅방 목록 조회 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "chat/room"; // templates/chat/room.html 을 엽니다.
    }

    // 2. 모든 채팅방 목록 반환 (API)
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDTO> room() {
        return chatMapper.findAllRooms();
    }

    // 3. 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoomDTO createRoom(@RequestParam("name") String name) {
        ChatRoomDTO room = new ChatRoomDTO();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(name);
        
        chatMapper.insertRoom(room);
        return room;
    }

    // 4. 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable("roomId") String roomId, @AuthenticationPrincipal AccountDTO accountDTO) {
        model.addAttribute("roomId", roomId);
        String userName = accountDTO.getUsername();
        model.addAttribute("user",userName);
        return "chat/roomdetail"; // templates/chat/roomdetail.html 을 엽니다.
    }
}
