package yuemu.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.configure.Configure;
import yuemu.services.SearchService;

@SuppressWarnings("serial")
@WebServlet("/search")
public class Search extends HttpServlet {
	SearchService service = new SearchService();
	private final int pageSize = 10;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;UTF-8");
		String type = "all";
		if (req.getParameter("type") != null)
			type = req.getParameter("type");
		int typeID = checkType(type);

		int pageNum = 1;
		if (req.getParameter("current") != null)
			pageNum = Integer.parseInt(req.getParameter("current"));

		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();

		if (req.getParameter("keyword") != null) {
			String k = req.getParameter("keyword");
			if (k == "") {
				object = getJSON(new JSONArray());
			} else {
				String[] keyword = k.split(" +");
				array = service.SearchBoth(Arrays.asList(keyword), typeID,
						pageNum, pageSize);
				object = getJSON(array);
			
			}
		} else if (req.getParameter("title") != null) {
			String t = req.getParameter("title");
			// t = setCharacter(t);
			if (t == "") {
				object = getJSON(new JSONArray());
			} else {
				String[] title = t.split(" +");
				array = service.SearchByTitles(Arrays.asList(title), typeID,
						pageNum, pageSize);
				object = getJSON(array);
			
			}
		} else if (req.getParameter("tag") != null) {
			String s = req.getParameter("tag");
			// s = setCharacter(s);
			if (s == "") {
				object = getJSON(new JSONArray());
			} else {
				String[] sarray = s.split(" +");
				array = service.SearchByTags(Arrays.asList(sarray), typeID,
						pageNum, pageSize);
				object = getJSON(array);
			
			}
		} else {
			// 处理title和tags都没有的情况
			object = getJSON(new JSONArray());
			
		}

		PrintWriter pw = resp.getWriter();
		pw.print(object);
		pw.close();
	}

	/**
	 * 将获得的jsonArray传到object中
	 * 
	 * @param array
	 * @return
	 */
	private JSONObject getJSON(JSONArray array) {
		JSONObject object = new JSONObject();
		if (array.length() == 0) {
			try {
				object.put("code", "-1");
				object.put("message", "wrong");
				object.put("data", array);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println(array.toString());
				object.put("code", "0");
				object.put("message", "ok");
				object.put("data", array);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	/**
	 * 用于将前台的type由string转换为int
	 * 
	 * @param type
	 * @return
	 */
	private int checkType(String type) {
		return Configure.typeOrderFromString(type);
	}
}
