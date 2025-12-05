🚌 NEIGHBUS (네이버스)

이웃과 취미를 잇다, 우리 동네 커뮤니티 플랫폼

📝 프로젝트 소개

**NEIGHBUS(Neighborhood + Bus)**는 지역 주민들을 위한 통합 커뮤니티 플랫폼입니다.
이웃 간의 소통 부재를 해결하고, 공통된 취미 활동을 매개로 지역 사회의 연결성을 강화하는 것을 목표로 합니다.

개발 기간: 2024.10 ~ 2024.12 (4인 프로젝트)

💡 주요 기대 효과

지역 유대감 형성: 내 위치 기반의 이웃들과 자연스러운 소통

취미 생활 활성화: 동아리 및 원데이 클래스 등 오프라인 모임 촉진

편의성 증대: AI 챗봇 및 실시간 알림을 통한 사용자 경험 개선

🛠 기술 스택 (Tech Stack)

Backend

Tech

Detail

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Java-007396%3Fstyle%3Dflat%26logo%3DJava%26logoColor%3Dwhite"/>

JDK 17

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Spring%2520Boot-6DB33F%3Fstyle%3Dflat%26logo%3DSpringBoot%26logoColor%3Dwhite"/>

version 3.5.8

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Spring%2520Security-6DB33F%3Fstyle%3Dflat%26logo%3DSpringSecurity%26logoColor%3Dwhite"/>

인증/인가 및 OAuth2

<img src="https://www.google.com/search?q=https://img.shields.io/badge/MyBatis-000000%3Fstyle%3Dflat%26logo%3DMyBatis%26logoColor%3Dwhite"/>

SQL Mapper Framework

<img src="https://www.google.com/search?q=https://img.shields.io/badge/MySQL-4479A1%3Fstyle%3Dflat%26logo%3DMySQL%26logoColor%3Dwhite"/>

RDBMS (HikariCP)

Frontend

Tech

Detail

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Thymeleaf-005F0F%3Fstyle%3Dflat%26logo%3DThymeleaf%26logoColor%3Dwhite"/>

Server-side Template Engine

<img src="https://www.google.com/search?q=https://img.shields.io/badge/JavaScript-F7DF1E%3Fstyle%3Dflat%26logo%3DJavaScript%26logoColor%3Dblack"/>

Vanilla JS (ES6+)

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Bootstrap-7952B3%3Fstyle%3Dflat%26logo%3DBootstrap%26logoColor%3Dwhite"/>

UI Framework

Infrastructure & Tools

Tech

Detail

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Gradle-02303A%3Fstyle%3Dflat%26logo%3DGradle%26logoColor%3Dwhite"/>

Build Tool

<img src="https://www.google.com/search?q=https://img.shields.io/badge/OpenAI-412991%3Fstyle%3Dflat%26logo%3DOpenAI%26logoColor%3Dwhite"/>

Spring AI (Chatbot)

<img src="https://www.google.com/search?q=https://img.shields.io/badge/Git-F05032%3Fstyle%3Dflat%26logo%3DGit%26logoColor%3Dwhite"/>

Version Control

🏗 시스템 아키텍처

graph TD
    User[Client / Browser]
    
    subgraph "Presentation Layer"
        Controller[REST Controller]
        Thymeleaf[Thymeleaf Templates]
        WS[WebSocket Handler]
    end
    
    subgraph "Security Layer"
        Sec[Spring Security]
        OAuth[OAuth2 Client]
    end

    subgraph "Business Layer"
        Service[Service]
        Scheduler[Scheduler]
        AI[Spring AI]
    end

    subgraph "Persistence Layer"
        Mapper[MyBatis Mapper]
        DB[(MySQL Database)]
    end
    
    User --> Controller
    User --> Thymeleaf
    User --> WS
    
    Controller --> Sec
    Sec --> Service
    Service --> Mapper
    Mapper --> DB
    
    Service -- "External API" --> AI


✨ 핵심 기능 상세 (Key Features)

1. 회원 및 보안 시스템 (Account & Security)

안전하고 간편한 인증 프로세스 구현

다중 로그인 지원: 일반 이메일 로그인뿐만 아니라 Google, Naver, Kakao OAuth2를 연동하여 접근성을 높였습니다.

철저한 보안: Spring Security 기반으로 CSRF Protection, XSS 방지 처리가 되어 있으며, 비밀번호는 BCrypt로 암호화되어 저장됩니다.

실명 인증: Nurigo SMS API를 활용하여 회원가입 시 휴대폰 문자 인증을 수행, 허위 계정 생성을 방지합니다.

2. 커뮤니티 생태계 (Community)

지역 기반의 활발한 소통 공간

동아리(Club) 관리:

지역/카테고리(운동, 문화, 학습 등) 필터링을 통한 맞춤형 동아리 탐색.

리더 권한을 통한 회원 관리(가입 승인/거절, 강제 탈퇴) 기능 구현.

모임(Recruitment) 시스템:

지도 API(Kakao Maps)를 연동하여 시각적인 모임 장소 확인 및 위도/경도 저장.

