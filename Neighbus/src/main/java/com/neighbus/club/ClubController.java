package com.neighbus.club;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/club")
public class ClubController {

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    @Autowired
    private ClubService clubService;

    @GetMapping("/")
    public String clubList(Model model) {
        List<ClubDTO> clubs = clubService.getAllClubs();
        model.addAttribute("clubs", clubs);
        return "club/clubList";
    }

    @GetMapping("/create")
    public String createClubForm() {
        return "club/createClub";
    }

    @PostMapping("/create")
    public String createClub(ClubDTO clubDTO,
    		@AuthenticationPrincipal AccountDTO accountDTO) {
        clubDTO.setCreator(accountDTO.getId()); // 세션에서 실제 사용자 ID를 가져와야 합니다.
        clubDTO.setCity(accountDTO.getCity()); // 사용자의 지역 정보를 가져와야 합니다.
        
        logger.info("Creating club: {}", clubDTO.getClubName());
        
        boolean success = clubService.createClubAndAddCreator(clubDTO);
        if (success) {
            logger.info("Club created successfully!");
            return "redirect:/club/";
        } else {
            logger.error("Failed to create club.");
            return "club/createClub";
        }
    }

    @GetMapping("/join")
    public String joinClubForm(Model model) {
        List<ClubDTO> clubs = clubService.getAllClubs();
        model.addAttribute("clubs", clubs);
        return "club/joinClub";
    }

    @PostMapping("/join")
    public String joinClub(@RequestParam("clubId") int clubId,
    		@AuthenticationPrincipal AccountDTO accountDTO) {
        ClubMemberDTO clubMemberDTO = new ClubMemberDTO();
        clubMemberDTO.setClubId(clubId);
        clubMemberDTO.setUserId(accountDTO.getId()); // 세션에서 실제 사용자 ID를 가져와야 합니다.
        
        logger.info("User {} joining club {}", clubMemberDTO.getUserId(), clubMemberDTO.getClubId());

        boolean success = clubService.joinClub(clubMemberDTO);
        if (success) {
            logger.info("Successfully joined club!");
            return "redirect:/club/";
        } else {
            logger.error("Failed to join club.");
            return "club/joinClub";
        }
    }
}