package com.neighbus.gallery;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public List<Map<String ,Object>> getGalleryAll(GalleryDTO galleryDTO){
		//갤러리 정보와 갤러리 이미지 정보 가져오기
		List<Map<String ,Object>> galleryMapList = galleryMapper.getGallery(galleryDTO);
		int minId = 999999999;
		int maxId = 0;
		int j=0;
		for(Map<String ,Object> m : galleryMapList) {
			j = (int) m.get("ID");
			minId = j < minId? j : minId;
			maxId = j > maxId? j : maxId;
		}
		Map<String, Integer> giMap = new HashMap<String, Integer>();
		giMap.put("minId", minId);
		giMap.put("maxId", maxId);
		List<Map<String ,Object>> galleryImageMapList = galleryMapper.getGalleryImage(giMap);
		
		//갤러리 이미지 정보를 리스트로 만들어 갤러리 정보에 추가하여 리턴하기
		for(Map<String ,Object> galleryMap : galleryMapList) {
			List<Map<String ,Object>> list = new ArrayList<Map<String ,Object>>();
			int galleryId = (int)galleryMap.get("ID");
			for(Map<String, Object> galleryImgMap : galleryImageMapList) {
				int imgGalleryId = (int)galleryImgMap.get("GALLERY");
				String galleryImgName = (String) galleryImgMap.get("IMG");
				if(galleryId == imgGalleryId) {
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("ID", (int)galleryImgMap.get("ID"));
					map.put("IMG", galleryImgName);
					list.add(map);
				};
			}
			galleryMap.put("galleryImageMapList", list);
		}
		
		return galleryMapList;
	}
}
