package com.neighbus.recruitment;

import java.util.List;
import java.util.Map;

public interface RecruitmentService {

    /**
     * 모임을 생성합니다.
     * @param dto 생성할 모임 정보
     * @return 생성된 모임의 ID
     */
    int createRecruitment(RecruitmentDTO dto);

    /**
     * 모임을 삭제합니다.
     * @param recruitmentId 삭제할 모임의 ID
     * @return 삭제 성공 여부 (1: 성공, 0: 실패)
     */
    int deleteRecruitment(int recruitmentId);

    /**
     * 모임에 가입합니다.
     * @param params (Map) "recruitmentId", "userId" 포함
     * @return 가입 성공 여부 (1: 성공, 0: 실패)
     */
    int joinRecruitment(Map<String, Object> params);

    /**
     * 모임에서 탈퇴합니다.
     * @param params (Map) "recruitmentId", "userId" 포함
     * @return 탈퇴 성공 여부 (1: 성공, 0: 실패)
     */
    int withdrawalRecruitment(Map<String, Object> params);

    // 전체조회
	List<RecruitmentDTO> findAllRecruitments();

	// 상세보기
	RecruitmentDTO findById(int id);

    // 가입자 수 조회
    int countMembers(int recruitmentId);

    // 가입 여부 확인
    boolean isMember(int recruitmentId, int userId);
    // 내가 가입한 모임 리스트
    List<RecruitmentDTO> getRecruitmentsByUserId(int userId);
    // 가입 클럽 모임 리스트
    public List<RecruitmentDTO> getRecruitmentsByMyClubs(int userId);
    // 날짜별 모임
    public List<RecruitmentDTO> getRecruitmentsByClubAndDate(int clubId, String date);

    
    // 모집중인 갯수
    int countByRecruitment();

    Map<String, Object> getGatheringsPaginated(int page, int size, String keyword, String status);

    List<Integer> getMemberIdsByRecruitmentId(int recruitmentId);
}

