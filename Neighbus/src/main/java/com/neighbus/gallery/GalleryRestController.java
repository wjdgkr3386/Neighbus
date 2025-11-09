package com.neighbus.gallery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.Util;

@RestController
public class GalleryRestController {

	@Autowired
	GalleryService galleryService;
	
	@PostMapping(value="/insertGallery")
	public Map<String, Object> insertGallery(
		GalleryDTO galleryDTO,
		HttpServletRequest req
	){
		System.out.println("GalleryRestController - insertGallery");
		
		Map<String ,Object> response = new HashMap<String, Object>();
		int status = Util.saveFileToDirectory(galleryDTO);
		
		if(status != 1) {
			response.put("status", status);
			return response;
		}
		
		try {
			galleryService.insertGallery(galleryDTO);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
}
