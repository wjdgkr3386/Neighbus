package com.neighbus.club;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ClubService {
	// 동아리 생성
	@Transactional
    boolean createClubAndAddCreator(ClubDTO clubDTO);
    
   // 기존 동아리에 유저를 멤버로 가입시킵니다.    
    boolean joinClub(ClubMemberDTO clubMemberDTO);
    
    // 모든 동아리 목록을 가져옵니다.
    List<ClubDTO> getAllClubs();
    //동아리 상세보기
    ClubDTO getClubById (int id);
    // 동아리 탈퇴
    void deleteClubMember(Long clubId, Long userId);
    
   int isMember(ClubMemberDTO clubMemberDTO);

   // 페이징 처리를 위한 메소드
   int getClubCount(String keyword);
   List<ClubDTO> getClubListWithPaging(ClubDTO clubDTO);
}
