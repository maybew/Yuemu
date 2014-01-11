package yuemu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuemu.dao.AccountDAO;
import yuemu.po.Account;
import yuemu.po.UserType;
import yuemu.services.AccountService;
import yuemu.services.ResourceService;

/**
 * Servlet implementation class Manage
 */
@SuppressWarnings("serial")
@WebServlet("/manage")
public class Manage extends HttpServlet {

	ResourceService service = new ResourceService();
	AccountService as = new AccountService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		Long id = Long.parseLong(request.getParameter("id"));

		if (action.equals("publish")) {
			service.publish(id);
		} else if (action.equals("remove")) {
			service.delete(id);
		} else if (action.equals("user_tonormal")) {
			AccountDAO ad = AccountDAO.getInstance();
			Account a = ad.find(id);
			a.setUserType(UserType.CUSTOMER);
			ad.update(a);
		} else if (action.equals("user_toadmin")) {
			AccountDAO ad = AccountDAO.getInstance();
			Account a = ad.find(id);
			a.setUserType(UserType.ADMIN);
			ad.update(a);
		} else if (action.equals("user_remove")) {
			AccountDAO ad = AccountDAO.getInstance();
			Account a = ad.find(id);
			ad.delete(a);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
