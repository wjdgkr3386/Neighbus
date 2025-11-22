package com.neighbus.club;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;
import com.neighbus.recruitment.RecruitmentService;
import com.neighbus.util.FileService;
import com.neighbus.util.PagingDTO;

@Controller
@RequestMapping("/club")
public class ClubController {

	private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

	private final ClubService clubService;
	private final RecruitmentService recruitmentService;
	private final com.neighbus.util.FileService fileService;

	public ClubController(ClubService clubService, RecruitmentService recruitmentService, FileService fileService) {
		this.clubService = clubService;
		this.recruitmentService = recruitmentService;
		this.fileService = fileService;
	}

	@GetMapping(value = { "/", "" })
	public String clubList(Model model, ClubDTO clubDTO,
			@RequestParam(value = "keyword", required = false) String keyword) {
		try {
			clubDTO.setKeyword(keyword);
			PagingDTO<ClubDTO> result = clubService.getClubsWithPaging(clubDTO);
			

			model.addAttribute("clubs", result.getList());
			model.addAttribute("pagingMap", result.getPagingMap());
			model.addAttribute("keyword", keyword);
		} catch(Exception e) {
			System.out.println(e);
		}
		return "club/clubList";
	}

	@GetMapping("/create")
	public String createClubForm(Model model) {
		ClubDTO clubDTO = new ClubDTO();
		model.addAttribute("clubForm", clubDTO);
		// DB에서 대한민국 지역 가져오기
		List<Map<String, Object>> provinceList = clubService.getProvince();
		List<Map<String, Object>> regionList = clubService.getCity();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		return "club/createClub";
	}

		@GetMapping("/{id}")
		public String viewDetail(@PathVariable("id") int id,
				Model model,
				@AuthenticationPrincipal AccountDTO accountDTO) {
	
			ClubDetailDTO clubDetail = clubService.getClubDetail(id, accountDTO);
	
			if (clubDetail == null || clubDetail.getClub() == null) {
				return "redirect:/club";
			}
	
			model.addAttribute("club", clubDetail.getClub());
			model.addAttribute("isLoggedIn", clubDetail.isLoggedIn());
			model.addAttribute("isMember", clubDetail.isMember());
	
			return "club/clubPage";
		}
	@PostMapping("/create")
	public String createClub(@ModelAttribute("clubForm") ClubDTO club, // 1. 폼 데이터만 받습니다.
			@AuthenticationPrincipal AccountDTO accountDTO) {

		// 1. 폼의 데이터를 서비스용 객체로 변환합니다.
		ClubDTO clubToCreate = new ClubDTO();
		clubToCreate.setClubName(club.getClubName()); // 폼에서 입력한 동아리 이름
		clubToCreate.setClubInfo(club.getClubInfo()); // 폼에서 입력한 동아리 소개
		clubToCreate.setCity(club.getCity());
		clubToCreate.setProvinceId(club.getProvinceId());
		clubToCreate.setClubId(accountDTO.getId()); // 생성자 ID 설정

		// 2. 이미지 파일 처리
		String savedFilename = fileService.saveFile(club.getClubImage(), "club");
		if (savedFilename != null) {
			clubToCreate.setClubImageName(savedFilename);
		}

		logger.info("Creating club: {}", clubToCreate.getClubName());
		clubService.createClubAndAddCreator(clubToCreate);
		logger.info("Club created successfully!");
		return "redirect:/club/";

	}

// 동아리 가입
	@PostMapping("/join/{id}")
	public String joinClub(@PathVariable("id") int clubId,
			@AuthenticationPrincipal AccountDTO accountDTO,
			RedirectAttributes redirectAttributes) {

		ClubMemberDTO clubMemberDTO = new ClubMemberDTO();
		clubMemberDTO.setClubId(clubId);
		clubMemberDTO.setUserId(accountDTO.getId());

		logger.info("User {} joining club {}", clubMemberDTO.getUserId(), clubMemberDTO.getClubId());

		boolean success = clubService.joinClub(clubMemberDTO);

		if (success) {
			logger.info("Successfully joined club!");
			redirectAttributes.addFlashAttribute("successMessage", "동아리 가입이 완료되었습니다.");
		} else {
			logger.error("Failed to join club.");
			redirectAttributes.addFlashAttribute("errorMessage", "가입에 실패했습니다. 이미 가입한 동아리입니다.");
		}

		return "redirect:/club/" + clubId;
	}

	// 탈퇴 처리
	@PostMapping("/withdraw/{clubId}")
	public String withdrawFromClub(@PathVariable("clubId") Long clubId,
			@AuthenticationPrincipal AccountDTO accountDTO) {

		// 1. DTO에서 int 타입으로 ID를 가져옴
		int userId = accountDTO.getId();

		// 2. int를 Long 타입으로 명시적으로 변환 (★이 부분이 수정됨★)
		Long longUserId = Long.valueOf(userId);
		clubService.deleteClubMember(clubId, longUserId);

		return "redirect:/club/";

	}

	// 필터링
	@PostMapping("/filter-clubs")
	public String filterClubs(ClubDTO clubDTO, Model model) {
		List<ClubDTO> clubFilter = clubService.getFilteredClubs(clubDTO);
		model.addAttribute("clubs", clubFilter);
		return "club/oder :: #clubListFragment";
	}

	// clubPage 이동
	@GetMapping("/myClubPage")
	public String myClubPage(@AuthenticationPrincipal AccountDTO accountDTO,Model model) {
		if (accountDTO == null) {
            return "redirect:/account/login";
        }

        List<ClubDTO> myClubs = clubService.getMyClubs(accountDTO.getId());

        model.addAttribute("myClubs", myClubs);
		return "club/myclubPage";
	}

}