package com.neighbus.gallery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {

	@GetMapping(value={"/",""})
	public String galleryForm() {
		System.out.println("GalleryController - galleryForm");
		return "gallery/gallery";
	}

	@GetMapping(value={"/write"})
	public String writeForm() {
		System.out.println("GalleryController - writeForm");
		return "gallery/write";
	}
	
	
}
