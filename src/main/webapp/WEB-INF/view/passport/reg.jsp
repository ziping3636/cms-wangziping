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
<title>CMS注冊</title>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<link rel="stylesheet" href="/resource/css/jquery/screen.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/jquery.validate.js"></script>
</head>
<body>
	<div class="container" style="width: 250px">
		<h1>用户注册</h1>
		<hr>
		<span style="color: red">${error}</span>
		<div>
			<form id="form" action="/passport/reg" method="post">
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
				<div class="form-group">
					<label for="repassword">确认密码：</label> <input id="repassword"
						class="form-control" type="password" name="repassword"
						autocomplete="off">
				</div>
				<div class="form-group">
					<label for="repassword">性别：</label> <input type="radio"
						name="gender" value="0" checked="checked">男 <input
						type="radio" name="gender" value="1">女
				</div>
				<div class="form-group">
					<button class="btn btn-info" type="submiit">注册</button>
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
						rangelength : [ 2, 10 ],//用户名的长度必须在2-10之间
					},
					password : {
						required : true,
						rangelength : [ 6, 12 ],
					},
					repassword : {
						required : true,
						equalTo : "#password",//两次密码必须一致
					}
				},
				messages : {
					username : {
						required : "*用户名必须输入",
						rangelength : "*用户名的长度必须在2-10之间",//用户名的长度必须在2-10之间
					},
					password : {
						required : "密码必须输入",
						rangelength : "*密码长度在6-12之间",
					},
					repassword : {
						required : "*确认密码用户名必须输入",
						equalTo : "*两次密码必须一致",//两次密码必须一致
					}
				}
			})
		})
	</script>
</body>
</html>