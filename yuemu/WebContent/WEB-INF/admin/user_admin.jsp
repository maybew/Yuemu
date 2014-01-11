<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="content" class="content">

	<%@ page import="yuemu.services.AccountService,java.util.List,yuemu.po.Account"%>

	<%
		request.setCharacterEncoding("utf-8");
		AccountService service = new AccountService();
		int current = 1;
		int x = service.getAdminNum();
		int totalPage = (x % 10 == 0) ? x / 10 : (x / 10) + 1;
		if (request.getParameter("current") != null)
			current = Integer.parseInt(request.getParameter("current"));

		int nextPage = (current == totalPage) ? totalPage : current + 1;
		int prePage = (current == 1) ? 1 : current - 1;
	%>

	<div>
		悦目网目前拥有 <span style="color: green;"><%=x%></span> 名管理员； 在这里，你可以进行如下操作：
	</div>
	<ul style="margin-top: 12px;">
		<li style="margin-bottom: 8px;">（1）查看： 查看管理员的详细信息</li>
		<li>（2）权限更改： 解除管理员的管理权限</li>
	</ul>
	<ul style="margin-top: 30px;">
		<li>
			<table class="table2">
				<tr>
					<th width="15%">姓名</th>
					<th width="10%">性别</th>
					<th width="15%">生日</th>
					<th width="16%">职业</th>
					<th width="24%">E-Mail</th>
					<th width="20%">操作</th>
				</tr>
				<%
					List<Account> list = service.getAdminPage(current, 10);
					for (int i = 0; i < list.size(); i++) {
						Account a = list.get(i);
				%>
				<tr id="u_<%=a.getId()%>">
					<td><%=a.getName()%></td>
					<td><%=a.getSex()%></td>
					<td><%=a.getBirthday().toString().split(" ")[0]%></td>
					<td><%=a.getProfession()%></td>
					<td><%=a.getEmail()%></td>
					<td><a href="javascript:user_tonormal('<%=a.getId()%>')">取消管理权限</a></td>
				</tr>
				<%
					}
				%>
			</table>
		</li>
		<li class="pager">共 <%=totalPage%>页 <a href="/admin.jsp?current=<%=prePage%>">上一页</a> <a
			onclick="switch_page('merchant&page=1')" class="current"><%=current%></a> <a href="/admin.jsp?current=<%=nextPage%>">下一页</a>
		</li>
	</ul>
</div>