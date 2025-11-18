package com.neighbus.club;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClubMapper {
	// 새로운 동아리를 clubs 테이블에 삽입합니다
	int insertClub(ClubDTO clubDTO);
	// 동아리 생성자(creator)를 club_member 테이블에 멤버로 추가합니다
	int addCreatorAsMember(ClubDTO clubDTO);
	// 동아리 가입 
	int insertClubMember(ClubMemberDTO clubMemberDTO);
	// 모든 동아리 목록을 가져옵니다.
	List<ClubDTO> findAllClubs();
	// 상세보기
	ClubDTO getClubById(int id);
	// 중복 동아리 검색
	int isMember(ClubMemberDTO dto);
	
	// 시,도 카테고리 분류
	 List<ClubDTO> getOderProvince(@Param("provinceId") int provinceId);
	 List<ClubDTO> getOderCity(Map<String, Object> params);
	
	
}
