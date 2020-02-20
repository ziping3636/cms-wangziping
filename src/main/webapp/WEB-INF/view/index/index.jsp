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
<title>CMS首页</title>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<link rel="stylesheet" href="/resource/css/index.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
<style type="text/css">
.subchannel {
	height: 38px;
	width: 100%;
	z-index: 50;
	margin-bottom: 16px;
}

.subchannel .sub-list {
	top: -50px;
	background: #fff;
	height: 38px;
	border-bottom: 2px solid #f2f2f2;
}

.subchannel .sub-item:first-child {
	margin-left: 0 !important;
}

.subchannel .sub-selected {
	color: #f85959;
	border-bottom: 2px solid #f85959;
}

.subchannel .sub-item {
	line-height: 38px;
	font-size: 16px;
	display: inline-block;
	color: #444;
	margin-left: 33px;
	background: #fff;
}

li {
	list-style: none;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="height: 34px; background-color: #222222">
			&nbsp;&nbsp;<a href="#"><font color="#FFFFFF" size="3px">下载APP</font></a>
			<span style="float: right"></span>
			<div>
				<c:if test="${null != sessionScope.user}">
					<font style="color: white;">
						登录人：${sessionScope.user.username},<a href="/passport/logout">注销</a></a>
					</font>
				</c:if>
				<c:if test="${null == sessionScope.user}">
					<a href="/passport/login">登录</a>|<a href="/passport/reg">注册</a>
				</c:if>
			</div>
		</div>
		<div class="container" style="margin-top: 5px">
			<div class="row">
				<!-- 左侧栏目 -->
				<div class="col-md-2" style="height: 550px;" id="channel">
					<img alt="" src="/resource/images/logo.png" style="width: 150px">
					<ul class="list-group">
						<a href="?channelId=${channel.id}"><li
							class="channel-item ${article.channelId==null?'active':''}"><span>热门</span></li></a>
						<c:forEach items="${channels}" var="c">
							<a href="?channelId=${c.id}"><li
								class="channel-item ${c.id==article.channelId?'active':''}"><span>${c.name}</span></li></a>
						</c:forEach>
					</ul>
				</div>
				<div class="col-md-7">
					<!-- 轮播图 -->
					<c:if test="${null == article.channelId}">
						<div>
							<div id="carouselExampleCaptions" class="carousel slide"
								data-ride="carousel">
								<ol class="carousel-indicators">
									<li data-target="#carouselExampleCaptions" data-slide-to="0"
										class="active"></li>
									<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
									<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
									<li data-target="#carouselExampleCaptions" data-slide-to="3"></li>
								</ol>
								<div class="carousel-inner">
									<c:forEach items="${slides}" var="s" varStatus="i">
										<div class="carousel-item ${i.index==0?"active":""}">
											<img src="/pic/${s.url}" class="d-block w-100" alt="...">
											<div class="carousel-caption d-none d-md-block">
												<h5>${s.title}</h5>
											</div>
										</div>
									</c:forEach>
								</div>
								<a class="carousel-control-prev" href="#carouselExampleCaptions"
									role="button" data-slide="prev"> <span
									class="carousel-control-prev-icon" aria-hidden="true"></span> <span
									class="sr-only">Previous</span>
								</a> <a class="carousel-control-next"
									href="#carouselExampleCaptions" role="button" data-slide="next">
									<span class="carousel-control-next-icon" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
							</div>
						</div>
					</c:if>

					<!-- 中间的分类 -->
					<div>
						<ul class="subchannel">
							<li class="sub-item  ${article.categoryId==null?"sub-selected":""}"><a
								class="nav-link" href="?channelId=${article.channelId}">全部 </a></li>
							<c:forEach items="${categorys}" var="c">
								<li class="sub-item ${c.id==article.categoryId?"sub-selected":""}"><a
									class="nav-link"
									href="?channelId=${article.channelId}&categoryId=${c.id}">${c.name}
								</a></li>
							</c:forEach>
						</ul>
					</div>
					<br>
					<!-- 分类文章 -->
					<div>
						<c:forEach items="${info.list}" var="a">
							<div class="media">
								<a href="/article?id=${a.id}" target="_blank"><img
									src="/pic/${a.picture}" class="mr-3" alt="..."
									style="width: 156px; height: 102px"></a>
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
						<jsp:include page="/WEB-INF/view/common/pages.jsp"></jsp:include>
					</div>
				</div>
				<div class="col-md-3">
					<form action="/search" method="get" target="_blank">
						<div class="input-group mb-3" style="margin-top: 15px">
							<input type="text" class="form-control" name="key" value="${key}"
								placeholder="请输入要搜索的内容" aria-label="Recipient's username"
								aria-describedby="button-addon2">
							<div class="input-group-append">
								<button class="btn btn-outline-secondary" id="button-addon2">搜索</button>
							</div>
						</div>
					</form>

					<div class="card"
						style="width: 18rem; padding-left: 2px; margin-top: 20px">
						<div class="card-header">最新文章</div>
						<br>
						<c:forEach items="${lastInfo.list}" var="last">
							<div class="class-body">
								<div class="media">
									<a href="/article?id=${last.id}" target="_blank"><img
										src="/pic/${last.picture}" class="card-img-top" alt="..."
										style="width: 60px; height: 60px"></a>&nbsp;&nbsp;
									<div class="media-body">
										<h6 class="mt-0">
											<a href="/article?id=${last.id}" target="_blank">${last.title}</a>
										</h6>
									</div>
								</div>
							</div>
							<hr>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
<script type="text/javascript">
	function goPage(page) {
		var categoryId = '${article.categoryId}';
		var channelId = '${article.channelId}';

		location.href = "?channelId=" + channelId + "&categoryId=" + categoryId
				+ "&pageNum=" + page;
	}
</script>
</html>