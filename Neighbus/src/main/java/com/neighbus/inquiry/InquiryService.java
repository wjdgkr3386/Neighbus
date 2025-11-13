package com.neighbus.inquiry; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    @Autowired
    public InquiryService(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    public int registerInquiry(InquiryDto dto, Integer currentUserId) {
        if (dto.getTitle() == null || dto.getContent() == null || currentUserId == null) {
            return 0; 
        }
        dto.setWriterId(currentUserId);
        return inquiryMapper.insertInquiry(dto);
    }
    
    public List<Map<String, Object>> getAllInquiries() {
        return inquiryMapper.selectAllInquiries();
    }
    
    /** 💡 추가: 문의 상태 업데이트 서비스 메서드 */
    public int updateInquiryStatus(int inquiryId, int newStatus) {
        return inquiryMapper.updateInquiryStatus(inquiryId, newStatus);
    }
}