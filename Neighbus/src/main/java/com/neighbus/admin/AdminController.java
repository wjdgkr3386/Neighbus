package com.neighbus.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ★ 관리자 전용 컨트롤러 ★
 *
 * 이 컨트롤러의 모든 메서드는 ROLE_ADMIN 권한이 필요합니다.
 * SecurityConfig.java에서 /admin/** 경로는 ROLE_ADMIN 권한을 요구하도록 설정되어 있습니다.
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	/**
	 * 관리자 대시보드 메인 페이지
	 */
	@GetMapping
	public String adminDashboard(Authentication authentication, Model model) {
		model.addAttribute("username", authentication.getName());
		return "admin/dashboard"; // admin/dashboard.jsp 또는 admin/dashboard.html
	}

	/**
	 * 사용자 관리 페이지
	 */
	@GetMapping("/users")
	public String manageUsers(Model model) {
		// 사용자 목록 조회 로직을 여기에 추가할 수 있습니다.
		return "admin/users"; // admin/users.jsp 또는 admin/users.html
	}

	/**
	 * 시스템 설정 페이지
	 */
	@GetMapping("/settings")
	public String systemSettings(Model model) {
		return "admin/settings"; // admin/settings.jsp 또는 admin/settings.html
	}

	/**
	 * 공지사항 관리 페이지
	 */
	@GetMapping("/notice")
	public String manageNotice(Model model) {
		return "admin/notice"; // admin/notice.html
	}

	/**
	 * 모임 관리 페이지
	 */
	@GetMapping("/gatherings")
	public String manageGatherings(Model model) {
		return "admin/gatherings"; // admin/gatherings.html
	}

	/**
	 * 게시글 관리 페이지
	 */
	@GetMapping("/posts")
	public String managePosts(Model model) {
		return "admin/posts"; // admin/posts.html
	}

	/**
	 * 신고 관리 페이지
	 */
	@GetMapping("/reports")
	public String manageReports(Model model) {
		return "admin/reports"; // admin/reports.html
	}

	/**
	 * 문의 관리 페이지
	 */
	@GetMapping("/inquiries")
	public String manageInquiries(Model model) {
		return "admin/inquiries"; // admin/inquiries.html
	}
}
