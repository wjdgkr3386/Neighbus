package com.neighbus.meeting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeetingController {

    // 모임 폼만 담당
    @GetMapping("/meeting")
    public String meetingForm() {
        // src/main/resources/templates/meeting/meeting.html 파일을 찾습니다.
        return "meeting/meeting";
    }
}