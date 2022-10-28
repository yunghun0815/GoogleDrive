<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인</title>
</head>
<body>
	<c:if test="${error eq 'true'}">
		<p style="color: red">${message}</p>
	</c:if>
	<form action="/login" method="post">
		<input type="text" name="id">
		<input type="password" name="password">
		<input type="submit" value="로그인">
	</form>
	<div>
		<a href="/oauth2/authorization/google">Google</a>
		<a href="/oauth2/authorization/naver">Naver</a>
	</div>
</body>
</html>