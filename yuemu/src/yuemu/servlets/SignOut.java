package yuemu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuemu.po.Account;
import yuemu.services.LogService;

@SuppressWarnings("serial")
@WebServlet("/signOut")
public class SignOut extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		Account a = (Account) session.getAttribute("account");
		LogService.log("邮箱为" + a.getEmail() + "的用户退出登录", "用户离开");

		session = req.getSession();
		session.removeAttribute("account");
		resp.sendRedirect("signin.jsp");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
