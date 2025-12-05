# 🍏 NEIGHBUS  
### 지역 기반 통합 커뮤니티 플랫폼  
_Designed with Apple-like minimalism and clarity._

---

## 🖼️ 프로젝트 대표 이미지 (Apple Banner)
![banner](assets/banner.png)

---

## 🧭 Overview
NEIGHBUS는 **지역 기반 동아리 / 모임 / 실시간 채팅 / AI 챗봇** 기능을 한 플랫폼에서 제공하는  
로컬 중심 커뮤니티 서비스입니다.

Apple의 디자인 언어에서 영감을 받아  
- 심플한 구조  
- 넓은 여백  
- 부드러운 톤  
- 직관적인 정보 흐름  
으로 구성했습니다.

---

## 🍃 Technology

### **Backend**
- Spring Boot 3.5.8  
- Java 17  
- MyBatis  
- MySQL 8.0  

### **Frontend**
- Thymeleaf  
- JavaScript (ES6)  
- Bootstrap  

### **AI / Real-time / Security**
- OpenAI GPT  
- WebSocket(STOMP)  
- Spring Security  
- OAuth2 (구글, 네이버, 카카오)

---

## 🖼 UI Screenshots (Apple Style)

> 아래 이미지는 예시 자리입니다.  
> 실제 이미지를 넣으면 Apple-style 느낌이 완성됩니다.

### 🌤 Main Page
![main](assets/main.png)

---

### 🏫 Club List
![club-list](assets/club_list.png)

---

### 📍 Recruitment / Meetup (지도 연동)
![recruitment-create](assets/recruitment_create.png)

---

### 💬 Real-time Chat (WebSocket)
![chat-ui](assets/chat_ui.png)

---

### 📊 Admin Dashboard
![admin-dashboard](assets/admin_dashboard.png)

---

## ⚙ Core Features

### 1️⃣ 동아리 시스템 (Club)
- 동아리 생성 / 가입 / 승인
- 역할(리더 / 부리더 / 멤버)
- 게시판 / 갤러리 제공
- 위치 기반 추천

---

### 2️⃣ 모임 시스템 (Recruitment)
- 모임 생성 / 참여 / 취소
- 지도 기반 위치 등록
- 자동 마감 스케줄러 (매 시 정각)
- 참여 인원 실시간 반영

---

### 3️⃣ 실시간 채팅 (WebSocket)
- 1:1 및 그룹 채팅
- STOMP 기반 통신
- 채팅 기록 저장 및 읽음 처리

---

### 4️⃣ AI 챗봇 (OpenAI GPT)
- FAQ 자동 응답
- 맥락 유지 대화
- 사용자 로그 기반 개선

---

### 5️⃣ 알림 시스템
- 댓글 / 모임 / 공지 / 친구 활동 알림
- 실시간 배지 & 읽음 관리

---

### 6️⃣ 관리자 대시보드
- 회원 / 게시글 / 모임 / 갤러리 관리
- 신고 처리
- 통계 분석 (Chart.js)

---

## 🧬 ERD (Minimal Apple-like)

```mermaid
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


📂 프로젝트 구조
arduino
코드 복사
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
bash
코드 복사
./gradlew build
java -jar neighbus.jar
