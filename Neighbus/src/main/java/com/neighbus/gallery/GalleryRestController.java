package com.neighbus.gallery;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GalleryRestController {

	@PostMapping(value="/insertGallery")
	public Map<String, Object> insertGallery(
		GalleryDTO galleryDTO
	){
		System.out.println("GalleryRestController - insertGallery");
		System.out.println(galleryDTO.getTitle());
		System.out.println(galleryDTO.getContent());
		for(int i=0; i<galleryDTO.getFileList().size(); i++) {
			System.out.println(galleryDTO.getFileList().get(i).getOriginalFilename());
		}
		//여기 할 차례
		return null;
	}
}
