package com.neighbus.club;

import java.time.LocalDateTime;

public class ClubDTO {
	private int ClubId;                 // ID
	private int Id;
    private String clubName;        // 동아리이름 (club_name)
    private int creator;            // 생성자 (creator, FK: 유저ID)
    private int city;               // 소속 지역 (city, FK: 지역ID)
    private LocalDateTime createdAt;  // 생성일 (created_at)
    
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getClubId() {
		return ClubId;
	}
	public void setClubId(int clubId) {
		ClubId = clubId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public ClubDTO(int clubId, int id, String clubName, int creator, int city, LocalDateTime createdAt) {
		super();
		ClubId = clubId;
		Id = id;
		this.clubName = clubName;
		this.creator = creator;
		this.city = city;
		this.createdAt = createdAt;
	}
	public ClubDTO() {}
    
}
