package com.neighbus.club;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClubServiceImpl implements ClubService{
	
	@Autowired
    private ClubMapper clubMapper;

	@Override
	@Transactional
	public boolean createClubAndAddCreator(ClubDTO clubDTO) {
		// 1. 동아리 생성 (clubs 테이블 INSERT)
        int clubResult = clubMapper.insertClub(clubDTO);
        
        // * clubMapper.insertClub 실행 후, clubDTO 객체에는 새로 생성된 ID가 채워집니다.
        
        // 2. 생성자 멤버 추가 (club_member 테이블 INSERT)
        int memberResult = clubMapper.addCreatorAsMember(clubDTO);
        
        // 두 작업이 모두 성공했는지 확인
        return clubResult > 0 && memberResult > 0;
		
	}

	@Override
	public boolean joinClub(ClubMemberDTO clubMemberDTO) {
		// Mapper에 쿼리가 있으므로 호출만 합니다.
        int result = clubMapper.insertClubMember(clubMemberDTO);
		return result > 0;
	}

	@Override
	public List<ClubDTO> getAllClubs() {
		return clubMapper.findAllClubs();
	}
	
	@Override
	public ClubDTO getClubById(int id) {
	    return clubMapper.getClubById(id);
	}

	
	
}
