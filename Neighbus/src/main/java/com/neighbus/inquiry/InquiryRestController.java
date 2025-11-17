package com.neighbus.inquiry; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// 동일 패키지에 있기 때문에 InquiryDto, InquiryService는 별도 import 없이 사용 가능 (권장)

@RestController 
@RequestMapping("/api/inquiry") 
public class InquiryRestController {

    private final InquiryService inquiryService;

    @Autowired 
    public InquiryRestController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerInquiry(@RequestBody InquiryDto inquiryDto) {
        Map<String, Object> response = new HashMap<>();

        System.out.println(1);
        try {
            // ⚠️ 임시 사용자 ID 설정: 
            // DB의 users 테이블에 1번 ID가 존재해야 합니다. (FK 제약조건 만족용)
            Integer currentUserId = 1; 

            int result = inquiryService.registerInquiry(inquiryDto, currentUserId);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "문의가 성공적으로 접수되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "문의 등록에 실패했습니다. (DB 삽입 오류)");
                return ResponseEntity.internalServerError().body(response);
            }

        } catch (Exception e) {
            // SQL 오류, 연결 오류 등 서버 내부 문제 처리
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "서버 내부 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}