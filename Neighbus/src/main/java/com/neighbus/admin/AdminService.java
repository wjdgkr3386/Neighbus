package com.neighbus.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<Map<String, Object>> getAllUsers() {
        return adminMapper.selectAllUsers();
    }
}
