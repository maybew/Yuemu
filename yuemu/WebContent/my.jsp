<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 我的资源</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<link type="text/css" href="css/view.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/clj.js"></script>
<script type="text/javascript" src="js/imageview.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/view.js"></script>
<style>
.type_chooser {
	margin-bottom: 25px;
}

.type_chooser span {
	display: inline-block;
	width: 60px;
	height: 26px;
	border: 1px solid #999;
	border-left: 0;
	text-align: center;
	line-height: 26px;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
}

.type_chooser span.first {
	border-left: 1px solid #999;
}

.type_chooser span.selected {
	background: #bbb;
}

.snippet {
	float: left;
	width: 140px;
	height: 120px;
	margin: 0 20px 20px 0;
	cursor: pointer;
}

.snippet .icon {
	width: 100%;
	height: 100%;
}

.snippet .title {
	margin-top: -30px;
	background: #111;
	filter: alpha(opacity =         20);
	background: rgba(1, 1, 1, 0.2);
	height: 30px;
	line-height: 30px;
	color: #EEE;
	padding-left: 6px;
	overflow: hidden;
}

.snippet .title a {
	color: #fff;
	position: relative;
}

#main .left {
	float: left;
	width: 640px;
}

#main .right {
	float: right;
	width: 300px;
}

#main .right .t {
	border-left: 3px solid #999;
	padding: 8px;
	margin-bottom: 18px;
}

#main .right .i {
	background: #f1f1f1;
	min-height: 300px;
}

#main .right  .i {
	padding: 10px;
}

#main .right  .i td {
	padding: 12px 0;
}

#main .right .i_1 {
	border: 0;
}

#main .right  .i_2 {
	margin-top: 20px;
	text-align: center;
	border: 1px solid #DDD;
	padding: 10px 0;
}

#main .right  .i_3 {
	text-align: left;
	color: #666;
	padding: 20px 5px;
}

#feedinfo .input {
	color: #444;
	width: 180px;
	height: 24px;
	line-height: 24px;
	border: 1px solid #bbb;
	padding-left: 5px;
}

#feedinfo .ops a,#feedinfo .ops2 input {
	border: 0;
	background: #069;
	padding: 7px 30px;
	color: white;
	margin: 10px;
	display: inline-block;
}

#feedinfo .b {
	display: none;
}

#feedinfo .hidden_input {
	display: none;
}

#hotlist li {
	display: block;
	height: 32px;
	overflow: hidden;
}

