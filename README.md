🌟 NEIGHBUS — 지역 기반 통합 커뮤니티 플랫폼
<p align="center"> <img src="https://readme-typing-svg.herokuapp.com?font=Montserrat&size=28&duration=2200&pause=600&color=3BAFDA&center=true&vCenter=true&width=800&lines=NEIGHBUS:+Neighborhood+Community+Platform;동아리+%2F+모임+%2F+채팅+%2F+AI+챗봇+올인원" /> </p>
<br>
🖼 프로젝트 대표 이미지 (배너)

📌 여기에 대표 배너 이미지를 넣으면 포트폴리오 느낌 최고!
(이미지 파일 주면 자동 스타일링 해드림)

![neighbus-banner](assets/banner.png)

🏷️ Tech Stack (포트폴리오 스타일 아이콘 정렬)
🔧 Backend
Tech	Badge
Spring Boot	

Java	

MyBatis	

MySQL	
🔐 Security
Tech	Badge
Spring Security	

OAuth2	
🖥 Frontend
Tech	Badge
Thymeleaf	

JavaScript	

Bootstrap	
🖼 주요 화면 미리보기

✔ 이 섹션이 들어가야 포트폴리오 느낌이 확 살아납니다.
✔ 아래 이미지 파일만 보내주면 자동으로 사이즈 맞게 넣어줌.

🏠 메인 페이지
![main-page](assets/main.png)

🏫 동아리 목록 화면
![club-list](assets/club_list.png)

📅 모임 생성 & 지도 연동
![recruitment-create](assets/recruitment_create.png)

💬 실시간 채팅(WebSocket)
![chat-ui](assets/chat_ui.png)

🎨 관리자 대시보드(통계 차트)
![admin-dashboard](assets/admin_dashboard.png)

