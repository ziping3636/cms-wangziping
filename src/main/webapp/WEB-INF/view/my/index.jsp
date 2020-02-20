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
<title>个人中心</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div class="container">
		<div class="row" style="height: 80px; margin-top: 10px">
			<div class="col-md-12" style="background-color: #0073b7">
				<img alt="" src="/resource/images/logo.png" height="80px"
					class="rounded-circle">&nbsp;<font color="#ffffff"
					style="font-family: cursive; font-size: 30px">个人中心</font> <span><font
					color="white" size="3px">登录人：${sessionScope.user.username}</font><a
					href="/passport/logout">注销</a></span>
			</div>
		</div>
		<hr style="height: 2px; border: none; border-top: 1px dotted #185598">
		<div class="row" style="height: 600px">
			<div class="col-md-3" style="background-color: black">
				<div style="margin-top: 30px">
					<div class="list-group">
						<a href="#" data="my/article/articles"
							class="list-group-item list-group-item-action active">我的文章</a> <a
							href="#" data="/my/article/publish"
							class="list-group-item list-group-item-action">发布文章</a> <a
							href="#" data="/my/article/updateArticleInfo"
							class="list-group-item list-group-item-action">编辑文章</a> <a
							href="#" data="/my/article/collects"
							class="list-group-item list-group-item-action">我的收藏</a> <a
							href="#" class="list-group-item list-group-item-action">用户设置</a>
						<a href="#"
							class="list-group-item list-group-item-action disabled"
							tabindex="-1" aria-disabled="true">发布图片</a>
					</div>
				</div>
			</div>
			<div class="col-md-9" id="center">
				<!-- 引入kindeditor -->
				<div style="display: none">
					<jsp:include page="/resource/kindeditor/jsp/demo.jsp" />
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {

		$("#center").load("/my/article/articles");

		$("a").click(function() {
			var url = $(this).attr("data");
			$("a").removeClass("list-group-item active");
			$(this).addClass("list-group-item active");
			$("#center").load(url);
		})
	})
</script>
</html>