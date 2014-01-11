<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 错误</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<style>
#main .wrap {
	height: 500px;
	background: white;
	text-align: center;
	line-height: 500px;
	-webkit-user-select: none;
	-mozilla-user-select: none;
}
</style>
<script>
	setTimeout('jump("index.jsp")', 3000);
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap">
			系统出错，正在跳转到首页<b>...</b>
		</div>
	</div>


	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>
