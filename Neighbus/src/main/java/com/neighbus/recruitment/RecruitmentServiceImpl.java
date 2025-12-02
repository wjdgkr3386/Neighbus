package com.neighbus.recruitment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
	 // MyBatis 매퍼 인터페이스를 주입받음 (DI)
    private final RecruitmentMapper recruitmentMapper;

    @Autowired
    public RecruitmentServiceImpl(RecruitmentMapper recruitmentMapper) {
        this.recruitmentMapper = recruitmentMapper;
    }

    /**
     * 모임 생성
     */
    @Override
    @Transactional
    public int createRecruitment(RecruitmentDTO dto) {
        recruitmentMapper.createRecruitment(dto);
        return dto.getId(); 
    }

    /**
     * 모임 삭제
     */
    @Override
    @Transactional
    public int deleteRecruitment(int recruitmentId) {
        return recruitmentMapper.deleteRecruitment(recruitmentId);
    }

    /**
     * 모임 가입 (비즈니스 로직 추가)
     */
    @Override
    @Transactional
    public int joinRecruitment(Map<String, Object> params) {
        int recruitmentId = (int) params.get("recruitmentId");
        int userId = (int) params.get("userId");

        // 1. 해당 모임 정보 조회
        RecruitmentDTO recruitment = recruitmentMapper.findById(recruitmentId);
        if (recruitment == null) {
            System.out.println("가입 실패: 존재하지 않는 모임입니다.");
            return 0; // 존재하지 않는 모임
        }

        // 2. 이미 가입했는지 확인
        if (isMember(recruitmentId, userId)) {
            System.out.println("가입 실패: 이미 가입한 모임입니다.");
            return 0; // 이미 가입함
        }

        // 3. 최대 인원(maxUser)을 초과하는지 확인
        int currentUserCount = recruitmentMapper.countMembersByRecruitmentId(recruitmentId);
        if (currentUserCount >= recruitment.getMaxUser()) {
            System.out.println("가입 실패: 모임 인원이 모두 찼습니다.");
            return 0; // 인원 초과
        }
        
        // 4. 가입 처리
        return recruitmentMapper.joinRecruitment(params);
    }

    /**
     * 모임 탈퇴
     */
    @Override
    @Transactional
    public int withdrawalRecruitment(Map<String, Object> params) {
        // TODO: 방장(writer)은 탈퇴할 수 없도록 막는 로직 추가 필요
        return recruitmentMapper.withdrawalRecruitment(params);
    }

    /**
     * 모임 전체 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecruitmentDTO> findAllRecruitments() {
        return recruitmentMapper.findAll();
    }
    // 가입 클럽 모임 리스트
    @Override
    public List<RecruitmentDTO> getRecruitmentsByMyClubs(int userId) {
        // 필요시 이곳에서 비즈니스 로직(예: 사용자 존재 여부 확인)을 추가할 수 있습니다.
        return recruitmentMapper.findRecruitmentsByMyClubs(userId);
    }
    
  
    
    /**
     * 특정 동아리(clubId)의 특정 날짜(date) 모집글 목록 조회
     * @param clubId 동아리 ID
     * @param date 날짜 문자열 (YYYY-MM-DD)
     * @return 모집글 리스트
     */
    @Override
    public List<RecruitmentDTO> getRecruitmentsByClubAndDate(int clubId, String date) {
        return recruitmentMapper.findRecruitmentsByClubAndDate(clubId, date);
    }

	/**
     * 모임 상세 조회
     */
    @Override
    @Transactional(readOnly = true)
    public RecruitmentDTO findById(int id) {
        return recruitmentMapper.findById(id);
    }

    /**
     * 현재 가입자 수 조회
     */
    @Override
    @Transactional(readOnly = true)
    public int countMembers(int recruitmentId) {
        return recruitmentMapper.countMembersByRecruitmentId(recruitmentId);
    }

    /**
     * 가입 여부 확인
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isMember(int recruitmentId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("recruitmentId", recruitmentId);
        params.put("userId", userId);
        return recruitmentMapper.isMember(params) > 0;
    }
    
    @Override
    public List<RecruitmentDTO> getRecruitmentsByUserId(int userId) {
        return recruitmentMapper.findRecruitmentsByUserId(userId);
    }
    
    @Override
    public int countByRecruitment() {
        return recruitmentMapper.countByRecruitment();
    }
    
    @Override
    public List<Integer> getMemberIdsByRecruitmentId(int recruitmentId) {
        return recruitmentMapper.findMemberIdsByRecruitmentId(recruitmentId);
    }
    
    @Override
    public Map<String, Object> getGatheringsPaginated(int page, int size, String keyword, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        params.put("offset", (page - 1) * size);
        params.put("keyword", keyword);
        params.put("status", status);

        List<Map<String, Object>> gatherings = recruitmentMapper.selectGatheringsPaginated(params);
        int totalElements = recruitmentMapper.countTotalGatherings(params);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        // Additional stats
        // These stats are calculated without filters for the top cards.
        int activeGatherings = recruitmentMapper.countTotalGatherings(Map.of("status", "진행중"));
        int endedGatherings = recruitmentMapper.countTotalGatherings(Map.of("status", "마감"));

        Map<String, Object> response = new HashMap<>();
        response.put("content", gatherings);
        response.put("totalPages", totalPages);
        response.put("totalElements", totalElements);
        response.put("number", page);
        response.put("size", size);
        response.put("activeGatherings", activeGatherings);
        response.put("endedGatherings", endedGatherings);

        return response;
    }
}
