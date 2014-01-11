<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="content" class="content">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/menu.js"></script>
	<style type="text/css">
a {
	color: #005179;
}

#container {
	margin-top: 10px;
}

#current {
	border-bottom: 1px solid #CCC;
	padding-bottom: 12px;
}

.term {
	float: left;
	margin: 0 6px 20px 0;
	width: 115px;
	height: 90px;
	text-align: center;
	padding: 8px 0;
}

.term_dir {
	cursor: pointer;
}

.term_dir img {
	width: 70px;
	height: 70px;
}

.term_file {
	cursor: pointer;
}

.term_file img {
	width: 70px;
	height: 70px;
}

.term_t {
	line-height: 20px;
}

.helplay {
	z-index: 3;
	position: absolute;
	border: 1px solid #06C;
	padding: 5px;
	background-color: #FFF;
	text-align: left;
	color: #06C;
	font-family: arial, sans-serif;
}

.info_table {
	font-size: 10px;
	width: 200px;
	border: 0px;
}
</style>
	<script type="text/javascript">
		var res = {

		};
		var current;
		function initMenu() {

			document.oncontextmenu = function() {
				return false;
			};

			$('div.term_file').contextMenu('file_menu', {

				bindings : {

					'file_open' : function(t) {
						var temp_id = t.id;
						var object = res[temp_id].resource;
						if (object.type == "MUSIC") {
							showMusic(object.preview_url);
						} else if (object.type == "IMAGE") {
							showImage(object.preview_url);
						} else if (object.type == "VIDEO") {
							showVideo(object.preview_url);
						} else if (object.type == "DOCUMENT") {
							showDocument(object.preview_url);
						}

					},

					'file_delete' : function(t) {
						if (confirm("是否确认要删除？")) {
							var path = res[t.id].resource.source_url;

							$.post("explore", {
								"action" : "delete",
								"dir" : path
							}, function(json, textStatus) {

							});
							setTimeout('list(current)', 500);
						}
					},

				}

			});

			$('div.term_dir').contextMenu('dir_menu', {

				bindings : {

					'file_open' : function(t) {
						var name = res[t.id].name;
						list(name);

					},

					'file_delete' : function(t) {
						if (confirm("是否确认要删除？")) {
							var dir = '/resource' + res[t.id].name;
							$.post("explore", {
								"action" : "delete",
								"dir" : dir
							}, function(json, textStatus) {

							});
							setTimeout('list(current)', 500);
						}
					},

				}

			});

		}

		function show(dir, arr) {
			$('.term').remove();
			var c = $('#container');

			for ( var i in arr) {
				var temp_id = "r_" + i;

				var html = '<div class="term"><div class="'
						+ (arr[i].type == 'dir' ? 'term_dir' : 'term_file')
						+ '" id="'
						+ temp_id
						+ '"'
						+ (arr[i].type == 'dir' ? ' onclick="list(\'' + dir
								+ '/' + arr[i].name + '\')"'
								: 'onmouseover=\'return showInfo.showLayer(event,\"testhidden\", "'
										+ temp_id
										+ '");\' onmouseout=\'return showInfo.hideLayer(event,\"testhidden\");\'')
						+ '><img src="images/'
						+ (arr[i].type == 'dir' ? 'abcd' : 'zzzzz')
						+ '.png" /></div><div class="term_t">'
						+ (arr[i].type == 'dir' ? arr[i].name
								: arr[i].resource.title) + '</div></div>';

				c.append(html);

				if (arr[i].type == 'dir') {
					arr[i].name = dir + '/' + arr[i].name;
				}
				eval("res." + temp_id + " = arr[i]");
			}
			initMenu();
		}

		function list(dir) {
			//alert("list" + dir);
			var x = dir.split("/");
			var html = '<a href="javascript:list(\'/\')">ROOT</a>';
			var dir = '';
			for ( var i in x) {
				if (x[i] != '') {
					dir += '/' + x[i];
					html += ' / <a href="javascript:list(\'' + dir + '\')">'
							+ x[i] + '</a>';
				}
			}
			$('#current').html(html);

			$.post("explore", {
				"action" : "list",
				"dir" : dir
			}, function(json, textStatus) {
				if (json.code == 0) {
					show(dir == '/' ? '' : dir, json.data);
					current = dir;
				}
			});
		}

		var showInfo = new function() {
			this.showLayer = function(e, id, res_id) {
				var temp_res = res[res_id].resource;
				$("#info_tag").html(temp_res.tags);
				$("#info_category").html(temp_res.category);
				$("#info_type").html(temp_res.type);
				$("#info_title").html(temp_res.title);
				$("#info_date").html(temp_res.date);
				$("#info_user").html(temp_res.uploader.accountEmail);
				$("#info_status").html(temp_res.status);
				var p = window.event ? [ event.clientX, event.clientY ] : [
						e.pageX, e.pageY ];
				with (document.getElementById(id + "").style) {
					display = "block";
					left = p[0] + 10 + "px";
					top = p[1] + 10 + "px";
				}
				if (window.event) {
					window.event.cancelBubble = true;
				} else {
					if (e) {
						e.preventDefault();
					}
				}
			};
			this.hideLayer = function(e, id) {
				with (document.getElementById(id + "").style) {
					display = "none";
				}
				if (window.event) {
					window.event.cancelBubble = true;
				} else {
					if (e) {
						e.preventDefault();
					}
				}
			};
		};
	</script>
	<div id="current">
		<a href="javascript:list('/')">ROOT</a> / <a
			href="javascript:list('/document')">文档</a>
	</div>
	<div id="container">
		<script type="text/javascript">
			list('/');
		</script>
	</div>

	<div style="display: none" id="testhidden" class="helplay">
		<table class="info_table">
			<tr>
				<th scope="row">标题:</th>
				<td id="info_title"></td>
			</tr>
			<tr>
				<th scope="row">类型:</th>
				<td id="info_type"></td>
			</tr>
			<tr>
				<th scope="row">分类:</th>
				<td id="info_category"></td>
			</tr>
			<tr>
				<th scope="row">日期:</th>
				<td id="info_date"></td>
			</tr>
			<tr>
				<th scope="row">标签:</th>
				<td id="info_tag"></td>
			</tr>
			<tr>
				<th scope="row">上传者:</th>
				<td id="info_user"></td>
			</tr>
			<tr>
				<th scope="row">状态:</th>
				<td id="info_status"></td>
			</tr>
		</table>
	</div>
	<div class="contextMenu" id="file_menu">

		<ul>

			<li id="file_open"><img src="images/menu_ok.png" /> 打开</li>


			<li id="file_delete"><img src="images/menu_delete.png" /> 删除</li>

		</ul>

	</div>
	<div class="contextMenu" id="dir_menu">

		<ul>

			<li id="file_open"><img src="images/menu_ok.png" /> 打开</li>

			<li id="file_delete"><img src="images/menu_delete.png" /> 删除</li>

		</ul>

	</div>
</div>