package com.neighbus.gallery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		try {
			galleryService.updateViewCount(id);
		}catch(Exception e) {
			System.out.println(e);
		}
		if(galleryMap == null || galleryMap.isEmpty()) {
			return "gallery/error";
		}
		model.addAttribute("galleryMap", galleryMap);
		return "gallery/detail";
	}
	
	@PostMapping(value="/insertComment/{id}")
	public String insertComment(
		@AuthenticationPrincipal AccountDTO user,
		@PathVariable(value="id") int id,
	    @RequestParam(value="parent", required=false) Integer parent, // null 가능
	    @RequestParam("comment") String comment
	) {
		System.out.println("GalleryController - insertComment:"+id);
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("gallery_id", id);
		map.put("user_id", user.getId());
		map.put("parent", parent);
		map.put("comment", comment);
		try {
			galleryService.insertComment(map);
		}catch(Exception e) {
			System.out.println(e);
		}
		return "redirect:/gallery/detail/" + id;
	}
}