🚀 주요 기능 카드(Portfolio Style)
<table> <tr> <td align="center"> <img src="https://img.shields.io/badge/-동아리%20시스템-3BAFDA?style=for-the-badge" /><br/> 동아리 생성 / 가입 / 역할 관리 / 갤러리 / 게시판 </td> <td align="center"> <img src="https://img.shields.io/badge/-모임%20스케줄러-7AB93E?style=for-the-badge" /><br/> 모임 모집 / 참여 / 자동 마감 배치 / 지도 기반 위치 </td> </tr> <tr> <td align="center"> <img src="https://img.shields.io/badge/-실시간%20채팅-FF7E47?style=for-the-badge" /><br/> WebSocket + STOMP 그룹/1:1 채팅 </td> <td align="center"> <img src="https://img.shields.io/badge/-AI%20챗봇-7257D8?style=for-the-badge" /><br/> OpenAI GPT 기반 FAQ/문의 응답 </td> </tr> <tr> <td align="center"> <img src="https://img.shields.io/badge/-알림%20시스템-FFCE33?style=for-the-badge" /><br/> 댓글·모임·친구·공지 알림 </td> <td align="center"> <img src="https://img.shields.io/badge/-관리자%20대시보드-000000?style=for-the-badge" /><br/> 회원/게시글/신고/통계 관리 </td> </tr> </table>
📐 ERD 다이어그램 (Mermaid)
erDiagram

    USERS ||--o{ CLUB_MEMBERS : "joins"
    USERS ||--o{ RECRUITMENT_MEMBER : "joins"
    USERS ||--o{ FREEBOARD : "writes"
    USERS ||--o{ COMMENTS : "writes"
    USERS ||--o{ REPORTS : "reports"
    USERS ||--o{ CHAT_MESSAGES : "sends"
    USERS ||--o{ ALARMS : "receives"

    CLUBS ||--o{ CLUB_MEMBERS : "members"
    CLUBS ||--o{ GALLERIES : "photos"
    CLUBS ||--o{ RECRUITMENTS : "meetings"

    FREEBOARD ||--o{ COMMENTS : "comments"
    GALLERIES ||--o{ GALLERY_COMMENTS : "comments"

    RECRUITMENTS ||--o{ RECRUITMENT_MEMBER : "participants"

    REPORTS }o--|| USERS : "reported user"
    REPORTS }o--|| FREEBOARD : "reported post"
    REPORTS }o--|| COMMENTS : "reported comment"
    REPORTS }o--|| GALLERIES : "reported gallery"
    REPORTS }o--|| RECRUITMENTS : "reported meeting"

🧩 프로젝트 구조
com.neighbus
├── account
├── admin
├── alarm
├── chat
├── chatbot
├── club
├── recruitment
├── freeboard
├── gallery
├── notice
├── friend
├── inquiry
├── config
└── weather

🚀 실행 방법
./gradlew build
java -jar neighbus.jar

🎁 남은 단계:
👉 이미지 파일만 주면 위 README를 “완성된 포트폴리오 버전"으로 정리해드릴게요!

필요한 이미지:

대표 배너

메인 페이지

동아리 목록

모임 생성

채팅 화면

관리자 대시보드

원하는 스타일이 있으면
🎨 “애플 스타일”, “노션 스타일”, “네온 UI”, “모던 미니멀” 도 적용해드릴게!

나의 말:
애플 스타일 
ChatGPT의 말:

좋아! 이제 **애플 스타일(Apple Design Language)**로
미니멀 + 고급스러운 + 여백 넉넉 + 음영 부드러운
완전 포트폴리오 감성의 README로 다시 재구성해줄게.

※ 여전히 이미지는 “자리만 마련”해두었고
실제 이미지 파일을 주면 → 애플 스타일 톤(Soft Shadow + Rounded + Clean Frame)로 완전하게 마크다운 교체해드릴게.

🍏 NEIGHBUS
지역 기반 통합 커뮤니티 플랫폼

Clean. Minimal. Human-centered — Inspired by Apple’s Design Language.

<p align="center"> <img src="assets/banner.png" width="85%" style="border-radius:22px; box-shadow:0 8px 30px rgba(0,0,0,0.12);" alt="neighbus-banner"/> </p>
<br>
🧭 Overview

NEIGHBUS는 지역 기반 동아리 / 모임 / 채팅 / AI 챗봇을 하나로 통합한
로컬 커뮤니티 플랫폼입니다.

직관적인 UI

부드러운 애니메이션

심플한 정보 흐름

Apple-like minimal design

을 목표로 설계되었습니다.

<br>
🍃 Technology
Backend
<p align="left"> <img src="https://img.shields.io/badge/Spring%20Boot-3.5.8-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/MyBatis-FF2D20?style=flat-square&logo=databricks&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white"/> </p>
Frontend
<p align="left"> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf"/> <img src="https://img.shields.io/badge/JavaScript(ES6)-F7DF1E?style=flat-square&logo=javascript"/> <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=flat-square&logo=bootstrap"/> </p>
AI · Real-time · Security
<p align="left"> <img src="https://img.shields.io/badge/OpenAI-GPT-412991?style=flat-square&logo=openai"/> <img src="https://img.shields.io/badge/WebSocket-STOMP-6BAAFC?style=flat-square&logo=socketdotio"/> <img src="https://img.shields.io/badge/Spring%20Security-OAuth2-4CBB17?style=flat-square&logo=springsecurity"/> </p>
<br>
🖼 UI Showcase (Apple Style)

아래는 “이미지 자리”입니다.
실제 캡처 이미지를 주면 Apple-style 프레임 + 그림자 적용해서 완성해드립니다.

🌤 Main Page
<p align="center"> <img src="assets/main.png" width="85%" style="border-radius:18px; box-shadow: 0 10px 40px rgba(0,0,0,0.15);" /> </p>
🏫 Club List
<p align="center"> <img src="assets/club_list.png" width="85%" style="border-radius:18px; box-shadow: 0 10px 40px rgba(0,0,0,0.15);" /> </p>
📍 Recruitment (Meetup) – 지도 연동
<p align="center"> <img src="assets/recruitment_create.png" width="85%" style="border-radius:18px; box-shadow: 0 10px 40px rgba(0,0,0,0.15);" /> </p>
💬 Real-time Chat (WebSocket)
<p align="center"> <img src="assets/chat_ui.png" width="85%" style="border-radius:18px; box-shadow: 0 10px 40px rgba(0,0,0,0.15);" /> </p>
📊 Admin Dashboard
<p align="center"> <img src="assets/admin_dashboard.png" width="85%" style="border-radius:18px; box-shadow: 0 10px 40px rgba(0,0,0,0.15);" /> </p>
<br>
⚙ Core Features
🍀 1. 동아리 시스템 (Club)

동아리 생성 / 가입 / 승인

역할(리더/부리더/멤버)

갤러리 · 게시판 통합

위치 기반 추천

📅 2. 모임 시스템 (Recruitment)

모임 생성 / 참여 / 취소

Web 지도 기반 위치 저장

자동 마감 스케줄러 (매시 정각)

참여 인원 실시간 반영

💬 3. 실시간 채팅

WebSocket + STOMP

1:1 / 그룹 채팅

채팅 내역 저장

읽음 표시

🤖 4. AI 챗봇 (OpenAI GPT)

FAQ 자동 응답

컨텍스트 유지

사용자 질문 로그 기반 개선

🔔 5. 알림 시스템

댓글 / 모임 / 친구 / 공지

Badge + 실시간 알림

읽음/안읽음 관리

🛡 6. 관리자 대시보드

회원/게시글/동아리/모임/갤러리 통합 관리

신고 처리

실시간 통계 페이지 (Chart.js)

<br>
🧬 ERD (Apple Minimal)
%% 애플 스타일을 담아 여백 넓고 깔끔한 구성
erDiagram
    USERS ||--o{ CLUB_MEMBERS : joins
    USERS ||--o{ RECRUITMENT_MEMBER : joins
    USERS ||--o{ FREEBOARD : writes
    USERS ||--o{ COMMENTS : writes
    USERS ||--o{ REPORTS : reports
    USERS ||--o{ CHAT_MESSAGES : sends
    USERS ||--o{ ALARMS : receives

    CLUBS ||--o{ CLUB_MEMBERS : members
    CLUBS ||--o{ GALLERIES : photos
    CLUBS ||--o{ RECRUITMENTS : meetings

    FREEBOARD ||--o{ COMMENTS : comments
    GALLERIES ||--o{ GALLERY_COMMENTS : comments

    RECRUITMENTS ||--o{ RECRUITMENT_MEMBER : participants

    REPORTS }o--|| USERS : "reported user"
    REPORTS }o--|| FREEBOARD : "reported post"
    REPORTS }o--|| COMMENTS : "reported comment"
    REPORTS }o--|| GALLERIES : "reported gallery"
    REPORTS }o--|| RECRUITMENTS : "reported meeting"

<br>
📂 프로젝트 구조
com.neighbus
├── account
├── admin
├── alarm
├── chat
├── chatbot
├── club
├── recruitment
├── freeboard
├── gallery
├── notice
├── friend
├── inquiry
├── config
└── weather

<br>
🚀 실행 방법
./gradlew build
java -jar neighbus.jar

