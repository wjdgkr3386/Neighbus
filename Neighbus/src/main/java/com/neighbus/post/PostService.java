package com.neighbus.post;

import java.util.List;

public interface PostService {

    /** 게시글 작성 */
    void insertPost(PostDTO postDTO);
    
    /** 게시글 목록 조회 */
    List<PostDTO> selectPostList();

    /** 게시글 상세 조회 (조회수 증가 포함) */
    PostDTO selectPostDetail(int id);
}