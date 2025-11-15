package com.neighbus.recruitment;

import java.util.List;
import java.util.Map;

public interface RecruitmentService {

	
	/**
     * 모임을 생성합니다.
     * @param dto 생성할 모임 정보
     * @return 생성된 모임의 ID
     */
    int createRecruitment(recruitmentDTO dto);

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
	List<recruitmentDTO> findAllRecruitments();
	// 상세보기
	recruitmentDTO findById(int id);
}
