package yuemu.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import yuemu.dao.DAOFactory;
import yuemu.po.Account;
import yuemu.services.UserService;

@SuppressWarnings("serial")
@WebServlet("/user")
public class UserServlet extends HttpServlet {

	UserService us = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {

			System.out.println("upload");
			// get upload file list
			List<FileItem> fileList = null;
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("utf-8");

			try {
				fileList = upload.parseRequest(req);
			} catch (FileUploadException ex) {
				ex.printStackTrace();
				return;
			}
			String portrait = us.processUploadPortrait(fileList);
			resp.getWriter().write(portrait);

		} else {
			Account account = (Account) req.getSession()
					.getAttribute("account");
			req.setCharacterEncoding("utf-8");

			if (req.getParameter("action") != null
					&& req.getParameter("action").equals("updateSession")) {
				account = DAOFactory.getAccountDAO().find(account.getId());
				req.getSession().setAttribute("account", account);
				return;
			}
			req.setCharacterEncoding("utf-8");

			if (account == null) {
				System.out.println("account is null");
				return;
			}
			resp.setContentType("application/json; charset=UTF-8");

			if (account != null && us.updateUserInfo(req, account)) {

			} else {

			}
			resp.sendRedirect("profile.jsp");

		}
	}

}
