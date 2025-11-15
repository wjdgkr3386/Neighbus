package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

public interface GalleryService {

	void insertGallery(GalleryDTO galleryDTO) throws Exception;
	List<Map<String ,Object>> getGalleryList(GalleryDTO galleryDTO) throws Exception;
	void insertComment(Map<String ,Object> map) throws Exception;
	void updateViewCount(int id) throws Exception;
}
