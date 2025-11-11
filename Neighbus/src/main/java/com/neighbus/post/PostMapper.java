package com.neighbus.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
	// 게시글 작성 (insertPost)
    // PostDTO를 받아 처리하며, keyProperty="id" 설정에 따라 PostDTO 객체의 id 필드가 업데이트됩니다.
    void insertPost(PostDTO postDTO);

    // 게시글 목록 조회 (selectPostList)
    // PostDTO 리스트를 반환합니다.
    List<PostDTO> selectPostList();
    
    // 게시글 상세 조회 (selectPostDetail)
    // 게시글 ID(int)를 받아 PostDTO 객체를 반환합니다.
    PostDTO selectPostDetail(int id);
    
    // 조회수 증가 (incrementViewCount)
    // 게시글 ID(int)를 받아 조회수를 1 증가시킵니다.
    void incrementViewCount(int id);

}
