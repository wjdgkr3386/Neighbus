🌟 NEIGHBUS — 지역 기반 통합 커뮤니티 플랫폼
<p align="center"> <img src="https://readme-typing-svg.herokuapp.com?font=Montserrat&size=28&duration=2200&pause=600&color=3BAFDA&center=true&vCenter=true&width=800&lines=NEIGHBUS:+Neighborhood+Community+Platform;동아리+%2F+모임+%2F+채팅+%2F+AI+챗봇+올인원" /> </p>
<br>
🏷️ Tech Stack

포트폴리오 스타일 + 통일된 아이콘 색상 버전

<div align="center">
💚 Backend
<img src="https://img.shields.io/badge/Spring Boot-3.5.8-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/MyBatis-3.0.3-FF2D20?style=for-the-badge&logo=databricks&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
🛡 Security
<img src="https://img.shields.io/badge/Spring Security-6-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/OAuth2-Google/Naver/Kakao-FBBC05?style=for-the-badge&logo=google&logoColor=white"/>
🔌 Communication
<img src="https://img.shields.io/badge/WebSocket-STOMP-FF8800?style=for-the-badge&logo=socketdotio&logoColor=white"/> <img src="https://img.shields.io/badge/OpenAI API-GPT-412991?style=for-the-badge&logo=openai&logoColor=white"/>
🎨 Frontend
<img src="https://img.shields.io/badge/Thymeleaf-3.1-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/> <img src="https://img.shields.io/badge/JavaScript-ES6-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"/> <img src="https://img.shields.io/badge/Bootstrap-5.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"/> <img src="https://img.shields.io/badge/Chart.js-3.9-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white"/>
⚙ Tools & DevOps
<img src="https://img.shields.io/badge/Gradle-Build Tool-02303A?style=for-the-badge&logo=gradle&logoColor=white"/> <img src="https://img.shields.io/badge/JUnit5-Test Suite-25A162?style=for-the-badge&logo=junit5&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub-Version Control-000000?style=for-the-badge&logo=github&logoColor=white"/> </div>
<br>
🗂 프로젝트 소개

**NEIGHBUS(네이버스)**는 지역 주민을 연결하는 동아리 기반 지역 커뮤니티 플랫폼입니다.

✔ 동아리 생성/가입
✔ 모임 일정 관리 & 자동 마감 스케줄러
✔ 실시간 WebSocket 그룹채팅
✔ GPT 기반 AI 챗봇
✔ 알림 시스템
✔ 관리자 대시보드(신고·회원·게시글·모임 관리)

<br>
🧭 Architecture
Presentation: Thymeleaf, Bootstrap, WebSocket(STOMP)
Application: Spring Boot, Spring Security, OAuth2
Business: Service Layer, Scheduler, Validation
Persistence: MyBatis, DTO/Mapper
Database: MySQL + HikariCP
External: OpenAI, Nurigo SMS, Weather API

<br>
🧩 프로젝트 구조
com.neighbus
├── account         # 회원/인증/인가/OAuth2
├── admin           # 관리자 대시보드 및 신고 관리
├── alarm           # 실시간 알림
├── chat            # WebSocket 채팅
├── chatbot         # OpenAI GPT 챗봇
├── club            # 동아리 CRUD + 멤버 관리
├── recruitment     # 모임 생성/참여/스케줄러
├── freeboard       # 자유게시판
├── gallery         # 갤러리 이미지 업로드
├── notice          # 공지사항
├── inquiry         # 문의하기
├── friend          # 친구 시스템
├── config          # 보안/DB/Websocket 설정
└── weather         # 날씨 API

<br>
📐 ERD (Mermaid 이미지 자동 생성)

GitHub에서 자동으로 다이어그램으로 렌더링 됩니다.

erDiagram

    USERS ||--o{ CLUB_MEMBERS : "joins"
    USERS ||--o{ RECRUITMENT_MEMBER : "joins"
    USERS ||--o{ FREEBOARD : "writes"
    USERS ||--o{ COMMENTS : "writes"
    USERS ||--o{ REPORTS : "reports"
    USERS ||--o{ CHAT_MESSAGES : "sends"
    USERS ||--o{ ALARMS : "receives"

    CLUBS ||--o{ CLUB_MEMBERS : "has members"
    CLUBS ||--o{ GALLERIES : "uploads photos"
    CLUBS ||--o{ RECRUITMENTS : "hosts meetings"

    FREEBOARD ||--o{ COMMENTS : "has comments"
    GALLERIES ||--o{ GALLERY_COMMENTS : "has comments"

    RECRUITMENTS ||--o{ RECRUITMENT_MEMBER : "participants"

    REPORTS }o--|| USERS : "reported user"
    REPORTS }o--|| FREEBOARD : "reported post"
    REPORTS }o--|| COMMENTS : "reported comment"
    REPORTS }o--|| GALLERIES : "reported gallery"
    REPORTS }o--|| RECRUITMENTS : "reported meeting"

<br>
✨ 주요 기능
👤 회원

SMS 인증

OAuth2(Google, Naver, Kakao)

비밀번호 재설정

자동 로그인(Remember-Me)

🏫 동아리

생성/수정/삭제

지역·카테고리 검색

리더/부리더/멤버 역할

🗓 모임

위치(위도/경도) 저장

참여/취소

자동 마감 스케줄러

📝 자유게시판 & 댓글

게시글 CRUD

댓글/대댓글

검색/정렬/페이징

🖼 갤러리

이미지 업로드

모달 보기

동아리 연결

🔥 실시간 채팅

WebSocket(STOMP)

그룹/1:1 채팅

메시지 영구 저장

🤖 AI 챗봇

OpenAI GPT 연동

FAQ/지역 정보 답변

사용자 발화 로그 기반 학습 개선

🔔 알림 시스템

댓글/대댓글

요청 승인

모임 참여

신고 처리

👮 관리자

회원 관리

게시글 관리

신고 처리

통계(Chart.js)

<br>
🛠️ 실행 방법
./gradlew build
java -jar neighbus.jar


기본 포트: 8080

profile: dev, prod

<br>
📌 향후 개선

AWS S3 이미지 업로드

모바일 앱(Flutter/React Native)

Redis 캐싱

Elasticsearch 통합

Docker 배포

CI/CD 자동화

<br>
👥 Team
이름	담당
팀원 A	회원/보안/인증
팀원 B	동아리/모임/스케줄러
팀원 C	게시판/갤러리/댓글
팀원 D	관리자/신고/통계
<br>
📄 License

MIT License · © 2024 NEIGHBUS Team
