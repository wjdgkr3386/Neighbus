package com.neighbus.inquiry; 

import com.neighbus.inquiry.InquiryDto; 
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface InquiryMapper {
    int insertInquiry(InquiryDto dto);
    
    List<Map<String, Object>> selectAllInquiries();

    /** 💡 추가: 문의 상태 업데이트 메서드 */
    int updateInquiryStatus(@Param("id") int inquiryId, @Param("status") int newStatus); 
}