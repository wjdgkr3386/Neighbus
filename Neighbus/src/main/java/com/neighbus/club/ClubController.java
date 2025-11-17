package com.neighbus.club;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/club")
public class ClubController {

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    @Autowired
    private ClubService clubService;

    @GetMapping(value = {"/",""})
    public String clubList(Model model) {
        List<ClubDTO> clubs = clubService.getAllClubs();
        model.addAttribute("clubs", clubs);
        return "club/clubList";
    }

    @GetMapping("/create")
    public String createClubForm(Model model) {
    	ClubDTO clubDTO = new ClubDTO();
    	model.addAttribute("clubForm",clubDTO);
        return "club/createClub";
    }
    @GetMapping("/{id}")
    public String viewDetail(@PathVariable("id") int id, Model model) {
    	// 1. 서비스에서 ID로 ClubDTO를 조회합니다.
        ClubDTO club = clubService.getClubById(id);

        // 2. (필수) 조회된 club이 null인지 확인합니다.
        if (club == null) {
            // 3. null이면, 해당 ID의 동아리가 없다는 뜻이므로
            //    상세 페이지로 가지 않고 목록(list) 페이지로 리다이렉트합니다.
            return "redirect:/club"; // (목록 페이지 URL로 변경하세요)
        }

        // 4. null이 아닐 때만 모델에 담아서 상세 페이지로 보냅니다.
        model.addAttribute("club", club);

        return "club/clubDetail";
    }

    @PostMapping("/create")
    public String createClub(
            @ModelAttribute("clubForm") ClubDTO club, // (수정) th:object 이름과 맞춤
            @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        
        // 1. 폼(ClubFormDTO)의 데이터를 서비스용(ClubDTO) 객체로 변환합니다.
        ClubDTO clubToCreate = new ClubDTO();
        clubToCreate.setClubName(club.getClubName()); // 폼에서 입력한 동아리 이름
        clubToCreate.setClubInfo(club.getClubInfo()); // 폼에서 입력한 동아리 소개
        

        // 2. 폼에 없는 'creator' 정보를 세션(accountDTO)에서 가져와 설정합니다.
        clubToCreate.setCreator(accountDTO.getId());
        clubToCreate.setCity(accountDTO.getCity());


        logger.info("Creating club: {}", clubToCreate.getClubName());
        
        // 3. 완성된 ClubDTO를 서비스로 전달합니다.
        boolean success = clubService.createClubAndAddCreator(clubToCreate);
        
        if (success) {
            logger.info("Club created successfully!");
            // 4. 성공 시 목록 페이지로 리다이렉트
            return "redirect:/club/"; 
        } else {
            logger.error("Failed to create club.");
            // 5. 실패 시, 폼 데이터를 유지한 채로 생성 페이지를 다시 보여줍니다.
            // (참고: @ModelAttribute를 쓰면 실패 시 'form' 객체가 자동으로 다시 모델에 담깁니다)
            return "club/createClub";
        }
    }

//    @GetMapping("/join/{id}")
//    public String joinClubForm(Model model) {
//        List<ClubDTO> clubs = clubService.getAllClubs();
//        model.addAttribute("clubs", clubs);
//        return "club/joinClub";
//    }

    @PostMapping("/join/{id}")
    public String joinClub(
            @PathVariable("id") int clubId,
            @AuthenticationPrincipal AccountDTO accountDTO,
            RedirectAttributes redirectAttributes // 2. RedirectAttributes 파라미터 추가
    ) {
        
        ClubMemberDTO clubMemberDTO = new ClubMemberDTO();
        clubMemberDTO.setClubId(clubId);
        clubMemberDTO.setUserId(accountDTO.getId());
        
        logger.info("User {} joining club {}", clubMemberDTO.getUserId(), clubMemberDTO.getClubId());

        boolean success = clubService.joinClub(clubMemberDTO);
        
        if (success) {
            logger.info("Successfully joined club!");
            
            // 3. (성공) Flash Attribute에 성공 메시지 추가
            redirectAttributes.addFlashAttribute("successMessage", "동아리 가입이 완료되었습니다.");
            
        } else {
            logger.error("Failed to join club.");
            
            // 4. (실패) Flash Attribute에 실패 메시지 추가 (예: 이미 가입한 경우)
            redirectAttributes.addFlashAttribute("errorMessage", "가입에 실패했습니다. 이미 가입한 동아리입니다.");
        }
        
        // 5. URL 파라미터 없이 깔끔하게 상세 페이지로 리다이렉트
        return "redirect:/club/" + clubId;
    }
}