#hotlist li span {
	color: #999;
	padding-right: 12px;
}
#hotlist a {
	color: #429C3F;
}
</style>
<script>
	var res = {};
	var status_map = {
		0 : "已审核",
		1 : "正在审核",
		2 : "已删除"
	};
	var type = getQueryString('type') == '' ? "image" : getQueryString('type');
	var page = getQueryString('page') == '' ? 1 : parseInt(getQueryString('page'));

	function show_view() {
		$(".b").hide();
		$(".a").show();
		$(".ops2").hide();
		$(".ops").show();
	}

	function show_input() {
		$(".a").hide();
		$(".b").show();
		$(".ops").hide();
		$(".ops2").show();
	}

	function show_feedinfo() {
		if ($('#hotfeed').css('display') != 'none') {
			$('#hotfeed').css({
				'display' : 'none'
			});
			$('#feedinfo').css({
				'display' : 'block'
			});
		}
	}

	function show_hotfeed() {
		if ($('#hotfeed').css('display') != 'block') {
			$('#hotfeed').css({
				'display' : 'block'
			});
			$('#feedinfo').css({
				'display' : 'none'
			});
		}
	}

	function switch_feed(id) {
		var temp_res = res[id].resource;
		$('#input_id').attr('value', temp_res.id);
		$('#input_type').attr('value', temp_res.type);
		$('#input_title').attr('value', temp_res.title);
		$('#input_tag').attr('value', temp_res.tags);
		$('#input_desc').attr('value', temp_res.description);
		$('#feed_title').html(temp_res.title);
		$('#feed_desc').html(temp_res.description);
		$('#feed_visitcount').html(res[id].visit + " 次");
		$('#feed_downcount').html(res[id].download + " 次");
		$('#feed_tag').html(temp_res.tags);
		$('#feed_status').html(status_map[temp_res.status]);
		$('#feed_time').html(temp_res.date);
		show_feedinfo();
	}

	function append(data, textStatus) {
		if (data.code != 0) {
			$(".snippet").remove();
			return;
		}

		$(".snippet").remove();
		for ( var i = 0; i < data.data.length; i++) {
			$("#board").append(make_snippet(data.data[i], i));
		}
	}

	function chooseType(obj, t) {
		show_view();
		show_hotfeed();
		type = t;
		page = 1;
		$('#currentPage').html('' + page);
		$('.type_chooser .selected').removeClass('selected');
		$(obj).addClass('selected');
		fetch();
		get_top_ten();
	}

	var map = {
		"IMAGE" : "showImage",
		"MUSIC" : "showMusic",
		"VIDEO" : "showVideo",
		"DOCUMENT" : "showDocument"
	};

	function make_snippet(json, i) {
		var temp_id = "r_" + i;
		var html = '<div class="icon" style="background: url(images/my_' + json.resource.type.toLowerCase()
				+ '.png);"></div>' + '<div class="title"><a href="view.jsp?id=' + json.resource.id + '">' + json.resource.title + '</a></div>';

		var div = document.createElement('div');
		$(div).attr('class', 'snippet ' + json.resource.type.toLowerCase());
		$(div).attr('onclick', 'switch_feed(\'' + temp_id + '\')');
		$(div).html(html);
		eval("res." + temp_id + " = json");

		return div;
	}

	function fetch() {
		$.post("feedByAccount", {
			"type" : type,
			"page" : page
		}, append);
	}

	function load_prev_page() {
		if (page != 1) {
			--page;
			$('#currentPage').html('' + page);
			fetch();
		}
	}

	function load_next_page() {
		if ($('.snippet').length == 12) {
			++page;
			$('#currentPage').html('' + page);
			fetch();
		}
	}

	function delete_item() {
		var delete_id = $('#input_id').attr('value');
		var delete_type = $('#input_type').attr('value');
		$.post("userResource", {
			"type" : delete_type,
			"action" : "delete",
			"id" : delete_id
		}, function(data, textStatus) {
			//jump("/my.jsp");
			fetch();
			show_hotfeed();
		});

	}

	function get_top_ten() {
		$.post("topFeedByAccount", {
			"type" : type
		}, function(data, textStatus) {
			if (data.code != 0) {

			}
			var html = "";
			for ( var i = 0; i < data.data.length; i++) {
				html = html + '<a href=view.jsp?id='+ data.data[i].id +'><li><span>' + (i + 1 < 10 ? '0' + (i + 1) : i + 1) + '</span>' + data.data[i].title
						+ '</li></a>';
			}
			$('#hotlist').html(html);
		});
	}

	$(document).ready(function() {
		$('#i_' + type).addClass("selected");
		$('#currentPage').html('' + page);
		fetch();
		get_top_ten();
	});
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap">
			<div class="left">
				<div class="type">
					<div class="type_chooser" style="float: left">
						<span id="i_image" class="first" onclick="chooseType(this,'image')">图片</span><span id="i_music"
							onclick="chooseType(this,'music')">音乐</span><span id="i_video" onclick="chooseType(this,'video')">视频</span><span
							id="i_document" onclick="chooseType(this,'document')">文档</span>
					</div>
					<div class="pager" style="float: right; line-height: 26px; margin-right: 18px;">
						<span style="cursor: pointer" onclick="load_prev_page()">上一页</span>&nbsp;&nbsp;<span id="currentPage">1</span>&nbsp;&nbsp;<span
							style="cursor: pointer" onclick="load_next_page()">下一页</span>
					</div>
					<div class="clear"></div>
					<div id="top10" style="display: none">
						<div style="float: left;">
							<div>我的热门资源</div>
							<ul>
								<!-- <li><a href=""><span>1</span>我是中国人</a></li>
								<li><a href=""><span>2</span>我是中国人</a></li>
								<li><a href=""><span>3</span>我是中国人</a></li>
								<li><a href=""><span>4</span>我是中国人</a></li> -->
							</ul>
						</div>
						<div style="float: right;">
							<div>资源分布</div>
							<img src="images/temp.png" width="200" height="200">
						</div>
						<div class="clear"></div>
					</div>

					<div id="board"></div>
				</div>
				<div class="view_type"></div>
			</div>
			<div class="right">
				<div class="t">资源信息</div>
				<div class="i" id="hotfeed">
					<div style="color: #000; padding: 8px 0 15px 0; border-bottom: 1px solid #ddd; text-align: center;">我的热门资源</div>
					<ul id="hotlist" style="line-height: 32px; padding: 8px 15px;">
						<!-- <li>1. 这里是标题</li>
						<li>2. 这里是标题</li>
						<li>3. 这里是标题</li>
						<li>4. 这里是标题</li>
						<li>5. 这里是标题</li>
						<li>6. 这里是标题</li>
						<li>7. 这里是标题</li>
						<li>8. 这里是标题</li>
						<li>9. 这里是标题</li>
						<li>10. 这里是标题</li> -->
					</ul>
				</div>
				<div class="i" id="feedinfo" style="display: none;">
					<form action="userResource?action=update" method="post">
						<input class="hidden_input" name="input_id" id="input_id" /> <input class="hidden_input" name="input_type"
							id="input_type" />
						<table class="i_1">

							<tr>
								<td width="50px;">标题：</td>
								<td><span id="feed_title" class="a"></span><span class="b"><input class="input" id="input_title"
										name="input_title" value=""></span></td>
							</tr>
							<tr>
								<td>描述：</td>
								<td><span id="feed_desc" class="a"></span><span class="b"><input class="input" id="input_desc"
										name="input_desc" value=""></span></td>
							</tr>
							<tr>
								<td>标签：</td>
								<td><span id="feed_tag" class="a"></span><span class="b"><input class="input" id="input_tag"
										name="input_tag" value=""></span></td>
							</tr>
							<tr style="display: none;">
								<td>分类：</td>
								<td id="feed_category"></td>
							</tr>
							<tr>
								<td>浏览：</td>
								<td id="feed_visitcount"></td>
							</tr>
							<tr>
								<td>下载：</td>
								<td id="feed_downcount"></td>
							</tr>
							<tr>
								<td>状态：</td>
								<td id="feed_status"></td>
							</tr>
							<tr>
								<td>时间：</td>
								<td id="feed_time"></td>
							</tr>
						</table>
						<div class="b ops2">
							<input type="submit" value="提交"><a href="javascript: show_view();">取消</a>
						</div>
					</form>
					<div class="ops">
						<a href="javascript: show_input();">修改</a> <a style="background: #991800" onclick="delete_item()">删除</a>
					</div>

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