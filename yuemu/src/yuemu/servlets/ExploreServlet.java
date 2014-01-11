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

import yuemu.services.ExploreService;

@SuppressWarnings("serial")
@WebServlet("/explore")
public class ExploreServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=UTF-8");
		ExploreService es = new ExploreService();

		String action = req.getParameter("action");
		String dir = req.getParameter("dir");
		JSONObject object = new JSONObject();
		if (action.equals("list")) {
			JSONArray array = es.listAllItem(dir);

			if (array != null) {

				try {
					object.put("code", 0);
					object.put("message", "ok");
					object.put("data", array);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					object.put("code", -1);
					object.put("message", "ok");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} else if (action.equals("delete")) {
			try {
				if(dir.contains("path=")) {
					dir = dir.split("path=")[1];
				}
				object.put("code", es.deleteItem(dir) ? 0 : -1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("update")) {
			try {
				object.put("code", es.updateItem(dir, req) ? 0 : -1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintWriter pw = resp.getWriter();
		pw.print(object);
		pw.close();
	}

}
