package com.neighbus.mypage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.account.AccountDTO;
import com.neighbus.account.AccountMapper;
import com.neighbus.account.AccountService;
import com.neighbus.club.ClubMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MyPageService myPageService;
	@Autowired
	private MyPageMapper myPageMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private ClubMapper clubMapper;

	/**
	 * 마이페이지 메인 화면을 표시합니다. 세션에서 로그인 사용자 정보를 가져옵니다.
	 */
	@GetMapping({ "", "/" })
	public String mypageForm(@AuthenticationPrincipal AccountDTO loginUser, Model model) {
		System.out.println("MyPageController - mypageForm");

		// 세션에 로그인 정보가 없을 경우 로그인 페이지로 리다이렉트
		if (loginUser == null) {
			return "redirect:/account/login";
		}

		String username = loginUser.getUsername();

		// 1. 내 정보 불러오기
		// DTO에 regionName, provinceName 필드가 추가되었다면 지역 이름도 함께 가져옵니다.
		Map<String, Object> myInfo = myPageService.getMyPageInfo(username);
		model.addAttribute("myInfo", myInfo);

		// 2. 내가 쓴 게시글
		model.addAttribute("myPosts", myPageService.getMyPosts(username));

		// 3. 내가 쓴 댓글
		model.addAttribute("myComments", myPageService.getMyComments(username));

		model.addAttribute("friendState", myPageMapper.getFriendState(loginUser.getId()));
		
		//내 동아리 리스트
		model.addAttribute("myClubs", clubMapper.getMyClubs(loginUser.getId()));
		
		
		
		// 4. 프로필 수정용 주소 데이터
		List<Map<String, Object>> provinceList = accountMapper.getProvince();
		List<Map<String, Object>> regionList = accountMapper.getCity();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("regionList", regionList);

		return "mypage/mypage"; // mypage.jsp or mypage.html
	}

	@PostMapping(value = "addFriend")
	public String addFriend(@AuthenticationPrincipal AccountDTO loginUser,
			@RequestParam("friendCode") String friendCode, RedirectAttributes ra) {
		if (loginUser.getUserUuid().equals(friendCode)) {
			// 나 자신일경우
			ra.addFlashAttribute("errorMessage", "친구 요청 처리 중 오류가 발생했습니다.");
			return "redirect:/mypage";
		}

		int result = myPageService.addFriend(loginUser.getId(), friendCode);

		if (result == -1) {
			// -1: 유저 없음 (없는 유저)
			ra.addFlashAttribute("errorMessage", "해당 친구 코드를 가진 유저가 존재하지 않습니다.");
		} else if (result == 1) {
			// 1: 요청 성공
			ra.addFlashAttribute("successMessage", "친구 요청에 성공했습니다.");
		} else {
			// 기타 오류 (0: 이미 친구, -2: 자기 자신, 등)
			ra.addFlashAttribute("errorMessage", "친구 요청 처리 중 오류가 발생했습니다.");
		}
		return "redirect:/mypage";
	}

	@PostMapping(value = "/handleFriendRequest")
	public String handleFriendRequest(@AuthenticationPrincipal AccountDTO loginUser, @RequestParam("action") int action, // 1:수락
																															// ,
																															// 2:거절
			@RequestParam("sender") int sender) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", loginUser.getId());
		map.put("sender", sender);
		if (action == 1) {
			myPageService.friendAccept(map);
		} else if (action == 2) {
			myPageService.friendReject(map);
		}

		return "redirect:/mypage";
	}

	/**
	 * 프로필 수정 - 닉네임과 지역만 수정 가능
	 */
	@PostMapping("/update")
	public String updateProfile(@AuthenticationPrincipal AccountDTO loginUser,
			@RequestParam("nickname") String nickname,
			@RequestParam("province") int province,
			@RequestParam("city") int city,

			RedirectAttributes ra) {
		System.out.println("MyPageController - updateProfile");

		if (loginUser == null) {
			return "redirect:/account/login";
		}

		try {
			// 닉네임과 지역 업데이트
			Map<String, Object> updateData = new HashMap<>();
			updateData.put("id", loginUser.getId());
			updateData.put("nickname", nickname);
			updateData.put("province", province);
			updateData.put("city", city);

			myPageService.updateProfile(updateData);

			ra.addFlashAttribute("successMessage", "프로필이 성공적으로 수정되었습니다.");
		} catch (Exception e) {
			System.err.println("프로필 수정 중 오류: " + e.getMessage());
			ra.addFlashAttribute("errorMessage", "프로필 수정 중 오류가 발생했습니다.");
		}

		return "redirect:/mypage";
	}

	/**
	 * 프로필 이미지 업로드
	 */
	@PostMapping("/uploadProfileImage")
	public String uploadProfileImage(@AuthenticationPrincipal AccountDTO loginUser,
			@RequestParam("profileImage") MultipartFile profileImage, RedirectAttributes ra) {
		System.out.println("MyPageController - uploadProfileImage");

		if (loginUser == null) {
			return "redirect:/account/login";
		}

		try {
			if (profileImage.isEmpty()) {
				ra.addFlashAttribute("errorMessage", "이미지를 선택해주세요.");
				return "redirect:/mypage";
			}

			// 파일 확장자 체크
			String originalFilename = profileImage.getOriginalFilename();
			String[] allowedExtensions = { "jpg", "jpeg", "png", "jfif" };
			String extension = "";
			if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
				extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
			}

			boolean validExtension = false;
			for (String ext : allowedExtensions) {
				if (ext.equals(extension)) {
					validExtension = true;
					break;
				}
			}

			if (!validExtension) {
				ra.addFlashAttribute("errorMessage", "jpg, jpeg, png, jfif 형식의 이미지만 업로드 가능합니다.");
				return "redirect:/mypage";
			}

			// UUID로 파일명 생성
			String uuid = UUID.randomUUID().toString() + "." + extension;

			// 저장 경로 설정 - 프로젝트의 실제 경로 사용
			String projectPath = System.getProperty("user.dir");

			// src 폴더에 저장 (소스 보관용)
			String srcUploadDir = projectPath + "/src/main/resources/static/img/profile/";
			File srcFolder = new File(srcUploadDir);
			if (!srcFolder.exists()) {
				srcFolder.mkdirs();
			}

			// bin 폴더에 저장 (런타임 서빙용)
			String binUploadDir = projectPath + "/bin/main/static/img/profile/";
			File binFolder = new File(binUploadDir);
			if (!binFolder.exists()) {
				binFolder.mkdirs();
			}

			// src 폴더에 파일 저장
			File srcDest = new File(srcUploadDir + uuid);
			profileImage.transferTo(srcDest);

			// bin 폴더에 파일 복사
			File binDest = new File(binUploadDir + uuid);
			java.nio.file.Files.copy(srcDest.toPath(), binDest.toPath(),
					java.nio.file.StandardCopyOption.REPLACE_EXISTING);

			// DB 업데이트
			Map<String, Object> updateData = new HashMap<>();
			updateData.put("id", loginUser.getId());
			updateData.put("image", uuid);

			myPageService.updateProfileImage(updateData);
			loginUser.setImage(uuid);
			ra.addFlashAttribute("successMessage", "프로필 이미지가 성공적으로 변경되었습니다.");
		} catch (IOException e) {
			System.err.println("프로필 이미지 업로드 중 오류: " + e.getMessage());
			e.printStackTrace();
			ra.addFlashAttribute("errorMessage", "프로필 이미지 업로드 중 오류가 발생했습니다.");
		} catch (Exception e) {
			System.err.println("프로필 이미지 업데이트 중 오류: " + e.getMessage());
			e.printStackTrace();
			ra.addFlashAttribute("errorMessage", "프로필 이미지 업데이트 중 오류가 발생했습니다.");
		}

		return "redirect:/mypage";
	}

	// 탈퇴 처리
	@PostMapping("/delMyUser")
	public String delMyUser(HttpServletRequest request, HttpServletResponse response,
			@AuthenticationPrincipal AccountDTO accountDTO) {

		if (accountDTO != null) {
			// 1. DB에서 회원 정보 삭제 (이전 질문의 XML 파라미터 타입에 맞춰 DTO 전달)
			myPageService.delMyUser(accountDTO);

			// 2. 스프링 시큐리티를 이용한 강제 로그아웃 (세션 무효화, 쿠키 삭제 등 포함)
			new SecurityContextLogoutHandler().logout(request, response,
					SecurityContextHolder.getContext().getAuthentication());

			System.out.println("탈퇴 및 로그아웃 완료");
		}

		// 3. 로그인 페이지로 리다이렉트
		return "redirect:/account/login";
	}
	
	@GetMapping("/passwordUpdate")
	public String passwordUpdate() {
		return "/mypage/passwordUpdate";
	}

}