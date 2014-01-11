<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 瀑布流</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<link type="text/css" href="css/view.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/clj.js"></script>
<script type="text/javascript" src="js/imageview.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/view.js"></script>
<style>
#main .wrap {
	width: 980px;
	padding-left: 20px;
}

.fall {
	float: left;
	width: 25%;
}
</style>
<script>
	var heights = [ 0, 0, 0, 0 ];
	var last = -1;

	function append(data, textStatus) {
		if (data.code != 0) {
			alert("貌似没有了！");
			return;
		}
		var arr = data.data;

		for ( var i in arr) {
			var idx = 0;
			var min = heights[idx];
			for ( var j = 1; j < heights.length; j++) {
				if (heights[j] < min) {
					idx = j;
					min = heights[idx];
				}
			}

			var obj = $('#f_' + idx);
			obj.append('<div onclick="showImage(\'' + arr[i].resource.preview_url + '\')" style="margin: 0 20px 20px 0">' + '<img src="' + arr[i].resource.snippet_url + '" width="100%">' + '</div>');
			heights[idx] += (obj.width() - 20) * arr[i].height / arr[i].width;

			last = arr[i].resource.id;
		}
	}

	function fetch() {
		$.post('feed', {
			'type' : 'image',
			'last' : last
		}, append);
	}

	$(document).ready(function() {
		$(window).scroll(function() {
			if (($(window).height() + $(window).scrollTop()) >= $('body').height()) {
				setTimeout(fetch, 500);
			}
		});
		fetch();fetch();
	});
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>


	<div id="main">
		<div class="wrap">
			<div id="f_0" class="fall"></div>
			<div id="f_1" class="fall"></div>
			<div id="f_2" class="fall"></div>
			<div id="f_3" class="fall"></div>
			<div style="clear: both"></div>
		</div>
	</div>

	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>
