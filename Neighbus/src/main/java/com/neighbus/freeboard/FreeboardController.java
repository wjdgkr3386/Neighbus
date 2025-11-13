package com.neighbus.freeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // HTTP 상태 코드 사용을 위해 추가
import org.springframework.http.ResponseEntity; // API 응답을 위해 추가
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping; // 댓글 삭제(API)를 위해 추가
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // JSON 본문 처리를 위해 추가
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; // API 응답을 위해 추가
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 사용하지 않지만 유지

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
        @AuthenticationPrincipal AccountDTO accountDTO,
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
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO == null) {
            return "redirect:/account/login";
        }
        freeboardDTO.setWriter(accountDTO.getId());
        freeboardService.insertPost(freeboardDTO);

        return "redirect:/freeboard";

    }

    // -----------------------------------------------------------------
    // 1. 게시글 상세 및 댓글 목록 조회 (Controller에서 Model에 댓글 목록 담기)
    // -----------------------------------------------------------------
    @GetMapping("/{id}")
    public String postDetail(@PathVariable int id, Model model) {
        
        // 1. 게시글 상세 정보 조회 (Service에서 조회수 증가 로직 포함)
        FreeboardDTO post = freeboardService.selectPostDetail(id);
        
        if (post == null) {
            return "redirect:/freeboard/list"; 
        }
        
        // 2. 댓글 목록 조회
        List<CommentDTO> comments = freeboardService.getCommentList(id);
        
        // 3. 모델에 담아 뷰로 전달
        model.addAttribute("post", post);
        model.addAttribute("comments", comments); // 🚨 댓글 목록 추가
        model.addAttribute("commentForm", new CommentDTO()); // 댓글 등록 폼 바인딩용
        
        return "freeboard/postDetail"; 
    }

    // -----------------------------------------------------------------
    // 2. 댓글 등록 처리 (API Endpoint)
    // -----------------------------------------------------------------
    @PostMapping("/comment")
    @ResponseBody
    public ResponseEntity<String> registerComment(
        @RequestBody CommentDTO commentDTO, // 🚨 JSON 형태로 댓글 데이터 받음
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        
        // 작성자 ID 설정
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
        
        // Service에서 댓글 ID와 유저 ID를 확인하여 권한 체크 후 삭제하는 로직이 필요합니다.
        
        boolean success = freeboardService.removeComment(id);
        
        if (success) {
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글 삭제에 실패했습니다. (댓글이 없거나 권한이 없습니다)");
        }
    }
}