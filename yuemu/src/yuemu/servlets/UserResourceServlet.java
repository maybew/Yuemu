package yuemu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuemu.dao.DAOFactory;
import yuemu.po.Account;
import yuemu.po.Resource;
import yuemu.services.LogService;
import yuemu.services.ResourceService;

@SuppressWarnings("serial")
@WebServlet("/userResource")
public class UserResourceServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		ResourceService rs = new ResourceService();
		
		String action = req.getParameter("action");
		if(action == null || action.equals(""))
			return;
		if(action.equals("delete")) {
			String idString = req.getParameter("id");
			if(idString != null) {
				rs.delete(Long.valueOf(idString));
				HttpSession session =req.getSession();
				Account a=(Account)(session.getAttribute("account"));
				LogService.log("邮箱为"+a.getEmail()+"的用户删除了资源", "资源管理");
			}
		} else if (action.equals("update")) {
			String idString = req.getParameter("input_id");
			if(idString != null) {
				Resource res = DAOFactory.getResourceDAO().find(Long.valueOf(idString));
				if(res != null) {
					res.setTitle(req.getParameter("input_title"));
					res.setTag(req.getParameter("input_tag"));
					res.setDescription(req.getParameter("input_desc"));
					rs.update(res);
				}
			}
			resp.sendRedirect("/my.jsp");
		}
	}
	
}
