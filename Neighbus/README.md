<div align="center">

# 🏘️ NEIGHBUS

### 이웃과 함께하는 지역 커뮤니티 플랫폼

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.8-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk" alt="Java"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" alt="MySQL"/>
  <img src="https://img.shields.io/badge/MyBatis-3.0.3-red?style=for-the-badge" alt="MyBatis"/>
</p>

<p align="center">
  <strong>개발 기간:</strong> 2024.10 ~ 2024.12 |
  <strong>개발 인원:</strong> 4명 |
  <strong>버전:</strong> 0.0.1-SNAPSHOT
</p>

</div>

---

## 📋 목차

- [프로젝트 소개](#-프로젝트-소개)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [시작하기](#-시작하기)
- [프로젝트 구조](#-프로젝트-구조)
- [주요 모듈](#-주요-모듈)
- [API 문서](#-api-문서)
- [팀원](#-팀원)

---

## 🎯 프로젝트 소개

**NEIGHBUS**(네이버스)는 **Neighborhood + Bus**의 합성어로, 지역 주민들을 연결하는 통합 커뮤니티 플랫폼입니다.

### 🌟 프로젝트 목적

- 이웃 간 소통과 취미 활동 공유를 통한 지역 사회 연결성 강화
- 지역 주민 간 유대감 형성 및 삶의 질 향상
- 오프라인 모임 촉진을 통한 실질적 커뮤니티 형성

---

## ✨ 주요 기능

### 👥 커뮤니티 기능
- **동아리 관리** - 지역 기반 취미 동아리 생성 및 참여
- **모임 모집** - 동아리 내 모임 일정 생성 및 자동 마감
- **자유게시판** - 지역 주민 간 자유로운 소통 공간
- **갤러리** - 사진/이미지 중심의 콘텐츠 공유

### 💬 소통 기능
- **실시간 채팅** - WebSocket 기반 실시간 메시징
- **친구 관리** - UUID 기반 친구 추가/관리
- **알림 시스템** - 실시간 알림 전송
- **AI 챗봇** - OpenAI GPT 기반 24/7 사용자 지원

### 🔐 인증 & 보안
- **소셜 로그인** - Google, Naver, Kakao OAuth2 연동
- **이메일 인증** - 회원가입 시 이메일 인증
- **SMS 인증** - Nurigo API 연동 본인 인증
- **Spring Security** - 강력한 보안 체계

### 👨‍💼 관리자 기능
- **통합 대시보드** - Chart.js 기반 시각화 대시보드
- **회원 관리** - 사용자 조회, 정지, 삭제
- **콘텐츠 관리** - 게시글, 댓글, 갤러리 관리
- **신고 관리** - 사용자 신고 접수 및 처리
- **문의 관리** - 사용자 문의 답변 시스템

---

## 🛠 기술 스택

### Backend
```
📦 Framework
├─ Spring Boot 3.5.8
├─ Spring Security 6
├─ Spring WebSocket
├─ Spring Mail
└─ Spring Scheduling

🗄️ Database
├─ MyBatis 3.0.3
├─ MySQL 8.0+
└─ HikariCP

🔐 Authentication
├─ OAuth2 Client
└─ Spring Validation

🤖 AI/ML
└─ Spring AI 1.1.0 (OpenAI GPT)

📱 External APIs
├─ Nurigo SMS SDK 4.3.0
└─ OAuth2 (Google, Naver, Kakao)
```

### Frontend
```
🎨 Template Engine
└─ Thymeleaf

💻 JavaScript
├─ Vanilla JS (ES6+)
└─ Chart.js (시각화)

🎨 Styling
├─ Custom CSS
├─ Bootstrap
└─ Font Awesome
```

### DevOps & Tools
```
🔧 Build Tool
└─ Gradle

🖥️ Development
├─ Spring Boot DevTools
├─ Lombok
└─ JUnit 5

🍎 Infrastructure
└─ macOS (Apple Silicon) 최적화
```

---

## 🏗 시스템 아키텍처

```
┌─────────────────────────────────────────────────────────┐
│                  Presentation Layer                      │
│    Thymeleaf Templates | REST API | WebSocket           │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                    Security Layer                        │
│       Spring Security + OAuth2 + CSRF Protection        │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                   Business Layer                         │
│        Service Layer | Scheduler | Validator            │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                  Persistence Layer                       │
│              MyBatis Mapper | DTO Objects               │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                     Data Layer                           │
│                   MySQL Database                         │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│                 External Services                        │
│      OpenAI API | OAuth2 Providers | Nurigo SMS         │
└─────────────────────────────────────────────────────────┘
```

---

## 🚀 시작하기

### 필수 요구사항

```bash
☑️ Java 17 이상
☑️ MySQL 8.0 이상
☑️ Gradle 7.x 이상
```

### 설치 및 실행

1. **레포지토리 클론**
```bash
git clone https://github.com/yourusername/neighbus.git
cd neighbus
```

2. **데이터베이스 설정**
```bash
# MySQL 데이터베이스 생성
mysql -u root -p
CREATE DATABASE neighbus;

# 테이블 생성 스크립트 실행
mysql -u root -p neighbus < 테이블\ 생성.txt
```

3. **환경 변수 설정**
```bash
# application.properties 파일 생성 및 설정
cp src/main/resources/application.properties.example src/main/resources/application.properties

# 필수 설정 항목 입력:
# - 데이터베이스 정보
# - OAuth2 클라이언트 ID/Secret
# - OpenAI API Key
# - Nurigo API Key
# - SMTP 설정
```

4. **애플리케이션 실행**
```bash
# Gradle을 사용한 빌드 및 실행
./gradlew clean build
./gradlew bootRun

# 또는 JAR 파일로 실행
java -jar build/libs/neighbus-0.0.1-SNAPSHOT.jar
```

5. **접속**
```
🌐 애플리케이션: http://localhost:8080
👨‍💼 관리자 페이지: http://localhost:8080/admin
```

---

## 📁 프로젝트 구조

```
com.neighbus
├── 📂 about              # 서비스 소개
├── 📂 account            # 회원 인증/인가
│   ├── Controller        # 로그인, 회원가입, OAuth2
│   ├── Service           # 사용자 인증 처리
│   └── DTO               # 사용자 데이터 전송
├── 📂 admin              # 관리자 기능
│   ├── Controller        # 관리자 페이지 라우팅
│   ├── RestController    # 관리자 API
│   ├── Service           # 관리자 비즈니스 로직
│   └── Mapper            # 통합 관리 쿼리
├── 📂 alarm              # 알림 시스템
├── 📂 chat               # 실시간 채팅 (WebSocket)
├── 📂 chatbot            # AI 챗봇 (OpenAI)
├── 📂 club               # 동아리 관리
├── 📂 config             # 설정
│   ├── SecurityConfig    # Spring Security
│   ├── WebSocketConfig   # WebSocket
│   └── MyBatisConfig     # MyBatis
├── 📂 freeboard          # 자유게시판
├── 📂 friend             # 친구 관리
├── 📂 gallery            # 갤러리
├── 📂 inquiry            # 문의하기
├── 📂 main               # 메인 페이지
├── 📂 mypage             # 마이페이지
├── 📂 notice             # 공지사항
├── 📂 recruitment        # 모임 모집
│   ├── Controller        # 모임 CRUD
│   ├── Scheduler         # 자동 마감 스케줄러
│   └── Service           # 모임 처리
└── 📂 util               # 유틸리티
    ├── EmailService      # 이메일 전송
    └── FileUpload        # 파일 업로드
```

---

## 🎯 주요 모듈

### 🔐 인증 시스템
- **일반 로그인**: 이메일/비밀번호 기반 인증
- **소셜 로그인**: Google, Naver, Kakao OAuth2
- **보안**: Spring Security + CSRF Protection

### 💬 실시간 채팅
- **WebSocket**: STOMP 프로토콜 기반
- **1:1 채팅**: 친구와의 실시간 메시징
- **채팅방 관리**: 채팅 내역 저장 및 조회

### 🤖 AI 챗봇
- **OpenAI GPT**: 자연어 처리 기반 대화
- **컨텍스트 유지**: 대화 맥락 기억
- **24/7 지원**: 실시간 사용자 지원

### 📅 모임 자동 마감
- **스케줄러**: Spring Scheduling 기반
- **자동 마감**: 모임 날짜 도달 시 자동 마감
- **상태 관리**: OPEN → CLOSED 자동 전환

### 📊 관리자 대시보드
- **통계 시각화**: Chart.js 기반 차트
- **실시간 모니터링**: 사용자, 게시글, 신고 현황
- **통합 관리**: 단일 페이지에서 모든 관리 기능 제공

---

## 📚 API 문서

### 주요 API 엔드포인트

#### 인증 API
```
POST   /account/login              # 로그인
POST   /account/signup             # 회원가입
GET    /account/oauth2/{provider}  # OAuth2 로그인
POST   /account/logout             # 로그아웃
```

#### 동아리 API
```
GET    /club                       # 동아리 목록
GET    /club/{id}                  # 동아리 상세
POST   /club/create                # 동아리 생성
PUT    /club/{id}/update           # 동아리 수정
DELETE /club/{id}/delete           # 동아리 삭제
```

#### 모임 API
```
GET    /recruitment                # 모임 목록
GET    /recruitment/{id}           # 모임 상세
POST   /recruitment/create         # 모임 생성
POST   /recruitment/{id}/join      # 모임 참여
```

#### 채팅 API (WebSocket)
```
CONNECT /ws                        # WebSocket 연결
SEND   /app/chat.sendMessage       # 메시지 전송
SUB    /topic/chat/{roomId}        # 채팅방 구독
```

#### 관리자 API
```
GET    /api/admin/users            # 사용자 목록
GET    /api/admin/reports          # 신고 목록
POST   /api/admin/reports/block    # 사용자 정지
GET    /api/admin/dashboard/stats  # 대시보드 통계
```

---

## 👥 팀원

<table>
  <tr>
    <td align="center">
      <b>팀원 1</b><br>
      <sub>역할: Backend Development</sub>
    </td>
    <td align="center">
      <b>팀원 2</b><br>
      <sub>역할: Backend Development</sub>
    </td>
    <td align="center">
      <b>팀원 3</b><br>
      <sub>역할: Frontend Development</sub>
    </td>
    <td align="center">
      <b>팀원 4</b><br>
      <sub>역할: Database & DevOps</sub>
    </td>
  </tr>
</table>

---

## 📝 라이선스

This project is licensed under the MIT License.

---

<div align="center">

### 🏘️ NEIGHBUS - 이웃과 함께 성장하는 커뮤니티

**Made with ❤️ by NEIGHBUS Team**

</div>
