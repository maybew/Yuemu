<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 首页</title>
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
#main .left {
	width: 640px;
	float: left;
}

#navi {
	width: 100%;
	height: 50px;
	margin-bottom: 30px;
}

#navi li {
	float: left;
	height: 48px;
	line-height: 48px;
	width: 115px;
	background: #ccc;
	margin-right: 16px;
	text-align: center;
	background: #8e9193;
	background: rgba(1, 1, 1, 0.32);
}

#navi li:hover,#navi li.selected {
	background: #005179;
}

#navi li a {
	color: #eee;
	display: block;
	width: 100%;
	height: 100%;
	line-height: 48px;
}

#navi li a img {
	margin-bottom: -6px;
	margin-right: 8px;
	width: 24px;
	height: 24px;
}

#main .right {
	width: 290px;
	float: right;
}

#search {
	padding: 30px 0;
}

#sbox {
	width: 174px;
	height: 30px;
	border: 0;
	float: left;
	outline: none;
	padding-left: 10px;
}

#sbtn {
	width: 92px;
	height: 32px;
	float: right;
	border: 0;
	background: #369;
	color: #fff;
}

#tags {
	margin-top: 20px;
}

#tags li {
	line-height: 36px;
	height: 36px;
	background: #C7CBCE;
	background: rgba(1, 1, 1, 0.12);
	margin-bottom: 8px;
	-webkit-user-select: none;
	-moz-user-select: none;
}

#tags li:hover {
	background: #5789a2;
	background: rgba(0, 81, 121, 0.7);
	color: #222;
}

#tags a {
	display: block;
	width: 100%;
	height: 100%;
	color: #333;
}

#tags a:hover {
	color: #fff;
}

#tags .t {
	display: inline-block;
	width: 16px;
	height: 16px;
	background: url(images/tag.png) no-repeat;
	margin: 10px 10px 0 10px;
	vertical-align: top;
}

#board {
	position: relative;
	padding-bottom: 100px;
}

#loading {
	display: none;
	position: absolute;
	right: 0;
	bottom: 0;
	width: 570px;
	height: 90px;
	background: #fff;
	text-align: center;
	color: #222;
	padding-top: 20px;
}

#top10 {
	margin-top: 20px;
}

#top10 a {
	line-height: 36px;
	height: 36px;
	background: #C7CBCE;
	background: rgba(1, 1, 1, 0.12);
	margin-bottom: 8px;
	-webkit-user-select: none;
	-moz-user-select: none;
	display: block;
	color: #0F820C;
	padding-left: 10px;
}

#top10 a:hover {
	background: #5789a2;
	background: rgba(0, 81, 121, 0.7);
	color: #fff;
}
#rocket {
	width: 64px;
	height: 64px;
	background: #ccc;
	position: fixed;
	right: 0;
	bottom: 0;
}
</style>
<script>
	var type = 'all';
	var last = -1;
	var disabled = false;

	function append(data, textStatus) {
		/* var x = {
			'id' : '' + Math.random() * 1000,
			'type' : 'VIDEO',
			'title' : '测试标题',
			'tags' : '测试标签',
			'description' : '测试描述',
			'date' : '2012-07-14 19:14:00',
			'snippet_url' : '/images/doc.jpg',
			'preview_url' : '/video/test.flv',
			'song' : '歌曲',
			'singer' : '歌手',
			'album' : '专辑',
			'genre' : '流派'
		}; */
		if (data.code != 0) {
			disabled = true;
			$("#loading").html("<br><br>没有咯！！！");
			return;
		}
		for ( var i = 0; i < data.data.length; i++) {
			var obj = data.data[i];

			last = obj.resource.id;
			$("#board").append(make_item(obj));
		}
		$("#loading").hide();
	}

	function fetch() {
		$.post("feed", {
			"type" : type,
			"last" : last
		}, append);
	}

	$(document).ready(function() {
		$(window).scroll(function() {
			if (($(window).height() + $(window).scrollTop()) >= $("body").height()) {
				if (!disabled) {
					$("#loading").show();
					setTimeout(fetch, 1000);
					//fetch();
				}
			}
		});
		$('#rocket').click(function() {
			window.scrollTo(0, 0);
		});
		fetch();
	});

	function chooseType(obj, t) {
		type = t;
		last = -1;
		disabled = false;

		$('#navi .selected').removeClass('selected');
		$('.item').remove();
		$(obj).addClass('selected');
		fetch();
	}
</script>
</head>

<body>
<div id="rocket"></div>
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap">
			<div class="left" id="board">
				<div id="navi">
					<ul>
						<li onclick="chooseType(this,'all')" class="selected"><a href="#"><img src="images/11.png"><span>全部</span></a></li>
						<li onclick="chooseType(this,'image')"><a href="#"><img src="images/22.png"><span>图片</span></a></li>
						<li onclick="chooseType(this,'music')"><a href="#"><img src="images/33.png"><span>音乐</span></a></li>
						<li onclick="chooseType(this,'video')"><a href="#"><img src="images/44.png"><span>视频</span></a></li>
						<li onclick="chooseType(this,'document')" style="margin-right: 0"><a href="#"><img src="images/55.png"><span>文档</span></a></li>
					</ul>
				</div>

				<div id="loading">
					<img src="images/loading10.gif" width="49" height="50"><br> 正在加载<b>...</b>
				</div>
			</div>

			<div class="right">
				<div id="search">
					<form action="search.jsp" method="get">
						<input id="sbox" type="text" name="keyword" autocomplete="off"><input id="sbtn" type="submit" value="搜索">
					</form>
					<div class="clear"></div>
				</div>
				<div id="tags">
					<ul>
						<li><a href="search.jsp?tag=MV"><span class="t"></span>MV</a></li>
						<li><a href="search.jsp?tag=生活"><span class="t"></span>生活</a></li>
						<li><a href="search.jsp?tag=汉语"><span class="t"></span>汉语</a></li>
						<li><a href="search.jsp?tag=PPT"><span class="t"></span>PPT</a></li>
						<li><a href="search.jsp?tag=小说"><span class="t"></span>小说</a></li>
						<li><a href="search.jsp?tag=自然"><span class="t"></span>自然</a></li>
						<li><a href="search.jsp?tag=长沙"><span class="t"></span>长沙</a></li>
					</ul>
				</div>
				<div id="top10">
					<a href="top.jsp">浏览排行榜</a>
					<a href="flow.jsp">瀑布流 Beta</a>
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
