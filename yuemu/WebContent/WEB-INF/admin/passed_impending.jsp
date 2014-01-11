<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="yuemu.services.*,java.util.List,yuemu.po.*,java.util.ArrayList"%>
<%
	request.setCharacterEncoding("utf-8");

	String type = "image";
	
	if (request.getParameter("type") != null) {
		type = request.getParameter("type");
	}
	
	int status = (request.getParameter("status") != null) ? Integer
			.parseInt(request.getParameter("status")) : 1;
	int pageNum = (request.getParameter("page") != null) ? Integer
			.parseInt(request.getParameter("page")) : 1;
			
	int pageSize = 10;
	int totalPage = 1;
	int prePage = 1;
	int nextPage = 1;
	int all = 0;
	List<Resource> list = new ArrayList<Resource>();

	if (type.equals("all")) {

	} else if (type.equals("video")) {
		VideoService s = new VideoService();
		list = s.getByStatus(status, pageNum, pageSize);
		all = s.countByStatus(status);
		totalPage = (all%pageSize==0)?all/pageSize:(all/pageSize)+1;
		totalPage = totalPage == 0?1:totalPage;
		prePage = pageNum <= 1 ? 1 : pageNum - 1;
		nextPage = pageNum >= totalPage ? totalPage : pageNum + 1;

	} else if (type.equals("music")) {
		MusicService s = new MusicService();
		list = s.getByStatus(status, pageNum, pageSize);
		all = s.countByStatus(status);
		totalPage = (all%pageSize==0)?all/pageSize:(all/pageSize)+1;
		totalPage = totalPage == 0?1:totalPage;
		prePage = pageNum <= 1 ? 1 : pageNum - 1;
		nextPage = pageNum >= totalPage ? totalPage : pageNum + 1;

	} else if (type.equals("image")) {
		ImageService s = new ImageService();
		list = s.getByStatus(status, pageNum, pageSize);
		all = s.countByStatus(status);
		totalPage = (all%pageSize==0)?all/pageSize:(all/pageSize)+1;
		totalPage = totalPage == 0?1:totalPage;
		prePage = pageNum <= 1 ? 1 : pageNum - 1;
		nextPage = pageNum >= totalPage ? totalPage : pageNum + 1;

	} else if (type.equals("document")) {
		DocumentService s = new DocumentService();
		list = s.getByStatus(status, pageNum, pageSize);
		all = s.countByStatus(status);
		totalPage = (all%pageSize==0)?all/pageSize:(all/pageSize)+1;
		totalPage = totalPage == 0?1:totalPage;
		prePage = pageNum <= 1 ? 1 : pageNum - 1;
		nextPage = pageNum >= totalPage ? totalPage : pageNum + 1;

	}
%>
