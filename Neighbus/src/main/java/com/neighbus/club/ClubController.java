package com.neighbus.club;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.account.AccountDTO;
import com.neighbus.account.AccountMapper;
import com.neighbus.recruitment.RecruitmentDTO;
import com.neighbus.recruitment.RecruitmentService;

@Controller
@RequestMapping("/club")
public class ClubController {

	private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private ClubService clubService;
	@Autowired
	private ClubMapper clubMapper;
	@Autowired
	private RecruitmentService recruitmentService;

	@GetMapping(value = { "/", "" })
	public String clubList(Model model, ClubDTO clubDTO, 
			@RequestParam(value = "keyword", required = false) String keyword) {
		try {
			// 검색 키워드 설정
			clubDTO.setKeyword(keyword);

			int searchAllCnt = clubService.getClubCount(keyword); // 동아리 전체 개수 조회
			Map<String, Integer> pagingMap = com.neighbus.Util.searchUtil(searchAllCnt, clubDTO.getSelectPageNo(), 9); // 페이지당 9개

			clubDTO.setSearchAllCnt(searchAllCnt);
			clubDTO.setSelectPageNo(pagingMap.get("selectPageNo"));
			clubDTO.setRowCnt(pagingMap.get("rowCnt"));
			clubDTO.setBeginPageNo(pagingMap.get("beginPageNo"));
			clubDTO.setEndPageNo(pagingMap.get("endPageNo"));
			clubDTO.setBeginRowNo(pagingMap.get("beginRowNo"));
			clubDTO.setEndRowNo(pagingMap.get("endRowNo"));

			List<ClubDTO> clubs = clubService.getClubListWithPaging(clubDTO);

			model.addAttribute("clubs", clubs);
			model.addAttribute("pagingMap", pagingMap);
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
		List<Map<String, Object>> provinceList = accountMapper.getProvince();
		List<Map<String, Object>> regionList = accountMapper.getCity();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);
		return "club/createClub";
	}

	@GetMapping("/{id}")
	public String viewDetail(@PathVariable("id") int id, 
			Model model, 
			@AuthenticationPrincipal AccountDTO accountDTO) {

		ClubDTO club = clubService.getClubById(id);

		if (club == null) {
			return "redirect:/club";
		}

		// 2. 현재 사용자의 상태 확인 (로그인 상태일 때)
		boolean isMember = false;
		// boolean isCreator = false; // (제거) 개설자 로직 제거
		boolean isLoggedIn = (accountDTO != null); // 로그인 여부

		if (isLoggedIn) {

			// (제거) 개설자 확인 로직 삭제
			// if (club.getCreator() == accountDTO.getId()) { ... }

			// 2b. 본인이 이미 가입했는지 확인 (isMember 쿼리 재사용)
			// (수정) 개설자가 아닌 경우에만 확인하던 'else' 블록을 제거하고 항상 체크하도록 수정
			ClubMemberDTO memberCheck = new ClubMemberDTO();
			memberCheck.setClubId(id);
			memberCheck.setUserId(accountDTO.getId());

			isMember = (clubService.isMember(memberCheck) > 0);
		}

		model.addAttribute("club", club);
		model.addAttribute("isLoggedIn", isLoggedIn); // 3. 로그인 여부
		model.addAttribute("isMember", isMember); // 3. 가입 여부
		// model.addAttribute("isCreator", isCreator); // (제거) 개설자 여부 전달 제거

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

	// 기존 oder 메서드는 '도' 목록만 불러오도록 수정
	@GetMapping("/oder")
	public String oder(Model model) {
		List<Map<String, Object>> provinceList = accountMapper.getProvince();
		model.addAttribute("provinceList", provinceList);

		List<Map<String, Object>> regionList = accountMapper.getCity();
		model.addAttribute("regionList", regionList);

		List<ClubDTO> clubs = clubService.getAllClubs();
		model.addAttribute("clubs", clubs);

		return "club/oder";
	}

	// 필터링
	@PostMapping("/filter-clubs")
	public String filterClubs(ClubDTO clubDTO, Model model) {
		List<ClubDTO> clubFilter = null;
		if (clubDTO.getCity() == 0) {
			clubFilter = clubMapper.getOderProvince(clubDTO.getProvinceId());
		} else {
			Map<String, Object> params = new HashMap<>();
			params.put("provinceId", clubDTO.getProvinceId());
			params.put("city", clubDTO.getCity());
			clubFilter = clubMapper.getOderCity(params);
		}
		model.addAttribute("clubs", clubFilter);
		return "club/oder :: #clubListFragment";
	}
	
	// clubPage 이동
	@GetMapping("/clubPage")
	public String clubPage() {
		// TODO Auto-generated method stub
		return "club/clubPage";
	}

}