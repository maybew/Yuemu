<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Account user = (Account) session.getAttribute("account");
	String useremail = user.getEmail();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 上传</title>
<link type="image/x-icon" href="images/favicon.ico" rel="shortcut icon">
<link type="text/css" href="css/main.css" rel="stylesheet">
<link type="text/css" href="css/uploadify.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/uploadify.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<style>
.steps {
	height: 30px;
	margin: 20px 0 30px 0;
}

.steps ol {
	position: relative;
	margin: 0 0 45px;
	height: 13px;
	border-bottom: 1px solid #EBEBEB;
}

.steps ol li {
	position: absolute;
	display: inline-block;
	height: 25px;
	width: 25px;
	border: 1px solid #EBEBEB;
	background: white;
	color: #666;
	line-height: 25px;
	text-align: center;
	font-weight: bold;
	-webkit-border-radius: 1em;
	-moz-border-radius: 1em;
	border-radius: 1em;
}

.steps ol li.selected {
	color: white;
	background: #DD4B39;
	border: 0;
}

.steps .step-2 {
	margin-left: -14px;
	left: 50%;
}

.steps .step-3 {
	right: 0;
}

.form {
	float: left;
	width: 380px;
	background: #dfdfdf;
	padding: 5px 0 7px 0;
	margin: 0 20px 20px 0
}

.r_title,.r_tags,.r_category {
	color: #444;
	width: 200px;
	height: 22px;
	line-height: 22px;
	border: 1px solid #bbb;
	padding-left: 5px;
}

.r_tags {
	width: 170px;
}

.r_description {
	color: #444;
	width: 250px;
	height: 100px;
	border: 1px solid #bbb;
}

.r_submit {
	width: 90px;
	height: 28px;
	border: 0;
	background: #339937;
	color: #fff
}

.step_container {
	padding: 30px 20px 50px 20px;
	min-height: 500px;
}

.intro {
	float: left;
	width: 450px;
}

.intro p {
	margin-top: 50px;
	line-height: 24px;
	color: #999;
	clear: both;
}

.intro p.first {
	margin-top: 0;
}

.intro img {
	width: 180px;
	height: 120px;
	float: left;
	padding: 5px 10px 10px 0;
}

.intro .t {
	display: block;
	color: #444;
	padding-bottom: 5px;
	line-height: 30px;
}

.upload {
	float: right;
	width: 400px;
	min-height: 400px;
	background: #eee;
	padding: 20px;
}

#uploader_queue {
	min-height: 200px;
}

#uploader_op {
	display: none;
	margin-top: 20px;
}

#uploader_msg {
	color: red;
	margin-top: 40px;
}

#start_upload {
	background: #339937;
	color: white;
	display: inline-block;
	width: 80px;
	height: 26px;
	line-height: 26px;
	text-align: center;
	margin-right: 10px;
}

.forms_container {
	width: 800px;
	margin: 0 auto;
	padding-left: 15px;
}

#forms_tip {
	background: #ccc;
	height: 50px;
	line-height: 50px;
	margin: 50px 20px 0 0;
	text-align: center;
}

.congrutuation {
	text-align: center;
	padding-top: 100px;
	font-size: 14px;
}

.congrutuation a {
	display: inline-block;
	margin-left: 8px;
	color: #fff;
	background: #339937;
	padding: 6px 15px;
}

.r_other {
	width: 100%;
	background: #ccc;
	padding: 8px 0;
}

.r_song,.r_singer,.r_album {
	width: 130px;
	height: 20px;
	border: 1px solid #ccc;
}

.r_singer {
	width: 80px;
}

select.first {
	width: 80px;
	height: 24px;
}

