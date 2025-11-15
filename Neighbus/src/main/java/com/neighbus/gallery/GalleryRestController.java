package com.neighbus.gallery;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;

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
		//이미지를 저장할 경로
		String folderPath = "C:\\Users\\aa\\git\\Neighbus\\Neighbus\\src\\main\\resources\\static\\img\\gallery\\";
		
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
}
