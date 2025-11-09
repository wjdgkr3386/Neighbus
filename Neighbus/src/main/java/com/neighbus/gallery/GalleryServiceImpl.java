package com.neighbus.gallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GalleryServiceImpl implements GalleryService {

	@Autowired
	GalleryDAO galleryDAO;
	
	public int insertGallery(GalleryDTO galleryDTO) {
		System.out.println("GalleryServiceImpl - insertGallery");
		if(galleryDAO.insertGallery(galleryDTO)>0) {
			int galleryId = galleryDAO.getGalleryMaxId(galleryDTO);
			galleryDTO.setGalleryId(galleryId);
			galleryDAO.insertGalleryImage(galleryDTO);
		}
		return 0;
	}
}
