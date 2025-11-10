// InquiryMapper.java
package com.neighbus.inquiry; // 패키지 경로 확인

import com.neighbus.inquiry.InquiryDto; // 🚨 이 import 문이 올바른지 확인
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InquiryMapper {
    int insertInquiry(InquiryDto dto);
}