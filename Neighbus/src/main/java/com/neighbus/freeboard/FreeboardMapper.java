package com.neighbus.freeboard;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FreeboardMapper {
	// 게시글 작성 (insertPost)
    // FreeboardDTO를 받아 처리하며, keyProperty="id" 설정에 따라 FreeboardDTO 객체의 id 필드가 업데이트됩니다.
    void insertPost(FreeboardDTO freeboardDTO);

    // 게시글 목록 조회 (selectPostList)
    // FreeboardDTO 리스트를 반환합니다.
    List<FreeboardDTO> selectPostList();
    
    // 게시글 상세 조회 (selectPostDetail)
    // 게시글 ID(int)를 받아 FreeboardDTO 객체를 반환합니다.
    FreeboardDTO selectPostDetail(int id);
    
    // 조회수 증가 (incrementViewCount)
    // 게시글 ID(int)를 받아 조회수를 1 증가시킵니다.
    void incrementViewCount(int id);
    
    // 댓글 생성 
    @Insert("INSERT INTO freeboard_comments (freeboard, parent, writer, content, created_at) " +
            "VALUES (#{freeboard}, #{parent}, #{writer}, #{content}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertComment(CommentDTO commentDTO);
    // 삭제
    @Delete("DELETE FROM freeboard_comments WHERE id = #{id}")
    int deleteComment(@Param("id") int id);
    // 게시판 댓글 리스트
    List<CommentDTO> selectCommentList(@Param("freeboardId") int freeboardId);
}
