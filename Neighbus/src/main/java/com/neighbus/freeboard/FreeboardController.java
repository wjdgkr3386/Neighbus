package com.neighbus.freeboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.neighbus.account.AccountDTO;

@Controller
@RequestMapping("/freeboard")
public class FreeboardController {

    @Autowired
    private FreeboardService freeboardService;

    // 게시글 목록을 보여줍니다.
    @GetMapping
    public String list(Model model) {
        List<FreeboardDTO> posts = freeboardService.selectPostList();
        model.addAttribute("posts", posts);
        return "freeboard/postList";
    }

    // 글쓰기 폼을 보여줍니다.
    @GetMapping("/write")
    public String postForm(
        @SessionAttribute(name = "loginUser", required = false) AccountDTO loginUser
    ) {
        if (loginUser == null) {
            return "redirect:/account/login";
        }
        return "freeboard/postForm";
    }

    // 글쓰기 폼을 처리합니다.
    @PostMapping("/write")
    public String submitPost(
        FreeboardDTO freeboardDTO,
        @AuthenticationPrincipal AccountDTO accountDTO
    ) {
        if (accountDTO.getName() == null) {
            return "redirect:/account/login";
        }
        freeboardDTO.setWriter(loginUser.getId());
        freeboardService.insertPost(freeboardDTO);
        return "redirect:/freeboard";
    }
}