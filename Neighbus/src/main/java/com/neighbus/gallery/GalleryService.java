package com.neighbus.gallery;

import java.util.List;
import java.util.Map;

public interface GalleryService {

	int insertGallery(GalleryDTO galleryDTO) throws Exception;
	List<Map<String ,Object>> getGalleryAll() throws Exception;
}
