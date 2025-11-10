package com.neighbus.gallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GalleryServiceImpl implements GalleryService {

	@Autowired
	GalleryMapper galleryMapper;
	
	public int insertGallery(GalleryDTO galleryDTO) {
		System.out.println("GalleryServiceImpl - insertGallery");
		if(galleryMapper.insertGallery(galleryDTO)>0) {
			int galleryId = galleryMapper.getGalleryMaxId(galleryDTO);
			galleryDTO.setGalleryId(galleryId);
			galleryMapper.insertGalleryImage(galleryDTO);
		}
		return 0;
	}
}
