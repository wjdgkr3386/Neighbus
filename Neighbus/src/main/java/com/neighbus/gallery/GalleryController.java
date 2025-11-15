package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		GalleryDTO galleryDTO
	) {
		System.out.println("GalleryController - galleryForm");
		try {
			int searchAllCnt = galleryMapper.searchAllCnt(); //갤러리 게시글 전체 개수
			Map<String, Integer> pagingMap = Util.searchUtil(searchAllCnt, galleryDTO.getSelectPageNo(), galleryDTO.getRowCnt());
			
			galleryDTO.setSearchAllCnt(searchAllCnt);
			galleryDTO.setSelectPageNo(pagingMap.get("selectPageNo"));
			galleryDTO.setRowCnt(pagingMap.get("rowCnt"));
			galleryDTO.setBeginPageNo(pagingMap.get("beginPageNo"));
			galleryDTO.setEndPageNo(pagingMap.get("endPageNo"));
			galleryDTO.setBeginRowNo(pagingMap.get("beginRowNo"));
			galleryDTO.setEndRowNo(pagingMap.get("endRowNo"));

			List<Map<String ,Object>> galleryMapList = galleryService.getGalleryAll(galleryDTO);
			model.addAttribute("galleryMapList", galleryMapList);
			model.addAttribute("pagingMap", pagingMap);
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return "gallery/gallery";
	}

	@GetMapping(value="/write")
	public String writeForm(
	) {
		System.out.println("GalleryController - writeForm");
		return "gallery/write";
	}
}
