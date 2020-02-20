<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<div class="container" style="margin-top: 50px">
		<table class="table">
			<tr>
				<th>ID</th>
				<th>文本</th>
				<th>地址</th>
				<th>收藏时间</th>
				<th>收藏者Id</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${info.list}" var="co">
				<tr>
					<td>${co.id}</td>
					<td>${co.text}</td>
					<td><a href="${co.url}">${co.url}</a></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${co.created}" /></td>
					<td>${co.userId}</td>
					<td><button onclick="deleteCollect(${co.id})"
							class="btn btn-danger">删除</button></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="100">
					<button onclick="goPage(1)">首页</button>
					<button onclick="goPage(${info.prePage})">上一页</button>
					<button onclick="goPage(${info.nextPage})">下一页</button>
					<button onclick="goPage(${info.pages})">尾页</button>
					共${info.total}条记录
				</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	function goPage(pageNum) {
		var url = "/my/article/collects?pageNum="+pageNum;
		$("#center").load(url);
	}

	function deleteCollect(id) {
		location.href="/my/deleteCollect?id="+id;
	}
</script>
</html>