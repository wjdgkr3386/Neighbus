package com.neighbus.freeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/freeboard")
public class FreeboardController {

    @Autowired
    private FreeboardService freeboardService;

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
    // 게시글 상세 보기 
    @GetMapping("/{id}")
    public String postDetail(@PathVariable int id, Model model) {        

        // 2. 게시글 상세 정보 조회 (조회수 증가 후 최신 데이터 가져오기)
        FreeboardDTO post = freeboardService.selectPostDetail(id);
        
        if (post == null) {
            // 게시글이 없을 경우 처리 (예: 목록으로 리다이렉트)
            return "redirect:/freeboard/list"; 
        }
        
        // 3. 모델에 담아 View로 전달
        model.addAttribute("post", post);
        
        return "freeboard/postDetail"; 
    }
}