package com.neighbus.freeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FreeboardServiceImpl implements FreeboardService {

    @Autowired
    private FreeboardMapper freeboardMapper;

    /**
     * 게시글 작성
     */
    @Override
    public void insertPost(FreeboardDTO freeboardDTO) {
        System.out.println("FreeboardServiceImpl - insertPost");
        freeboardMapper.insertPost(freeboardDTO);
    }

    /**
     * 게시글 목록 조회
     */
    @Override
    public List<FreeboardDTO> selectPostList() {
        System.out.println("FreeboardServiceImpl - selectPostList");
        return freeboardMapper.selectPostList();
    }

    /**
     * 게시글 목록 조회 (페이징)
     */
    @Override
    public List<FreeboardDTO> selectPostListWithPaging(FreeboardDTO freeboardDTO) {
        System.out.println("FreeboardServiceImpl - selectPostListWithPaging");
        return freeboardMapper.selectPostListWithPaging(freeboardDTO);
    }

    /**
     * 게시글 전체 개수 조회
     */
    @Override
    public int searchAllCnt(String keyword) {
        System.out.println("FreeboardServiceImpl - searchAllCnt");
        return freeboardMapper.searchAllCnt(keyword);
    }

    /**
     * 게시글 상세 조회 및 조회수 증가
     */
    @Override
    public FreeboardDTO selectPostDetail(int id) {
        System.out.println("FreeboardServiceImpl - selectPostDetail");
        
        // 1. 조회수 증가 (Mapper에 구현되어 있어야 함)
        freeboardMapper.incrementViewCount(id);
        
        // 2. 상세 정보 조회
        return freeboardMapper.selectPostDetail(id);
    }

    // ==========================================================
    // 댓글 관련 메서드 구현
    // ==========================================================

    @Override
    public boolean registerComment(CommentDTO commentDTO) {
        // 성공적으로 1개 이상 삽입되었는지 확인
        return freeboardMapper.insertComment(commentDTO) > 0;
    }

    @Override
    public boolean removeComment(int id, int userId) {
        CommentDTO comment = freeboardMapper.selectCommentById(id);
        if (comment != null && comment.getWriter() == userId) {
            return freeboardMapper.deleteComment(id) > 0;
        }
        return false;
    }

    @Override
    public List<CommentDTO> getCommentList(int freeboardId) {
        // 댓글 목록 조회
        return freeboardMapper.selectCommentList(freeboardId);
    }

    @Override
    public boolean updatePost(FreeboardDTO freeboardDTO, int userId) {
        FreeboardDTO post = freeboardMapper.selectPostDetail(freeboardDTO.getId());
        if (post != null && post.getWriter() == userId) {
            freeboardMapper.updatePost(freeboardDTO);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePost(int id, int userId) {
        FreeboardDTO post = freeboardMapper.selectPostDetail(id);
        if (post != null && post.getWriter() == userId) {
            freeboardMapper.deletePost(id);
            return true;
        }
        return false;
    }
    
    
}