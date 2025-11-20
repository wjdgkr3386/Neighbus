package com.neighbus.admin;

import com.neighbus.freeboard.FreeboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final AdminMapper adminMapper;
    private final FreeboardMapper freeboardMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper, FreeboardMapper freeboardMapper) {
        this.adminMapper = adminMapper;
        this.freeboardMapper = freeboardMapper;
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<Map<String, Object>> getAllUsers() {
        return adminMapper.selectAllUsers();
    }

    /**
     * 회원 삭제
     */
    public int deleteUser(int userId) {
        return adminMapper.deleteUser(userId);
    }

    /**
     * 게시글 삭제 (관리자용 - 권한 체크 없음)
     */
    public void deletePost(int postId) {
        freeboardMapper.deletePost(postId);
    }
}
