package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GalleryMapper {

	// 갤러리 테이블에 저장
	int insertGallery(GalleryDTO galleryDTO);
	int insertGalleryImage(GalleryDTO galleryDTO);
	int getGalleryMaxId(GalleryDTO galleryDTO);

	//입력된 갤러리 정보 가져오기
	List<Map<String, Object>> getGalleryList(GalleryDTO galleryDTO);

	//단일 게시판 정보 가져오기
	Map<String, Object> getGalleryById(@Param("id") int id);

	//페이징 처리
	int searchAllCnt(@Param("keyword") String keyword);
	
	void insertComment(Map<String ,Object> map);
	
	void updateViewCount(@Param("id") int id);
}
