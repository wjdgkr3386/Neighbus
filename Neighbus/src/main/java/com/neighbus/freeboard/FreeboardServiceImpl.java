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
}