<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="yuemu.services.*,java.util.List,java.util.ArrayList,yuemu.po.*,org.json.JSONArray,org.json.JSONObject"%>
<%
	VisitStatisticsService service = new VisitStatisticsService();
	int topNum = 10;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - 排行榜</title>
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
#main .wrap {
	background: #fff;
	min-height: 500px;
}

table {
	border: 0;
	padding-right: 5px;
}

td {
	line-height: 39px;
}

td a {
	color: #0F820C;
}

td.num {
	color: #999;
	width: 40px;
	text-align: center;
}

td.tt {
	display: inline-block;
	height: 39px;
	overflow: hidden;
}

.rank {
	padding: 20px;
}

.rank .title {
	line-height: 60px;
	border-bottom: 3px solid #ccc;
	margin-bottom: 20px;
	font-size: 25px;
	font-family: 'Microsoft YaHei';
}

.all .title {
	font-size: 32px;
}

.rank .top1 {
	width: 500px;
	float: left;
	border: 1px solid #ddd;
	position: relative;
}

.rank .top1_a {
	color: #0F820C;
	font-size: 14px;
	line-height: 44px;
	height: 44px;
	padding-left: 50px;
	font-size: 13px;
	padding-right: 10px;
	height: 44px;
	overflow: hidden;
}

.rank .top1_b {
	margin: 0 20px 20px;
	max-height: 320px;
	overflow: hidden;
}

.document .top1_b img {
	border: 1px solid #eee;
}

.rank .top1_c {
	position: absolute;
	left: 0;
	top: 0;
}

.rank .other {
	width: 400px;
	background: #eee;
	float: right
}
</style>
</head>

