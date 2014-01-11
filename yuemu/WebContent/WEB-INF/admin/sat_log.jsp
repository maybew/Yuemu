<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>

<%@page import="yuemu.po.Log"%>
<%@page import="java.util.List"%>
<%@page import="yuemu.services.LogService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	int current=1;
	request.setCharacterEncoding("utf-8");
	LogService service =new LogService();

	
	if(request.getParameter("current")!=null) current=Integer.parseInt(request.getParameter("current"));
	int pageSize=10;
	List<Log>	 lg=service.findAllLog(current, pageSize);
	
	
	long totalPage = service.getAllPageCount(pageSize);
	
	long nextPage = (current==totalPage)?totalPage:current+1;
	int prePage = (current<=1)?1:current-1;
%>

<div id="content" class="content"> 
<div style="padding-bottom: 12px;
border-bottom: 1px solid #DDD;font-size: 14px; color: #005179">日志查看</div>
	<table class="table3" style="width: 100%;margin-top: 30px;">
		<tr>
			<th width="25%">时间</th>
			<th width="10%">模块</th>
			<th width="65%">事件</th>
		</tr>
		
	<% for(int i=0;i<lg.size();i++){  %>
	
	<% if(i%2==0){%>
		<tr>
		
		<%DateFormat df =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		String xx = df.format(new Date(lg.get(i).getLogTime().getTime()));
	
		%>
		
			<td ><%=xx%></td>
			<td ><%=lg.get(i).getMode()%></td>
			<td ><%=lg.get(i).getDescribe()%></td>
		</tr>
	<% } else {%>
	
	<%DateFormat df =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		String xx = df.format(new Date(lg.get(i).getLogTime().getTime()));
	
		%>
		<tr class="even">
			<td><%=xx%></td>
			<td><%=lg.get(i).getMode()%></td>
			<td><%=lg.get(i).getDescribe()%></td>
		</tr>
		<% }%>
		<%}%>
	
	</table> 

		<div class="pager">
		共 <%=totalPage%> 页 <a  href="admin.jsp?action=sat_log&current=<%=prePage%>">上一页</a> <a onclick="switch_page('merchant&page=1')" class="current" ><%=current%></a> <a href="admin.jsp?action=sat_log&current=<%=nextPage%>">下一页</a>
		</div>
	
</div>