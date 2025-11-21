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
    @Autowired
    private RecruitmentService recruitmentService;

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
    public ChatRoomDTO createRoom(@RequestParam("roomId") String roomId, // 프론트에서 보낸 recruitId 받기
                                  @RequestParam("name") String name) {
        
        // 1. 이미 방이 있는지 확인 (중복 생성 방지) 추후 기능
//        ChatRoomDTO existingRoom = chatMapper.findRoomById(roomId);
//        if (existingRoom != null) {
//            return existingRoom;
//        }

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
        
        model.addAttribute("roomId", roomId);
        
        // 2. String -> int 변환 및 DB 조회
        int id = Integer.parseInt(roomId);
        RecruitmentDTO recruitment = recruitmentService.findById(id); // 소문자(인스턴스)로 호출
        
        // 3. 모집글 정보는 로그인 여부와 상관없이 모델에 담아야 함 (제목 표시용)
        model.addAttribute("recruitment", recruitment); 

        if (accountDTO != null) {
            model.addAttribute("user", accountDTO.getUsername());
        } else {
            // 비로그인 사용자 처리
            model.addAttribute("user", "익명" + (int)(Math.random()*1000));
        }
        
        return "chat/roomdetail";
    }
}
