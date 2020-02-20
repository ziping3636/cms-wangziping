<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CMS登录</title>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<link rel="stylesheet" href="/resource/css/jquery/screen.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/jquery.validate.js"></script>
</head>
<body>
	<div class="container" style="width: 250px">
		<h1>用户登录</h1>
		<hr>
		<span style="color: red">${error}</span>
		<div>
			<form id="form" action="/passport/login" method="post">
				<div class="form-group">
					<label for="username">用户名：</label> <input id="username"
						class="form-control" type="text" name="username"
						autocomplete="off" value="${user.username}">
				</div>
				<div class="form-group">
					<label for="password">密码：</label> <input id="password"
						class="form-control" type="password" name="password"
						autocomplete="off" value="${user.password}">
				</div>
				<!-- <div class="form-group">
					<label for="isRemember">10天免登陆</label> <input type="checkbox"
						id="isRemember" name="isRemember">
				</div> -->
				<div class="form-group">
					<button class="btn btn-info" type="submiit">登录</button>
					<button class="btn btn-info" onclick="goReg()" type="button">前往注册</button>
					<button class="btn btn-warning" type="reset">重置</button>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#form").validate({
				rules : {
					username : {
						required : true,
					},
					password : {
						required : true,
					},
				},
				messages : {
					username : {
						required : "*用户名必须输入",
					},
					password : {
						required : "密码必须输入",
					},
				}
			})
		})
	</script>
</body>
<script type="text/javascript">
	function goReg() {
		location.href = "/passport/reg";
	}
</script>
</html>