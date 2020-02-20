<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String htmlData = request.getParameter("content") != null ? request.getParameter("content") : "";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>KindEditor JSP</title>
<link rel="stylesheet"
	href="/resource/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="/resource/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/resource/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>
<script charset="utf-8"
	src="/resource/kindeditor/plugins/code/prettify.js"></script>
<script>

	$(function() {
		KindEditor.ready(function(K) {
			window.editor1 = K.create('textarea[name="content"]', {
				cssPath : '/resource/kindeditor/plugins/code/prettify.css',
				uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
	})
</script>
</head>
<body>
	<%=htmlData%>
	<form id="form1">
		<div class="form-group">
			<input id="id" class="form-control" type="hidden" name="id"
				value="${article.id}"> <label for="title">文章标题:</label><input
				id="title" class="form-control" type="text" name="title"
				value="${article.title}"><br>
		</div>
		<div class="form-group form-inline">
			<label>文章栏目:</label> <select class="form-control" id="channel"
				name="channelId">
				<option>请选择</option>
			</select> <label>文章分类:</label> <select class="form-control" id="category"
				name="categoryId">
				<option>请选择</option>
			</select>
		</div>
		<div class="form-group">
			文章标题图片:<input class="form-control" type="file" name="file" value="${article.picture}"> <img
				alt="" src="${article.picture}" id="picture"> <br>
		</div>
		<textarea name="content" cols="100" rows="8"
			style="width: 100%; height: 250px; visibility: hidden;">${article.content}<%=htmlspecialchars(htmlData)%></textarea>
		<br />
		<button type="button" class="btn btn-info" onclick="publish()"
			name="button">提交内容</button>
		(提交快捷键: Ctrl + Enter)
	</form>
</body>
<script type="text/javascript">

	function publish() {
		var formData = new FormData($("#form1")[0])
		formData.set("content", editor1.html())
		$.ajax({
			type:"post",
			url:"/my/article/updateArticleInfo",
			data:formData,
			// 告诉jQuery不需要去处理发送的数据
			processData : false,
			// 告诉jQuery不要去设置Content-Type请求头
			contentType:false,
			success : function(flag) {
				if(flag) {
					alert("修改成功!")
					location.href="/my";
				} else {
					alert("修改失败!")
				}
			}
		})
	}
	
	$(function() {
		var channelId = "${article.channelId}";
		var categoryId = "${article.categoryId}";
		$.get("/my/channel/selects", function(list) {
			$("#channel").empty();
			$("#channel").append("<option>请选择</option>")
			for ( var i in list) {
				if (channelId == list[i].id) {
					
					$("#channel").append(
							"<option value='"+list[i].id+"' selected>" + list[i].name
									+ "</option>")
				} else {
					
					$("#channel").append(
							"<option value='"+list[i].id+"'>" + list[i].name
									+ "</option>")
				}
			}
			$.get("/my/category/selectsByChannelId", {
				channelId : channelId
			}, function(list) {
				for ( var i in list) {
					if (categoryId == list[i].id) {
						
						$("#category").append(
								"<option value='"+list[i].id+"' selected>"
										+ list[i].name + "</option>")
					} else {
						
						$("#category").append(
								"<option value='"+list[i].id+"'>"
										+ list[i].name + "</option>")
					}
				}
			}, "json")
		}, "json")
	})
	
	$("#channel").change(
				function() {
					var channelId = $(this).val();
					$("#category").empty();
					$("#category").append("<option>请选择</option>")
					if (channelId == "请选择")
						return;
					$.get("/my/category/selectsByChannelId", {
						channelId : channelId
					}, function(list) {
						for ( var i in list) {
							$("#category").append(
									"<option value='"+list[i].id+"'>"
											+ list[i].name + "</option>")
						}
					}, "json")
				})
</script>
</html>
<%!private String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}%>