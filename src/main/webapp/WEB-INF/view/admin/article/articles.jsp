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
<title>文章列表</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<body>
	<div class="container">
		<div style="margin-bottom: 10px" class="form-inline">
			<label for="title">标题:</label> <input id="title" class="form-control"
				type="text" name="title" value="${article.title}"> &nbsp;
			审核状态:<select class="form-control" id="status" name="status">
				<option value="0">待审</option>
				<option value="1">已审</option>
				<option value="-1">驳回</option>
				<option value="99">全部</option>
			</select> &nbsp;
			<button class="btn btn-info" type="button" onclick="query()">查询</button>
		</div>
		<table class="table">
			<tr align="center">
				<th>序号</th>
				<th>文章标题</th>
				<th>栏目</th>
				<th>分类</th>
				<th>作者</th>
				<th>文章状态</th>
				<th>发布时间</th>
				<th colspan="2">操作</th>
			</tr>
			<c:forEach items="${info.list}" var="article" varStatus="count">
				<tr align="center">
					<td>${count.count + info.startRow - 1}</td>
					<td>${article.title}</td>
					<td>${article.channel.name}</td>
					<td>${article.category.name}</td>
					<td>${article.user.username}</td>
					<td>${article.status==0?"待审":article.status==1?"已审":"驳回"}</td>
					<td><fmt:formatDate value="${article.created}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><c:if test="${article.hot==0}">
							<button type="button" class="btn btn-success"
								onclick="update(${article.id}, this)">非热门</button>
						</c:if> <c:if test="${article.hot==1}">
							<button type="button" class="btn btn-warning"
								onclick="update(${article.id}, this)">热门</button>
						</c:if></td>
					<td><a href="/admin/article/select?id=${article.id}"
						target="_blanck">详情</a></td>
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
							onclick="goPage(${info.prePage == 0 ? info.prePage + 1 : info.prePage})">Previous</button>
					</li>
					<c:forEach begin="${info.pageNum-2 > 1 ? info.pageNum-2:1}"
						end="${info.pageNum+2 > info.pages ? info.pages:info.pageNum+2}"
						varStatus="index">
						<c:if test="${info.pageNum!=index.index}">
							<li class="page-item"><button type="button"
									class="btn btn-light" onclick="goPage(${index.index})">${index.index}</button></li>
						</c:if>
						<c:if test="${info.pageNum==index.index}">
							<li class="page-item">
								<button type="button" class="btn btn-primary"
									onclick="goPage(${index.index})">${index.index}</button>
							</li>
						</c:if>
					</c:forEach>
					<li class="page-item">
						<button type="button" class="btn btn-primary"
							onclick="goPage(${info.pageNum == info.pages ? info.pages:info.pageNum + 1})">Next</button>
					</li>
				</ul>
				</nav>
	</div>
</body>
<script type="text/javascript">
	function goPage(page) {
		//location.href = "/user/selects?pageNum=" + page;
		url = "/admin/article/selects?pageNum=" + page + "&title=" + $("[name='title']").val()
		+ "&status=" + $("[name='status']").val();
		$("#center").load(url);
	}

	function query() {
		var url = "/admin/article/selects?title=" + $("[name='title']").val()
				+ "&status=" + $("[name='status']").val();
		$("#center").load(url);
	}

	$(function() {
		$("#status").val('${article.status}')
	})
</script>
<script type="text/javascript">
function update(id, obj) {
	var hot = $(obj).text()=="非热门"?1:0;
	
	$.post("/admin/article/update", {id:id, hot:hot}, function(flag) {
		if(flag) {
			//alert("操作成功!")
			$(obj).text(hot == 1?"热门":"非热门");
			$(obj).attr("class", hot==1?"btn btn-warning":"btn btn-success")
		} else {
			alert("操作失败!")
		}
	})
}
</script>
</html>