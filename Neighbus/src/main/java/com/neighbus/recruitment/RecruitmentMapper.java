package com.neighbus.recruitment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecruitmentMapper {
	
	// 전체 모임 조회
	@Select("SELECT * FROM recruitments")
	List<recruitmentDTO> findAll();
	/*
	 * 모임 생성 (XML ID: createRecruitment)
     * @param dto 생성할 모임 정보 (recruitmentDTO)
     * @return 삽입된 행의 수
     */
    int createRecruitment(recruitmentDTO dto);

    /**
     * 모임 삭제 (XML ID: deleteRecruitment)
     * @param recruitmentId 삭제할 모임의 ID
     * @return 삭제된 행의 수
     */
    int deleteRecruitment(int recruitmentId);

    /**
     * 모임 가입 (XML ID: joinRecruitment)
     * @param params "recruitmentId"와 "userId"를 포함하는 Map
     * @return 삽입된 행의 수
     */
    int joinRecruitment(Map<String, Object> params);

    /**
     * 모임 탈퇴 (XML ID: withdrawalRecruitment)
     * @param params "recruitmentId"와 "userId"를 포함하는 Map
     * @return 삭제된 행의 수
     */
    int withdrawalRecruitment(Map<String, Object> params);
    
    // 모임 상세보기
    recruitmentDTO findById(int id);	
	
}
