package com.neighbus.admin;

import com.neighbus.inquiry.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // JSON 데이터를 반환하는 컨트롤러
@RequestMapping("/api/admin") 
public class AdminRestController {

    private final InquiryService inquiryService;

    @Autowired // InquiryService 의존성 주입
    public AdminRestController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 1. 문의 목록 조회 API
    @GetMapping("/inquiries")
    public List<Map<String, Object>> getInquiryList() {
        // (권한 체크 로직 필요)
        return inquiryService.getAllInquiries();
    }
    
    // 2. 답변 처리 및 상태 업데이트 API (추가할 메서드)
    @PostMapping("/process-inquiry")
    public ResponseEntity<Map<String, Object>> processInquiry(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();

        try {
            // JS에서 넘어온 데이터 추출
            Object idObj = payload.get("inquiryId"); // ID는 Long, Integer, Double 등 다양한 형태로 넘어올 수 있음

            // 💡 ID를 안전하게 Integer로 변환하는 로직
            Integer inquiryId = null;
            if (idObj instanceof Number) {
                inquiryId = ((Number) idObj).intValue();
            } else if (idObj != null) {
                inquiryId = Integer.parseInt(idObj.toString());
            }

            String action = (String) payload.get("action"); // 'answered' 또는 'closed'
            
            // 상태 문자열을 DB 숫자(INT) 상태로 변환 (answered=2, closed=3)
            int newStatus = (action.equals("answered")) ? 2 : 3; 

            if (inquiryId == null) {
                response.put("status", 0);
                response.put("message", "문의 ID가 유효하지 않습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // DB 업데이트 (Service 호출)
            int result = inquiryService.updateInquiryState(inquiryId, newStatus); 

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "상태가 성공적으로 업데이트되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "업데이트 실패: 해당 ID의 문의를 찾을 수 없습니다.");
                return ResponseEntity.internalServerError().body(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "처리 중 서버 오류가 발생했습니다. 상세: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}