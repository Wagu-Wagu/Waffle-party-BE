## ❤️ 기획 배경
**와플파티 - OTT 같이 볼 사람이 필요할 때** <br> 

OTT를 같이 보면서 채팅하는 `왓챠파티`, `Teleparty`, `Screena`에서 **함께 볼 사람을 모집**하는 요구사항에서 기획을 하게 되었습니다. <br> 

기존의 '다음카페', '트위터' 등의 특정 커뮤니티에서 함께 볼 사람을 모집하는 어려움을, '와플파티'를 통해서 함께 같이 볼 사람을 쉽게 구할 수 있습니다. <br> 

![image](https://github.com/Wagu-Wagu/Waffle-party-BE/assets/77230391/805c5883-8785-46fe-a1d7-802499519a05)



<br> 


## 💻 주 기능 화면

### 랜딩 페이지 및 홈화면  
<img src="https://github.com/Wagu-Wagu/Waffle-party-BE/assets/77230391/b17ffde3-8b18-4ac1-8ea3-c324af12598d" width="50%" height="50%">

- 로그인 기능 : 소셜 로그인 API 와 JWT토큰을 사용하여 구현
- 홈 화면 : 이용자가 설정한 filter 값을 기준으로 글 내역 로드

### 게시글 상세 페이지, 댓글 대댓글, 비밀댓글 구현 
<img src="https://github.com/Wagu-Wagu/Waffle-party-BE/assets/77230391/68b18f3f-b7a9-4c47-a1c7-324e6ae5892a" width="50%" height="50%">

- 게시글 상세 페이지 : 이미지, 글 내용 등 상세 내용 출력
- 댓글, 대댓글, 비밀댓글 : 댓글 작성 시 게시글 작성자에게 알람, 대댓글 작성 시 게시글 작성자와 댓글 작성자에게 알람

<br> 

## ⚒️ Backend Introduction
### 📁 프로젝트 구조
![wagu drawio](https://github.com/Wagu-Wagu/Waffle-party-BE/assets/77230391/814b642d-f664-46b3-a342-74b351eaab45)


<br> 

### 📄 프로젝트 ERD
![image](https://github.com/Wagu-Wagu/Waffle-party-BE/assets/77230391/014074f7-898d-45fb-959b-4993909d74c3)

<br> 

### ⚒️ 사용 기술 스택
- `Spring Boot`, `Spring Data JPA`, `MySQL`
- `EC2`, `S3`, `RDS`, `Code Deploy`, `ApplicationLoadBalancer`
<br> 

<br> 


### 👨‍👦 백엔드 팀원 
<table>

  <tr>
    <td align="center">
      <a href="https://github.com/GaHee99">
        <img
          src="https://github-production-user-asset-6210df.s3.amazonaws.com/77230391/335972318-4606bc8c-4d8e-466b-9b57-1e8bd78ffcb7.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240603%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240603T071717Z&X-Amz-Expires=300&X-Amz-Signature=a20308d1c85dca65f200d38ae4e5f814e4293b93097383d1f613f431f9565f59&X-Amz-SignedHeaders=host&actor_id=77230391&key_id=0&repo_id=802793179"
          width="100px;"
        /><br />최가희(FE)</a><br />
    </td>
    <td align="center">
      <a href="https://github.com/kjy-asl">
        <img
          src="https://github.com/Wagu-Wagu/Waffle-party-BE/assets/77230391/acfc7443-54ae-4be9-aa34-5c2d2752b4e8"
          width="100px;"
        /><br />김주영(BE)</a><br />
    </td>
  </tr>
</table>
