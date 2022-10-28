<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="/css/common/common.css">
<meta charset="UTF-8">
<header>
	<div class="header-bar flex">
		<ul class="flex">
			<li> <!-- 로고, 타이틀 -->
				<a href="/">
					<img class="logo" src="/images/logo.png">
					<span class="logo-title">드라이브</span>
				</a>
			</li>
			<li> <!-- 검색 바 -->
				<form action="/search" method="get">
					<input type="text" name="name" placeholder="드라이브에서 검색">
					<img src="/images/search.svg">
					<input type="submit" value="검색">
				</form>
			</li>
			<li> <!-- 로그인  -->
				<sec:authorize access="isAnonymous()">
					<a href="/member/login">로그인</a>
					<a href="/member/signup">회원가입</a>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal" var="user" />
					<c:catch var="e">
						<span>${user.username}님</span>
					</c:catch>
					<c:if test="${ e != null }"> 
						<c:if test="${empty user.attributes.name}">
							<span>${user.attributes.response.name}님</span>
						</c:if>
						<c:if test="${empty user.attributes.response.name}">
							<span>${user.attributes.name}님</span>
						</c:if>
					</c:if>
					 
					<%-- <sec:authentication property="principal" var="user" />${user.attributes.response.name} <!-- naver -->
					<sec:authentication property="principal" var="user" />${user.attributes.name} <!-- google --> --%>
					<a href="/logout">로그아웃</a>
					
				</sec:authorize>
			</li>
		</ul>
	</div>
</header>