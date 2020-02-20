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
<title>${article.title}</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div class="container" style="margin-top: 50px">
		<h2>${article.title}</h2>
		<div>
			<button type="button" class="btn btn-success"
				onclick="chk(1, ${article.id})">同意</button>
			<button type="button" class="btn btn-danger"
				onclick="chk(-1, ${article.id})">驳回</button>
			<button type="button" class="btn btn-info" onclick="window.close()">关闭</button>
		</div>
		<h6 style="margin-top: 20px">${article.user.username},
			<fmt:formatDate value="${article.created}"
				pattern="yyyy-MM-dd HH:mm:ss" />
		</h6>
		<hr>
		<div align="center">${article.content}</div>
	</div>
</body>
<script type="text/javascript">
	function chk(status, id) {
		$.post("/admin/article/update", {id:id, status:status}, function (flag) {
			if(flag) {
				if (status == 1) {
					alert("审核通过!")
				} else {
					alert("审核已驳回!")
				}
			} else {
				alert("操作失败");
			}
		})
	}
</script>
</html>