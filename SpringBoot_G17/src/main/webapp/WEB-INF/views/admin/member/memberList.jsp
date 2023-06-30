<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/admin/header.jsp"%>
<%@ include file="../../include/admin/sub_menu.jsp"%>

<article>
<h1>회원리스트</h1>  
<form name="frm" method="post">
<table style="float:right; ">
	<tr><td>회원 이름 <input type="text" name="key" value="${key}">
	<input class="btn" type="button" value="검색" onclick="go_search('memberList')">
	<input class="btn" type="button" name="btn_total" value="전체보기 " onClick="go_total('memberList')"></td> </tr>
</table><br>
<table id="orderList"  align="center">
	<tr><th>아이디(탈퇴여부)</th><th> 이름 </th><th>이메일</th><th>우편번호</th><th>주소</th><th>전화</th><th>가입일</th></tr>
    <c:forEach items="${memberList}" var="memberVO">  
	    <tr><td>${memberVO.ID} 
	    	<c:choose>
	      		<c:when test='${memberVO.USEYN=="Y"}'>
	        		<input type="checkbox" name="useyn"   
	        			onchange="reInsert('${memberVO.ID}', '${memberVO.USEYN}');">
		        </c:when>
		        <c:otherwise>
		        	<input type="checkbox" name="useyn" checked="checked"
		        		onchange="reInsert('${memberVO.ID}', '${memberVO.USERYN}');" >
		        </c:otherwise>
	    	</c:choose></td>
	    	<td>${memberVO.NAME}</td><td>${memberVO.EMAIL}</td><td>${memberVO.ZIP_NUM}</td>
	    	<td>${memberVO.ADDRESS1} ${memberVO.ADDRESS2} </td>
	    	<td>${memberVO.PHONE}</td><td><fmt:formatDate value="${memberVO.INDATE}"/></td></tr>
  </c:forEach>
</table>
<jsp:include page="../../include/paging/paging.jsp">   
    <jsp:param value="memberList" name="command" />
</jsp:include>
</form>
</article>


<%@ include file="../../include/admin/footer.jsp"%>