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
}
