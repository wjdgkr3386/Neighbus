package com.neighbus.club;

import java.util.HashMap; // Added import for HashMap
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody; // Added import for ResponseBody
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;
import com.neighbus.recruitment.RecruitmentService;
import com.neighbus.s3.S3UploadService;
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
	
	@Autowired
	S3UploadService s3UploadService;

	@GetMapping(value = { "/", "" })
	public String clubList(
		Model model, 
		ClubDTO clubDTO, 
		@RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
		@AuthenticationPrincipal AccountDTO User
	) {
		try {
			
			int userGrade = User.getGrade(); // 예를 들어, 관리자 등급
	        model.addAttribute("userGrade", userGrade);
			// 1. 필터링 조건이 포함된 clubDTO를 사용하여 페이징된 클럽 목록을 가져옵니다.
			PagingDTO<ClubDTO> result = clubService.getClubsWithPaging(clubDTO);
			model.addAttribute("clubs", result.getList());
			model.addAttribute("pagingMap", result.getPagingMap());
			model.addAttribute("keyword", clubDTO.getKeyword());
			model.addAttribute("provinceId", clubDTO.getProvinceId());
			model.addAttribute("city", clubDTO.getCity());
			model.addAttribute("clubDTO", clubDTO);
			// 2. AJAX 요청이 아닌 경우에만 지역 목록을 추가합니다.
			if (!"XMLHttpRequest".equals(requestedWith)) {
				model.addAttribute("provinceList", clubService.getProvince());
				model.addAttribute("regionList", clubService.getCity());
				model.addAttribute("categoryList", clubMapper.getClubCategory());
			}
		} catch (Exception e) {
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
		// 카테고리 가져오기
		model.addAttribute("categoryList", clubMapper.getClubCategory());
		return "club/createClub";
	}

	@GetMapping("/{id}")
	public String viewDetail(
		@PathVariable("id") int id,
		Model model,
		@AuthenticationPrincipal AccountDTO accountDTO)
	{
		ClubDetailDTO clubDetail = clubService.getClubDetail(id, accountDTO);
		System.out.println(clubDetail.getClub());
		if (clubDetail == null || clubDetail.getClub() == null) {
			return "redirect:/club";
		}
		model.addAttribute("club", clubDetail.getClub());
		model.addAttribute("isLoggedIn", clubDetail.isLoggedIn());
		model.addAttribute("isMember", clubDetail.isMember());
		return "club/clubPage";
	}

	@PostMapping("/create")
	public String createClub(@ModelAttribute("clubForm") ClubDTO club, @AuthenticationPrincipal AccountDTO accountDTO) {
	    // 🚨 [필수 수정] 로그인이 안 되어 있거나 세션이 만료된 경우를 처리합니다.
	    if (accountDTO == null) {
	        logger.error("동아리 생성 실패: 로그인 정보가 없습니다.");
	        // 로그인 페이지로 보내거나 에러 메시지를 포함하여 리다이렉트합니다.
	        return "redirect:/account/login"; 
	    }

	    // 1. 폼의 데이터를 서비스용 객체로 변환합니다.
	    club.setWriteId(accountDTO.getId());
	    
	    MultipartFile file = club.getClubImage();
	    
	    // 이미지가 없을 경우에 대한 예외 처리도 필요할 수 있습니다.
	    if (file == null || file.isEmpty()) {
	        logger.warn("업로드된 이미지가 없습니다.");
	        // 필요 시 에러 처리 로직 추가
	    }

	    String key = Util.s3Key();
	    try {
	        // S3에 이미지 업로드
	        String imageUrl = s3UploadService.upload(key, file);

	        if (imageUrl != null) {
	            club.setClubImageName(imageUrl);
	            // DB 저장 및 생성자를 멤버로 자동 추가
	            clubService.createClubAndAddCreator(club);
	        }
	    } catch(Exception e) {
	        logger.error("동아리 생성 중 오류 발생: ", e);
	        s3UploadService.delete(key);
	        // 에러 발생 시 사용자에게 알림을 줄 수 있는 페이지로 이동하거나 이전 폼으로 이동
	        return "redirect:/club/create?error";
	    }
	    
	    return "redirect:/club/";
	}

	// 동아리 가입
	@PostMapping("/join/{id}")
	public String joinClub(@PathVariable("id") int clubId, @AuthenticationPrincipal AccountDTO accountDTO, RedirectAttributes redirectAttributes) {
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
	public String withdrawFromClub(@PathVariable("clubId") Long clubId, @AuthenticationPrincipal AccountDTO accountDTO) {
		// 1. DTO에서 int 타입으로 ID를 가져옴
		int userId = accountDTO.getId();
		// 2. int를 Long 타입으로 명시적으로 변환
		Long longUserId = Long.valueOf(userId);
		clubService.deleteClubMember(clubId, longUserId);
		return "redirect:/club/";
	}

	// 회원 강제 탈퇴 (관리자 기능)
	@PostMapping("/members/remove")
	@ResponseBody
	public Map<String, Object> removeMember(@RequestParam("clubId") int clubId, @RequestParam("userId") int userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			boolean success = clubService.removeClubMember(clubId, userId);
			if (success) {
				response.put("status", "success");
				response.put("message", "회원이 성공적으로 탈퇴 처리되었습니다.");
			} else {
				response.put("status", "fail");
				response.put("message", "회원 탈퇴 처리에 실패했습니다. 해당 회원을 찾을 수 없거나 이미 탈퇴했습니다.");
			}
		} catch (Exception e) {
			logger.error("Error removing club member: clubId={}, userId={}", clubId, userId, e);
			response.put("status", "error");
			response.put("message", "서버 오류로 회원 탈퇴 처리에 실패했습니다.");
		}
		return response;
	}

	// 동아리 폐쇄 (모임장만 가능)
	@PostMapping("/closeClub/{clubId}")
	public String closeClub(@PathVariable("clubId") int clubId, @AuthenticationPrincipal AccountDTO accountDTO, RedirectAttributes redirectAttributes) {
		if (accountDTO == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
			return "redirect:/account/login";
		}
		// 생성자 ID로 검증
		boolean success = clubService.deleteClubByCreator(clubId, accountDTO.getId());
		if (success) {
			redirectAttributes.addFlashAttribute("successMessage", "동아리가 성공적으로 폐쇄되었습니다.");
			return "redirect:/club"; // 동아리 목록 페이지로 리다이렉트
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "동아리 폐쇄에 실패했습니다. 권한이 없거나 동아리를 찾을 수 없습니다.");
			return "redirect:/club/" + clubId + "/clubsetting"; // 설정 페이지로 돌아가기
		}
	}

	// clubPage 이동
	@GetMapping("/myClubPage")
	public String myClubPage(@AuthenticationPrincipal AccountDTO accountDTO, Model model) {
		if (accountDTO == null) {
			return "redirect:/account/login";
		}
		List<ClubDTO> myClubs = clubService.getMyClubs(accountDTO.getId());
		model.addAttribute("myClubs", myClubs);
		return "club/myclubPage";
	}

	// club 설정 페이지 이동
	@GetMapping("/clubsetting")
	public String viewClubSetting(@RequestParam("clubId") int clubId, @AuthenticationPrincipal AccountDTO accountDTO, Model model) {
		// 1. 로그인 체크
		if (accountDTO == null) {
			return "redirect:/account/login";
		}
		System.out.println("ClubController.viewClubSetting - clubId: " + clubId);
		System.out.println("ClubController.viewClubSetting - Logged-in User ID: " + accountDTO.getId());

		// 2. 동아리 상세 정보 가져오기
		ClubDetailDTO clubDetail = clubService.getClubDetail(clubId, accountDTO);
		if (clubDetail == null || clubDetail.getClub() == null) {
			System.out.println("ClubController.viewClubSetting - Club details not found for clubId: " + clubId);
			return "redirect:/club";
		}
		System.out.println("ClubController.viewClubSetting - Club DTO ID: " + clubDetail.getClub().getId());
		System.out.println("ClubController.viewClubSetting - Club Write ID (Creator): " + clubDetail.getClub().getWriteId());

		// 3. 모임장(Master) 여부 확인
		boolean isMaster = false;
		if (clubDetail.getClub().getWriteId() == accountDTO.getId()) { // CORRECTED
			isMaster = true;
		}
		System.out.println("ClubController.viewClubSetting - isMaster: " + isMaster);
		// 4. 모델에 데이터 담기
		model.addAttribute("club", clubDetail.getClub());
		model.addAttribute("isMember", clubDetail.isMember());
		model.addAttribute("isMaster", isMaster);
		
		// 5. 회원 목록 가져오기 (마스터에게만 필요)
		if (isMaster) {
			List<Map<String, Object>> clubMembers = clubService.getClubMembers(clubId);
			model.addAttribute("clubMembers", clubMembers);
		}
		return "club/clubsetting";
	}
}