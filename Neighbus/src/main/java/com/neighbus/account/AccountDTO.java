package com.neighbus.account;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountDTO implements UserDetails {

	private String name;		// 이름
	private String username; 	// 로그인 아이디
	private String password; 	// 비밀번호 (암호화 저장)
	private int city; 			// FK: 지역 ID
	private String address; 	// 상세 주소
	private String phone; 		// 전화번호
	private String email; 		// 이메일
	private String image; 		// 프로필 사진 경로
	private int grade; 			// 평점 (DECIMAL 타입 대신 int를 사용했다면 정수형 평점)
	private String birth; 		// 생년월일 (YYMMDD)
	private String sex; 		// 성별
	private String user_uuid; 	// UUID (고유 식별자 문자열)
	private String nickname; 	// 닉네임
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
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

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String toString() {
		return "AccountDTO [name=" + name + ", username=" + username + ", password=" + password + ", city=" + city
				+ ", address=" + address + ", phone=" + phone + ", email=" + email + ", image=" + image + ", grade="
				+ grade + ", birth=" + birth + ", sex=" + sex + ", user_uuid=" + user_uuid + ", nickname=" + nickname
				+ "]";
	}
    
}
