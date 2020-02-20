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
<title>${key}_搜索</title>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<link rel="stylesheet" href="/resource/css/index.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div>
		<div class="col-md-3">
			<form action="/search" method="get">
				<div class="input-group mb-3" style="margin-top: 10px">
					<input type="text" class="form-control" name="key" value="${key}"
						placeholder="请输入要搜索的内容" aria-label="Recipient's username"
						aria-describedby="button-addon2">
					<div class="input-group-append">
						<button class="btn btn-outline-secondary" id="button-addon2">搜索</button>
					</div>
				</div>
				<%-- 本次搜索用时${t}ms --%>
			</form>
		</div>
		<div class="col-md-7">
			<div class="container" style="margin-top: 10px">
				<c:forEach items="${info.list}" var="a">
					<div class="media">
						<a href="/article?id=${a.id}" target="_blank"><img
							src="/pic/${a.picture}" class="mr-3" alt="..."
							style="width: 156px; height: 100px"></a>
						<div class="media-body">
							<h5 class="mt-0">
								<a href="/article?id=${a.id}" target="_blank">${a.title}</a>
							</h5>
							<h5 class="mt-0" style="margin-bottom: 2px">${a.user.username}&nbsp;<fmt:formatDate
									value="${a.created}" pattern="yyyy-MM-dd HH:mm:ss" />
							</h5>
						</div>
					</div>
					<hr>
				</c:forEach>
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
		</div>
		<div class="col-md-3"></div>
	</div>
</body>
<script type="text/javascript">
function goPage(page) {
	var key = '${key}';

	location.href = "search?key=" + key+ "&pageNum=" + page;
}
</script>
</html>