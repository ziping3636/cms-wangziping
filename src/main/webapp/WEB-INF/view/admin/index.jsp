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
<title>CMS后台管理系统</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div class="container">
		<div class="row" style="height: 80px; margin-top: 10px">
			<div class="col-md-12" style="background-color: #0073b7">
				<img alt="" src="/resource/images/logo.png" height="80px"
					class="rounded-circle">&nbsp;<font color="white" size="5px">CMS后台管理</font>
				<span><font color="white" size="3px">登录人：${sessionScope.admin.username}</font><a
					href="/passport/logout">注销</a></span>
			</div>
		</div>
		<hr style="height: 2px; border: none; border-top: 1px dotted #185598">
		<div class="row" style="height: 600px">
			<div class="col-md-3" style="background-color: black">
				<div style="margin-top: 30px">
					<!-- As a link -->
					<nav class="navbar navbar-light bg-light"> <a
						class="navbar-brand" href="#" data="/admin/user/selects">用户管理</a>
					</nav>

					<!-- As a heading -->
					<nav class="navbar navbar-light bg-light"> <span
						class="navbar-brand mb-0 h1"> <a class="navbar-brand"
						href="#" data="/admin/article/selects">文章管理</a></span> </nav>
					<!-- As a heading -->
					<nav class="navbar navbar-light bg-light"> <span
						class="navbar-brand mb-0 h1"><a class="navbar-brand"
						href="#" data="/admin/article/complains">举报管理</a></span> </nav>
					<!-- As a heading -->
					<nav class="navbar navbar-light bg-light"> <span
						class="navbar-brand mb-0 h1">栏目管理</span> </nav>
					<!-- As a heading -->
					<nav class="navbar navbar-light bg-light"> <span
						class="navbar-brand mb-0 h1">系统维护</span> </nav>
				</div>
			</div>
			<div class="col-md-9" id="center"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		
		$("#center").load("/admin/user/selects");
		
		$("a").click(function() {
			var url = $(this).attr("data");

			$("a").removeClass("list-group-item active");
			$(this).addClass("list-group-item active");
			$("#center").load(url);
		})
	})
</script>
</html>