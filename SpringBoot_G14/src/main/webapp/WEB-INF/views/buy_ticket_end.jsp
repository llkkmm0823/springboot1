<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>buy_ticket_end.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>

<div class="container mt-5">
<table class="table table-striped table-hover">
   <thead>
      <tr align="center"><th scope="col" colspan="2"><h2>buy_ticket_end.jsp 입니다.</h2><h1>티켓이 아래의 정보로 정상 구매 되었습니다.</h1></th></tr>
   </thead>
   <tbody class="table-group-divider">
      <tr align="center"><th scope="row" style="width:15%;">아이디</th><td>${id}</td></tr>
      <tr align="center"><th scope="row">수량</th><td>${amount}</td></tr>
      <tr align="center"><th scope="row">에러(1:에러없음, 0:에러발생)</th><td>${error}</td></tr>
   </tbody>
</table>
</div>

</body>
</html>