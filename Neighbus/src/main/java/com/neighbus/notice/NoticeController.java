package com.neighbus.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {

	@GetMapping(value={"/",""})
	public String noticeForm() {
		System.out.println("NoticeController - noticeForm");
		return "notice/notice";
	}
}
