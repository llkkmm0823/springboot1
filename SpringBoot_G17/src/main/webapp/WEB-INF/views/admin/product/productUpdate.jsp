<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../../include/admin/header.jsp"%>
<%@ include file="../../include/admin/sub_menu.jsp"%>
<article>
<h1>상품수정</h1>  
<form name="frm" method="post" >
<input type="hidden" name="pseq" value="${dto.PSEQ}">
<input type="hidden" name="oldfilename" value="${dto.IMAGE}">
<table id="list">
  <tr><th>상품분류</th><td colspan="5">
	    <select name="kind">
		      <c:forEach items="${kindList}" var="kind" varStatus="status">
			        <c:choose>
				          <c:when test="${dto.KIND==status.count}">
				            	<option value="${status.count}" selected="selected">${kind}</option>
				          </c:when>
			          <c:otherwise><option value="${status.count}">${kind}</option></c:otherwise>
			        </c:choose>
		      </c:forEach>
	    </select></td></tr>
  <tr><th>상품명</th><td width="343" colspan="5">
      <input type="text" name="name" size="47" value="${dto.NAME}"></td></tr>
  <tr><th>원가[A]</th><td width="70">        
      <input type="text" name="price1" size="11" value="${dto.PRICE1}" onKeyup="cal()"></td>
       <th>판매가[B]</th><td width="70">
      <input type="text" name="price2" size="11" value="${dto.PRICE2}"  onKeyup="cal()"></td>
  <th>[B-A]</th><td width="72">
      <input type="text" name="price3" size="11" value="${dto.PRICE3}"></td></tr>
  <tr><th>베스트상품</th><td>
      <c:choose>
        <c:when test='${dto.BESTYN=="Y"}'>
            <input type="checkbox" name="bestyn" value="Y" checked="checked">
        </c:when>
        <c:otherwise>
        	<input type="checkbox" name="bestyn" value="N">
        </c:otherwise>
      </c:choose></td>        
    <th>사용유무</th><td>
      <c:choose>
        <c:when test='${dto.USEYN=="Y"}'>
              <input type="checkbox" name="useyn" value="Y" checked="checked">
        </c:when>
      <c:otherwise>
      		<input type="checkbox" name="useyn" value="N">
      	</c:otherwise>
    </c:choose></td></tr>
  <tr><th>상세설명</th><td colspan="5">
      <textarea name="content" rows="8" cols="70" >${dto.CONTENT}</textarea></td></tr>
  <tr><th>상품이미지</th>
  
  		<td width="343" colspan="5" style="vertical-align:top;">
      			현재이미지 : <img src="product_images/${dto.IMAGE}" width="200pt"><br>
      			<!-- <input type="file" name="image"> --> * 주의 : 이미지를 수정할때에만 선택해주세요
	   			<input type="hidden" name="image" id="image" value="${newImage}">
	   			<div id="filename">${newImage}</div>
		</td>
		
   </tr>    
</table>
<input class="btn" type="button" value="수정" onClick="go_mod_save('${dto.pseq}')">           
<input class="btn" type="button" value="취소" onClick="go_mov()">
</form> 

<div style="position:relative;  border:1px solid black; width:500px; margin:0 auto;">
	<form name="fromm" id="fileupForm" method="post" enctype="multipart/form-data">
				<input type="file" name="fileimage">
				<input type="button" id="myButton" value="추가">
	</form>
</div>

</article>
<%@ include file="../../include/admin/footer.jsp"%>