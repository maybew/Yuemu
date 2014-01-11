<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 搜索</title>
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
#main .right {
	width: 290px;
	float: right;
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

#sb {
	margin-bottom: 40px;
	padding: 20px;
	text-align: center;
	background: #ccc;
	background: rgba(204, 204, 204, 0.7);
	padding-top: 35px;
}

#s_ipt {
	width: 400px;
	height: 30px;
	padding-left: 10px;
	border: 0px;
	outline: none;
}

#s_btn {
	border: 0;
	width: 100px;
	height: 32px;
	background: #069;
	color: #fff;
	vertical-align: top;
	cursor: pointer;
}

#s_types {
	margin-top: 18px;
}

#s_types input {
	margin: 0 5px 0 15px;
}

#s_types input.first {
	margin-left: 0;
}

#results {
	width: 700px;
	margin: 0 auto;
	min-height: 500px;
}

#pager {
	display: none;
}
</style>
<script>
	var bool = true;
	$(document).ready(function() {
		if ($.browser.webkit) {
			$("#s_ipt").css('line-height', '');
		}

		$("#sb form").submit(function() {
			get_parameter();
			if (bool)
				do_search();
			return false;
		});
	});

	var type = 'all';
	var page = 1;
	var keyword = "";
	var content = "";

	function append(data, textStatus) {
		for ( var i = 0; i < data.data.length; i++) {
			var obj = data.data[i];
			last = obj.resource.id;
			$("#results").append(make_item(obj));
		}
		$('#pager').show();
	}

	function do_search() {
		$("#results").html("");
		if (keyword == "keyword") {
			$.post("search", {
				"type" : type,
				"current" : page,
				"keyword" : content
			}, append);
		} else if (keyword == "tag") {
			$.post("search", {
				"type" : type,
				"current" : page,
				"tag" : content
			}, append);
		} else if (keyword == "title") {
			$.post("search", {
				"type" : type,
				"current" : page,
				"title" : content
			}, append);
		} else {
			$.post("search", {
				"type" : type,
				"current" : page,
			}, append);
		}
	}

	function check() {
		var keywords = getQueryString2('keyword');
		if (keywords == "") {
			var tag = getQueryString2("tag");
			if (tag == "") {
				var title = decodeURI(getQueryString2("title"));
				if (title != "") {
					$("#title")[0].checked = true;
					$("#tag")[0].checked = false;
					$("#s_ipt").attr("value", title);
					get_parameter();
					do_search();
				}
			} else {
				$("#title")[0].checked = false;
				$("#tag")[0].checked = true;
				$("#s_ipt").attr("value", tag);
				get_parameter();
				do_search();
			}
		} else {
			$("#title")[0].checked = true;
			$("#tag")[0].checked = true;
			$("#s_ipt").attr("value", keywords);
			get_parameter();
			do_search();
		}
	}


	function get_parameter() {
		content = $("#s_ipt").val();
		var span = $("#types input");
		for ( var i = 0; i < span.length; i++) {
			if ($(span)[i].checked == true) {
				type = $(span)[i].value;
			}
		}
		get_keyword();
	}

	function get_keyword() {
		var title = $("#title")[0];
		var tag = $("#tag")[0];
		if (title.checked == false && tag.checked == false) {
			alert("请选择搜索方式（标签or标题or两者）");
			bool = false;
			return;
		} else {
			if (title.checked == true && tag.checked == true) {
				keyword = "keyword";
				bool = true;
			} else {
				if (title.checked == true) {
					keyword = "title";
					bool = true;
				} else {
					keyword = "tag";
					bool = true;
				}
			}
		}
	}

	function load_prev_page() {
		if (page != 1) {
			--page;
			$('#currentPage').html('' + page);
			do_search();
		}
	}

	function load_next_page() {
		if ($('.item').length == 10) {
			++page;
			$('#currentPage').html('' + page);
			do_search();
		}
	}
</script>
</head>

<body onload="check()">
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap">
			<div id="sb">
				<form action="" method="post">
					<div>
						<input type="text" id="s_ipt" style="line-height: 30px;"
							autocomplete="off"><input id="s_btn" type="submit"
							value="搜索">
					</div>
					<div id="s_types">
						<span id="types"><input class="first" name="type"
							type="radio" value="all" checked>全部<input name="type"
							type="radio" value="image">图片<input name="type"
							type="radio" value="music">音乐<input name="type"
							type="radio" value="video">视频<input name="type"
							type="radio" value="document">文档</span> <span> <input
							type="checkbox" id="title" checked>标题<input
							type="checkbox" id="tag" checked>标签
						</span>
					</div>
				</form>
			</div>


			<div id="results"></div>


			<div id="pager" style="text-align: center; margin-top: 60px;">
				<span style="cursor: pointer" onclick="load_prev_page()">上一页</span>&nbsp;&nbsp;<span
					id="currentPage">1</span>&nbsp;&nbsp;<span style="cursor: pointer"
					onclick="load_next_page()">下一页</span>
			</div>
		</div>
	</div>

	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>

