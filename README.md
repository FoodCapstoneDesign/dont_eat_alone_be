# MealMate
## 기능 추가
<로그인>
1. SNS 연동로그인
2. JWT로그인 방식 //
3. ID까먹은 경우 -> 이름, 생년월일로 확인
4. PW까먹은 경우 -> 이메일 인증
5. 회원탈퇴
6. 로그아웃

<회원가입>
1. 이메일, 이름, 단과대, 학과, ID, PW -> 완료되면 좋아하는 음식 카테고리 설정(3가지), 주민번호 앞자리 6자리 -> 앞자리 성별저장
2. 중복체크 -> 이메일, ID
3. 학교 인증 -> 이메일인증(학교메일로 인증코드 발송 6자리)

<메인 화면>
1. 신창 식당 불러옴
2. 카테고리 & 식당 명으로 검색
3. 게시글 목록으로 가는 API
4. 회원정보

<회원 정보 수정>
1. ID
2. PW
3. 좋아하는 음식 카테고리

<식당 정보 페이지>
1. 가게 정보 불러오기 API
2. 가게 메뉴 API
3. 해당 가게에 등록된 게시물 조회 API -> 모집 중, 모집완료 상태인지
4. 게시물 페이징 10개씩(최신 순)
5. 단과대, 과 검색해서 sort한 결과값 출력
6. 디폴트 메뉴 페이지

<게시물 세부페이지>
1. 게시물 정보 넘기는 API -> 제목, 작성자, 내용, 작성일자, 조회 수, 학과
2. 채팅 기능 API
3. 목록 하단에 이전 게시글, 다음 게시글 표시 (임시)

<채팅>
1. 채팅
2. 채팅 내용 저장

