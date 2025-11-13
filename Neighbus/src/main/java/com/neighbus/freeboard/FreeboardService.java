package com.neighbus.freeboard;

import java.util.List;

public interface FreeboardService {

    /** 게시글 작성 */
    void insertPost(FreeboardDTO freeboardDTO);
    
    /** 게시글 목록 조회 */
    List<FreeboardDTO> selectPostList();

    /** 게시글 상세 조회 (조회수 증가 포함) */
    FreeboardDTO selectPostDetail(int id);
}