package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

			List<Map<String ,Object>> galleryMapList = galleryService.getGalleryList(galleryDTO);
			Util.printMapList(galleryMapList);
			model.addAttribute("galleryMapList", galleryMapList);
			
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
	
	@GetMapping(value="/detail/{id}")
	public String detail(
		@PathVariable("id") int id,
		Model model
	) {
		System.out.println("GalleryController - detail:"+id);
		Map<String, Object> galleryMap = galleryMapper.getGalleryById(id);
		System.out.println();
		if(galleryMap == null || galleryMap.isEmpty()) {
			return "gallery/error";
		}
		model.addAttribute("galleryMap", galleryMap);
		return "gallery/detail";
	}
}
