package com.neighbus.freeboard;

import java.util.List;

public interface FreeboardService {

    /** 게시글 작성 */
    void insertPost(FreeboardDTO freeboardDTO);
    
    /** 게시글 목록 조회 */
    List<FreeboardDTO> selectPostList();

    /** 게시글 상세 조회 (조회수 증가 포함) */
    FreeboardDTO selectPostDetail(int id);
    
    // ==========================================================
    // 댓글 관련 메서드 추가
    // ==========================================================
    
    // 댓글 등록 (INSERT)
    boolean registerComment(CommentDTO commentDTO);
    
    // 댓글 삭제 (DELETE)
    boolean removeComment(int id);
    
    // 댓글 목록 조회 (SELECT)
    List<CommentDTO> getCommentList(int freeboardId); 
}