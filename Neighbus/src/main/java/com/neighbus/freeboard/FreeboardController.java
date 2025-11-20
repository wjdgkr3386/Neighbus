package com.neighbus.freeboard;

import java.util.List;
import java.util.Map;

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

import com.neighbus.Util;
import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/freeboard")
public class FreeboardController {

    @Autowired
    private FreeboardService freeboardService;

    // -----------------------------------------------------------------
    // 게시글 목록, 작성 폼, 작성 처리 (기존 로직 유지)
    // -----------------------------------------------------------------
    
    @GetMapping(value={"/list",""})
    public String list(Model model, FreeboardDTO freeboardDTO) {
        System.out.println("FreeboardController - list");
        try {
            int searchAllCnt = freeboardService.searchAllCnt(); // 게시글 전체 개수
            Map<String, Integer> pagingMap = Util.searchUtil(searchAllCnt, freeboardDTO.getSelectPageNo(), freeboardDTO.getRowCnt());

            freeboardDTO.setSearchAllCnt(searchAllCnt);
            freeboardDTO.setSelectPageNo(pagingMap.get("selectPageNo"));
            freeboardDTO.setRowCnt(pagingMap.get("rowCnt"));
            freeboardDTO.setBeginPageNo(pagingMap.get("beginPageNo"));
            freeboardDTO.setEndPageNo(pagingMap.get("endPageNo"));
            freeboardDTO.setBeginRowNo(pagingMap.get("beginRowNo"));
            freeboardDTO.setEndRowNo(pagingMap.get("endRowNo"));

            List<FreeboardDTO> posts = freeboardService.selectPostListWithPaging(freeboardDTO);

            model.addAttribute("posts", posts);
            model.addAttribute("pagingMap", pagingMap);
        } catch(Exception e) {
            System.out.println(e);
        }
        return "freeboard/postList";
    }

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
    // 1. 게시글 상세 및 댓글 목록 조회
    // -----------------------------------------------------------------
    @GetMapping("/{id}")
    public String postDetail(
        @PathVariable("id") int id, // 🚨 수정: 매개변수 이름 명시
        Model model,
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        FreeboardDTO post = freeboardService.selectPostDetail(id);
        
        if (post == null) {
            return "redirect:/freeboard/list"; 
        }
        
        int currentUserId = 0;
        if (accountDTO != null) {
            currentUserId = accountDTO.getId(); // AccountDTO의 getId() 호출
        }
        
        List<CommentDTO> comments = freeboardService.getCommentList(id);
        
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("commentForm", new CommentDTO());
        model.addAttribute("currentUserId", currentUserId);
        
        return "freeboard/postDetail"; 
    }

    // -----------------------------------------------------------------
    // 2. 댓글 등록 처리 (API Endpoint)
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
        @PathVariable("id") int id, // 🚨 수정: 매개변수 이름 명시
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        
        // Service에서 댓글 ID와 유저 ID를 확인하여 권한 체크 후 삭제하는 로직 수행
        boolean success = freeboardService.removeComment(id, accountDTO.getId());
        
        if (success) {
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } else {
            // NOT_FOUND 대신, 권한 부족일 경우 FORBIDDEN을 반환할 수 있음
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제에 실패했거나 권한이 없습니다.");
        }
    }

    // -----------------------------------------------------------------
    // 4. 게시글 수정 및 삭제
    // -----------------------------------------------------------------

    // 게시글 수정 폼
    @GetMapping("/edit/{id}")
    public String editPostForm(
        @PathVariable("id") int id, // 🚨 수정: 매개변수 이름 명시
        Model model, 
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO == null) {
            return "redirect:/account/login";
        }

        FreeboardDTO post = freeboardService.selectPostDetail(id);

        if (post == null || post.getWriter() != accountDTO.getId()) {
            // 🚨 개선: 권한 없음 메시지를 추가하여 사용자에게 피드백 제공
            return "redirect:/freeboard/" + id + "?error=permission"; 
        }

        model.addAttribute("post", post);
        return "freeboard/postForm";
    }

    // 게시글 수정 처리
    @PostMapping("/edit/{id}")
    public String updatePost(
        @PathVariable("id") int id, // 🚨 수정: 매개변수 이름 명시
        FreeboardDTO freeboardDTO, 
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO == null) {
            return "redirect:/account/login";
        }

        freeboardDTO.setId(id);
        freeboardService.updatePost(freeboardDTO, accountDTO.getId());

        return "redirect:/freeboard/" + id;
    }

    // 게시글 삭제 처리 (GET 요청 대신 POST/DELETE 요청을 권장하지만, 현재 GET 유지)
    @GetMapping("/delete/{id}") 
    public String deletePost(
        @PathVariable("id") int id, // 🚨 수정: 매개변수 이름 명시
        @AuthenticationPrincipal AccountDTO accountDTO, 
        RedirectAttributes redirectAttributes
    ) {
        if (accountDTO == null) {
            return "redirect:/account/login";
        }

        boolean success = freeboardService.deletePost(id, accountDTO.getId());

        if (success) {
            redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "게시글 삭제에 실패했거나 권한이 없습니다."); // 에러 메시지 변경
        }
        return "redirect:/freeboard";
    }
}