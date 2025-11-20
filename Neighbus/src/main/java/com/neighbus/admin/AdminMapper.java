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

    // 게시글 목록 조회 (댓글 수 포함)
    List<Map<String, Object>> selectPostsWithCommentCount();

    // 동아리 목록 조회 (회원 수 포함)
    List<Map<String, Object>> selectAllClubsWithMemberCount();

    // 동아리 삭제
    int deleteClub(@Param("clubId") int clubId);
}
