package com.neighbus.club;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.account.AccountDTO;
import com.neighbus.recruitment.RecruitmentService;
import com.neighbus.util.PagingDTO;

@Controller
@RequestMapping("/club")
public class ClubController {

	private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

	@Autowired
	ClubService clubService;
	@Autowired
	ClubMapper clubMapper;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	com.neighbus.util.FileService fileService;

	@GetMapping(value = { "/", "" })
	public String clubList(Model model, ClubDTO clubDTO,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		try {
			// 1. 필터링 조건이 포함된 clubDTO를 사용하여 페이징된 클럽 목록을 가져옵니다.
			PagingDTO<ClubDTO> result = clubService.getClubsWithPaging(clubDTO);
			
			model.addAttribute("clubs", result.getList());
			model.addAttribute("pagingMap", result.getPagingMap());
			model.addAttribute("keyword", clubDTO.getKeyword());
			model.addAttribute("provinceId", clubDTO.getProvinceId());
			model.addAttribute("city", clubDTO.getCity());

			// 2. AJAX 요청이 아닌 경우에만 지역 목록을 추가합니다.
			if (!"XMLHttpRequest".equals(requestedWith)) {
				List<Map<String, Object>> provinceList = clubService.getProvince();
				List<Map<String, Object>> regionList = clubService.getCity();
				model.addAttribute("provinceList", provinceList);
				model.addAttribute("regionList", regionList);
			}
			
		} catch(Exception e) {
			logger.error("Error getting club list", e);
		}

		// 3. AJAX 요청인 경우 프래그먼트만 반환합니다.
		if ("XMLHttpRequest".equals(requestedWith)) {
			return "club/clubList :: clubListFragment";
		}

		// 4. 일반 요청인 경우 전체 페이지를 반환합니다.
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
		//카테고리 가져오기
		model.addAttribute("categoryList", clubMapper.getClubCategory());
		
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
		clubToCreate.setCategory(club.getCategory()); // 폼에서 입력한 동아리 카테고리 ID
		clubToCreate.setClubName(club.getClubName()); // 폼에서 입력한 동아리 이름
		clubToCreate.setClubInfo(club.getClubInfo()); // 폼에서 입력한 동아리 소개
		clubToCreate.setCity(club.getCity());
		clubToCreate.setProvinceId(club.getProvinceId());
		clubToCreate.setId(accountDTO.getId()); // 생성자 ID 설정

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
	
	// club 설정 페이지 이동
		@GetMapping("/clubsetting")
		public String viewClubSetting(@RequestParam("clubId") int clubId, 
				@AuthenticationPrincipal AccountDTO accountDTO, 
				Model model) {
			
			// 1. 로그인 체크
			if (accountDTO == null) {
	            return "redirect:/account/login";
	        }
			
			// 2. 동아리 상세 정보 가져오기 (기존 viewDetail 로직 활용)
			// Service에서 Club, Member여부 등을 포함한 DTO를 가져옵니다.
			ClubDetailDTO clubDetail = clubService.getClubDetail(clubId, accountDTO);

			// 동아리가 존재하지 않으면 목록으로 리다이렉트
			if (clubDetail == null || clubDetail.getClub() == null) {
				return "redirect:/club";
			}

			// 3. 모임장(Master) 여부 확인
			// ClubDTO에 userId(개설자ID)가 있다고 가정하고 비교합니다.
			boolean isMaster = false;
			if (clubDetail.getClub().getId() == accountDTO.getId()) {
				isMaster = true;
			}
			
			// 4. 모델에 데이터 담기 (HTML에서 사용하는 변수명과 일치해야 함)
			model.addAttribute("club", clubDetail.getClub());
			model.addAttribute("isMember", clubDetail.isMember()); // HTML의 th:if="${isMember}" 사용
			model.addAttribute("isMaster", isMaster); // HTML의 th:if="${isMaster}" 사용
			
			return "club/clubsetting";
		}

	

}
