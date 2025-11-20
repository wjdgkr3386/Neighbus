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
import com.neighbus.notice.NoticeController;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {

    private final NoticeController noticeController;

	@Autowired
	GalleryMapper galleryMapper;
	@Autowired
	GalleryService galleryService;

    GalleryController(NoticeController noticeController) {
        this.noticeController = noticeController;
    }
	
	@GetMapping(value={"/",""})
	public String galleryForm(
		Model model,
		GalleryDTO galleryDTO,
		@RequestParam(value = "keyword", required = false) String keyword
	) {
		System.out.println("GalleryController - galleryForm");
		try {
			// 검색 키워드 설정
			galleryDTO.setKeyword(keyword);

			int searchAllCnt = galleryMapper.searchAllCnt(keyword); //갤러리 게시글 전체 개수
			Map<String, Integer> pagingMap = Util.searchUtil(searchAllCnt, galleryDTO.getSelectPageNo(), galleryDTO.getRowCnt());

			galleryDTO.setSearchAllCnt(searchAllCnt);
			galleryDTO.setSelectPageNo(pagingMap.get("selectPageNo"));
			galleryDTO.setRowCnt(pagingMap.get("rowCnt"));
			galleryDTO.setBeginPageNo(pagingMap.get("beginPageNo"));
			galleryDTO.setEndPageNo(pagingMap.get("endPageNo"));
			galleryDTO.setBeginRowNo(pagingMap.get("beginRowNo"));
			galleryDTO.setEndRowNo(pagingMap.get("endRowNo"));


			List<Map<String ,Object>> galleryMapList = galleryService.getGalleryList(galleryDTO);

			for (Map<String, Object> galleryMap : galleryMapList) {
			    galleryMap.put("CONTENT", Util.convertAngleBracketsString((String) galleryMap.get("CONTENT"), "<br>"));
			    galleryMap.put("TITLE", Util.convertAngleBracketsString((String) galleryMap.get("TITLE"), "<br>"));
			}

			model.addAttribute("pagingMap", pagingMap);
			model.addAttribute("galleryMapList", galleryMapList);
			model.addAttribute("keyword", keyword);

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
		
		galleryMap.put("CONTENT", Util.convertAngleBracketsString((String) galleryMap.get("CONTENT"), "<br>"));
		galleryMap.put("TITLE", Util.convertAngleBracketsString((String) galleryMap.get("TITLE"), "<br>"));
		List<Map<String, Object>> comments = (List<Map<String, Object>>) galleryMap.get("COMMENTS");
		if(comments != null) {
		    for(Map<String, Object> comment : comments) {
		        String commentContent = Util.convertAngleBracketsString((String) comment.get("CONTENT"), "<br>");
		        comment.put("CONTENT", commentContent);
		        List<Map<String, Object>> replies = (List<Map<String, Object>>) comment.get("REPLIES");
		        if(replies != null) {
		            for(Map<String, Object> reply : replies) {
		                String replyContent = Util.convertAngleBracketsString((String) reply.get("CONTENT"), "<br>");
		                reply.put("CONTENT", replyContent);
		            }
		        }
		    }
		}
		model.addAttribute("galleryMap", galleryMap);
		return "gallery/detail";
	}
	
	@PostMapping(value="/insertComment/{id}")
	public String insertComment(
		@AuthenticationPrincipal AccountDTO user,
		@PathVariable(value="id") int id,
		@RequestParam(value = "parent", required = false) Integer parent,
	    @RequestParam("comment") String comment
	) {
		System.out.println("GalleryController - insertComment:"+id);
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("gallery_id", id);
		map.put("user_id", user.getId());
		map.put("parent", parent==null?0:parent);
		map.put("comment", comment);
		try {
			galleryService.insertComment(map);
		}catch(Exception e) {
			System.out.println(e);
		}
		return "redirect:/gallery/detail/" + id;
	}
}
