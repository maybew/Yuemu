<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.Date,yuemu.po.Sex,java.text.*,yuemu.dao.manage.*,yuemu.po.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 个人信息</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<link type="text/css" href="css/uploadify.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/uploadify.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<style type="text/css">
table {
	border: 0;
}

#main .wrap {
	border: 1px solid #D6D6D6;
	background: #fff;
}

#main .left {
	float: left;
	width: 240px;
	margin: 10px;
	text-align: center;
	border-bottom: 1px solid #e9e9e9;
}

#main .right {
	float: left;
	padding: 26px 20px;
	border-left: 1px solid #ddd;
	min-height: 540px;
}

#main .title {
	height: 40px;
	line-height: 40px;
	background: #e8e8e8;
	padding-left: 10px;
	color: #000
}

#main .self img {
	padding: 20px;
}

#summary {
	width: 180px;
	margin: 0 auto;
	border-spacing: 0px;
}

#summary a {
	color: #069;
}

#summary td {
	border-left: 1px solid #ddd;
}

#summary td.first {
	border-left: 0;
}

#info td {
	height: 50px;
}

#info .modify {
	display: none;
}

#info .modify span {
	background: #ccc;
	display: inline-block;
	width: 16px;
	height: 16px;
}

#info .input {
	color: #444;
	width: 180px;
	height: 24px;
	line-height: 24px;
	border: 1px solid #bbb;
	padding-left: 5px;
}

#info .button {
	width: 100px;
	height: 32px;
	border: 0;
	background: #369;
	color: #fff;
	margin-top: 15px;
}

#main .b {
	display: none;
}

#main .ops,#main  .ops2 {
	margin-top: 30px;
}

#main .ops a,#main  .ops2 input {
	border: 0;
	background: #069;
	padding: 8px 30px;
	color: white;
	margin-left: 3px;
	background: #069;
}

#main  .ops2 a {
	color: #E91818;
	margin-left: 30px;
}

#uploadify {
	margin: 20px auto;
}
</style>
</head>

