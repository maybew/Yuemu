<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="yuemu.services.UploadStatisticsService"%>

<%@ include file="statistics.jsp" %>

<%
	UploadStatisticsService service = new UploadStatisticsService();
	long[] pieValues = service.getTotalVisit();
	String urlPie = request.getContextPath()
			+ "/servlet/DisplayChart?filename="
			+ pie.getURL(pie.getChart(keys, pieValues, "资源比例图"),
					session);
	double[][] categoryValues = service
			.getEveryMonthInYear(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
	String filename = category.getURL(category.getChart(keys, dates,
			categoryValues, "资源按月分布", "月份", "数量"), session);
	String urlCategory = request.getContextPath()
			+ "/servlet/DisplayChart?filename=" + filename;
%>

<div id="content" class="content">
	 <div style="padding-bottom: 12px; border-bottom: 1px solid #DDD;color: #222; margin-bottom: 20px; font-size: 14px; color: #005179">资源分布统计图</div>
	 <img src="<%=urlCategory%>" title="纵向向直方图">
	  <img src="<%=urlPie%>" title="圆饼图" style="margin-top: 60px;"> 
</div>