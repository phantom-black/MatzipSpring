<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" type="text/css" href="/res/css/common.css?dkdk=10">
<c:forEach items="${css}" var="item">
	<link rel="stylesheet" type="text/css" href="/res/css/${item}.css?dkddk=110">
</c:forEach>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
	<div id="container">
		<header>
			<div id="headerLeft">
				<c:if test="${loginUser != null}">
					<div class="containerPImg">
						<c:choose>
							<c:when test="${loginUser.profile_img != null}">
								<img class="pImg" src="/res/img/user/${loginUser.i_user}/${loginUser.profile_img}">
							</c:when>
							<c:otherwise>
								<img class="pImg" src="/res/img/default_profile.jpg">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="ml5">${loginUser.nm}님 환영합니다.</div>
					<div class="ml15" id="headerLogout"><a href="/user/logout">로그아웃</a></div>
				</c:if>
				<c:if test="${loginUser == null}">
					<div class="ml15" id="headerLogout"><a href="/user/login">로그인</a></div>
				</c:if>
			</div>
			<div id="headerRight">
				<a href="/rest/map">지도</a>
				<c:if test="${loginUser != null}">
					<a class="ml15" href="/rest/reg">등록</a>
				</c:if>
				<c:if test="${loginUser == null}">
					<a class="ml15" href="#" onclick="alert('로그인이 필요합니다.')">등록</a>
				</c:if>
				<a class="ml15" href="/user/restFavorite">찜</a>
			</div>
		</header>
		<section>
			<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
		</section>
		<footer>
			<span>회사 정보</span>
		</footer>
	</div>
	<script>
		
	</script>
</body>
</html>