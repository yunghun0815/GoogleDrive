<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원가입</title>
</head>
<body>
	<h1>회원가입 페이지</h1>
	<form action="/member/signup" method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="id" required></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password" required></td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td><input type="password" required></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="name" required></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" required></td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<input type="radio" name="gender" value="male">남자
					<input type="radio" name="gender" value="female">여자
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<select name="birthYear">
						<c:forEach begin="1910" end="2021" step="1" var="num">
							<option value="${num}">${num}</option>
						</c:forEach>
					</select>
					<select name="birthMonth">
						<c:forEach begin="1" end="12" step="1" var="num">
							<option value="${num}">${num}</option>
						</c:forEach>
					</select>
					<select name="birthDay">
						<c:forEach begin="1" end="31" step="1" var="num">
							<option value="${num}">${num}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="회원가입">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>