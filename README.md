# Neighbus
동네 모임 서비스



# 나중에 할 것들
1. ERD 새로 그리기
2. 
3. 
4. 
5. 


cd .. : 상위 폴더로 이동
cd / : 최상위 폴더로 이동
cd 폴더경로 : 폴더 경로로 이동

git init : 현재 폴더를 Git 저장소로 초기화
git clone 깃헙프로젝트주소 : 프로젝트 폴더 복제 (설정도 그대로 복제)

git pull origin 가져올브랜치 : origin이 가리키는 주소의 브랜치에서 pull

git remote -v : 연결된 저장소 확인
git remote remove 저장소별칭: 저장소별칭과 관련된 설정 삭제
git remote add origin 저장소주소 : 현재 폴더를 origin이라는 별칭으로 저장소 주소에 연결

git branch : 현재 로컬 브랜치 목록 확인
git branch -r ; 원격 저장소의 브랜치 목록 확인
git branch -M main : 내 로컬 저장소 브랜치 이름을 main으로 변경

git checkout 브랜치명 : 이미 존재하는 로컬 브랜치로 전환
git checkout -b 로컬브랜치명 : 새 브랜치를 만들고 바로 체크아웃
git checkout -b 로컬브랜치명 origin/원격브랜치명 : 로컬브랜치명으로 로컬에서 작업할 브랜치를 만들고 origin/원격브랜치에서 가지고온 후 전환한다.

git add . : 모든 파일 스테이징
git commit -m "커밋내용" : 커밋 생성

git push origin 브랜치명 : 내 로컬을 브랜치로 푸쉬
git push origin main:junghak : 원격저장소의 junghak 브랜치에 푸쉬
git push -u origin 브랜치명 : 이걸로 push하면 다음부터는 git push만 해도 됨
git push -f origin 브랜치명 : 내 로컬 기록을 별칭에 연결된 저장소에 강제로 덮어씀 (다른 사람 커밋 다 날라감)