select.second {
	width: 100px;
	height: 24px;
	margin-left: 15px;
}
</style>
<script type="text/javascript">
	var category = {
		'image' : {
			'摄影' : [ '自然', '人物', '动物', '其他' ],
			'艺术' : [ '行为', '其他' ],
			'时尚' : [ '生活', '其他' ],
			'美食' : [ '餐点', '小吃', '其他' ]
		},
		'music' : {
			'华语' : [ '流行', '摇滚', '民谣', '爵士', '说唱', '舞曲', '蓝调', '乡村', '美声', 'hip-hop', '拉丁', 'R&B', '其他' ],
			'欧美' : [ '流行', '摇滚', '民谣', '爵士', '说唱', '舞曲', '蓝调', '乡村', '美声', 'hip-hop', '拉丁', 'R&B', '其他' ],
			'日韩' : [ '流行', '摇滚', '民谣', '爵士', '说唱', '舞曲', '蓝调', '乡村', '美声', 'hip-hop', '拉丁', 'R&B', '其他' ],
			'其他' : [ '流行', '摇滚', '民谣', '爵士', '说唱', '舞曲', '蓝调', '乡村', '美声', 'hip-hop', '拉丁', 'R&B', '其他' ]
		},
		'video' : {
			'电影' : [ '武侠', '警匪', '犯罪', '科幻', '战争', '恐怖', '惊悚', '纪录片', '其他' ],
			'电视剧' : [ '古装', '武侠', '警匪', '军事', '神话', '科幻', '悬疑', '历史', '儿童', '其他' ],
			'MV' : [ '流行', '摇滚', '民谣', '爵士', '说唱', '舞曲', '蓝调', '乡村', '美声', 'hip-hop', '拉丁', 'R&B', '其他' ],
			'动漫' : [ '国产', '日产', '其他' ]
		},
		'document' : {
			'教育' : [ '小学', '初中', '高中', '外语', '其他' ],
			'应用' : [ '经验', 'PPT', '办公文档', '其他' ],
			'书籍' : [ '计算机', '经济学', '历史', '数学', '其他' ]
		}
	};

	function goto_step_2() {
		$('#step1').hide();
		$('#step2').show();
	}
	function goto_step_3() {
		$('#step2').hide();
		$('#step3').show();
	}

	function file_type(name) {
		var _name = name.toLowerCase();
		var _res = {};

		_res.image = /(\.bmp|\.png|\.jpg|\.jpeg|\.gif)$/.test(_name);
		_res.document = /(\.doc|\.docx|\.xls|\.xlsx|\.ppt|\.pptx|\.txt)$/.test(_name);
		_res.music = /(\.mp3|\.wma)$/.test(_name);
		_res.video = /(\.mp4|\.flv)$/.test(_name);

		if (_res.image) {
			_res.string = "image";
		} else if (_res.document) {
			_res.string = "document";
		} else if (_res.music) {
			_res.string = "music";
		} else if (_res.video) {
			_res.string = "video";
		}

		return _res;
	}

	function sub_cat(obj, type) {
		var t = category[type];
		var sb = '';
		for ( var first in t) {
			if (first == obj.value) {
				for ( var second in t[first]) {
					sb += "<option>" + t[first][second] + "</option>";
				}
				break;
			}
		}
		$($(obj).siblings()[0]).html(sb);
	}

	function make_form(local, remote) {
		var type = file_type(local);
		var addition = '';

		if (type.music) {
			addition += '<tr><td align="center">其他：</td><td><div class="r_other">'
					+ '<table border="0" width="100%">'
					+ '<tr><td align="right" width="25%">歌曲：</td><td><input type="text" class="r_song" name="song"></td></tr>'
					+ '<tr><td align="right">歌手：</td><td><input type="text" class="r_singer" name="singer"></td></tr>'
					+ '<tr><td align="right">专辑：</td><td><input type="text" class="r_album" name="album"></td></tr>'
					+ '<tr><td align="right" class="r_genre">流派：</td><td><select name="genre">'
					+ '<option>流行</option><option>摇滚</option><option>民谣</option><option>爵士</option><option>说唱</option><option>舞曲</option><option>蓝调</option><option>乡村</option><option>美声</option><option>hip-hop</option><option>拉丁</option><option>R&amp;B</option><option>其他</option>'
					+ '</select></td></tr>' + '</table></div></td></tr>';
		}

		var t = category[type.string];
		var sb = '<select name="first" class="first" onchange="sub_cat(this, \'' + type.string + '\')">';
		for ( var first in t) {
			sb += "<option>" + first + "</option>";
		}
		sb += "</select>";
		for ( var first in t) {
			sb += '<select name="second" class="second">';
			for ( var second in t[first]) {
				sb += "<option>" + t[first][second] + "</option>";
			}
			sb += "</select>";
			break;
		}

		var html = '<form onsubmit="return submit_form(this);">'
				+ '<input type="hidden" name="file" value="' + remote + '">'
				+ '<input type="hidden" name="type" value="' + type.string + '">'
				+ '<table border="0" cellspacing="15">'
				+ '<tr><td align="right" width="18%">文件：</td><td>'
				+ local
				+ '</td></tr>'
				+ '<tr><td align="right">标题：</td><td><input name="title" value="' + local.substr(0, local.indexOf('.')) + '" class="r_title" type="text"></td></tr>'
				+ '<tr><td align="right">标签：</td><td><input name="tag" class="r_tags"></td></tr>'
				+ '<tr><td align="right">分类：</td><td>'
				+ sb
				+ '</td></tr>'
				+ '<tr><td align="center">描述：</td><td><textarea name="description" class="r_description"></textarea></td></tr>'
				+ addition + '<tr><td></td><td><input class="r_submit" type="submit" value="提交"></td></tr>'
				+ '</table></form>';
		var div = document.createElement('div');
		$(div).attr('class', 'form');
		$(div).html(html);

		return div;
	}

	function check_upload_all() {
		var forms = document.forms;
		for ( var i = 0; i < forms.length; i++) {
			var inputs = forms[i].getElementsByTagName('input');

			for ( var j = 0; j < inputs.length; j++) {
				if ($(inputs[j]).attr('type') == 'submit') {
					if (inputs[j].value == '提交')
						return false;
				}
			}
		}

		return true;
	}

	function submit_form(obj) {
		var params = {};

		var inputs = obj.getElementsByTagName('input');
		for ( var i = 0; i < inputs.length; i++) {
			if (inputs[i].name) {
				params[inputs[i].name] = inputs[i].value;
			}
		}
		var temp = obj.getElementsByTagName('textarea');
		for ( var i = 0; i < temp.length; i++) {
			params[$(temp[i]).attr('name')] = $(temp[i]).val();
		}
		temp = obj.getElementsByTagName('select');
		for ( var i = 0; i < temp.length; i++) {
			params[$(temp[i]).attr('name')] = $(temp[i]).val();
		}

		$.post("upload", params, function(data, textStatus) {
			if (data.code == 0) {
				for ( var i = 0; i < inputs.length; i++) {
					if ($(inputs[i]).attr('type') == 'submit') {
						inputs[i].disabled = true;
						inputs[i].value = '已提交';
						inputs[i].style.background = '#888';

						if (check_upload_all())
							setTimeout(goto_step_3, 1000);
					}
				}
			}
		});
		return false;
	}

	function check_upload() {
		if ($('.uploadify-queue-item').length > 0)
			$('#uploader_op').show();
		else
			$('#uploader_op').hide();
	}

	function check_progress() {
		var xx = $('#uploader_queue .data');
		for ( var i = 0; i < xx.length; i++) {
			if (xx[i].innerHTML.indexOf('Complete') == -1)
				return false;
		}
		return true;
	}

	var form_counter = 0;
	var uploadify_config = {
		'swf' : 'swf/uploadify.swf',
		'uploader' : 'upload',
		'queueID' : 'uploader_queue',
		'auto' : false,
		'removeCompleted' : false,
		'muti' : true,
		'fileSizeLimit' : '200MB',
		'queueSizeLimit' : 8,
		'buttonText' : '浏览',
		'uploadLimit' : 8,
		'overrideEvents' : [ 'onSelectError' ],
		'fileTypeExts' : '*.jpg; *.jpeg; *.png; *.bmp; *.mp4; *.flv; *.mp3; *.doc; *.docx; *.ppt; *.pptx; *.xls; *.xlsx; *.txt',
		'onSelect' : function(file) {
			check_upload();
		},
		'onCancel' : function(file) {
			setTimeout(check_upload, 2000);
		},
		'onSelectError' : function(file, errorCode, errorMsg) {
			if (errorCode == SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
				$('#uploader_msg').html("超过上传数目上限");
			}
			if (errorCode == SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT) {
				$('#uploader_msg').html("单个文件不能超过200M");
			}
			if (errorCode == SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE) {
				$('#uploader_msg').html("文件大小不能为0");
			}
			if (errorCode == SWFUpload.QUEUE_ERROR.INVALID_FILETYPE) {
				$('#uploader_msg').html("不支持的文件类型");
			}
		},
		'onUploadSuccess' : function(file, data, response) {
			$('#' + file.id + '>.cancel').css('visibility', 'hidden');
			$('#forms').append(make_form(file.name, data));

			if (++form_counter % 2 == 0) {
				$('#forms').append('<div class="clear"></div>');
			}

			if (check_progress()) {
				setTimeout(goto_step_2, 1000);
			}
		}
	};
	$(document).ready(function() {
		$("#uploadify").uploadify(uploadify_config);
	});
