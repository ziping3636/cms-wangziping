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
	<div class="container-fluid">
		<ul class="list-unstyled">
			<c:forEach items="${info.list}" var="article" varStatus="count">
				<li class="media"><img src="/pic/${article.picture}"
					class="mr-3" alt="..." style="width: 160px; height: 120px">
					<div class="media-body">
						<h5 class="mt-0 mb-1">
							<a href="/my/article/article?id=${article.id}" target="_blank">${article.title}</a>
						</h5>
						<div style="margin-top: 60px">
							<fmt:formatDate value="${article.created}"
								pattern="yyyy-MM-dd HH:mm:ss" />
							<span style="float: right"><button type="button"
									class="btn-sm btn-info"
									onclick="updateArticleInfo(${article.id})">编辑文章</button></span>
							<c:if test="${article.deleted==0}">
								<span style="float: right"><button type="button"
										class="btn-sm btn-danger" onclick="update(${article.id},this)">删除</button></span>
							</c:if>
							<c:if test="${article.deleted==1}">
								<span style="float: right"><button type="button"
										class="btn-sm btn-warning"
										onclick="update(${article.id},this)">已删除</button></span>
							</c:if>
						</div>
					</div></li>
				<hr>
			</c:forEach>
		</ul>
	</div>
	<jsp:include page="/WEB-INF/view/common/pages.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
	
	function update(id, obj) {
		var deleted = $(obj).text()=="删除"?1:0;
		
		$.post("/my/article/update", {id:id, deleted:deleted}, function (flag) {
			if(flag) {
				$(obj).text(deleted==1?"已删除":"删除");
				$(obj).attr("class", deleted==1?"btn-sm btn-warning":"btn-sm btn-danger");
				
			} else {
				alert("操作失败！")
			}
		}, "json")
	}
	
	function updateArticleInfo(id) {
		url="/my/article/updateArticleInfo?id="+id;
		$("#center").load(url);
	}

	function goPage(page) {
		//location.href = "/user/selects?pageNum=" + page;
		url = "/my/article/articles?pageNum=" + page;
		$("#center").load(url);
	}

	function query() {
		var url = "/admin/article/selects?title=" + $("[name='title']").val()
				+ "&status=" + $("[name='status']").val();
		$("#center").load(url);
	}
</script>
</html>