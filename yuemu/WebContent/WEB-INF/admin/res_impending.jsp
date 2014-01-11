<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="yuemu.services.*,java.util.*,yuemu.po.*,java.util.ArrayList"%>
<%@ include file="passed_impending.jsp"%>

<div id="content" class="content">
	<div style="border-bottom: 1px solid #ddd; padding-bottom: 12px;">
		共有 <span style="color: green;"><%=all%></span> 个<%=type%>资源正在等候你的审核 <span style="float: right;"> <select
			id="type_select" style="width: 80px; height: 24px; margin-top: -8px;">
				<option value="image">图片</option>
				<option value="music">音乐</option>
				<option value="video">视频</option>
				<option value="document">文档</option>
		</select> <script>
			$(document).ready(function() {

				var t = getQueryString('type') == '' ? 'image' : getQueryString('type');
				$("#type_select").val(t);

				$('#type_select').change(function() {
					var action = getQueryString('action') == '' ? 'user_normal' : getQueryString('action');
					var type = $("#type_select").val();
					jump('admin.jsp?action=' + action + '&type=' + type + '&status=' + "1");
				});
			});
		</script>
		</span>
	</div>
	<ul style="margin-top: 30px;">
		<li>
			<table class="table2">
				<tr>
					<th width="20%">日期</th>
					<th width="20%">标题</th>
					<th width="10%">作者</th>
					<th width="30%">标签</th>
					<th width="20%">操作</th>
				</tr>
				<%
					Map<ResourceType, String> map = new HashMap<ResourceType, String>();
					map.put(ResourceType.IMAGE, "showImage");
					map.put(ResourceType.MUSIC, "showMusic");
					map.put(ResourceType.VIDEO, "showVideo");
					map.put(ResourceType.DOCUMENT, "showDocument");

					for (int i = 0; i < list.size(); i++) {
						Resource s = list.get(i);
						int index = s.getPreviewUrl().lastIndexOf('.');
						String ext = "name" + s.getPreviewUrl().substring(s.getPreviewUrl().lastIndexOf('.'));
				%>
				<tr id="r_<%=s.getId()%>">
					<td width="20%"><%=s.getUploadTime().toString().split(" ")[0]%></td>
					<td width="20%"><%=s.getTitle()%></td>
					<td width="10%"><%=s.getUploader().getName()%></td>
					<td width="30%"><%=s.getTag()%></td>
					<td width="20%"><a
						href="javascript:<%=map.get(s.getType())%>('url?action=admin&id=<%=s.getId()%>&ext=<%=ext%>')">查看</a><a
						href="javascript:publish('<%=s.getId()%>')">发布</a><a href="javascript:remove('<%=s.getId()%>')">删除</a></td>
				</tr>
				<%
					}
				%>
			</table>
		</li>
		<li class="pager">共 <%=totalPage%> 页 <a
			href="admin.jsp?action=<%=request.getParameter("action") == null ? "res_impending" : request.getParameter("action")%>&page=<%=prePage%>&type=<%=type%>&status=<%=status%>">上一页</a>
			<a class="current"><%=pageNum%></a> <a
			href="admin.jsp?action=<%=request.getParameter("action") == null ? "res_impending" : request.getParameter("action")%>&page=<%=nextPage%>&type=<%=type%>&status=<%=status%>">下一页</a>
		</li>
	</ul>
</div>