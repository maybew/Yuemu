package yuemu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuemu.services.AccountService;
import yuemu.services.LogService;

@SuppressWarnings("serial")
@WebServlet("/signIn")
public class SignIn extends HttpServlet {
	AccountService service = new AccountService();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		
		if (req.getParameter("email") != null) {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			HttpSession session = req.getSession();
			if (service.vertify(email, password)) {
				LogService.log("邮箱为"+email+"的用户登录成功", "用户登录");
				if (req.getParameter("url") == null) {
					session.setAttribute("account", service.findByEmail(email));
					resp.sendRedirect("my.jsp");
				} else {
					String url = req.getParameter("url");
					resp.sendRedirect(url);
				}
			} else {
				// 当账户或密码错误时处理
				LogService.log("邮箱为"+email+"的用户登录失败", "用户登录");
				resp.sendRedirect("signin.jsp");
			}
		} else {

		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
