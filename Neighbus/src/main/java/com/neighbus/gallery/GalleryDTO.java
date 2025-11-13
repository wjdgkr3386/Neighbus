package com.neighbus.gallery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class GalleryDTO {

	private String username;
	private String title;
	private String content;
	private int galleryId;
	private List<MultipartFile> fileList = new ArrayList<MultipartFile>();
	private List<String> fileNameList = new ArrayList<String>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(int galleryId) {
		this.galleryId = galleryId;
	}
	public List<MultipartFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<MultipartFile> fileList) {
		this.fileList = fileList;
	}
	public List<String> getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}
	
	
}
