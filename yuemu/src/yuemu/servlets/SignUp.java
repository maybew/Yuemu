package yuemu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuemu.services.AccountService;
import yuemu.services.LogService;

@SuppressWarnings("serial")
@WebServlet("/signUp")
public class SignUp extends HttpServlet {

	AccountService service = new AccountService();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		if (req.getParameter("email") != null) {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String name = req.getParameter("name");
			int sex = Integer.parseInt(req.getParameter("sex"));
			String birthday = req.getParameter("birthday");
			String occupation = req.getParameter("occupation");
			String contact = req.getParameter("contact");
			String address = req.getParameter("address");
			if (service.insert(email, password, name, sex, birthday,
					occupation, contact, address)){
				resp.sendRedirect("signin.jsp");
				LogService.log("邮箱为"+email+"的用户注册成功", "用户注册");}
			else  {
				LogService.log("邮箱为"+email+"的用户注册失败", "用户注册");
				// 处理注册出错;
				}
		} 
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
