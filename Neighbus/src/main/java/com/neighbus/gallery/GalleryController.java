package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neighbus.Util;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {

	@Autowired
	GalleryMapper galleryMapper;
	@Autowired
	GalleryService galleryService;
	
	@GetMapping(value={"/",""})
	public String galleryForm(
			Model model,
			HttpServletRequest request
	) {
		System.out.println("GalleryController - galleryForm");
		
		List<Map<String ,Object>> galleryMapList = galleryMapper.getGalleryAll();
		Util.printMapList(galleryMapList);
		
		return "gallery/gallery";
	}

	@GetMapping(value={"/write"})
	public String writeForm(
		Model model,
		HttpServletRequest request
	) {
		System.out.println("GalleryController - writeForm");

		// 쿠키값 가지고 들어가기
		Util.getCookie(request, model);
	    
		return "gallery/write";
	}
	
	
}
