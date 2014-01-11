<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="yuemu.po.Account,yuemu.po.UserType"%>
<%
	Account account = (Account) session.getAttribute("account");
%>
<div id="header">
	<div class="wrap">
		<div id="logo">
			<a href="index.jsp"></a>
		</div>
		<div id="link">
			<%
				if (account != null) {
						%>
						<div style="float: right;">
							<a id="user" href="javascript: header_menu('m_02')"> <%=account.getName()%><span
								class="toggle_icon"></span> <span class="padding" id="m_02_p"></span>
								<span class="submenu" id="m_02"> <span class="submenu_bg">
										<span onclick="jump('upload.jsp')">我要上传</span> <span
										onclick="jump('my.jsp')">我的资源</span> <span
										onclick="jump('profile.jsp')">个人信息</span> <span class="sep"></span>
										<span onclick="jump('signOut')">退出</span>
								</span>
							</span>
							</a>
						</div>
						<%
					if (account.getUserType() == UserType.ADMIN) {
							%>
							<div style="float: right;">
								<a id="admin" href="javascript: header_menu('m_01')">管理<span
									class="toggle_icon"></span> <span class="padding" id="m_01_p"></span>
									<span class="submenu" id="m_01"> <span class="submenu_bg">
											<span onclick="jump('admin.jsp?action=res_impending&type=image')">审核图片</span>
											<span onclick="jump('admin.jsp?action=res_impending&type=music')">审核音乐</span>
											<span onclick="jump('admin.jsp?action=res_impending&type=video')">审核视频</span>
											<span
											onclick="jump('admin.jsp?action=res_impending&type=document')">审核文档</span>
											<span class="sep"></span> <span
											onclick="jump('admin.jsp?action=user_normal')">用户管理</span> <span
											onclick="jump('admin.jsp?action=sat_resources')">数据统计</span> <span
											onclick="jump('admin.jsp?action=sat_log')">日志查看</span>
									</span>
								</span>
								</a>
							</div>
							<%
					}
						%>
						<%
				} else {
					String uri = request.getRequestURI();
					//System.out.println(uri);
					if (uri.equals("admin.jsp") || uri.equals("my.jsp")
							|| uri.equals("upload.jsp")
							|| uri.equals("profile.jsp")) {
						response.sendRedirect("/signin.jsp");
					}
					%>
					<span><a id="signin" href="signin.jsp">登陆</a></span> <span><a
						id="signup" href="signup.jsp">注册</a></span>
					<%
				}
			%>
		</div>
	</div>
</div>
