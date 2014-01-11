package yuemu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuemu.services.AccountService;

@SuppressWarnings("serial")
@WebServlet("/checkEmail")
public class CheckEmail extends HttpServlet {
	
	AccountService service = new AccountService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		
		PrintWriter writer = resp.getWriter();
		if (service.check(email)) {
			
			writer.write("ok");
			
		} else {
			writer.write("duplicataion");
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
