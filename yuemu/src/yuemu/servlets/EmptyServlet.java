package yuemu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuemu.services.DatabaseService;
import yuemu.web.SessionSet;

@SuppressWarnings("serial")
@WebServlet("/empty")
public class EmptyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DatabaseService.emptyDatabase();
		DatabaseService.insertRootAdministrator();
		SessionSet.emptySessions();

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter()
				.println("<html><head><meta charset=\"utf-8\">" +
						"<meta http-equiv=\"refresh\" content=\"3;url=index.jsp\">" +
						"<title>悦目 - 管理</title></head>" +
						"<body style=\"font-size:13px\">正在跳转至首页</body><html>");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
