<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.LoginUser" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles.css">
<title>ログイン画面</title>
</head>


<body>
	<% 
	//処理メッセージとエラー判定を取得 
	String message = (String)request.getAttribute("message");
	String error = (String)request.getAttribute("error");
	
	//異常終了した場合のメッセージを表示
	if(message != null && error != null) {
	%>
	 	<div id="loginAlert">
	 		<%= message %>
		</div>
	<% 	
	}
	%>
	<h3 id="loginTitle">アルバムアプリ</h3>
	<form action="LoginServlet" method="post" id="loginForm">
		<!-- ログインID -->
		<label class="loginId">ID<input type="text" id="id" class="test" name="loginId"></label>
		<!-- パスワード -->
		<label>PW<input type="password" id="password" class="test" name="loginPassword"></label>
		<input type="submit" id="loginBtn" value="ログイン">
	</form>
</body>
</html>