<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>buy_ticket.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>

<div class="container mt-5">
   <h2 class="mb-3">카드 결제</h2>
   <form action="buyTicketCard" method="post">
      <div class="form-floating mb-3">
         <input type="text" class="form-control" id="id" placeholder="고객 아이디" name="id" size="80" />
         <label for="id">고객 아이디</label>
      </div>
      <div class="form-floating mb-3">
         <input type="text" class="form-control" id="amount" placeholder="티켓 구매수" name="amount" size="80" />
         <label for="amount">티켓 구매수</label>
      </div>
      <div class="form-floating mb-3">
         <input type="text" class="form-control" id="error" placeholder="에러 발생 여부" name="error" size="80" value="1" />
         <label for="error">에러 발생 여부</label>
      </div>
      <input class="btn btn-primary" type="submit" value="구매">
   </form>
   <hr>
   에러 발생 여부에 0을 입력하면 에러가 발생합니다.
</div>

</body>
</html>