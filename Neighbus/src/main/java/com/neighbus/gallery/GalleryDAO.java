package com.neighbus.gallery;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GalleryDAO {

	// 갤러리 테이블에 저장
	int insertGallery(GalleryDTO galleryDTO);
	int insertGalleryImage(GalleryDTO galleryDTO);
	int getGalleryMaxId(GalleryDTO galleryDTO);
}
