package com.neighbus.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neighbus.account.AccountDTO;
import com.neighbus.account.AccountMapper;
import com.neighbus.club.ClubMapper;
import com.neighbus.recruitment.RecruitmentService;

@Controller
public class MainController {

	@Autowired
	AccountMapper accountMapper;
	@Autowired
	ClubMapper clubMapper;
	@Autowired
	RecruitmentService recruitmentService;
	
	
	@GetMapping(value="/")
	public String mainForm(
		Model model,
		@AuthenticationPrincipal AccountDTO accountDTO,
		SearchDTO searchDTO
	) {
		System.out.println("MainController - mainForm");
		
	    //DB에서 대한민국 지역 가져오기
		List<Map<String, Object>> provinceList = accountMapper.getProvince();
		List<Map<String, Object>> regionList = accountMapper.getCity();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);

		model.addAttribute("newClubList", clubMapper.getNewClub(searchDTO));
		model.addAttribute("popularClubList", clubMapper.getPopularClub(searchDTO));

		// 통계
		model.addAttribute("activeRecruitments", recruitmentService.findAllRecruitments().size());
		model.addAttribute("totalUsers", accountMapper.countUsers());
		model.addAttribute("totalViews", accountMapper.countViews());

		return "main/main";
	}

}
