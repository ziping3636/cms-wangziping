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
<title>用户列表</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div style="margin-bottom: 10px" class="form-inline">
			<label for="username">用户名:</label> <input id="username"
				class="form-control" type="text" name="username" value="${username}">
			&nbsp;
			<button class="btn btn-info" type="button" onclick="query()">查询</button>
		</div>
		<table class="table">
			<tr align="center">
				<th>序号</th>
				<th>用户名</th>
				<th>昵称</th>
				<th>生日</th>
				<th>注册日期</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${info.list}" var="user" varStatus="count">
				<tr align="center">
					<td>${count.count + info.startRow - 1}</td>
					<td>${user.username}</td>
					<td>${user.nickname}</td>
					<td><fmt:formatDate value="${user.birthday}" /></td>
					<td><fmt:formatDate value="${user.created}" /></td>
					<td><c:if test="${user.locked==0}">
							<button type="button" class="btn btn-success"
								onclick="update(${user.id}, this)">正常</button>
						</c:if> <c:if test="${user.locked==1}">
							<button type="button" class="btn btn-warning"
								onclick="update(${user.id}, this)">停用</button>
						</c:if></td>
				</tr>
			</c:forEach>
			<%-- <tr align="center">
				<td colspan="100"><jsp:include
						page="/WEB-INF/view/common/pages.jsp"></jsp:include></td>
			</tr> --%>
		</table>

		<nav aria-label="Page navigation example">
		<ul class="pagination">
			<li class="page-item">
				<button type="button" class="btn btn-primary"
					onclick="goPage(${info.prePage})">Previous</button>
			</li>
			<c:forEach begin="${info.pageNum-2 > 1 ? info.pageNum-2:1}"
				end="${info.pageNum+2 > info.pages ? info.pages:info.pageNum+2}"
				varStatus="index">
				<c:if test="${info.pageNum!=index.index}">
					<li class="page-item"><button type="button"
							class="btn btn-light" onclick="goPage(${index.index})">${index.index}</button></li>
				</c:if>
				<c:if test="${info.pageNum==index.index}">
					<li class="page-item"><button type="button"
							class="btn btn-primary" onclick="goPage(${index.index})">${index.index}</button></li>
				</c:if>
			</c:forEach>
			<li class="page-item">
				<button type="button" class="btn btn-primary"
					onclick="goPage(${info.nextPage})">Next</button>
			</li>
		</ul>
		</nav>
	</div>
</body>
<script type="text/javascript">
	function update(id, obj) {
		var locked = $(obj).text()=="正常"?1:0;
		
		$.post("/admin/user/update", {id:id, locked:locked}, function(flag) {
			if(flag) {
				//alert("操作成功!")
				$(obj).text(locked == 1?"停用":"正常");
				$(obj).attr("class", locked==1?"btn btn-warning":"btn btn-success")
			} else {
				alert("操作失败!")
			}
		})
	}

	function goPage(page) {
		url = "/admin/user/selects?pageNum=" + page;
		$("#center").load(url);
	}

	function query() {
		url = "/admin/user/selects?username=" + $("[name='username']").val();
		$("#center").load(url);
	}
</script>
</html>