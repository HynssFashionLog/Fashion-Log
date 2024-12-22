# Hynss Fashion-Log
<div align="center">
  <img src = "https://github.com/user-attachments/assets/bc9d675c-1535-444a-850f-74e564057e82">                
</div>
</br>

## 프로젝트 소개
<p>
  팀 Hynss의 Fashion-Log는 국내에서 특히 활성화 되어있는 패션 커뮤니티에 발 맞춰가기 위해 개발되었습니다.
  5가지의 카테고리 게시판을 통해 의류 외에도 룩북, 인터뷰와 같은 다양한 패션 정보 교류를 제공하는 웹 서비스 입니다.
  회원 가입 없이는 서비스 이용이 불가하기 때문에 초반 진입장벽이 높지만 이를 통해 질 좋은 정보와 거래 게시판의 신뢰도를 높혔습니다.
</p>
</br>

## 배포 주소
> ~~http://43.201.146.10:8080/fashionlog~~

> http://54.180.99.78:8080/fashionlog

(관리자 **아이디는 test**, **비밀번호는 1234** 입니다.)
</br>

## 팀원 소개
|지승우|이승희|이남경|허영윤|
|:-:|:-:|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/41260600?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/2533026?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/168792873?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/77563168?v=4" width="150" height="150"/>|
|[@JEESW](https://github.com/JEESW)|[@LLSNsssz](https://github.com/LLSNsssz)|[@NamK666](https://github.com/NamK666)|[@cloudisme99](https://github.com/cloudisme99)|
</br>

## 기술 스택
### Front-End
<img alt="HTML5" src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img alt="CSS" src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img alt="JAVASCRIPT" src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
<br>

### Back-End
<img alt="Thymeleaf" src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white"> <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F.svg?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"> <img src="https://img.shields.io/badge/spring Security-6DB33F?style=for-the-badge&logo=spring Security&logoColor=white"> <br> 
<img alt="MySQL" src ="https://img.shields.io/badge/MySQL-003545.svg?&style=for-the-badge&logo=MySQL&logoColor=white"/> <img alt="gradle" src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img alt="Amazon Lightsail" src ="https://img.shields.io/badge/Amazon Lightsail-FF9900.svg?&style=for-the-badge&logo=Amazon Lightsail&logoColor=white"/>

### Communitcation
<img alt="github" src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img alt="Discord" src="https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white"> <img alt="Slack" src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"> <img alt="Figma" src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white">

</br>


## 프로젝트 구조
### ERD
![image](https://github.com/user-attachments/assets/c7e5afe2-ce69-47ed-b884-c14c98c4238e)
</br>


## 화면 구성
| 메인 페이지  |  게시글/댓글작성   |
| :-------------------------------------------: | :------------: |
|  <img width="329" src="https://github.com/user-attachments/assets/2175d659-7485-49d5-b3a4-4aedf078acf7"/> |  <img width="329" src="https://github.com/user-attachments/assets/f83deb73-ac92-4525-959b-cb688f3818fd"/>|  
| 로그인 페이지   |  회원가입 페이지   |  
| <img width="329" src="https://github.com/user-attachments/assets/1bebba91-250f-4998-8386-da8fcf50e64f"/>   |  <img width="329" src="https://github.com/user-attachments/assets/b9071f53-1196-43d1-8afc-8044b6b30f9d"/>     |
| 게시글 목록   |  게시글 디테일   |  
| <img width="329" src="https://github.com/user-attachments/assets/810d9a4d-a417-41ab-8190-88d1057ccaf8"/>   |  <img width="329" src="https://github.com/user-attachments/assets/c56389c2-d3a0-41b4-bf42-de9ce93eba89"/>     |
</br>


## 주요 기능
### 회원가입, 로그인 기능
- 이메일, 닉네임, 핸드폰 번호 중복체크 기능
- 가입된 회원만 메인 페이지 입장 가능함
- `로그인 기억하기` 기능
- 회원 탈퇴 기능

### 관리자 페이지
- 관리자(`ADMIN`)만 접속 가능
- 관리자를 제외한 회원들의 권한을 변경할 수 있음

### 모든 게시글 작성 및 댓글 작성 기능
- 관리자, 일반 권한을 가진 회원만 작성 가능
- 단, 공지사항 게시판은 관리자만 작성 가능함
- 타인이 작성한 글이 아닌 본인이 작성한 글과 댓글만 수정 및 삭제 가능함
- 빈칸이 들어가는 경우나 글자 수가 많을 시 개행 처리와 오버된 경우 예외 처리
  
### 공지사항 게시판
- 관리자만 글 작성 가능
- 카테고리를 선택하여 게시판 별 공지사항을 작성할 수 있음
- 공지사항 게시판 내에선 모든 카테고리 공지사항 확인 가능
- 각 카테고리 별 게시판에선 카테고리에 해당되는 최신 공지사항 5개만 볼 수 있음
- 정지된 회원도 공지사항 게시판은 확인 가능


