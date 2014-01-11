function showVideo(src) {
	var height = getPageSize().windowHeight;
	$("#video_box").css('height', height + "px");

	$("#video_close").css("display", "block").css("position", "fixed");
	$("#video_mask").css("display", "block");
	$("#video_box").css("display", "block").css("top", $(window).scrollTop() + "px");
	$("html").css("overflow", "hidden");

	// swfobject.embedSWF(src, "video_swf", "900", height, "9.0.0",
	// "expressInstall.swf");
	play("video_swf", encodeURIComponent(src), "960", "564");
	$("#video_swf").css("margin-top", (height - 564) / 2 + 'px');

	$$("video_close").onclick = function() {
		$("#video_close").css("display", "none");
		$("#video_mask").css("display", "none");
		$("#video_box").css("display", "none");
		$("html").css("overflow", "auto");
		swfobject.removeSWF("video_swf");

		document.getElementById('video_box').innerHTML = '<div id="video_swf"></div>';
	};

	// window.scrollTo(0, 0);
}

function showMusic(src) {
	var height = getPageSize().windowHeight;
	$("#music_box").css('height', height + "px");

	$("#music_close").css("display", "block").css("position", "fixed");
	$("#music_mask").css("display", "block");
	$("#music_box").css("display", "block").css("top", $(window).scrollTop() + "px");
	$("html").css("overflow", "hidden");

	$("#music_box").css("padding-top", (height - 300) / 2 + 'px');
	play("music_swf", encodeURIComponent(src), '520', '24');
	$("#music_div table").html(
			'<tr><td align="right" width="25%">歌曲 ：</td><td>歌曲</td></tr>'
					+ '<tr><td align="right">歌手：</td><td>歌手</td></tr>'
					+ '<tr><td align="right">专辑：</td><td>专辑</td></tr>'
					+ '<tr><td align="right" class="r_genre">流派：</td><td>流行</td></tr>');

	$$("music_close").onclick = function() {
		$("#music_close").css("display", "none");
		$("#music_mask").css("display", "none");
		$("#music_box").css("display", "none");
		$("html").css("overflow", "auto");
		swfobject.removeSWF("music_swf");

		document.getElementById('music_swf_container').innerHTML = '<div id="music_swf"></div>';
	};

	// window.scrollTo(0, 0);
}

function showImage(src) {
	var height = getPageSize().windowHeight;
	$("#iv_image").css('height', height + "px");
	$("#iv_box").css('height', height + "px");

	$("#iv_close").css("display", "block").css("position", "fixed");
	$("#iv_mask").css("display", "block");
	$("#iv_box").css("display", "block").css("top", $(window).scrollTop() + "px");
	$("html").css("overflow", "hidden");

	var container = $$("iv_image");
	var options = {
		onPreLoad : function() {
			container.style.backgroundImage = "";
		},
		onLoad : function() {
			container.style.backgroundImage = "";
		},
		onError : function(err) {
			container.style.backgroundImage = "";
			alert(err);
		}
	};
	if (!document.all || window.opera) {
		options.mode = "canvas";
	}
	var it = new ImageTrans(container, options);
	it.load(src);

	$$("rotate_left").onclick = function() {
		it.left();
	};
	$$("rotate_right").onclick = function() {
		it.right();
	};
	$$("one_to_one").onclick = function() {
		it.reset();
	};
	$$("iv_close").onclick = function() {
		it.dispose();
		$("#iv_close").css("display", "none");
		$("#iv_mask").css("display", "none");
		$("#iv_box").css("display", "none");
		$("html").css("overflow", "auto");
	};

	// window.scrollTo(0, 0);
}

function showDocument(src) {
	var height = getPageSize().windowHeight;
	$("#doc_box").css('height', height + "px");

	$("#doc_close").css("display", "block").css("position", "fixed");
	;
	$("#doc_mask").css("display", "block");
	$("#doc_box").css("display", "block").css("top", $(window).scrollTop() + "px");
	;
	$("html").css("overflow", "hidden");

	swfobject.embedSWF(src, "doc_swf", "900", height, "9.0.0", "expressInstall.swf");

	$$("doc_close").onclick = function() {
		$("#doc_close").css("display", "none");
		$("#doc_mask").css("display", "none");
		$("#doc_box").css("display", "none");
		$("html").css("overflow", "auto");
		swfobject.removeSWF("doc_swf");

		document.getElementById('doc_box').innerHTML = '<div id="doc_swf"></div>';
	};

	// window.scrollTo(0, 0);
}

function play(id, url, width, height) {
	var flashvars = {
		"file" : url,
		"autostart" : "true",
		"volume" : "50"
	};
	var params = {
		"wmode" : "transparent",
		"allowscriptaccess" : "true",
		"allowfullscreen" : "true"
	};
	var attributes = {};

	swfobject.embedSWF("swf/jwplayer.swf", id, width, height, "9.0.0", "expressInstall.swf", flashvars, params,
			attributes);
}

function playVideo(url, id) {
	play(id, encodeURIComponent(url), '520', '390');
}

function playMusic(url, id) {
	play(id, encodeURIComponent(url), '520', '24');
}