package com.neighbus.admin;

import com.neighbus.inquiry.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") 
public class AdminController {
    
    private final InquiryService inquiryService;

    @Autowired
    public AdminController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("/inquiries") // URL: http://localhost:8080/admin/inquiries
    public String manageInquiries() {
        // 🚨 뷰 이름을 "admin_inquiry"로 확정합니다.
        // Spring은 src/main/resources/templates/admin/admin_inquiry.html을 찾습니다.
        return "admin/admin_inquiry"; 
    }
}