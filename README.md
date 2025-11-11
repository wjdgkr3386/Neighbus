# Neighbus
동네 모임 서비스
    

# 초기 설정
### mysql 테이블
CREATE DATABASE neighbus DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 나중에 할 것들
1. ERD 새로 그리기
2. 회사 연혁, 회장님의 말씀 등등
3. 만족도 기능
4. 친구 수락 , 거부 ,차단 기능
5. 


.git 폴더가 있는 곳이 git 저장소이다.
## git bash 명령어
cd .. : 상위 폴더로 이동  
cd : 최상위 폴더로 이동  
cd 폴더경로 : 폴더 경로로 이동  
	
git init : 현재 폴더를 Git 저장소로 초기화  
git clone 깃헙프로젝트주소 : 프로젝트 폴더 복제 (설정도 그대로 복제)  
	
ls -a : .git이 출력되면 현재 폴더가 깃 저장소이다  
git remote -v : 연결된 저장소 확인  
git remote remove 저장소별칭 : 저장소별칭과 관련된 설정 삭제  
git remote add origin 저장소주소 : 현재 폴더를 origin이라는 별칭으로 저장소 주소에 연결  
	
git pull origin 가져올브랜치 : origin이 가리키는 주소의 브랜치에서 pull  
	
git branch : 현재 로컬 브랜치 목록 확인  
git branch -r : 원격 저장소의 브랜치 목록 확인  
git branch -M main : 내 로컬 저장소 브랜치 이름을 main으로 변경  
	
git checkout 브랜치명 : 이미 존재하는 로컬 브랜치로 전환  
git checkout -b 로컬브랜치명 : 새 브랜치를 만들고 바로 체크아웃  
git checkout -b 로컬브랜치명 origin/원격브랜치명 : 로컬브랜치명으로 로컬에서 작업할 브랜치를 만들고 origin/원격브랜치에서 가지고온 후 전환  
	
git add . : 모든 파일 스테이징  
git commit -m "커밋내용" : 커밋 생성  
	
git push origin 원격브랜치명 : 원격브랜치로 푸쉬  
git push origin 로컬브랜치:원격브랜치 : 내 로컬 브랜치를 원격브랜치로 푸쉬  
git push -u origin 브랜치명 : 이걸로 push하면 다음부터는 git push만 해도 됨  
git push -f origin 브랜치명 : 내 로컬 기록을 별칭에 연결된 저장소에 강제로 덮어씀 (다른 사람 커밋 다 날라감)  













## 시험 출제 사이트
간단하게 시험을 출제하고 푸는 사이트

## 개발 기간
- 2025.11.03 ~ 2025.30 (2달)

## 기술 스택
- 프레임워크: Spring Boot
- 언어: Java, Thymeleaf, HTML, CSS
- DB: MySQL
- 빌드 도구: Gradle
- 배포 형식: jar

## 주요 기능
- 
- 

## ERD


## 프로젝트 화면
### 메인

## 문제점 및 해결방안
### 작품 개발 측면

### 해결 방안 
