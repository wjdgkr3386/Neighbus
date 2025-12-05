<div align="center">


# 🏘️ NEIGHBUS

### Neighborhood + Bus = 우리 동네 커뮤니티 플랫폼

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.8-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**이웃과 함께하는 취미생활, NEIGHBUS에서 시작하세요! 🚌**

[🎯 프로젝트 소개](#-프로젝트-소개) • 
[✨ 주요 기능](#-주요-기능) • 
[🛠️ 기술 스택](#️-기술-스택) • 
[📸 스크린샷](#-스크린샷) • 
[🚀 시작하기](#-시작하기)

</div>

---

## 🎯 프로젝트 소개

> **"혼자보다 함께, 온라인에서 오프라인으로"**

NEIGHBUS는 지역 주민들이 취미와 관심사를 공유하며 실제로 만날 수 있는 **지역 기반 커뮤니티 플랫폼**입니다.

### 🎪 개발 배경

현대 사회에서 이웃 간 소통이 줄어들고 있습니다. NEIGHBUS는 같은 동네에 살면서도 서로를 모르는 이웃들을 **온라인에서 연결하고, 오프라인 모임으로 확장**하여 진정한 커뮤니티를 만들고자 합니다.

### 📊 프로젝트 정보

| 항목              | 내용                      |
| ----------------- | ------------------------- |
| **개발 기간**     | 2024.10 ~ 2024.12 (3개월) |
| **팀 구성**       | 4인 (풀스택 개발)         |
| **프로젝트 성격** | 팀 포트폴리오 프로젝트    |

---

## ✨ 주요 기능

### 🎭 동아리 & 모임 관리

```
🏃 운동 모임 · 📚 독서 클럽 · 🎨 취미 활동 · 🍳 요리 교실
```

- **동아리 생성 및 가입**: 관심사에 맞는 동아리를 만들고 참여하세요
- **오프라인 모임 개설**: 실제 만남을 위한 모임을 손쉽게 조직
- **자동 마감 시스템**: 스케줄러가 지난 모임을 자동으로 마감 처리

### 💬 실시간 소통

- **WebSocket 기반 채팅**: 1:1 채팅과 동아리 그룹 채팅
- **실시간 알림**: 댓글, 가입 승인, 모임 참여 등 즉각적인 알림
- **AI 챗봇 지원**: OpenAI GPT를 활용한 24시간 FAQ 자동 응답

### 📝 커뮤니티 게시판

- **자유게시판**: 동네 이야기를 자유롭게 나누세요
- **갤러리**: 동아리 활동 사진을 공유하는 공간
- **대댓글 시스템**: 깊이 있는 대화가 가능한 댓글 구조

### 🛡️ 안전한 커뮤니티

- **신고 시스템**: 부적절한 콘텐츠 신고 및 관리자 검토
- **소셜 로그인**: Google, Naver, Kakao OAuth2 지원
- **Spring Security**: CSRF, XSS, SQL Injection 방어

### 📊 관리자 대시보드

- **실시간 통계**: Chart.js 기반 시각화된 데이터
- **통합 관리**: 회원, 동아리, 게시글, 신고 일괄 관리
- **월별 분석**: 가입자 추이, 카테고리별 모임 분포

---

## 🛠️ 기술 스택

### Backend

<div>


![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=for-the-badge&logo=mybatis&logoColor=white)
![Java](https://img.shields.io/badge/Java%2017-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

</div>

### Frontend

<div>


![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![Chart.js](https://img.shields.io/badge/Chart.js-FF6384?style=for-the-badge&logo=chart.js&logoColor=white)

</div>

### Database & AI

<div>


![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)

</div>

### Tools & Infra

<div>


![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-010101?style=for-the-badge&logo=socketdotio&logoColor=white)

</div>

### Architecture

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│   Thymeleaf · REST API · WebSocket     │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│          Security Layer                 │
│   Spring Security · OAuth2 · CSRF      │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│          Business Layer                 │
│   Service · Scheduler · Validator       │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│        Persistence Layer                │
│        MyBatis · DTO Objects            │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│           MySQL Database                │
└─────────────────────────────────────────┘
```

---

## 📸 스크린샷

<div align="center">


### 🏠 메인 화면

*지역별 인기 동아리와 추천 모임을 한눈에*

### 🎪 동아리 목록

*카테고리와 지역으로 필터링된 동아리 찾기*

### 💬 실시간 채팅

*WebSocket 기반의 끊김없는 대화*

### 📊 관리자 대시보드

*Chart.js로 시각화된 통계 데이터*

</div>

---

## 🎨 핵심 구현 기능

### 1️⃣ 자동 모임 마감 스케줄러

```java
@Scheduled(cron = "0 0 * * * *")
public void closeExpiredGatherings() {
    int count = recruitmentService.autoCloseExpiredGatherings();
    log.info("🔒 {}개의 모임이 자동 마감되었습니다", count);
}
```

- **매시간 정각** 실행되어 지난 모임을 자동으로 마감
- 관리자의 수동 관리 부담 최소화

### 2️⃣ 실시간 알림 시스템

```javascript
// 읽지 않은 알림 개수를 실시간으로 업데이트
async function updateUnreadCount() {
    const response = await fetch('/alarm/unread-count');
    const count = await response.json();
    document.querySelector('.badge').textContent = count;
}
```

- 비동기 알림 생성으로 사용자 경험 향상
- 댓글, 가입 승인, 친구 요청 등 다양한 이벤트 트리거

### 3️⃣ OAuth2 소셜 로그인

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService);
        return http.build();
    }
}
```

- Google, Naver, Kakao 3사 소셜 로그인
- 간편한 회원가입으로 진입 장벽 제거

### 4️⃣ MyBatis 동적 쿼리

```xml
<select id="selectGatheringsPaginated">
    SELECT * FROM recruitments
    <where>
        <if test="keyword != null">
            AND title LIKE CONCAT('%', #{keyword}, '%')
        </if>
        <if test="status != null">
            AND status = #{status}
        </if>
    </where>
    ORDER BY id ${sortOrder}
    LIMIT #{limit} OFFSET #{offset}
</select>
```

- 검색 조건에 따라 유연하게 변하는 쿼리
- 페이지네이션과 정렬 기능 구현

---

## 🚀 시작하기

### 📋 필수 요구사항

- **Java 17** 이상
- **MySQL 8.0** 이상
- **Gradle 8.0** 이상

### ⚙️ 설치 및 실행

1. **레포지토리 클론**

```bash
git clone https://github.com/your-username/neighbus.git
cd neighbus
```

2. **데이터베이스 설정**

```sql
CREATE DATABASE neighbus;
```

3. **application.properties 설정**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/neighbus
spring.datasource.username=your_username
spring.datasource.password=your_password

# OpenAI API Key
spring.ai.openai.api-key=your_api_key

# OAuth2 설정 (필요시)
spring.security.oauth2.client.registration.google.client-id=your_client_id
spring.security.oauth2.client.registration.google.client-secret=your_client_secret
```

4. **프로젝트 빌드 및 실행**

```bash
./gradlew build
./gradlew bootRun
```

5. **브라우저에서 접속**

```
http://localhost:8080
```

---

## 📁 프로젝트 구조

```
com.neighbus
├── 📂 account          # 회원 인증/인가
├── 📂 admin            # 관리자 기능
├── 📂 alarm            # 알림 시스템
├── 📂 chat             # 실시간 채팅
├── 📂 chatbot          # AI 챗봇
├── 📂 club             # 동아리 관리
├── 📂 config           # 설정 (Security, WebSocket)
├── 📂 freeboard        # 자유게시판
├── 📂 gallery          # 갤러리
├── 📂 recruitment      # 모임 모집
└── 📂 util             # 유틸리티
```

---

## 📊 ERD (데이터베이스 설계)

### 주요 테이블

| 테이블           | 설명       | 주요 컬럼                          |
| ---------------- | ---------- | ---------------------------------- |
| **users**        | 회원 정보  | id, username, email, role          |
| **clubs**        | 동아리     | id, club_name, category, leader_id |
| **recruitments** | 모임 모집  | id, club_id, meeting_date, status  |
| **freeboards**   | 자유게시판 | id, title, content, writer         |
| **alarms**       | 알림       | id, user_id, type, is_read         |
| **reports**      | 신고       | id, type, target_id, status        |

---

## 🎯 주요 API 엔드포인트

### 🔐 회원 관리

| Method | Endpoint                           | 설명        |
| ------ | ---------------------------------- | ----------- |
| POST   | `/account/signup`                  | 회원가입    |
| POST   | `/account/login`                   | 로그인      |
| GET    | `/oauth2/authorization/{provider}` | 소셜 로그인 |

### 🎪 동아리

| Method | Endpoint          | 설명        |
| ------ | ----------------- | ----------- |
| GET    | `/club/list`      | 동아리 목록 |
| POST   | `/club/create`    | 동아리 생성 |
| POST   | `/club/{id}/join` | 동아리 가입 |

### 📅 모임

| Method | Endpoint                 | 설명      |
| ------ | ------------------------ | --------- |
| GET    | `/recruitment/list`      | 모임 목록 |
| POST   | `/recruitment/create`    | 모임 생성 |
| POST   | `/recruitment/{id}/join` | 모임 참여 |

### 📊 관리자

| Method | Endpoint                     | 설명          |
| ------ | ---------------------------- | ------------- |
| GET    | `/api/admin/dashboard/stats` | 대시보드 통계 |
| GET    | `/api/admin/users`           | 회원 목록     |
| POST   | `/api/admin/reports/process` | 신고 처리     |

---

## 👥 팀원 소개

<div align="center">


|                                        |                                        |                                        |                                        |
| :------------------------------------: | :------------------------------------: | :------------------------------------: | :------------------------------------: |
|               **팀원 1**               |               **팀원 2**               |               **팀원 3**               |               **팀원 4**               |
|               회원/인증                |              동아리/모임               |             게시판/갤러리              |             관리자 페이지              |
| [@member1](https://github.com/member1) | [@member2](https://github.com/member2) | [@member3](https://github.com/member3) | [@member4](https://github.com/member4) |

</div>

---

## 📈 향후 개선 계획

### 🎯 기능 확장

- [ ] 📱 모바일 앱 개발 (React Native)
- [ ] 💰 결제 시스템 연동
- [ ] 📅 동아리 일정 캘린더
- [ ] ⭐ 포인트/리워드 시스템

### 🛠️ 기술 개선

- [ ] ☁️ AWS 배포 및 S3 스토리지
- [ ] 🔍 Elasticsearch 검색 엔진
- [ ] 🚀 Redis 캐싱
- [ ] 🐳 Docker 컨테이너화

---

## 🤝 기여하기

프로젝트에 기여하고 싶으신가요? 환영합니다!

1. 이 저장소를 Fork 하세요
2. Feature 브랜치를 생성하세요 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 커밋하세요 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 Push 하세요 (`git push origin feature/AmazingFeature`)
5. Pull Request를 열어주세요

---

## 📄 라이센스

이 프로젝트는 MIT 라이센스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

---

## 📞 문의

프로젝트에 대한 질문이나 제안이 있으시면 언제든지 연락주세요!

- 📧 Email: neighbus@example.com
- 🐛 Issues: [GitHub Issues](https://github.com/your-username/neighbus/issues)

---

<div align="center">


### ⭐ 이 프로젝트가 마음에 드셨다면 Star를 눌러주세요!

**Made with ❤️ by NEIGHBUS Team**

[⬆️ 맨 위로 돌아가기](#️-neighbus)

</div>
