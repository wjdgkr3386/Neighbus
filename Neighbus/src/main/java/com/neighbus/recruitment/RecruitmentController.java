package com.neighbus.recruitment;

import java.util.Collections;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {

	private final RecruitmentService recruitmentService;

	@Autowired
	public RecruitmentController(RecruitmentService recruitmentService) {
		this.recruitmentService = recruitmentService;
	}

	/**
	 * 모임 목록 페이지 (GET /recruitment)
	 * @param model View에 데이터를 전달할 Model 객체
	 * @return 렌더링할 Thymeleaf 템플릿 이름
	 */
	@GetMapping(value = {"/",""})
	public String showRecruitmentList(Model model) {
		// 서비스에서 전체 모임 목록을 조회합니다.
		List<RecruitmentDTO> recruitmentList = recruitmentService.findAllRecruitments();

		// Model에 "recruitments"라는 이름으로 목록을 추가합니다.
		model.addAttribute("recruitments", recruitmentList);

		// resources/templates/recruitment/recruitment.html 파일을 렌더링합니다.
		return "recruitment/recruitment";
	}

	/**
	 * 모임 상세 페이지 (GET /recruitment/{id})
	 */
		@GetMapping("/{id}")
		public String showRecruitmentDetail(@PathVariable("id") int id, Model model) {
			RecruitmentDTO recruitment = recruitmentService.findById(id);
			int currentUserCount = recruitmentService.countMembers(id);
	
			model.addAttribute("recruitment", recruitment);
			model.addAttribute("currentUserCount", currentUserCount);
			
			// TODO: 가입 여부 등 추가 정보 전달
			return "recruitment/recruitment_detail"; 
		}

		/**
	     * 새 모임 생성 폼 페이지 (GET /recruitment/new)
	     */
	    @GetMapping("/new")
	    public String showCreateForm(@RequestParam("clubId") int clubId, Model model) { // [수정 1] 파라미터 받기
	        RecruitmentDTO dto = new RecruitmentDTO();
	        dto.setClubId(clubId); // [수정 2] DTO에 동아리 ID 미리 세팅
	        
	        model.addAttribute("recruitmentDTO", dto); // (변수명 소문자 권장)
	        return "recruitment/recruitment_form";
	    }

	    /**
	     * 새 모임 생성 처리 (POST /recruitment/new)
	     */
	    @PostMapping("/new")
	    public String createRecruitment(@ModelAttribute RecruitmentDTO recruitmentDTO, 
	                                    @AuthenticationPrincipal AccountDTO accountDTO) {
	        // 1. 작성자 설정
	        recruitmentDTO.setWriter(accountDTO.getId());
	        
	        // 2. 서비스 호출 (이때 DTO 안에 clubId가 들어있어야 함)
	        recruitmentService.createRecruitment(recruitmentDTO);
	        
	        // 3. 생성 후 해당 동아리 상세 페이지로 돌아가기 (UX 향상)
	        return "redirect:/recruitment/" + recruitmentDTO.getClubId();
	    }
	
	// 가입한 클럽 모임 리스트
	@GetMapping("/recruitments/my-clubs-page")
    public String showMyClubsPage(@AuthenticationPrincipal AccountDTO accountDTO, Model model) {
        
        List<RecruitmentDTO> myClubsRecruitments;

        if (accountDTO != null) {
            // 1. 로그인한 사용자의 ID로 데이터를 조회
            int userId = accountDTO.getId(); 
            myClubsRecruitments = recruitmentService.getRecruitmentsByMyClubs(userId);
        } else {
            // 2. 비로그인 시 빈 목록
            myClubsRecruitments = Collections.emptyList();
        }

        // 3. Model에 조회한 데이터 목록을 추가
        model.addAttribute("recruitmentList", myClubsRecruitments);

        // 4. "recruitments/myClubsPage" 이름의 HTML 템플릿(JSP/Thymeleaf) 파일로 이동
        return "recruitment/myClubsPage"; 
    }
	
	
	//날짜별 모임
	@GetMapping("/recruitments/api") 
	@ResponseBody
	public List<RecruitmentDTO> getRecruitments(
	    @RequestParam("clubId") int clubId, 
	    @RequestParam("date") String date // 자바스크립트가 보내준 날짜
	) {
	    return recruitmentService.getRecruitmentsByClubAndDate(clubId, date);
	}
}
