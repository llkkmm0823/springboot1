<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<article>
<h1>배너 등록</h1>  
<form name="frm" action="bannerWrite" method="post">
	<table border="1"  cellspacing="0"  width="800">
		<tr><th>제목 </th><td width="642" >
			<input type="text" name="subject" size="47" ></td></tr>
		<tr><th>순위 </th>
			<td width="642">
				<select name="order_seq">
					<option value="">디스플레이될 순서를 선택하세요 </option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">사용안함 </option>
				</select>
			</td></tr>
		<tr height="250"><th>배너 이미지</th>
	    	<td width="642" style="vertical-align:top;">
	   			<input type="hidden" name="image" id="image" value="">
	   			<div id="filename"></div></td></tr>
	</table>
	<input class="btn" type="submit" value="등록" >   
	<input class="btn" type="reset" value="취소" >
	</form>
	<div style="position:relative; top:-70px; left:500px;">
		<form name="fromm" id="fileupForm" method="post" enctype="multipart/form-data">
					<input type="file" name="fileimage"><input type="button" id="myButton" value="추가">
		</form>
	</div></article>
