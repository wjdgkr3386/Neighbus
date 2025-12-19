package com.neighbus.club;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.account.AccountDTO;
import com.neighbus.util.PagingDTO;

@RestController
@RequestMapping("/api/mobile/club")
public class ClubMobileRestController {
    @Autowired
    private ClubService clubService;

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
}
