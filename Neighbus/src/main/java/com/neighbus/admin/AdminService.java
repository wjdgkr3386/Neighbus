package com.neighbus.admin;

import com.neighbus.freeboard.FreeboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 대시보드 통계 조회
     */
    public Map<String, Object> getDashboardStats() {
        return adminMapper.selectDashboardStats();
    }

    /**
     * 월별 가입자 수 조회
     */
    public List<Map<String, Object>> getMonthlySignups() {
        return adminMapper.selectMonthlySignups();
    }

    /**
     * 동아리별 회원 수 조회 (상위 5개)
     */
    public List<Map<String, Object>> getTopClubsByMembers() {
        return adminMapper.selectTopClubsByMembers();
    }

    /**
     * 게시글 목록 조회 (댓글 수 포함)
     */
    public List<Map<String, Object>> getPostsWithCommentCount() {
        return adminMapper.selectPostsWithCommentCount();
    }

    /**
     * 동아리 목록 조회 (회원 수 포함)
     */
    public List<Map<String, Object>> getAllClubsWithMemberCount() {
        return adminMapper.selectAllClubsWithMemberCount();
    }

    /**
     * 동아리 삭제
     */
    public int deleteClub(int clubId) {
        return adminMapper.deleteClub(clubId);
    }

    /**
     * 갤러리 목록 조회
     */
    public List<Map<String, Object>> getAllGalleries() {
        return adminMapper.selectAllGalleries();
    }

    /**
     * 갤러리 삭제 (관련 데이터 함께 삭제)
     */
    @Transactional
    public int deleteGallery(int galleryId) {
        // 1. 갤러리 댓글 삭제
        adminMapper.deleteGalleryComments(galleryId);

        // 2. 갤러리 이미지 삭제
        adminMapper.deleteGalleryImages(galleryId);

        // 3. 갤러리 본체 삭제
        return adminMapper.deleteGallery(galleryId);
    }

    // [신고 관리 서비스]
    
    public void createReport(ReportDTO reportDTO) {
        adminMapper.insertReport(reportDTO);
    }

    public List<ReportDTO> getAllReports() {
        return adminMapper.selectAllReports();
    }

    public int getReportTotalCount() {
        return adminMapper.countTotalReports();
    }

    public int getReportStatusCount(String status) {
        return adminMapper.countReportsByStatus(status);
    }

    public void updateReportStatus(int id, String status) {
        adminMapper.updateReportStatus(id, status);
    }

    public void deleteReport(int id) {
        adminMapper.deleteReport(id);
    }
}