최대 참여 인원 제한 및 실시간 참여 현황 파악.

게시판(Board) & 갤러리:

자유게시판 및 이미지 중심의 갤러리 뷰 제공.

이미지 업로드 시 썸네일 자동 생성 및 조회수 카운팅.

계층형 댓글(대댓글) 구조로 깊이 있는 대화 가능.

3. 실시간 인터랙션 (Real-time Service)

지연 없는 소통 환경 구축

웹소켓 채팅 (WebSocket & STOMP):

동아리 가입 시 자동으로 해당 동아리 단체 채팅방 생성 및 초대.

1:1 채팅 및 그룹 채팅 지원, 메시지 DB 저장을 통한 히스토리 로드.

실시간 알림 (Notification):

비동기 처리를 통해 댓글 작성, 모임 참여, 승인 등 이벤트 발생 시 즉시 알림 발송.

읽음/안읽음 상태 관리 및 배지(Badge) 카운트 갱신.

4. 지능형 자동화 (AI & Automation)

운영 효율성을 높이는 스마트 기능

AI 챗봇 (Spring AI + OpenAI):

GPT 모델을 연동하여 서비스 이용 방법 및 FAQ에 대한 24시간 자동 응답 제공.

사용자 질문 컨텍스트를 유지하는 대화형 인터페이스.

스케줄러 자동화 (Spring Scheduler):

매시간 정각(Cron: 0 0 * * * *)에 실행되는 배치 작업.

모임 시간이 지난 게시글(meeting_date < NOW())을 자동으로 감지하여 상태를 CLOSED로 변경.

5. 관리자 대시보드 (Admin Dashboard)

데이터 기반의 서비스 통합 관리

시각화 통계: Chart.js를 활용하여 일별/월별 가입자 추이, 카테고리별 모임 분포, 신고 처리 현황을 그래프로 제공.

신고 및 제재 시스템:

6가지 유형(욕설, 비방, 광고 등)의 신고 접수 및 처리 프로세스(접수 → 검토 → 처리/기각).

악성 사용자에 대한 일시 정지 및 영구 차단 기능.

콘텐츠 관리: 전체 회원, 게시글, 동아리 목록 조회 및 관리자 권한으로 강제 삭제/폐쇄 기능.

📂 프로젝트 구조 (Directory Structure)

com.neighbus
├── account      # 회원 인증/인가 (Login, OAuth2)
├── admin        # 관리자 기능 (Dashboard, User Mgmt)
├── alarm        # 실시간 알림
├── chat         # WebSocket 채팅
├── chatbot      # AI 챗봇 (OpenAI)
├── club         # 동아리 CRUD
├── config       # Security, MyBatis, WebSocket 설정
├── freeboard    # 자유게시판
├── gallery      # 갤러리 (이미지 처리)
├── main         # 메인 페이지
├── mypage       # 마이페이지 (프로필)
├── notice       # 공지사항
├── recruitment  # 모임 모집 및 스케줄러
└── weather      # 날씨 정보 API


💾 ERD (Entity Relationship Diagram)

주요 테이블 설계

Users: 회원 정보 및 프로필

Clubs & Club_Members: 동아리 정보 및 소속 회원 관계

Recruitments: 오프라인 모임 정보 (위치, 인원, 상태)

Boards (Freeboard, Gallery, Notice): 게시글 및 댓글

Chat & Alarms: 채팅 메시지 및 알림 로그

Reports: 신고 접수 및 처리 상태

🔌 API 명세 요약

<details>
<summary>👉 <b>API 리스트 펼치기</b></summary>

Domain

Method

URI

Description

Account

POST

/account/signup

회원가입



POST

/account/login

로그인

Club

GET

/club/list

동아리 목록 조회



POST

/club/create

동아리 생성

Recruitment

GET

/recruitment/list

모임 목록



POST

/recruitment/{id}/join

모임 참여

Admin

GET

/api/admin/dashboard/stats

대시보드 통계



POST

/api/admin/users/block

회원 정지 처리

Chatbot

POST

/chatbot/ask

AI 챗봇 질문

</details>

🚀 시작하기 (Getting Started)

Prerequisites

Java 17+

MySQL 8.0+

Installation

Repository Clone

git clone [https://github.com/your-username/neighbus.git](https://github.com/your-username/neighbus.git)


application.yml 설정 (DB 및 API Key)

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/neighbus
    username: root
    password: your_password
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}


Build & Run

./gradlew build
java -jar build/libs/neighbus.jar


👨‍💻 팀원 및 역할 (Team)

이름

역할

담당 기능

팀원 1

Back/Sec

회원 관리, 인증/인가(Security), OAuth2, 보안 설정

팀원 2

Fullstack

동아리/모임 관리 로직, 스케줄러(자동마감), 지도 API

팀원 3

Fullstack

게시판, 갤러리(이미지), 댓글 시스템, UI/UX 구현

팀원 4

Back/Admin

관리자 대시보드, 통계, 신고 처리, 공지사항 관리

📜 License

This project is licensed under the MIT License.
