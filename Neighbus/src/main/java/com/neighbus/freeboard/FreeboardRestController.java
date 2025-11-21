package com.neighbus.freeboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;
import com.neighbus.club.ClubMapper;

@RestController
public class FreeboardRestController {

    @Autowired
    private FreeboardService freeboardService;
    
	@PostMapping("/clubSelect")
	public Map<String,Object> clubSelect(
    	FreeboardDTO freeboardDTO,
		@RequestParam(value = "clubSelect", required = false) Integer clubSelect,
		@AuthenticationPrincipal AccountDTO user
    ){
		System.out.println("FreeboardRestController - clubSelect");
		Map<String,Object> response = new HashMap<String,Object>();

        int searchAllCnt = freeboardService.searchAllCnt(freeboardDTO.getKeyword()); // 게시글 전체 개수
        Map<String, Integer> pagingMap = Util.searchUtil(searchAllCnt, freeboardDTO.getSelectPageNo(), freeboardDTO.getRowCnt());
        freeboardDTO.setSearchAllCnt(searchAllCnt);
        freeboardDTO.setSelectPageNo(pagingMap.get("selectPageNo"));
        freeboardDTO.setRowCnt(pagingMap.get("rowCnt"));
        freeboardDTO.setBeginPageNo(pagingMap.get("beginPageNo"));
        freeboardDTO.setEndPageNo(pagingMap.get("endPageNo"));
        freeboardDTO.setBeginRowNo(pagingMap.get("beginRowNo"));
        freeboardDTO.setEndRowNo(pagingMap.get("endRowNo"));
        freeboardDTO.setUserId(user.getId());
        freeboardDTO.setClubId(clubSelect == null? 0 : clubSelect);
        
        System.out.println("clubId : "+freeboardDTO.getClubId());
        List<Map<String,Object>> posts = freeboardService.selectPostListWithPaging(freeboardDTO);
        response.put("posts", posts);
        System.out.println(response);
        
		return response;
	}
}
