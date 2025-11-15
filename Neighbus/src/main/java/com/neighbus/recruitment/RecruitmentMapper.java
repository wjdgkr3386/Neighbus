package com.neighbus.recruitment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitmentMapper {
	
	// 전체 모임 조회
	List<recruitmentDTO> findAll();

	// 모임 생성
    int createRecruitment(recruitmentDTO dto);

    // 모임 삭제
    int deleteRecruitment(int recruitmentId);

    // 모임 가입
    int joinRecruitment(Map<String, Object> params);

    // 모임 탈퇴
    int withdrawalRecruitment(Map<String, Object> params);
    
    // 모임 상세보기
    recruitmentDTO findById(int id);
    
    // 가입 여부 확인
    int isMember(Map<String, Object> params);
    
    // 현재 가입자 수 확인
    int countMembersByRecruitmentId(int recruitmentId);
}
