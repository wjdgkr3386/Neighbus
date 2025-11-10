package com.neighbus.inquiry; // 🚨 'service'를 제거합니다.

import com.neighbus.inquiry.InquiryDto;   // 🚨 DTO 임포트 경로 수정
import com.neighbus.inquiry.InquiryMapper; // 🚨 Mapper 임포트 경로 수정
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    @Autowired
    public InquiryService(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    public int registerInquiry(InquiryDto dto, Integer currentUserId) {
        
        // DTO 필드에 접근하는 메서드 이름이 DTO 파일 수정으로 해결됩니다.
        if (dto.getTitle() == null || dto.getContent() == null || currentUserId == null) {
            return 0; 
        }

        dto.setWriterId(currentUserId);
        
        return inquiryMapper.insertInquiry(dto);
    }
}