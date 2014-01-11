package yuemu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.configure.Configure;
import yuemu.po.Account;
import yuemu.po.ResourceType;
import yuemu.services.VisitStatisticsService;

@SuppressWarnings("serial")
@WebServlet("/topFeedByAccount")
public class TopForAccountFeed extends HttpServlet {

	VisitStatisticsService service = new VisitStatisticsService();

	private final int topNum = 10;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;UTF-8");

		String t = "all";
		if (req.getParameter("type") != null) {
			t = req.getParameter("type");
		}
		int type = checkType(t);

		if (req.getSession().getAttribute("account") != null) {
			Account account = (Account) req.getSession()
					.getAttribute("account");
			Long accountId = account.getId();
			JSONArray array = new JSONArray();
			switch (type) {
			case Configure.DOCUMENT:
				array = service.getTopForAccount(accountId, topNum,
						ResourceType.DOCUMENT);
				break;
			case Configure.IMAGE:
				array = service.getTopForAccount(accountId, topNum,
						ResourceType.IMAGE);
				break;
			case Configure.MUSIC:
				array = service.getTopForAccount(accountId, topNum,
						ResourceType.MUSIC);
				break;
			case Configure.VIDEO:
				array = service.getTopForAccount(accountId, topNum,
						ResourceType.VIDEO);
				break;
			case Configure.ALL:
				array = service.getTopForAccountAll(accountId, topNum);
			}

			JSONObject object = new JSONObject();
			if (array == null || array.length() == 0) {
				try {
					object.put("code", new Integer(-1));
					object.put("message", "wrong");
					object.put("data", array);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				try {
					object.put("code", new Integer(0));
					object.put("message", "ok");
					object.put("data", array);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			PrintWriter pw = resp.getWriter();
			pw.print(object);
			pw.close();
		}else {
			
		}
	}

	private int checkType(String type) {
		return Configure.typeOrderFromString(type);
	}
}