<body>
	<%@ include file="WEB-INF/inc/plugin.jsp"%>
	<%@ include file="WEB-INF/inc/head.jsp"%>

	<div id="main">
		<div class="wrap">
			<div class="rank all">
				<div class="title">总排行榜</div>
				<%
					JSONArray topAll = service.getTopForAll(topNum);
					if (topAll.length() == 0) {
					} else {
				%>
				<div class="top1">
					<div class="top1_a">
						<span></span>
							<a href="view.jsp?id=<%=((JSONObject) topAll.get(0)).get("id")%>">
								<%=((JSONObject) topAll.get(0)).get("title")%>
							</a>
					</div>
					<div class="top1_b">
						<a href="view.jsp?id=<%=((JSONObject) topAll.get(0)).get("id")%>">
							<%
								if (((JSONObject) topAll.get(0)).get("type") == ResourceType.VIDEO) {
							%>
								<img src="images/video2.png" width="460"> 

							<%
								} else if (((JSONObject) topAll.get(0)).get("type") == ResourceType.MUSIC) {
							%>
								<img src="images/music2.png" width="460"> 
							<%} else {%> 
								<img src="<%=((JSONObject) topAll.get(0)).get("snippet_url")%>"
								width="460">
							 <%}%>
						</a>
					</div>
					<div class="top1_c">
						<img src="images/no1.png" width="64" height="64">
					</div>
				</div>
				<div class="other">
					<table>
						<%
							for (int i = 1; i < topAll.length(); i++) {
						%>
						<tr>
							<td class="num"><%=i + 1%></td>
							<td class="tt"><a
								href="view.jsp?id=<%=((JSONObject) topAll.get(i)).get("id")%>"><%=((JSONObject) topAll.get(i)).get("title")%></a></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
				<div class="clear"></div>
				<%
					}
				%>
			</div>

			<div class="rank image">
				<%
					JSONArray topImage = service.getTop(topNum, ResourceType.IMAGE);
					if (topImage.length() == 0) {

					} else {
				%>
				<div class="title">图片排行榜</div>
				<div class="top1">
					<div class="top1_a">
						<span>
						<a href="view.jsp?id=<%=((JSONObject) topImage.get(0)).get("id")%>">
							<%=((JSONObject) topImage.get(0)).get("title")%>
						</a>
						</span>
					</div>
					<div class="top1_b">
						<a href="view.jsp?id=<%=((JSONObject) topImage.get(0)).get("id")%>">
							<img src="<%=((JSONObject) topImage.get(0)).get("snippet_url")%>"
							width="460">
						</a>
					</div>
					<div class="top1_c">
						<img src="images/no1.png" width="64" height="64">
					</div>
				</div>
				<div class="other">
					<table>
						<%
							for (int i = 1; i < topImage.length(); i++) {
						%>
						<tr>
							<td class="num"><%=i + 1%></td>
							<td class="tt"><a
								href="view.jsp?id=<%=((JSONObject) topImage.get(i)).get("id")%>"><%=((JSONObject) topImage.get(i)).get("title")%></a></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
				<div class="clear"></div>
				<%
					}
				%>
			</div>


			<div class="rank music">
				<div class="title">音乐排行榜</div>
				<%
					JSONArray topMusic = service.getTop(topNum, ResourceType.MUSIC);
					if (topMusic.length() == 0) {

					} else {
				%>
				<div class="top1">
					<div class="top1_a">
						<span>
							<a href="view.jsp?id=<%=((JSONObject) topMusic.get(0)).get("id")%>">
								<%=((JSONObject) topMusic.get(0)).get("title")%>
							</a>
						</span>
					</div>
					<div class="top1_b">
						<a href="view.jsp?id=<%=((JSONObject) topMusic.get(0)).get("id")%>">
							<img src="images/music2.png" width="460">
						</a>
					</div>
					<div class="top1_c">
						<img src="images/no1.png" width="64" height="64">
					</div>
				</div>
				<div class="other">
					<table>
						<%
							for (int i = 1; i < topMusic.length(); i++) {
						%>
						<tr>
							<td class="num"><%=i + 1%></td>
							<td class="tt"><a
								href="view.jsp?id=<%=((JSONObject) topMusic.get(i)).get("id")%>"><%=((JSONObject) topMusic.get(i)).get("title")%></a></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
				<div class="clear"></div>
				<%
					}
				%>
			</div>



			<div class="rank video">
				<div class="title">视频排行榜</div>
				<%
					JSONArray topVideo = service.getTop(topNum, ResourceType.VIDEO);
					if (topVideo.length() == 0) {

					} else {
				%>
				<div class="top1">
					<div class="top1_a">
						<span>
						<a href="view.jsp?id=<%=((JSONObject) topVideo.get(0)).get("id")%>">
							<%=((JSONObject) topVideo.get(0)).get("title")%>
						</a>
						</span>
					</div>
					<div class="top1_b">
						<a href="view.jsp?id=<%=((JSONObject) topVideo.get(0)).get("id")%>">
							<img src="images/video2.png" width="460">
						</a>
					</div>
					<div class="top1_c">
						<img src="images/no1.png" width="64" height="64">
					</div>
				</div>
				<div class="other">
					<table>
						<%
							for (int i = 1; i < topVideo.length(); i++) {
						%>
						<tr>
							<td class="num"><%=i + 1%></td>
							<td class="tt"><a
								href="view.jsp?id=<%=((JSONObject) topVideo.get(i)).get("id")%>"><%=((JSONObject) topVideo.get(i)).get("title")%></a></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
				<div class="clear"></div>
				<%
					}
				%>
			</div>


			<div class="rank document">
				<div class="title">文档排行榜</div>
				<%
					JSONArray topDocument = service.getTop(topNum,
							ResourceType.DOCUMENT);
					if (topDocument.length() == 0) {

					} else {
				%>
				<div class="top1">
					<div class="top1_a">
						<span>
						<a href="view.jsp?id=<%=((JSONObject) topDocument.get(0)).get("id")%>">
							<%=((JSONObject) topDocument.get(0)).get("title")%>
						</a>
						</span>
					</div>
					<div class="top1_b">
						<a href="view.jsp?id=<%=((JSONObject) topDocument.get(0)).get("id")%>">
							<img
							src="<%=((JSONObject) topDocument.get(0)).get("snippet_url")%>"
							width="460">
						</a>
					</div>
					<div class="top1_c">
						<img src="images/no1.png" width="64" height="64">
					</div>
				</div>
				<div class="other">
					<table>
						<%
							for (int i = 1; i < topDocument.length(); i++) {
						%>
						<tr>
							<td class="num"><%=i + 1%></td>
							<td class="tt"><a
								href="view.jsp?id=<%=((JSONObject) topDocument.get(i)).get("id")%>"><%=((JSONObject) topDocument.get(i)).get("title")%></a></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
				<div class="clear"></div>
				<%
					}
				%>
			</div>
		</div>
	</div>

	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>

