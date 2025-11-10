package com.neighbus.mypage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.neighbus.account.AccountDTO; // 회원 DTO 재사용

@Mapper
public interface MyPageMapper {

    // 내 정보 불러오기 (회원 DTO 재사용)
    AccountDTO getMyInfo(String username);
    
    // 내가 쓴 글 목록
    List<Map<String, Object>> getMyPosts(String username);
    
    // 내가 쓴 댓글 목록
    List<Map<String, Object>> getMyComments(String username);
    
    // 내가 누른 좋아요 수
    int getMyLikesCount(String username);
}
