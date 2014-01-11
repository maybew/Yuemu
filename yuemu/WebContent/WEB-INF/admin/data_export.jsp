<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="content" class="content">
	<div
		style="padding-bottom: 12px; border-bottom: 1px solid #DDD; color: #222; margin-bottom: 20px; font-size: 14px; color: #005179">数据导出</div>
	
	<ul style="padding-bottom: 30px;">
		<li style="margin-bottom: 10px;">（1）数据库： 保存在数据库中的信息</li>
		<li>（2）资源数据： 资源的源文件和转换后的文件</li>
	</ul>
	
	<a href="export?type=db" style="background: #069; color: white; padding: 6px 25px; margin-right: 20px;">数据库</a> <a
		href="export?type=data" style="background: #069; color: white; padding: 6px 25px;">资源数据</a>
</div>