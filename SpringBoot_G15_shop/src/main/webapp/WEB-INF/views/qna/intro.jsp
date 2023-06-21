<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub04/sub_image_menu.jsp" %>

<article>

<h2>회사 소개</h2>
슈샵은 어쩌구 저쩌구............

<h2>오시는 길</h2>
신촌역 4번출구 전방 200M 이대역 1번출구 전방 200M 지점 위고인빌딩 1층(상담실), 2층, 4층(강의실)

<h3>버스</h3>
신촌역 cgv에서 내리삼

<h3>전철</h3>
신촌역 4번출구 전방 200M 이대역 1번출구 전방 200M 지점 위고인빌딩 1층(상담실), 2층, 4층(강의실)

<h3>위치안내</h3>
<!-- * 카카오맵 - 지도퍼가기 -->
<!-- 1. 지도 노드 -->
<div id="daumRoughmapContainer1687325154888" class="root_daum_roughmap root_daum_roughmap_landing"></div>

<!--
	2. 설치 스크립트
	* 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
-->
<script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>
<!--▲ 위 스크립트는 한 번만 작성해도 됨  -->

<!-- 3. 실행 스크립트 -->
<script charset="UTF-8">
	new daum.roughmap.Lander({
		"timestamp" : "1687325154888",
		"key" : "2f9td",
		"mapWidth" : "640",
		"mapHeight" : "360"
	}).render();
</script>


</article>


<%@ include file="../include/headerfooter/footer.jsp" %>
