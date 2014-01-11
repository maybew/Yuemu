<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 登陆</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<style>
#main .wrap {
	background: url(images/y.jpg);
	height: 500px;
}

#main .form {
	width: 300px;
	float: right;
	background: #111;
	background: rgba(1, 1, 1, 0.15);
	filter: alpha(opacity =       15);
	text-align: center;
	margin-top: 100px;
	margin-right: 40px;
	text-align: center;
}

#main .title {
	font-size: 14px;
	color: white;
	background: #24769c;
	background: rgba(0, 102, 153, 0.9);
	padding: 15px;
	text-align: left;
}

form {
	padding: 20px 0 15px 0;
}

.text {
	height: 31px;
	background: #fff;
	margin: 35px 45px;
	padding: 0 8px;
	position: relative;
	text-align: left;
}

.text label {
	text-align: left;
	line-height: 31px;
	color: #333;
	-webkit-user-select: none;
	-moz-user-select: none;
}

.text input {
	border: 0;
	background: #fff;
	width: 150px;
	position: absolute;
	left: 50px;
	top: 4px;
	outline: none;
	vertical-align: middle;
}

.btn {
	margin: 34px 45px;
}

.btn input {
	width: 100px;
	height: 32px;
	border: 0;
	background: #369;
	color: white;
}

.btn input:hover {
	background: #00354F;
}

.message {
	color: white;
	padding-left: 20px;
	text-align: left;
	line-height: 32px;
	background: #E44141;
	text-align: left;
}
</style>
<script>
	$(document).ready(function() {
		$("form").submit(function() {
			var inputs = document.getElementsByTagName('input');

			for ( var i = 0; i < inputs.length; i++) {
				if (inputs[i].value == "") {
					inputs[i].focus();
					return false;
				}
			}
			return true;
		});
	});
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap">
			<div class="form">
				<div style="position: relative;">
					<div class="title">用户登陆</div>
					<form action="signIn" method="post">
						<div class="text">
							<label>邮箱：</label> <input id="zz" type="text" name="email" autocomplete="off">
						</div>
						<div class="text">
							<label>密码：</label> <input type="password" name="password">
						</div>
						<div class="btn">
							<input type="submit" value="登陆">
						</div>
					</form>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>


	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>