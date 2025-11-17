package com.neighbus.club;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// (선택) Lombok을 사용한다면 @Slf4j와 @RequiredArgsConstructor 사용 가능
@Service 
public class ClubServiceImpl implements ClubService {
    
    // (선택) @Slf4j 어노테이션이 없다면 Logger를 직접 선언해야 합니다.
    private static final Logger logger = LoggerFactory.getLogger(ClubServiceImpl.class);

    @Autowired
    private ClubMapper clubMapper; // Mybatis Mapper 주입

    /**
     * 동아리 생성 및 생성자 가입 (트랜잭션 처리)
     */
    @Override
    @Transactional // 이 메서드가 하나의 트랜잭션으로 실행되도록 보장
    public boolean createClubAndAddCreator(ClubDTO clubDTO) {
        try {
            // 1. 동아리 생성 (clubs 테이블 INSERT)
            int clubResult = clubMapper.insertClub(clubDTO);
            
            // 2. 생성자 멤버 추가 (club_member 테이블 INSERT)
            //    (insertClub 쿼리가 <selectKey>로 DTO에 ID를 다시 넣어준다고 가정)
            int memberResult = clubMapper.addCreatorAsMember(clubDTO);
            
            // 두 작업이 모두 성공했는지 확인 (1줄 이상 INSERT)
            return clubResult > 0 && memberResult > 0;
            
        } catch (Exception e) {
            logger.error("동아리 생성 또는 생성자 가입 중 오류 발생", e);
            // @Transactional에 의해 자동 롤백됩니다.
            return false;
        }
    }

    /**
     * 동아리 가입 (중복 확인)
     */
    @Override
    public boolean joinClub(ClubMemberDTO clubMemberDTO) {
        
        // 1. 이미 가입했는지 먼저 확인 (Mapper 호출)
        int count = clubMapper.isMember(clubMemberDTO);
        
        // 2. 이미 가입했다면(count > 0), false 반환
        if (count > 0) {
            logger.warn("User {} is already a member of club {}", 
                         clubMemberDTO.getUserId(), clubMemberDTO.getClubId());
            return false;
        }

        // 3. 가입하지 않았을 때만 INSERT 시도
        try {
            int result = clubMapper.insertClubMember(clubMemberDTO);
            return (result > 0); // 1줄 이상 INSERT 성공 시 true 반환
        } catch (Exception e) {
            logger.error("동아리 가입(INSERT) 중 오류 발생", e);
            return false;
        }
    }

    /**
     * 모든 동아리 조회
     */
    @Override
    public List<ClubDTO> getAllClubs() {
        return clubMapper.findAllClubs();
    }
    
    /**
     * 동아리 상세 조회
     */
    @Override
    public ClubDTO getClubById(int id) {
        return clubMapper.getClubById(id);
    }

    /**
     * 가입 여부 확인 (컨트롤러용)
     */
    @Override
    public int isMember(ClubMemberDTO clubMemberDTO) {
        // (수정) 이전에 'return 0;'으로 되어있던 부분을 수정
        // Mapper를 호출하여 실제 DB를 확인해야 합니다.
        return clubMapper.isMember(clubMemberDTO);
    }
}