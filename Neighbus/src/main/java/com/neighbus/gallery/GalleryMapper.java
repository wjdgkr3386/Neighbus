package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GalleryMapper {

	// 갤러리 테이블에 저장
	int insertGallery(GalleryDTO galleryDTO);
	int insertGalleryImage(GalleryDTO galleryDTO);
	int getGalleryMaxId(GalleryDTO galleryDTO);

	List<Map<String ,Object>> getGallery();
	List<Map<String ,Object>> getGalleryImage();
}
