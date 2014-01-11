<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="yuemu.jfreechart.PieChart,java.util.Calendar,yuemu.jfreechart.CategoryChart"%>

<%
	String[] keys = { "文档", "图片", "音乐", "视频" };
	PieChart pie = new PieChart();
	CategoryChart category = new CategoryChart();
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	String[] dates = new String[3];
	for (int i = 2; i >= 0; i--) {
		dates[i] = year + "/" + (month+1);
		if (month == 0) {
			month = 11;
			year = year - 1;
		} else {
			month = month - 1;
		}
	}
%>