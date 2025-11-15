package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GalleryServiceImpl implements GalleryService {

	@Autowired
	GalleryMapper galleryMapper;
	
	public void insertGallery(GalleryDTO galleryDTO) {
		System.out.println("GalleryServiceImpl - insertGallery");
		if(galleryMapper.insertGallery(galleryDTO)>0) {
			int galleryId = galleryMapper.getGalleryMaxId(galleryDTO);
			galleryDTO.setGalleryId(galleryId);
			galleryMapper.insertGalleryImage(galleryDTO);
		}
	}

	//갤러리 정보와 갤러리 이미지 정보 가져오기
	public List<Map<String ,Object>> getGalleryList(GalleryDTO galleryDTO){
		return galleryMapper.getGalleryList(galleryDTO.getRowCnt(), galleryDTO.getBeginRowNo());
	}
}
