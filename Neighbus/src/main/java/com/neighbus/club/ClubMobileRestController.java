package com.neighbus.club;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.account.AccountDTO;
import com.neighbus.recruitment.RecruitmentMapper;
import com.neighbus.util.PagingDTO;

@RestController
@RequestMapping("/api/mobile/club")
public class ClubMobileRestController {
    @Autowired
    ClubService clubService;
    @Autowired
    RecruitmentMapper recruitmentMapper;

    @GetMapping("/checkName")
    public ResponseEntity<Map<String, Boolean>> checkClubName(@RequestParam("clubName") String clubName) {
        boolean isDuplicate = clubService.isClubNameDuplicate(clubName);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getClubs")
    public ResponseEntity<Map<String, Object>> getClubs(
    	ClubDTO clubDTO,
    	@AuthenticationPrincipal AccountDTO accountDTO
    ){
    	System.out.println("ClubMobileRestController - getClubs");
    	System.out.println(accountDTO);
    	
    	
    	
    	Map<String, Object> response = new HashMap<>();
    	PagingDTO<ClubDTO> clubs = clubService.getClubsWithPaging(clubDTO);
    	response.put("clubs", clubs.getList());
    	response.put("paging", clubs.getPagingMap());
    	return ResponseEntity.ok(response);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> getClubDetail(
            @PathVariable("id") int id,
            @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        ClubDetailDTO clubDetail = clubService.getClubDetail(id, accountDTO);

        if (clubDetail == null || clubDetail.getClub() == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                        Map.of(
                            "success", false,
                            "message", "클럽을 찾을 수 없습니다."
                        )
                    );
        }

        // ✅ 동아리 ID로 모임 조회
        var recruitments = recruitmentMapper.findRecruitmentsByClubId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("club", clubDetail.getClub());
        response.put("isLoggedIn", clubDetail.isLoggedIn());
        response.put("isMember", clubDetail.isMember());
        response.put("recruitments", recruitments); // ✅ 추가

        return ResponseEntity.ok(response);
    }

}
