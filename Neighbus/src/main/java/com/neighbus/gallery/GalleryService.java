package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

public interface GalleryService {

	void insertGallery(GalleryDTO galleryDTO) throws Exception;
	List<Map<String ,Object>> getGalleryAll(GalleryDTO galleryDTO) throws Exception;
}
