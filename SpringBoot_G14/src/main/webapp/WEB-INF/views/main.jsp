<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<!-- <link rel="stylesheet" type="text/css" href="/css/board.css" > -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
   <h1 align="center">게시글 리스트</h1>
   ${loginUser.name}(${loginUser.userid})님 로그인
   <div class="d-flex mb-3">
      <div class="p-2">
         <div class="d-grid gap-2 d-md-block">
            <input class="btn btn-primary" type="button" value="정보수정" onClick="location.href='memberEditForm'"/>
            <input class="btn btn-primary" type="button" value="로그아웃" onClick="location.href='logout'">
         </div>
      </div>
      <div class="ms-auto p-2">
         <a class="btn btn-primary" href="boardWriteForm">게시글 등록</a>
      </div>
   </div>
   <table class="table table-striped table-hover">
      <thead>
      <tr align="center"><th scope="col">번호</th><th scope="col">제목</th><th scope="col">작성자</th><th scope="col">작성일</th><th scope="col">조회</th></tr>
      </thead>
      <tbody class="table-group-divider">
      <c:forEach var="board" items="${boardList}">
         <tr class="record"><td align="center">${board.num}</td>
            <td>
               <a href="boardView?num=${board.num}">${board.title}</a>
               <c:if test="${board.replycnt > 0}">
                  <span style="color:red;font-weight:bold;"> [${board.replycnt}] </span>
               </c:if>
            </td>
            <td align="center">${board.userid}</td>
            <td align="center"><fmt:formatDate value="${board.writedate }" /></td>
            <td align="center">${board.readcount}</td></tr>
      </c:forEach>
      </tbody>
   </table>

   <nav id="paging" aria-label="Page navigation default">
      <c:set var="action" value="/main?" />
      <ul class="pagination justify-content-center">
         <c:if test="${paging.endPage > 1}">
            <c:url var="action" value="${param.command}" />
            <c:if test="${paging.prev}">
               <li class="page-item">
                  <a class="page-link" href="/main?page=1"><span style="font-size:70%">처음으로</span></a>
               </li>
               <li class="page-item">
                  <a class="page-link" href="/main?page=${paging.beginPage - 1}">&laquo;</a>
               </li>
            </c:if>
            <c:forEach begin="${paging.beginPage}" end="${paging.endPage}" var="index">
               <c:choose>
                  <c:when test="${index == paging.page}">
                     <li class="page-item">
                        <a class="page-link active" aria-current="page">${index}</a>
                     </li>
                  </c:when>
                  <c:otherwise>
                     <li class="page-item">
                        <a class="page-link" href="/main?page=${index}">${index}</a>
                     </li>
                  </c:otherwise>
               </c:choose>
            </c:forEach>
            <c:if test="${paging.next}">
               <li class="page-item">
                  <a class="page-link" href="/main?page=${paging.endPage + 1}">&raquo;</a>
               </li>
               <li class="page-item">
                  <a class="page-link" href="/main?page=${paging.totalPage}"><span style="font-size:70%">마지막으로</span></a>
               </li>
            </c:if>
         </c:if>
      </ul>
   </nav>
   <div class="clear"></div>

</div>

</body>
</html>