package com.neighbus.account;

public class AccountDTO {

	private int id; 			// 유저 고유 ID (Primary Key)
	private String name;		// 이름
	private String username; 	// 로그인 아이디
	private String pwd; 		// 비밀번호 (암호화 저장)
	private int regionId; 		// FK: 지역 ID
	private String address; 	// 상세 주소
	private String phone; 		// 전화번호
	private String email; 		// 이메일
	private String photo; 		// 프로필 사진 경로
	private int rate; 			// 평점 (DECIMAL 타입 대신 int를 사용했다면 정수형 평점)
	private String birth; 		// 생년월일 (YYMMDD)
	private String sex; 		// 성별
	private String user_uuid; 	// UUID (고유 식별자 문자열)
	private String nickname; 	// 닉네임
	private String regionName; 
    private String provinceName;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUser_uuid() {
		return user_uuid;
	}
	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
    
    
    
}
