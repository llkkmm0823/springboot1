<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/admin/admin.css">
<script src="/admin/admin.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#myButton').click( function(){ // mybutton을 클릭하면 여기서부터 이 함수를 시작해주세요 라는 뜻
		
		var formselect = $("#fileupForm")[0];   // 지목된 폼을 변수에 저장
		var formdata = new FormData(formselect);   // 전송용 폼객에 다시 저장
		
		//에이잭스코드 시작
		$.ajax({    // 웹페이지 이동 또는 새로고침이 필요없는 request요청
			url:"<%=request.getContextPath() %>/fileup", /*현재 주소(위치)의 fileup리퀘스트로 요청 http://localhost:8070/fileup  */
			/*  request.getContextPath() 이 자리에 도메인을 넣어도 상관없음 */
			type:"POST",
			enctype:"multipart/form-data",
			async: false,
			data: formdata,
	    	timeout: 10000,
	    	contentType : false,
	        processData : false,
	        //여기까지가 설정을 넣어 전송하는 코드
	        
	        
	        success : function(data){	//controller에서 리턴된 해시맵이 data로 전달됨
	            if(data.STATUS == 1){  	//동적으로 div태그 달아주기.
	            	$("#filename").append("<div>"+data.FILENAME+"</div>");
	            	$("#image").val(data.FILENAME);
	            	$("#filename").append("<img src='product_images/"+data.FILENAME+"' height='150'/>");
	            	//이름도 쓰고 미리보기 이미지 태그도 넣어주고
	            }
	        },
	        error: function() {				alert("실패");			}
		});
	});
});
</script>

</head>
<body>

<div id="wrap">
<header>			
	<div id="logo">
		<img style="width:800px" src="/admin/bar_01.gif">
		<img src="/admin/text.gif">
	</div>	
	<input class="btn" type="button" value="logout" style="float: right;"
		onClick="location.href='adminLogout'">			
</header>
<div class="clear"></div>