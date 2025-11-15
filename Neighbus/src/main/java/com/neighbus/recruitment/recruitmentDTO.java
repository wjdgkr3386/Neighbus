package com.neighbus.recruitment;

import java.time.LocalDateTime;

public class recruitmentDTO {
	private int id; // 모집글 id
	private int clubId; //클럽 id
    private String title; // 모집 제목
    private String content; // 모집 내용
    private int writer; // 작성자 id
    private String address; // 만날장소 (주소)
    private int maxUser;  // 최대 유저
    private LocalDateTime created_at;  // 작성일
    private String meetingDate;  // 만날날짜
	public recruitmentDTO(int id, int clubId, String title, String content, int writer, String address, int maxUser,
			LocalDateTime created_at, String meetingDate) {
		super();
		this.id = id;
		this.clubId = clubId;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.address = address;
		this.maxUser = maxUser;
		this.created_at = created_at;
		this.meetingDate = meetingDate;
	}
	public recruitmentDTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClubId() {
		return clubId;
	}
	public void setClubId(int clubId) {
		this.clubId = clubId;
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
	public int getWriter() {
		return writer;
	}
	public void setWriter(int writer) {
		this.writer = writer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public String getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}
    
    
}
