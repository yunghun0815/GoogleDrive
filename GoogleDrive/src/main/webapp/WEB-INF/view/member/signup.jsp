<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ������</title>
</head>
<body>
	<h1>ȸ������ ������</h1>
	<form action="/member/signup" method="post">
		<table>
			<tr>
				<th>���̵�</th>
				<td><input type="text" name="id" required></td>
			</tr>
			<tr>
				<th>��й�ȣ</th>
				<td><input type="password" name="password" required></td>
			</tr>
			<tr>
				<th>��й�ȣ Ȯ��</th>
				<td><input type="password" required></td>
			</tr>
			<tr>
				<th>�̸�</th>
				<td><input type="text" name="name" required></td>
			</tr>
			<tr>
				<th>�̸���</th>
				<td><input type="text" name="email" required></td>
			</tr>
			<tr>
				<th>����</th>
				<td>
					<input type="radio" name="gender" value="male">����
					<input type="radio" name="gender" value="female">����
				</td>
			</tr>
			<tr>
				<th>�������</th>
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
					<input type="submit" value="ȸ������">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>