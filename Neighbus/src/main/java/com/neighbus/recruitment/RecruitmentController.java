package com.neighbus.recruitment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/recruitment")
public class RecruitmentController {

	private final RecruitmentService recruitmentService;

	@Autowired
	public RecruitmentController(RecruitmentService recruitmentService) {
		this.recruitmentService = recruitmentService;
	}

	/**
	 * 모임 목록 페이지 (GET /recruitments)
	 * 
	 * @param model View에 데이터를 전달할 Model 객체
	 * @return 렌더링할 Thymeleaf 템플릿 이름
	 */
	@GetMapping(value = {"/",""})
	public String showRecruitmentList(Model model) {
		// 서비스에서 전체 모임 목록을 조회합니다.
		List<recruitmentDTO> recruitmentList = recruitmentService.findAllRecruitments();

		// Model에 "recruitments"라는 이름으로 목록을 추가합니다.
		model.addAttribute("recruitments", recruitmentList);

		// resources/templates/recruitment_list.html 파일을 렌더링합니다.
		return "recruitment_list";
	}

	/**
	 * 모임 상세 페이지 (GET /recruitments/{id}) (TODO: 상세 조회 로직 구현 필요)
	 */
	@GetMapping("/{id}")
	public String showRecruitmentDetail(@PathVariable("id") int id, Model model) {
		// TODO: recruitmentService.findById(id) 등으로 상세 정보를 조회
		recruitmentDTO recruitment = recruitmentService.findById(id);
		model.addAttribute("recruitment", recruitment);

		return "recruitment_detail"; // (상세 페이지 HTML)

	}

	/**
	 * 새 모임 생성 폼 페이지 (GET /recruitments/new) (TODO: 생성 폼 HTML 필요)
	 */
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("recruitmentDTO", new recruitmentDTO());
		return "recruitment_form"; // (생성 폼 HTML)

	}
}
