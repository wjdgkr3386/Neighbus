package com.neighbus.recruitment;

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
     * (useGeneratedKeys="true" keyProperty="id" 설정으로 
     * 파라미터 dto 객체의 id 필드에 생성된 ID가 채워집니다.)
     */
    @Override
    @Transactional // 데이터 변경이므로 트랜잭션 처리
    public int createRecruitment(recruitmentDTO dto) {
        // 매퍼의 createRecruitment 메서드 호출
        recruitmentMapper.createRecruitment(dto);
        // DTO에 채워진 id 반환
        return dto.getId(); 
    }

    /**
     * 모임 삭제
     */
    @Override
    @Transactional
    public int deleteRecruitment(int recruitmentId) {
        // 매퍼의 deleteRecruitment 메서드 호출
        return recruitmentMapper.deleteRecruitment(recruitmentId);
    }

    /**
     * 모임 가입
     */
    @Override
    @Transactional
    public int joinRecruitment(Map<String, Object> params) {
        // TODO: 가입 전 중복 가입 확인, 최대 인원 수(maxUser) 체크 등의 로직 필요
        
        // 매퍼의 joinRecruitment 메서드 호출
        return recruitmentMapper.joinRecruitment(params);
    }

    /**
     * 모임 탈퇴
     */
    @Override
    @Transactional
    public int WithdrawalRecruitment(Map<String, Object> params) {
        // TODO: 방장(writer)인지 확인하는 로직, 방장 탈퇴 시 처리 로직 필요
        
        // 매퍼의 WithdrawalRecruitment 메서드 호출
        return recruitmentMapper.withdrawalRecruitment(params);
    }
    /**
     * 모임 전체 목록 조회
     */
    @Override
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<recruitmentDTO> findAllRecruitments() {
        // 매퍼의 findAll 메서드 호출
        return recruitmentMapper.findAll();
    }
}
