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
<title>Insert title here</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div class="container form-inline" style="margin-left: 400px">
		<form action="/my/doAddCollect" method="post">
		<h3>收藏页面</h3><br>
			<input type="hidden" name="userId" value="${user.id}"> 收藏地址：<input type="text"
				name="url" class="form-control" autocomplete="off"><br>
			<br> 收藏文本：<input type="text" name="text" class="form-control"><br><br>
			<input type="submit" value="收藏" class="btn btn-primary"><font color="red">${error}</font>
		</form>
	</div>
</body>
</html>