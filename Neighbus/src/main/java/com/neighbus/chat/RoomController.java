package com.neighbus.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neighbus.account.AccountDTO;
import com.neighbus.recruitment.RecruitmentDTO;
import com.neighbus.recruitment.RecruitmentService;

@Controller
@RequestMapping("/chat")
public class RoomController {

    private final ChatMapper chatMapper;
    private final RecruitmentService recruitmentService;

    @Autowired
    public RoomController(ChatMapper chatMapper, RecruitmentService recruitmentService) {
        this.chatMapper = chatMapper;
        this.recruitmentService = recruitmentService;
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
    public ChatRoomDTO createRoom(@RequestParam("roomId") String roomId, // 프론트에서 보낸 recruitId 받기
                                  @RequestParam("name") String name) {

        // 중복 체크
        ChatRoomDTO existingRoom = chatMapper.findByRoomId(roomId);
        if (existingRoom != null) {
            return existingRoom; // 이미 방이 존재하면 해당 방 정보를 반환
        }
        
        // 2. 방 생성
        ChatRoomDTO newRoom = new ChatRoomDTO();
        
        // ★ 수정된 부분: UUID 대신 받아온 roomId(모집글번호)를 그대로 사용
        newRoom.setRoomId(roomId); 
        
        newRoom.setRoomName(name);
        
        chatMapper.insertRoom(newRoom);
        return newRoom;
    }

    // 4. 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable("roomId") String roomId, 
            @AuthenticationPrincipal AccountDTO accountDTO) {
        
        if (accountDTO == null) {
            // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/account/login";
        }
        
        int recruitmentId = Integer.parseInt(roomId);
        RecruitmentDTO recruitment = recruitmentService.findById(recruitmentId);
        
        if (recruitment == null) {
            // 모집글이 없는 경우
            return "redirect:/"; // 혹은 에러 페이지
        }
        
        // 해당 모집에 가입했는지 확인
        boolean isMember = recruitmentService.isMember(recruitmentId, accountDTO.getId());
        
        if (!isMember) {
            // 가입하지 않은 경우
            model.addAttribute("message", "가입한 모임의 채팅방에만 입장할 수 있습니다.");
            model.addAttribute("searchUrl", "/");
            return "admin/message"; 
        }
        // 과거 채팅 내역 가져오기
        List<ChatMessageDTO> messageHistory = chatMapper.findMessagesByRoomId(roomId);
        model.addAttribute("messageHistory", messageHistory); // 모델에 담기

        model.addAttribute("roomId", roomId);
        model.addAttribute("recruitment", recruitment);
        model.addAttribute("user", accountDTO.getUsername());
        
        return "chat/roomdetail";
    }
}
