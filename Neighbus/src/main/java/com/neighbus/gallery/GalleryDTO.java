package com.neighbus.gallery;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class GalleryDTO {

	private String title;
	private String content;
	private List<MultipartFile> fileList;
	
	
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
	public List<MultipartFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<MultipartFile> fileList) {
		this.fileList = fileList;
	}
	
	
}
