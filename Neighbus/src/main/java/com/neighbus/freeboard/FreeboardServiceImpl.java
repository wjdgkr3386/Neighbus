package com.neighbus.freeboard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighbus.alarm.NotificationService;

@Transactional
@Service
public class FreeboardServiceImpl implements FreeboardService {

    @Autowired
    private FreeboardMapper freeboardMapper;
    private final NotificationService notificationService;
    
    

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
        int result = freeboardMapper.insertComment(commentDTO);

        // 2. 성공 시(1개 이상 삽입) 알림 발송 로직 실행
        if (result > 0) {
            sendCommentNotification(commentDTO);
        }

        // 3. 결과 반환 (기존 로직 유지)
        return result > 0;
    }

    // 코드가 지저분해지지 않게 알림 메서드를 따로 뺐습니다.
    private void sendCommentNotification(CommentDTO commentDTO) {
        try {
            // A. 게시글 번호(bno)로 게시글 정보를 조회합니다. (작성자를 알기 위해)
            // 본인 Mapper에 있는 게시글 상세 조회 메서드(예: read, selectOne)를 사용하세요.
            FreeboardDTO board = freeboardMapper.selectPostDetail(commentDTO.getFreeboard()); 

            if (board != null) {
                int postOwnerId = board.getWriter(); // 게시글 작성자
                int commenterId = commentDTO.getWriter(); // 댓글 작성자

                // B. 자기가 자기 글에 쓴 댓글은 알림 안 보냄
                if (postOwnerId!=commenterId) {
                    notificationService.send(
                        postOwnerId, 
                        "COMMENT", 
                        "작성하신 글에 새 댓글이 달렸습니다.", 
                        "/freeboard/" + commentDTO.getFreeboard() // 이동할 URL
                    );
                }
            }
        } catch (Exception e) {
            // 알림 실패해도 댓글 등록은 성공처리 되어야 하므로 로그만 찍음
            System.err.println("알림 전송 실패: " + e.getMessage());
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