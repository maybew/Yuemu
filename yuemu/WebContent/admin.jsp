<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action") == null ? "res_impending" : request.getParameter("action");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 管理</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<link type="text/css" href="css/view.css" rel="stylesheet">
<link type="text/css" href="css/admin.css" rel="stylesheet">
<link type="text/css" href="css/uploadify.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/uploadify.js"></script>
<script type="text/javascript" src="js/clj.js"></script>
<script type="text/javascript" src="js/imageview.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/view.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<style>
.menu,.menu ul {
	width: 200px;
	margin: 0;
	padding: 0;
	font-size: 13px;
	list-style: none;
}

.menu li {
	-webkit-user-select: none;
}

.menu .title {
	background: #069;
	color: #fff;
	height: 30px;
	line-height: 30px;
	cursor: pointer;
	margin-top: 10px;
	padding-left: 10px;
	text-align: center;
}

.menu .first {
	margin-top: 0;
}

.menu .title:hover {
	background: #F90;
}

.menu ul li {
	height: 29px;
	line-height: 29px;
	padding-left: 24px;
	font-size: 13px;
}

.menu ul li.selected {
	border-left: 5px solid #999;
	background: #c5c5c5;
	background: rgba(1, 1, 1, 0.15);
	padding-left: 19px;
}

.menu ul li:hover {
	background: #c0c0c0;
	background: rgba(1, 1, 1, 0.2);
}

.menu ul li a {
	display: block;
	width: 100%;
	height: 100%;
	text-decoration: none;
	color: #444;
}
</style>
<script>
	function toggle(id) {
		var x = document.getElementById(id);
		if (x.style.display == "none") {
			x.style.display = "block";
		} else {
			x.style.display = "none";
		}
	}
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>


	<div id="main">
		<div class="wrap" style="padding-top: 5px; padding-bottom: 40px;">
			<div style="float: left;">
				<ul class="menu">
					<li class="title first" onclick="toggle('m1')">资源管理</li>
					<li id="m1">
						<ul>
							<li id="res_impending"><a href="admin.jsp?action=res_impending&status=1">待审核</a></li>
							<li id="res_passed"><a href="admin.jsp?action=res_passed&status=0">已通过</a></li>
							<li id="res_tree"><a href="admin.jsp?action=res_tree">目录管理</a></li>
						</ul>
					</li>
					<li class="title" onclick="toggle('m2')">用户管理</li>
					<li id="m2">
						<ul>
							<li id="user_normal"><a href="admin.jsp?action=user_normal">普通用户</a></li>
							<li id="user_admin"><a href="admin.jsp?action=user_admin">管理员</a></li>
						</ul>
					</li>
					<li class="title" onclick="toggle('m3')">数据统计</li>
					<li id="m3">
						<ul>
							<li id="sat_resources"><a href="admin.jsp?action=sat_resources">资源分布</a></li>
							<li id="sat_browse"><a href="admin.jsp?action=sat_browse">访问量</a></li>
							<li id="sat_download"><a href="admin.jsp?action=sat_download">下载次数</a></li>
							<li id="sat_log"><a href="admin.jsp?action=sat_log">日志查看</a></li>
						</ul>
					</li>
					<li class="title" onclick="toggle('m4')">数据管理</li>
					<li id="m4">
						<ul>
							<li id="data_export"><a href="admin.jsp?action=data_export">数据导出</a></li>
							<li id="data_import"><a href="admin.jsp?action=data_import">数据导入</a></li>
							<li id="data_empty"><a href="admin.jsp?action=data_empty">数据清空</a></li>
						</ul>
					</li>
				</ul>
				<script>
					$(document).ready(function() {
						var action = getQueryString('action') == '' ? 'res_impending' : getQueryString('action');
						$("#" + action).addClass('selected');
					});
				</script>
			</div>
			<div style="float: right; width: 700px; background: #fff; padding: 20px;">
				<%
					if (action.equals("res_impending")) {
				%>
				<%@ include file="WEB-INF/admin/res_impending.jsp"%>
				<%
					} else if (action.equals("res_passed")) {
				%>
				<%@ include file="WEB-INF/admin/res_passed.jsp"%>
				<%
					} else if (action.equals("res_tree")) {
				%>
				<%@ include file="WEB-INF/admin/res_tree.jsp"%>
				<%
					} else if (action.equals("user_admin")) {
				%>
				<%@ include file="WEB-INF/admin/user_admin.jsp"%>
				<%
					} else if (action.equals("user_normal")) {
				%>
				<%@ include file="WEB-INF/admin/user_normal.jsp"%>
				<%
					} else if (action.equals("sat_resources")) {
				%>
				<%@ include file="WEB-INF/admin/sat_resources.jsp"%>
				<%
					} else if (action.equals("sat_browse")) {
				%>
				<%@ include file="WEB-INF/admin/sat_browse.jsp"%>
				<%
					} else if (action.equals("sat_download")) {
				%>
				<%@ include file="WEB-INF/admin/sat_download.jsp"%>
				<%
					} else if (action.equals("sat_log")) {
				%>
				<%@ include file="WEB-INF/admin/sat_log.jsp"%>
				<%
					} else if (action.equals("data_export")) {
				%>
				<%@ include file="WEB-INF/admin/data_export.jsp"%>
				<%
					} else if (action.equals("data_import")) {
				%>
				<%@ include file="WEB-INF/admin/data_import.jsp"%>
				<%
					} else if (action.equals("data_empty")) {
				%>
				<%@ include file="WEB-INF/admin/data_empty.jsp"%>
				<%
					}
				%>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>