<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="/WEB-INF/views/include/head.jsp"/>
<title>MyMall</title>
<script type="module" src="/resources/modules/pages/login.js?ver=<%= System.currentTimeMillis()%>"></script>
</head>
<body>
	<div id="wrap">
		<form class="form" id="login-form" action="/auth/login" method="POST">
			<div class="input-wrap">
				<input type="text" class="text" id="request-email" inputmode="email" required>
				<label class="title" for="request-email">Email</label>
			</div>
			<div class="input-wrap">
				<input type="password" class="text" id="request-password" inputmode="text" required>
				<label class="title" for="request-password">Password</label>
			</div>
			<button type="submit" class="button bg-be">Login</button>
		</form>
	</div>
</body>
</html>