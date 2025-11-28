package com.neighbus.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {
    // 전체 회원 목록 조회
    List<Map<String, Object>> selectAllUsers();

    // 회원 삭제
    int deleteUser(@Param("userId") int userId);

    // 대시보드 통계 조회
    Map<String, Object> selectDashboardStats();

    // 월별 가입자 수 조회
    List<Map<String, Object>> selectMonthlySignups();

    // 동아리별 회원 수 조회 (상위 5개)
    List<Map<String, Object>> selectTopClubsByMembers();

    // 게시글 목록 조회 (댓글 수 포함)
    List<Map<String, Object>> selectPostsWithCommentCount();

    // 동아리 목록 조회 (회원 수 포함)
    List<Map<String, Object>> selectAllClubsWithMemberCount();

    // 동아리 삭제
    int deleteClub(@Param("clubId") int clubId);

    // 갤러리 목록 조회
    List<Map<String, Object>> selectAllGalleries();

    // 갤러리 댓글 삭제
    int deleteGalleryComments(@Param("galleryId") int galleryId);

    // 갤러리 이미지 삭제
    int deleteGalleryImages(@Param("galleryId") int galleryId);

    // 갤러리 삭제
    int deleteGallery(@Param("galleryId") int galleryId);
    
    // [신고 관리]
    // 1. 신고 접수
    void insertReport(ReportDTO reportDTO);
    
    // 2. 모든 신고 목록 조회
    List<ReportDTO> selectAllReports();

    // 3. 전체 신고 수 조회
    int countTotalReports();

    // 4. 상태별 신고 수 조회
    int countReportsByStatus(@Param("status") String status);

    // 5. 신고 상태 변경
    void updateReportStatus(@Param("id") int id, @Param("status") String status);

    // 6. 신고 삭제
    void deleteReport(@Param("id") int id);
}

