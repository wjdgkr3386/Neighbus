package com.neighbus.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighbus.post.PostMapper; // Mapper 주입을 위해 필요

@Transactional // 트랜잭션 관리가 필요한 계층에 적용합니다.
@Service
public class PostServiceImpl implements PostService { // ⭐ 인터페이스를 구현합니다.

    @Autowired
    private PostMapper postMapper;

    /**
     * 게시글 작성
     */
    @Override
    public void insertPost(PostDTO postDTO) {
        System.out.println("PostServiceImpl - insertPost");
        postMapper.insertPost(postDTO);
    }

    /**
     * 게시글 목록 조회
     */
    @Override
    public List<PostDTO> selectPostList() {
        System.out.println("PostServiceImpl - selectPostList");
        return postMapper.selectPostList();
    }

    /**
     * 게시글 상세 조회 및 조회수 증가
     */
    @Override
    public PostDTO selectPostDetail(int id) {
        System.out.println("PostServiceImpl - selectPostDetail");
        
        // 1. 조회수 증가 (Mapper에 구현되어 있어야 함)
        postMapper.incrementViewCount(id);
        
        // 2. 상세 정보 조회
        return postMapper.selectPostDetail(id);
    }
}