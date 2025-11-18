package com.neighbus.club;

import java.time.LocalDateTime;

public class ClubDTO {
	private int ClubId; // club_memeber ID
	private int Id; 
	private String clubName; // 동아리이름 (club_name)
	private int city; // 소속 지역 (city, FK: 지역ID)
	private String cityName; // 지역 이름
	private String cityImg; // 지역 이름
	private int provinceId; // 소속 지역 (city, FK: 지역ID)
	private String provinceName; // 지역 이름
	private String clubInfo; // 동아리 상세정보
	private LocalDateTime createdAt; // 생성일 (created_at)
	public int getClubId() {
		return ClubId;
	}
	public void setClubId(int clubId) {
		ClubId = clubId;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityImg() {
		return cityImg;
	}
	public void setCityImg(String cityImg) {
		this.cityImg = cityImg;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getClubInfo() {
		return clubInfo;
	}
	public void setClubInfo(String clubInfo) {
		this.clubInfo = clubInfo;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "ClubDTO [ClubId=" + ClubId + ", Id=" + Id + ", clubName=" + clubName + ", city=" + city + ", cityName="
				+ cityName + ", cityImg=" + cityImg + ", provinceId=" + provinceId + ", provinceName=" + provinceName
				+ ", clubInfo=" + clubInfo + ", createdAt=" + createdAt + "]";
	}
	
	
	
}
