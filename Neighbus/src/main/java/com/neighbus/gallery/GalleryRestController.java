package com.neighbus.gallery;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;

@RequestMapping("/gallery/api")
@RestController
public class GalleryRestController {

	@Autowired
	GalleryService galleryService;
	
	@PostMapping(value="/insertGallery")
	public Map<String, Object> insertGallery(
		@ModelAttribute GalleryDTO galleryDTO,
		@AuthenticationPrincipal AccountDTO user
	){
		System.out.println("GalleryRestController - insertGallery");
		galleryDTO.setWriter(user.getId());
		
		Map<String ,Object> response = new HashMap<String, Object>();
		// 프로젝트 위치를 자동으로 찾아서 경로 생성
		String projectPath = System.getProperty("user.dir"); // 현재 프로젝트 폴더 (C:\Users\aa\git\Neighbus\Neighbus)
		String folderPath = projectPath + "\\src\\main\\resources\\static\\img\\gallery";

		// Util 호출
		Util.saveFileToDirectory(galleryDTO, folderPath);
	
		// 이미지 저장
		int status = Util.saveFileToDirectory(galleryDTO, folderPath);
		if(status != 1) {
			response.put("status", status);
			return response;
		}
		
		try {
			galleryService.insertGallery(galleryDTO);
			status = 1;
		}catch(Exception e) {
			System.out.println(e);
			status = -1;
		}

		response.put("status", status);
		return response;
	}
	
	@DeleteMapping("/deleteReaction")
	public Map<String, Object> deleteReaction(
		@RequestBody Map<String, Object> request,
		@AuthenticationPrincipal AccountDTO user
	) {
		System.out.println("ServiceRestController - deleteReaction");
		request.put("userId", user.getId());
		return galleryService.deleteReaction(request);
	}

	@PutMapping("/updateReaction")
	public Map<String, Object> updateReaction(
		@RequestBody Map<String, Object> request,
		@AuthenticationPrincipal AccountDTO user
	) {
		System.out.println("ServiceRestController - updateReaction");
		request.put("userId", user.getId());
		return galleryService.updateReaction(request);
	}
	
	@PostMapping("/insertReaction")
	public Map<String, Object> insertReaction(
		@RequestBody Map<String, Object> request,
		@AuthenticationPrincipal AccountDTO user
	) {
		System.out.println("ServiceRestController - insertReaction");
		request.put("userId", user.getId());
		return galleryService.insertReaction(request);
	}
}
