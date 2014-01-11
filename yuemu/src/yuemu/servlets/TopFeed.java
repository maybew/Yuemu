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
import yuemu.po.ResourceType;
import yuemu.services.DownloadStatisticsService;
import yuemu.services.VisitStatisticsService;

@SuppressWarnings("serial")
@WebServlet("/topFeed")
public class TopFeed extends HttpServlet {

	VisitStatisticsService vservice = new VisitStatisticsService();
	DownloadStatisticsService dservice = new DownloadStatisticsService();
	private final int topNum = 10;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;UTF-8");
		String countType = "visit";
		if (req.getParameter("countType") != null) {
			countType = req.getParameter("countType");
		}

		String t = "all";
		if (req.getParameter("type") != null) {
			t = req.getParameter("type");
		}
		int type = checkType(t);

		JSONArray array = null;
		if (countType.equals("visit")) {
			switch (type) {
			case Configure.DOCUMENT:
				array = vservice.getTop(topNum, ResourceType.DOCUMENT);
				break;
			case Configure.IMAGE:
				array = vservice.getTop(topNum, ResourceType.IMAGE);
				break;
			case Configure.MUSIC:
				array = vservice.getTop(topNum, ResourceType.MUSIC);
				break;
			case Configure.VIDEO:
				array = vservice.getTop(topNum, ResourceType.VIDEO);
				break;
			case Configure.ALL:
				array = vservice.getTopForAll(topNum);
				break;
			}
		} else if (countType.equals("download")) {
			switch (type) {
			case Configure.DOCUMENT:
				array = dservice.getTop(topNum, ResourceType.DOCUMENT);
				break;
			case Configure.IMAGE:
				array = dservice.getTop(topNum, ResourceType.IMAGE);
				break;
			case Configure.MUSIC:
				array = dservice.getTop(topNum, ResourceType.MUSIC);
				break;
			case Configure.VIDEO:
				array = dservice.getTop(topNum, ResourceType.VIDEO);
				break;
			case Configure.ALL:
				array = dservice.getTopForAll(topNum);
				break;
			}
		} else {
			array = new JSONArray();
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
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private int checkType(String type) {
		return Configure.typeOrderFromString(type);
	}

}
