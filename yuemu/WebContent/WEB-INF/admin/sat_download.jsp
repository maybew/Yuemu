<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="yuemu.services.DownloadStatisticsService" %>
<%@ include file="statistics.jsp"%>
<%
	DownloadStatisticsService service = new DownloadStatisticsService();
	long[] pieValues = service.getTotalVisit();
	String urlPie = request.getContextPath()
			+ "/servlet/DisplayChart?filename="
			+ pie.getURL(pie.getChart(keys, pieValues, "下载次数比例图"),
					session);
	double[][] categoryValues = service.getEveryMonthInYear(
			c.get(Calendar.YEAR), c.get(Calendar.MONTH));
	String filename = category.getURL(category.getChart(keys, dates,
			categoryValues, "资源下载次数按月分布", "月份", "数量"), session);
	String urlCategory = request.getContextPath()
			+ "/servlet/DisplayChart?filename=" + filename;
%>
<div id="content" class="content">
	<div>
		<div
			style="padding-bottom: 12px;
border-bottom: 1px solid #DDD;color: #222; margin-bottom: 20px; font-size: 14px; color: #005179">下载量统计图</div>
		<img src="<%=urlCategory%>" title="横向直方图">
		<img src="<%=urlPie%>" title="饼图" style="margin-top: 60px;">
	</div>
</div>