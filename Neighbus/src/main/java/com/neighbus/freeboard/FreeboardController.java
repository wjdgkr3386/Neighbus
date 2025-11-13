package com.neighbus.freeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/freeboard")
public class FreeboardController {

    @Autowired
    private FreeboardService freeboardService;

    // -----------------------------------------------------------------
    // 게시글 목록, 작성 폼, 작성 처리 (기존)
    // -----------------------------------------------------------------
    
    // 게시글 목록을 보여줍니다.
    @GetMapping(value={"/list",""})
    public String list(Model model) {
        List<FreeboardDTO> posts = freeboardService.selectPostList();
        model.addAttribute("posts", posts);
        return "freeboard/postList";
    }

    // 글쓰기 폼을 보여줍니다.
    @GetMapping("/write")
    public String postForm(
        @AuthenticationPrincipal AccountDTO accountDTO, // 🚨 글쓰기 폼은 로그인 유지
        Model model
    ) {
        if (accountDTO == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("post", new FreeboardDTO());
        return "freeboard/postForm";
    }

    // 글쓰기 폼을 처리합니다.
    @PostMapping("/write")
    public String submitPost(
        FreeboardDTO freeboardDTO,
        @AuthenticationPrincipal AccountDTO accountDTO // 🚨 글쓰기 처리는 로그인 유지
    ) {
        if (accountDTO == null) {
            return "redirect:/account/login";
        }
        freeboardDTO.setWriter(accountDTO.getId());
        freeboardService.insertPost(freeboardDTO);

        return "redirect:/freeboard";

    }

    // -----------------------------------------------------------------
    // 1. 게시글 상세 및 댓글 목록 조회 (수정됨)
    // -----------------------------------------------------------------
    @GetMapping("/{id}")
    public String postDetail(
        @PathVariable int id, 
        Model model,
        @AuthenticationPrincipal AccountDTO accountDTO // 🚨 권한 체크를 위해 accountDTO 유지
    ) {
        
        // 1. 게시글 상세 정보 조회 (Service에서 조회수 증가 로직 포함)
        FreeboardDTO post = freeboardService.selectPostDetail(id);
        
        if (post == null) {
            return "redirect:/freeboard/list"; 
        }
        
        // 🚨 수정: 로그인 유저 ID를 Model에 명시적으로 추가
        int currentUserId = 0;
        if (accountDTO != null) {
            currentUserId = accountDTO.getId(); // AccountDTO의 getId() 호출
        }
        
        // 2. 댓글 목록 조회
        List<CommentDTO> comments = freeboardService.getCommentList(id);
        
        // 3. 모델에 담아 뷰로 전달
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("commentForm", new CommentDTO());
        model.addAttribute("currentUserId", currentUserId); // 🚨 유저 ID 추가
        
        return "freeboard/postDetail"; 
    }

    // -----------------------------------------------------------------
    // 2. 댓글 등록 처리 (API Endpoint) - 🚨 권한 체크 제거됨
    // -----------------------------------------------------------------
    @PostMapping("/comment")
    @ResponseBody
    public ResponseEntity<String> registerComment(
        @RequestBody CommentDTO commentDTO,
        @AuthenticationPrincipal AccountDTO accountDTO 
    ) {
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        
        commentDTO.setWriter(accountDTO.getId()); 
        
        boolean success = freeboardService.registerComment(commentDTO);
        
        if (success) {
            return ResponseEntity.ok("댓글이 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록에 실패했습니다.");
        }
    }

    // -----------------------------------------------------------------
    // 3. 댓글 삭제 처리 (API Endpoint)
    // -----------------------------------------------------------------
    @DeleteMapping("/comment/{id}")
    @ResponseBody
    public ResponseEntity<String> removeComment(
        @PathVariable int id,
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        
        // TODO: Service에서 댓글 ID와 유저 ID를 확인하여 권한 체크 후 삭제하는 로직 추가
        
        boolean success = freeboardService.removeComment(id, accountDTO.getId());
        
        if (success) {
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글 삭제에 실패했습니다.");
        }
    }
}