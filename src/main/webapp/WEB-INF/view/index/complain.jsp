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
<title>举报</title>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div align="center" class="container" style="width: 600px">
		<h2>举报信息</h2>
		<form action="" id="form1">
			<input type="hidden" name="articleId" value="${article.id}">
			<input type="hidden" name="user_id" value="${article.user.id}">
			<div class="form-group form-inline">
				举报类型:<select name="typename" class="form-control">
					<option>垃圾广告</option>
					<option>政治反动</option>
					<option>钓鱼网站</option>
				</select>
			</div>
			<div class="form-group form-inline">
				举报地址:<input type="text" name="url" class="form-control">
			</div>
			<div class="form-group form-inline">
				举报内容:
				<textarea rows="10" cols="50" name="content" class="form-control">
			
			</textarea>
			</div>
			<div class="form-group form-inline">
				附件:<input type="file" name="file" class="form-control">
			</div>
			<div class="form-group form-inline">
				<button type="button" onclick="add()" class="form-control">举报</button>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	function add() {
		var formData = new FormData($("#form1")[0]);

		$.ajax({
			url : "/complain",
			type : "post",
			data : formData,
			processData : false,
			contentType : false,
			success : function(flag) {
				if (flag) {
					alert("举报成功")
					location.href="/index";
				} else {
					alert("举报失败")
				}
			}
		})
	}
</script>
</html>