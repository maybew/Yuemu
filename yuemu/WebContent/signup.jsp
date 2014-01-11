<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 注册</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<link href="css/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<style>
#main .wrap {
	background: #fff;
	padding-top: 5px;
	padding-bottom: 80px;
}

#main .title {
	font-size: 16px;
	color: #369;
	padding: 6px;
	border-left: 3px solid #888;
	margin: 30px;
	font-weight: bold;
}

#main .form {
	background: #e2e2e2;
	width: 600px;
	padding: 30px 0;
	margin: 0 auto;
	width: 600px;
}

table {
	border: 0;
	border-spacing: 22px;
}

table .input {
	color: #444;
	width: 180px;
	height: 24px;
	line-height: 24px;
	border: 1px solid #bbb;
	padding-left: 5px;
}

table .button {
	width: 100px;
	height: 32px;
	border: 0;
	background: #369;
	color: #fff;
	margin-top: 15px;
}

.correct,.incorrect {
	width: 16px;
	height: 16px;
	margin-right: 8px;
	background: url(images/correct.png);
	display: inline-block;
	margin-bottom: -3px;
}

.incorrect {
	background: url(images/incorrect.png);
}
</style>

<script>
	function checkEmail(obj) {
		if ($(obj).val() == "") {
			incorrect(obj, "不能为空");
		} else {
			$.ajax({
				type : "GET",
				url : "checkEmail",
				dataType : "html",
				data : "email=" + $(obj).val(),
				beforeSend : function() {
					$($(obj).parent().siblings()[1]).css("visibility", "visible");
					$($(obj).parent().siblings()[1].children[1]).html("检测中");
				},
				success : function(meg) {
					if (meg == "ok") {
						correct(obj);
					} else {
						incorrect(obj, "此账户已注册");
					}
				},
				error : function() {
					alert("error");
				}
			});
		}
	}

	function isEmail(emailStr) {
		if (emailStr.length == 0) {
			return fasle;
		} else {
			var emailPat = /^(.+)@(.+)$/;
			var specialChars = "\(\)<>@,;:\\\"\.\[\]";
			var validChars = "[^\s" + specialChars + "]";
			var quotedUser = "(\"[^\"/]*\")";
			var ipDomainPat = /^(d{1,3})[.](d{1,3})[.](d{1,3})[.](d{1,3})$/;
			var atom = validChars + '+';
			var word = "(" + atom + "|" + quotedUser + ")";
			var userPat = new RegExp("^" + word + "(\." + word + ")*$");
			var domainPat = new RegExp("^" + atom + "(\." + atom + ")*$");
			var matchArray = emailStr.match(emailPat);
			if (matchArray == null) {
				return false;
			}
			var user = matchArray[1];
			var domain = matchArray[2];
			if (user.match(userPat) == null) {
				return false;
			}
			var IPArray = domain.match(ipDomainPat);
			if (IPArray != null) {
				for ( var i = 1; i <= 4; i++) {
					if (IPArray[i] > 255) {
						return false;
					}
				}
				return true;
			}
			var domainArray = domain.match(domainPat);
			if (domainArray == null) {
				return false;
			}
			var atomPat = new RegExp(atom, "g");
			var domArr = domain.match(atomPat);
			var len = domArr.length;
			if ((domArr[domArr.length - 1].length < 2) || (domArr[domArr.length - 1].length > 3)) {
				return false;
			}
			if (len < 2) {
				return false;
			}
			return true;
		}
	}

	function vertifyPassword(obj) {
		if ($(obj).val() == $("#password").val() && $(obj).val() != "") {
			correct(obj);
		} else {
			incorrect(obj, "确认密码不一致");
		}
	}

	function isNull(obj) {
		if ($(obj).val() == "") {
			incorrect(obj, "不能为空");
		} else {
			correct(obj);
		}
	}

	function checkBirthday(obj) {
		if ($(obj).val().match(/\d\d\d\d-\d\d-\d\d/) == $(obj).val()) {
			correct(obj);
		} else {
			incorrect(obj, "格式不正确");
		}
	}

	function incorrect(obj, s) {
		var brother = $(obj).parent().siblings()[1];
		$(brother).css("visibility", "visible");
		$(brother.children[0]).attr("class", "incorrect");
		$(brother.children[1]).html(s);
		$(brother.children[1]).css("visibility", "visible");
	}

	function correct(obj) {
		var brother = $(obj).parent().siblings()[1];
		$(brother).css("visibility", "visible");
		$(brother.children[0]).attr("class", "correct");
		$(brother.children[1]).css("visibility", "hidden");
	}

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
		

		$('#birthday').simpleDatepicker({ startdate: 1980, enddate: 2000, x: 0, y: 27 });
	});
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/head.jsp"%>


	<div id="main">
		<div class="wrap">
			<div class="title">新用户注册</div>
			<div class="form">
				<form action="signUp" method="post">
					<table>
						<tr>
							<td align="right" width="24%">邮箱：</td>
							<td width="50%"><input class="input" type="text" name="email" style="width: 220px;"
								onblur="checkEmail(this);"></td>
							<td width="26%" style="visibility: hidden;"><span class="correct"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">密码：</td>
							<td><input class="input" type="password" id="password" name="password" onblur="isNull(this);"></td>
							<td style="visibility: hidden;"><span class="correct"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">确认密码：</td>
							<td><input class="input" type="password" name="password2" onblur="vertifyPassword(this);"></td>
							<td style="visibility: hidden;"><span class="correct"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">姓名：</td>
							<td><input class="input" type="text" name="name" style="width: 90px;" onblur="isNull(this)"></td>
							<td style="visibility: hidden;"><span class="correct"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">性别：</td>
							<td><input name="sex" type="radio" value="0" checked="checked">男 <input name="sex" type="radio"
								value="1" style="margin-left: 20px;">女</td>
						</tr>
						<tr>
							<td align="right">出生日期：</td>
							<td><input class="input" type="text" id="birthday" name="birthday" style="width: 200px;"></td>
							<td style="visibility: hidden;"><span class="correct"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">职业：</td>
							<td><input class="input" type="text" name="occupation" style="width: 140px;" onblur="isNull(this)" onfocus="checkBirthday(document.getElementById('birthday'))" ></td>
							<td style="visibility: hidden;"><span class="incorrect"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">联系方式：</td>
							<td><input class="input" type="text" name="contact" style="width: 140px;" onblur="isNull(this)"></td>
							<td style="visibility: hidden;"><span class="incorrect"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td align="right">地址：</td>
							<td><input class="input" type="text" name="address" style="width: 200px;" onblur="isNull(this)"></td>
							<td style="visibility: hidden;"><span class="incorrect"></span><span class="message"></span></td>
						</tr>
						<tr>
							<td></td>
							<td><input class="button" type="submit" value="立即注册"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>


	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>