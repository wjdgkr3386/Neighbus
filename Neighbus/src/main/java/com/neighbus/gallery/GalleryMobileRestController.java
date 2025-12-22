package com.neighbus.gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;
import com.neighbus.club.ClubMapper;
import com.neighbus.s3.S3UploadService;

@RestController
@RequestMapping("/api/mobile/gallery")
public class GalleryMobileRestController {

	@Autowired
	GalleryMapper galleryMapper;
	@Autowired
	GalleryService galleryService;
	@Autowired
	ClubMapper clubMapper;
	@Autowired
	S3UploadService s3UploadService;
	
	@GetMapping(value={"getGallery"})
    public ResponseEntity<Map<String, Object>> getGalleryList(
        GalleryDTO galleryDTO,
        @RequestParam(value = "keyword", required = false) String keyword,
        @AuthenticationPrincipal AccountDTO user
    ) {
		System.out.println("GalleryMobileRestController - getGalleryList");
        Map<String, Object> response = new HashMap<>();
        galleryDTO.setId(user.getId());

        try {
            if (keyword != null) { galleryDTO.setKeyword(keyword); }

            // 1. 검색 및 페이징 처리
            int searchCnt = galleryMapper.searchCnt(galleryDTO);
            Map<String, Integer> pagingMap = Util.searchUtil(searchCnt, galleryDTO.getSelectPageNo(), 6);
            
            // DTO 데이터 세팅
            galleryDTO.setSearchCnt(searchCnt);
            galleryDTO.setSelectPageNo(pagingMap.get("selectPageNo"));
            galleryDTO.setRowCnt(pagingMap.get("rowCnt"));
            galleryDTO.setBeginPageNo(pagingMap.get("beginPageNo"));
            galleryDTO.setEndPageNo(pagingMap.get("endPageNo"));
            galleryDTO.setBeginRowNo(pagingMap.get("beginRowNo"));
            galleryDTO.setEndRowNo(pagingMap.get("endRowNo"));

            // 2. 리스트 조회 및 데이터 가공
            List<Map<String, Object>> galleryMapList = galleryService.getGalleryList(galleryDTO);
            for (Map<String, Object> galleryMap : galleryMapList) {
                galleryMap.put("CONTENT", Util.convertAngleBracketsString((String) galleryMap.get("CONTENT"), "<br>"));
                galleryMap.put("TITLE", Util.convertAngleBracketsString((String) galleryMap.get("TITLE"), "<br>"));
            }

            // 3. 내 클럽 리스트 조회
            Map<String, Object> clubParam = new HashMap<>();
            clubParam.put("id", user.getId());
            List<Map<String, Object>> myClubList = clubMapper.getMyClub(clubParam);

            // 4. 결과 데이터 담기
            response.put("galleryDTO", galleryDTO);
            response.put("myClubList", myClubList);
            response.put("pagingMap", pagingMap);
            response.put("galleryMapList", galleryMapList);
            response.put("keyword", keyword);
            response.put("status", "success");

            System.out.println("galleryDTO : " + galleryDTO);
            System.out.println("myClubList : " + myClubList);
            System.out.println("pagingMap : " + pagingMap);
            System.out.println("galleryMapList : " + galleryMapList);
            System.out.println("keyword : " + keyword);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
	
	@PostMapping(value="/insertGallery")
	public Map<String, Object> insertGallery(
		@ModelAttribute GalleryDTO galleryDTO,
		@AuthenticationPrincipal AccountDTO user
	){
		System.out.println("GalleryMobileRestController - insertGallery");
		Map<String ,Object> response = new HashMap<String, Object>();
		List<MultipartFile> fileList = galleryDTO.getFileList();
		List<String> fileNameList = new ArrayList<String>();
		galleryDTO.setWriter(user.getId());
		int status = 0;

		try {
			// 이미지 저장
			for(MultipartFile file : fileList) {
				String key = Util.s3Key();
				String imgUrl = s3UploadService.upload(key, file);
				fileNameList.add(imgUrl);
			}
			galleryDTO.setFileNameList(fileNameList);
			
			galleryService.insertGallery(galleryDTO);
			status = 1;
		}catch(Exception e) {
			System.out.println(e);
			for(String fileName : fileNameList) {
				s3UploadService.delete(fileName);
			}
			status = -1;
		}

		response.put("status", status);
		return response;
	}
	
	@GetMapping(value="/detail/{id}")
	public ResponseEntity<Map<String, Object>> getGalleryDetail(
	    @PathVariable("id") int galleryId,
	    @AuthenticationPrincipal AccountDTO user
	) {
	    Map<String, Object> response = new HashMap<>();
	    
	    // 1. 사용자 체크
	    if (user == null) {
	        response.put("status", "fail");
	        response.put("message", "인증되지 않은 사용자입니다.");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    int userId = user.getId();
	    System.out.println("Gallery API - detail: " + galleryId + " by User: " + userId);

	    // 2. 게시글 조회
	    Map<String, Object> galleryMap = galleryMapper.getGalleryById(galleryId);
	    if (galleryMap == null || galleryMap.isEmpty()) {
	        response.put("status", "fail");
	        response.put("message", "해당 게시글을 찾을 수 없습니다.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }

	    // 3. 조회수 증가 (비동기 권장하지만 기존 로직 유지)
	    try {
	        galleryService.updateViewCount(galleryId);
	    } catch (Exception e) {
	        System.err.println("조회수 업데이트 실패: " + e.getMessage());
	    }

	    // 4. 리액션 데이터 조회 (좋아요/싫어요)
	    Map<String, Object> reactionDataMap = new HashMap<>();
	    reactionDataMap.put("userId", userId);
	    reactionDataMap.put("galleryId", galleryId);
	    
	    Map<String, Object> reaction = galleryMapper.selectReaction(reactionDataMap);
	    if (reaction == null) {
	        reaction = new HashMap<>();
	        reaction.put("likeCount", 0);
	        reaction.put("dislikeCount", 0);
	        reaction.put("userReaction", null);
	    }

	    // 5. 최종 데이터 조합 및 반환
	    response.put("status", "success");
	    response.put("userId", userId);
	    response.put("galleryMap", galleryMap);
	    response.put("reaction", reaction);

	    return ResponseEntity.ok(response);
	}
	
    @PostMapping("/insertComment/{id}")
    public ResponseEntity<?> insertComment(
            @AuthenticationPrincipal AccountDTO user,
            @PathVariable("id") int id,
            @RequestParam(value = "parent", required = false) Integer parent,
            @RequestParam("comment") String comment
    ) {
    	System.out.println("GalleryMobileRestController - insertComment");
        Map<String, Object> map = new HashMap<>();
        map.put("gallery_id", id);
        map.put("user_id", user.getId());
        map.put("parent", parent == null ? 0 : parent);
        map.put("comment", comment);

        try {
            galleryService.insertComment(map);

            // JSON 응답
            return ResponseEntity.ok(
                Map.of(
                    "success", true,
                    "galleryId", id
                )
            );

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                        Map.of(
                            "success", false,
                            "message", e.getMessage()
                        )
                    );
        }
    }
}