</script>
</head>

<body>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap" style="background: #fff;">
			<div id="step1" class="step_container">
				<div class="title">第一步，请选择要上传的文件：</div>
				<div class="steps">
					<ol>
						<li class="step-1 selected">1</li>
						<li class="step-2">2</li>
						<li class="step-3">3</li>
					</ol>
				</div>
				<div class="intro">
					<p class="first">
						<img src="images/pp.jpg"><span class="t">上传你的照片</span>你可以上传你的照片，并与你的好友、家人分享。 悦目网目前支持的图片格式有：BMP， PNG， JPEG，
						GIF。
					</p>
					<p>
						<img src="images/zxzx.png"><span class="t">上传你的文档</span>你可以上传你的工作、学习、生活文档。悦目网目前支持的图片格式有：DOC， DOCX， XLS，
						XLSX， PPT， PPTX， TXT。
					</p>
					<p>
						<img src="images/wyl.png"><span class="t">上传你喜欢的音乐、视频</span>你可以上传你你喜欢的歌曲、视频。悦目网目前支持的图片格式有：MP3，WMA, FLV， MP4。
					</p>
					<div class="clear"></div>
				</div>
				<div class="upload">
					<div id="uploader">
						<input type="file" name="uploadify" id="uploadify" />
						<div id="uploader_queue"></div>
					</div>
					<div id="uploader_op">
						<a href="javascript:$('#uploadify').uploadify('upload', '*');$('#uploadify').uploadify('disable', true)"
							id="start_upload">开始上传</a> <a href="javascript:$('#uploadify').uploadify('cancel', '*')">取消所有上传</a>
					</div>
					<div id="uploader_msg"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div id="step2" class="step_container" style="display: none;">
				<div class="title">第二步，填写相关信息：</div>
				<div class="steps">
					<ol>
						<li class="step-1">1</li>
						<li class="step-2 selected">2</li>
						<li class="step-3">3</li>
					</ol>
				</div>
				<div class="forms_container">
					<div id="forms"></div>
					<div class="clear"></div>
					<div id="forms_tip">
						请填写完整上面的信息<b>...</b>
					</div>
				</div>
			</div>
			<div id="step3" class="step_container" style="display: none;">
				<div class="title">第三步，完成：</div>
				<div class="steps">
					<ol>
						<li class="step-1">1</li>
						<li class="step-2">2</li>
						<li class="step-3 selected">3</li>
					</ol>
				</div>
				<div class="congrutuation" style="text-align: center; padding-top: 80px; font-size: 14px;">
					恭喜你，已成功上传，正在等候审核。<a href="my.jsp"
						style="display: inline-block; margin-left: 8px; color: #fff; background: #339937; padding: 6px 15px;">我的资源</a>
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>