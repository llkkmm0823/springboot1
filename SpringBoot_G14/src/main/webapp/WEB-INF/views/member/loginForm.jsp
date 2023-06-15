<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="/css/board.css" >
<script type="text/javascript" src="/script/board.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>

<div class="container mt-5">
   <form action="login" method="post">
      <div class="box mb-3"><div id="title">로그인</div></div>
      <div class="form-floating mb-3">
         <input type="text" class="form-control" id="userid" placeholder="아이디" name="userid" size="20" value="${dto.userid}" />
         <label for="userid">아이디</label>
      </div>
      <div class="form-floating mb-3">
         <input type="password" class="form-control" id="pwd" placeholder="비밀번호" name="pwd" size="20" />
         <label for="pwd">비밀번호</label>
      </div>
      <div class="justify-content-md-center">
         <input class="btn btn-primary" type="submit" value="로그인" />
         <input class="btn btn-primary" type="reset" value="다시작성" />
         <input class="btn btn-primary" type="button" value="회원가입" onClick="location.href='memberJoinForm'" />
         <div class="box"><div id="footer">${message}</div></div>
      </div>
   </form>
</div>

</body>
</html>