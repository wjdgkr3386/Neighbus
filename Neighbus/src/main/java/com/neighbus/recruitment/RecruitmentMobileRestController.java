package com.neighbus.recruitment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.account.AccountDTO;

@RestController
@RequestMapping("/api/mobile/recruitment")
public class RecruitmentMobileRestController {

	@Autowired
    RecruitmentService recruitmentService;

    @PostMapping("/new")
    public ResponseEntity<?> createRecruitment(
		@RequestBody RecruitmentDTO recruitmentDTO, 
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
    	System.out.println("RecruitmentMobileRestController - createRecruitment");
        try {
            // 1. 작성자 설정 (로그인한 사용자 ID)
            recruitmentDTO.setWriter(accountDTO.getId());
            
            // 2. 모임 생성 (DB 저장 및 ID 생성)
            recruitmentService.createRecruitment(recruitmentDTO);
            
            // 3. 생성자를 모임 멤버로 자동 가입 처리
            Map<String, Object> joinParams = new HashMap<>();
            joinParams.put("recruitmentId", recruitmentDTO.getId());
            joinParams.put("userId", accountDTO.getId());
            
            recruitmentService.joinRecruitment(joinParams);

            // 4. 성공 응답 반환 (생성된 모임 ID 포함)
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "모임이 생성되었으며 자동으로 가입되었습니다.");
            response.put("recruitmentId", recruitmentDTO.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 에러 발생 시 응답
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "모임 생성 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}