<body>
	<%@ include file="WEB-INF/inc/head.jsp"%>
	<%
		request.setCharacterEncoding("utf-8");
		Account user = (Account) session.getAttribute("account");
		if (user == null) {

		}
		String email = user.getEmail();
		String name = user.getName();
		String pwd = user.getPassword();
		String pwdHidden = pwd.replaceAll(".", "*");
		if (user.getSex() == Sex.FEMALE) {

		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = user.getBirthday();
		String birthday = format.format(date);
		String profession = user.getProfession();
		String contact = user.getContact();
		String address = user.getAddress();
		String portrait = user.getPortrait();
		boolean hasPortrait = portrait == null || portrait.equals("") ? false
				: true;
	%>
	<script>
	var trigger = true;
	function switch_view() {
		if (trigger) {
			$(".a").hide();
			$(".b").show();
			$(".ops").hide();
			$(".ops2").show();
		} else {
			$(".b").hide();
			$(".a").show();
			$(".ops2").hide();
			$(".ops").show();
		}
		trigger = !trigger;
	}

	$(document).ready(function() {
		$('#birthday').simpleDatepicker({
			startdate : 1980,
			enddate : 2000,
			x : 0,
			y : 27
		});
	});

	var uploadify_config = {
		'formData' : {'uploaderEmail' : '<%=email%>'
		},
		'swf' : 'swf/uploadify.swf',
		'uploader' : 'user',
		'queueID' : 'uploader_queue',
		'auto' : true,
		'removeCompleted' : true,
		'muti' : false,
		'fileSizeLimit' : '3MB',
		'queueSizeLimit' : 1,
		'buttonText' : '上传头像',
		'uploadLimit' : 5,
		'overrideEvents' : [ 'onSelectError' ],
		'fileTypeExts' : '*.jpg; *.jpeg; *.png; *.bmp',
		'onSelectError' : function(file, errorCode, errorMsg) {
			if (errorCode == SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
				$('#uploader_msg').html("超过上传数目上限");
			}
			if (errorCode == SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT) {
				$('#uploader_msg').html("单个文件不能超过3M");
			}
			if (errorCode == SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE) {
				$('#uploader_msg').html("文件大小不能为0");
			}
			if (errorCode == SWFUpload.QUEUE_ERROR.INVALID_FILETYPE) {
				$('#uploader_msg').html("不支持的文件类型");
			}
		},
		'onUploadSuccess' : function(file, data, response) {

			$.post("user", {
				'action' : 'updateSession'
			}, function(data, textStatus) {

			});
			jump('profile.jsp');
		}
	};
	$(document).ready(function() {
		$("#uploadify").uploadify(uploadify_config);
	});
</script>
	<div id="main">
		<div class="wrap">
			<div class="title">个人信息</div>
			<div class="left">
				<div class="self">
					<img id="portrait"
						src="<%=hasPortrait ? portrait : "images/user.png"%>" width="160"
						height="160">
				</div>
				<div style="padding-bottom: 10px;">
					<table id="summary">
						<tr>
							<td class="first"><%=ImageManageDAO.countOfByAccountId(user.getId(),ResourceStatus.ALL) %></td>
							<td><%=MusicManageDAO.countOfByAccountId(user.getId(),ResourceStatus.ALL)%></td>
							<td><%=VideoManageDAO.countOfByAccountId(user.getId(),ResourceStatus.ALL)%></td>
							<td><%=DocumentManageDAO.countOfByAccountId(user.getId(),ResourceStatus.ALL)%></td>
						</tr>
						<tr>
							<td class="first"><a href="my.jsp?type=image">图片</a></td>
							<td><a href="my.jsp?type=music">音乐</a></td>
							<td><a href="my.jsp?type=video">视频</a></td>
							<td><a href="my.jsp?type=document">文档</a></td>
						</tr>
					</table>
					<div class="clear"></div>
				</div>

				<div class="upload">
					<div id="uploader">
						<input type="file" name="uploadify" id="uploadify" />
						<div id="uploader_queue"></div>
						<div id="uploader_msg"></div>
					</div>
				</div>
			</div>

			<div class="right">
				<form action="user" method="post">
					<table id="info">
						<tr>
							<td width="120">邮箱：</td>
							<td width="360"><%=email%></td>
						</tr>
						<tr>
							<td>密码：</td>
							<td><span class="a"><%=pwdHidden%></span><span class="b"><input
									class="input" type="password" id="password" name="password"
									value="<%=pwd%>"></span></td>
						</tr>
						<tr>
							<td>姓名：</td>
							<td><span class="a"><%=name%></span><span class="b"><input
									class="input" type="text" name="name" value="<%=name%>"
									style="width: 90px;"></span></td>
						</tr>
						<tr>
							<td>性别：</td>
							<td><span class="a"><%=user.getSex() == Sex.MALE ? "男" : "女"%></span><span
								class="b"><input name="sex" type="radio" value="0"
									<%=user.getSex() == Sex.MALE ? "checked=\"checked\"" : ""%>>男
									<input name="sex" type="radio" value="1"
									style="margin-left: 20px;"
									<%=user.getSex() == Sex.MALE ? "" : "checked=\"checked\""%>>女</span></td>
						</tr>
						<tr>
							<td>出生日期：</td>
							<td><span class="a"><%=birthday%></span><span class="b"><input
									class="input" type="text" id="birthday" name="birthday"
									value="<%=birthday%>" style="width: 200px;"></span></td>
						</tr>
						<tr>
							<td>职业：</td>
							<td><span class="a"><%=profession%></span><span class="b"><input
									class="input" type="text" name="profession"
									value="<%=profession%>" style="width: 140px;"></span></td>
						</tr>
						<tr>
							<td>联系方式：</td>
							<td><span class="a"><%=contact%></span><span class="b"><input
									class="input" type="text" name="contact" value="<%=contact%>"
									style="width: 140px;"></span></td>
						</tr>
						<tr>
							<td>地址：</td>
							<td><span class="a"><%=address%></span><span class="b"><input
									class="input" type="text" name="address" value="<%=address%>"
									style="width: 200px;"></span></td>
						</tr>
					</table>
					<div class="b ops2">
						<input type="submit" value="提交"><a
							href="javascript: switch_view();">取消</a>
					</div>
				</form>
				<div class="ops">
					<a href="javascript: switch_view();">修改</a>
				</div>
				<div id="log"></div>
			</div>
			<div class="clear"></div>
		</div>
	</div>




	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>