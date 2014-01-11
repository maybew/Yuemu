window.yuemu = {
	"author" : "Y.L. Wu",
	"version" : "1.0",
	"date" : "2012-07-15"
};

function getPageSize() {
	var scrW = 0, scrH = 0;
	if (window.innerHeight && window.scrollMaxY) {
		// Mozilla
		scrW = window.innerWidth + window.scrollMaxX;
		scrH = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) {
		// all but IE Mac
		scrW = document.body.scrollWidth;
		scrH = document.body.scrollHeight;
	} else if (document.body) { // IE Mac
		scrW = document.body.offsetWidth;
		scrH = document.body.offsetHeight;
	}

	var winW = 0, winH = 0;
	if (window.innerHeight) { // all except IE
		winW = window.innerWidth;
		winH = window.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) {
		// IE 6 Strict Mode
		winW = document.documentElement.clientWidth;
		winH = document.documentElement.clientHeight;
	} else if (document.body) { // other
		winW = document.body.clientWidth;
		winH = document.body.clientHeight;
	}

	// for small pages with total size less then the viewport
	var pageW = (scrW < winW) ? winW : scrW;
	var pageH = (scrH < winH) ? winH : scrH;

	return {
		"pageWidth" : pageW,
		"pageHeight" : pageH,
		"windowWidth" : winW,
		"windowHeight" : winH
	};
}

function clear_menu() {
	$('#link .expanded').removeClass('expanded');
	$('#link .submenu').css('display', 'none');
	$('#link .padding').css('display', 'none');
}

function header_menu(id) {
	if ($('#' + id).css('display') == 'none') {
		clear_menu();
		$('#' + id + "_p").css('display', 'block');
		$('#' + id).css('display', 'block');
		$('#' + id).parent().addClass('expanded');
	} else {
		$('#' + id).parent().removeClass('expanded');
		$('#' + id + "_p").css('display', 'none');
		$('#' + id).css('display', 'none');
	}
}

function jump(url) {
	window.location.href = url;
}

$(document).ready(function() {
	$(document).click(function(ev) {
		ev = ev || window.event;
		if (ev.srcElement.tagName != 'A' && !$(ev.srcElement).hasClass('toggle_icon')) {
			clear_menu();
		}
	});
});

function getQueryString(name) {
	if (location.href.indexOf("?") == -1 || location.href.indexOf(name + '=') == -1) {
		return '';
	}
	var queryString = location.href.substring(location.href.indexOf("?") + 1);
	var parameters = queryString.split("&");
	var pos, paraName, paraValue;
	for ( var i = 0; i < parameters.length; i++) {
		pos = parameters[i].indexOf('=');
		if (pos == -1) {
			continue;
		}

		paraName = parameters[i].substring(0, pos);
		paraValue = parameters[i].substring(pos + 1);
		if (paraName == name) {
			return unescape(paraValue.replace(/\+/g, " "));
		}
	}
	return '';
};

function getQueryString2(name) {
	var href = decodeURI(window.location.href);
	if (href.indexOf("?") == -1 || href.indexOf(name + '=') == -1) {
		return '';
	}
	var queryString = href.substring(href.indexOf("?") + 1);
	var parameters = queryString.split("&");
	var pos, paraName, paraValue;
	for ( var i = 0; i < parameters.length; i++) {
		pos = parameters[i].indexOf('=');
		if (pos == -1) {
			continue;
		}

		paraName = parameters[i].substring(0, pos);
		paraValue = parameters[i].substring(pos + 1);
		if (paraName == name) {
			return unescape(paraValue.replace(/\+/g, " "));
		}
	}
	return '';
};

var funciton_map = {
	'VIDEO' : 'playVideo',
	'MUSIC' : 'playMusic',
	'IMAGE' : 'showImage',
	'DOCUMENT' : 'showDocument'
};

function make_item(res) {
	var addition = '';
	if (res.resource.type == 'MUSIC') {
		addition += '<p class="song">歌曲： ' + res.song + '</p>';
		addition += '<p class="singer">歌手： ' + res.singer + '</p>';
		addition += '<p class="album">专辑： ' + res.album + '</p>';
		addition += '<p class="genre">流派： ' + res.genre + '</p>';
	}
	var snippet = '';
	if (res.resource.type == 'IMAGE' || res.resource.type == 'DOCUMENT') {
		snippet += '<img src="' + res.resource.snippet_url + '" width="100%">';
	}

	var html = '<div class="user_icon">' + '<img src="images/user.png" width="54" height="54">' + '</div>'
			+ '<div class="user_say"></div>' + '<div class="body">' + '<div class="title"><a href="view.jsp?id='
			+ res.resource.id
			+ '">'
			+ res.resource.title
			+ '</a></div>'
			+ '<div class="content" id="r_'
			+ res.resource.id
			+ '"  onclick="'
			+ funciton_map[res.resource.type]
			+ '(\''
			+ res.resource.preview_url
			+ '\', \'r_'
			+ res.resource.id
			+ '\')">'
			+ snippet
			+ '</div>'
			+ '<p class="description">'
			+ res.resource.description
			+ '</p>'
			+ addition
			+ '<div class="other">'
			+ '<div class="tag">标签： '
			+ res.resource.tags
			+ '</div>'
			+ '<div class="date">上传于 '
			+ res.resource.date
			+ '</div>'
			+ '<div class="clear"></div>' + '</div>' + '</div>' + '<div class="clear"></div>';
	var div = document.createElement('div');
	$(div).attr('class', 'item ' + res.resource.type.toLowerCase());
	$(div).html(html);

	return div;
}