package com.neighbus.freeboard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighbus.alarm.NotificationService;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FreeboardServiceImpl implements FreeboardService {

    @Autowired
    private FreeboardMapper freeboardMapper;
    @Autowired
    private NotificationService notificationService;
    
    

    public FreeboardServiceImpl(FreeboardMapper freeboardMapper, NotificationService notificationService) {
		super();
		this.freeboardMapper = freeboardMapper;
		this.notificationService = notificationService;
	}

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
    public List<Map<String,Object>> selectPostListWithPaging(FreeboardDTO freeboardDTO) {
        System.out.println("FreeboardServiceImpl - selectPostListWithPaging");
        return freeboardMapper.selectPostListWithPaging(freeboardDTO);
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
        // 1. 댓글 삽입 실행 (결과를 int로 받음)
    	// 1. 댓글 삽입 실행
        int result = freeboardMapper.insertComment(commentDTO);
        
        // 🚨추가: result 값 확인
        System.out.println("DEBUG: insertComment Result Value: " + result);

        // 2. 성공 시(1개 이상 삽입) 알림 발송 로직 실행
        if (result > 0) {
            System.out.println("DEBUG: Notification Logic Initiated."); // 🚨추가
            sendCommentNotification(commentDTO);
        } else {
            System.out.println("DEBUG: Notification Skipped (Result <= 0)."); // 🚨추가
        }
        
        // 3. 결과 반환
        return result > 0;
    }

    private void sendCommentNotification(CommentDTO commentDTO) {
        try {
            System.out.println("DEBUG: Entered sendCommentNotification method.");
            
            FreeboardDTO board = freeboardMapper.selectPostDetail(commentDTO.getFreeboard()); 

            // 🚨추가: board 객체가 null인지 확인하는 로그
            if (board == null) {
                System.err.println("DEBUG ERROR: FreeboardDTO is NULL. 게시글 정보를 찾을 수 없습니다! BNO: " + commentDTO.getFreeboard());
                return; // null이면 알림 전송 로직을 여기서 중단
            }

            int postOwnerId = board.getWriter(); 
            System.out.println("DEBUG: Post Owner ID (작성자): " + postOwnerId); // 🚨추가
            
            int commenterId = commentDTO.getWriter(); 
            System.out.println("DEBUG: Commenter ID (댓글 작성자): " + commenterId); // 🚨추가

            // ... (나머지 로직)

        } catch (Exception e) {
            System.err.println("알림 전송 실패: " + e.getMessage());
            e.printStackTrace(); // 스택 트레이스로 정확한 위치 확인
        }
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