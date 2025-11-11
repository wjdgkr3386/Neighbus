package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {

	@Autowired
	GalleryMapper galleryMapper;
	@Autowired
	GalleryService galleryService;
	
	@GetMapping(value={"/",""})
	public String galleryForm(
		Model model
	) {
		System.out.println("GalleryController - galleryForm");
		
		try {
			List<Map<String ,Object>> galleryMapList = galleryService.getGalleryAll();
			model.addAttribute("galleryMapList", galleryMapList);
			Util.printMapList(galleryMapList);
		}catch(Exception e) {
			System.out.println(e);
		}
		return "gallery/gallery";
	}

	@GetMapping(value={"/write"})
	public String writeForm(
		HttpSession session,
		Model model
	) {
		System.out.println("GalleryController - writeForm");
		AccountDTO loginUser = (AccountDTO)session.getAttribute("loginUser");
		model.addAttribute("username", loginUser.getUsername());
		return "gallery/write";
	}
	
	
}
