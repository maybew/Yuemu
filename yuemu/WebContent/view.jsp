<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="yuemu.dao.*,yuemu.services.*,yuemu.dao.statistics.*,yuemu.dao.manage.*,yuemu.po.*"%>
<%@ include file="WEB-INF/inc/plugin.jsp"%>
<%@ include file="WEB-INF/inc/head.jsp"%>
<%
	ResourceService rs = new ResourceService();
	DownloadStatisticsService ds = new DownloadStatisticsService();
	VisitStatisticsService vs = new VisitStatisticsService();

	Resource r = rs.getById(Long.parseLong(request.getParameter("id")));
	int visitCount = (int) VisitStatisticsDAO.countByResourceId(r.getId());
	int downCount = (int) DownloadStatisticsDAO.countByResourceId(r.getId());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>悦目 - <%=r.getTitle()%></title>
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
	min-height: 600px;
}

td {
	padding: 15px 0;
}

#container {
	padding: 30px 25px 60px;
}

.item2 {
	float: left;
	width: 580px;
	margin-bottom: 20px;
}

.item2 .title {
	font-size: 16px;
	color: #005179;
	padding-bottom: 15px;
}

.item2 .author {
	color: #666;
	padding-bottom: 10px;
}

.info2 {
	float: right;
	width: 300px;
}

.info2 .title {
	border-left: 3px solid #999;
	padding: 8px;
	font-size: 14px;
	color: #069;
}

.info2 table {
	background: #EEE;
	padding: 10px 15px;
	width: 100%;
	margin-top: 27px;
	font-size: 13px;
}

.info2 .links {
	margin-top: 30px
}

.info2 .links a {
	background: #339937;
	color: white;
	display: inline-block;
	padding: 6px 20px;
}

#music_meta {
	margin-top: 30px;
	background: #DDD;
	padding: 25px 20px;
}

#music_meta  div {
	line-height: 50px;
}
</style>
</head>

<body>
	<div id="main">
		<div class="wrap">
			<div id="container">
				<div class="item2">
					<div class="title"><%=r.getTitle()%></div>
					<div class="author">
						BY
						<%=r.getUploader().getName()%></div>
					<div class="content">

						<%
							if (r.getType() == ResourceType.IMAGE) {
						%>
						<img
							onclick="showImage('url?action=preview&id=<%=r.getId()%>&ext=name<%=r.getFileExtension(r.getPreviewUrl())%>')"
							src="url?action=preview&id=<%=r.getId()%>&ext=name<%=r.getFileExtension(r.getPreviewUrl())%>"
							width="100%">
						<%
							}
						%>

						<%
							if (r.getType() == ResourceType.MUSIC) {
								Music m = DAOFactory.getMusicDAO().findByResourceId(r.getId());
						%>
						<div id="swf"></div>
						<div id="music_meta">
							<div>
								歌曲：
								<%=m.getSong()%></div>
							<div>
								歌手：
								<%=m.getSinger()%></div>
							<div>
								专辑：
								<%=m.getAlbum()%></div>
							<div>
								流派：
								<%=m.getGenre()%></div>
						</div>
						<script>
							play("swf", encodeURIComponent("url?action=preview&id=<%=r.getId()%>&ext=name<%=r.getFileExtension(r.getPreviewUrl())%>"), "580", "24");
						</script>

						<%
							}
						%>


						<%
							if (r.getType() == ResourceType.VIDEO) {
						%>
						<div id="swf"></div>
						<script>
							play("swf", encodeURIComponent("url?action=preview&id=<%=r.getId()%>&ext=name<%=r.getFileExtension(r.getPreviewUrl())%>"), "580", "400"); 
						</script>
						<%
							}
						%>


						<%
							if (r.getType() == ResourceType.DOCUMENT) {
						%>
						<div id="swf"></div>
						<script>
							swfobject.embedSWF("url?action=preview&id=<%=r.getId()%>&ext=name<%=r.getFileExtension(r.getPreviewUrl())%>",
											"swf", "580", "400", "9.0.0",
											"expressInstall.swf");
						</script>
						<%
							}
						%>

					</div>
				</div>
				<div class="info2">
					<div class="title">资源信息</div>
					<div>
						<table>
							<tr>
								<td width="50">作者：</td>
								<td><%=r.getUploader().getEmail()%></td>
							</tr>
							<tr>
								<td>描述：</td>
								<td><%=r.getDescription()%></td>
							</tr>
							<tr>
								<td>标签：</td>
								<td><%=r.getTag()%></td>
							</tr>
							<tr>
								<td>浏览：</td>
								<td><font color="red"><%=visitCount%></font> 次</td>
							</tr>
							<tr>
								<td>下载：</td>
								<td><font color="red"><%=downCount%></font> 次</td>
							</tr>
							<tr>
								<td>状态：</td>
								<td><%=r.getStatus() == 0 ? "审核通过" : (r.getStatus() == 1 ? "正在审核" : "已删除")%>
								</td>
							</tr>
						</table>
					</div>
					<%
						if (session.getAttribute("account") != null) {
					%>
					<div class="links">
						<a
							href="url?action=download&id=<%=r.getId()%>&ext=name<%=r.getFileExtension(r.getSourceUrl())%>">立即下载</a>
					</div>
					<%
						} else {
					%>
					<div>
						<div class="links">
							<a style="background: grey">登录后可下载</a>
						</div>
					</div>
					<%
						}
					%>
				</div>
				<div class="clear"></div>
			</div>

		</div>
	</div>

	<div id="footer">
		<div class="wrap">版权所有 &copy; 2012 悦目网</div>
	</div>
</body>
</html>


