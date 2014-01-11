package yuemu.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuemu.po.Account;
import yuemu.po.Resource;
import yuemu.services.DownloadStatisticsService;
import yuemu.services.LogService;
import yuemu.services.ResourceService;
import yuemu.services.VisitStatisticsService;

@SuppressWarnings("serial")
@WebServlet({ "/url", "/swf/url" })
public class URLServlet extends HttpServlet {

	ResourceService rservice = new ResourceService();
	DownloadStatisticsService dservice = new DownloadStatisticsService();
	VisitStatisticsService vservice = new VisitStatisticsService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String action = request.getParameter("action");
		Long id = Long.parseLong(request.getParameter("id"));
		Account account = (Account) request.getSession()
				.getAttribute("account");
		Resource resource = rservice.getById(id);

		String path = "";
		if ("snippet".equals(action)) {
			path = resource.getSnippetUrl();
		} else if ("preview".equals(action)) {
			path = resource.getPreviewUrl();

			vservice.addVisitedNum(account, resource);
		} else if ("download".equals(action)) {
			path = resource.getSourceUrl();

			if (account != null) {
				dservice.addDownloadNum(account, resource);
				response.setHeader("Content-Disposition",
						"attachment; filename=" + new File(path).getName());
				HttpSession session = request.getSession();
				Account a = (Account) (session.getAttribute("account"));
				LogService.log("邮箱为" + a.getEmail() + "的用户下载了一些资源", "资源下载");
			} else {
				response.sendRedirect("signin.jsp");
				return;
			}
		} else if ("admin".equals(action)) {
			path = resource.getPreviewUrl();
		} else {
			response.sendRedirect("index.jsp");
			return;
		}

		System.out.println(path);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
