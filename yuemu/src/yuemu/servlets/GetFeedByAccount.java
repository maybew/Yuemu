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
import yuemu.po.ResourceStatus;
import yuemu.services.DocumentService;
import yuemu.services.ImageService;
import yuemu.services.MusicService;
import yuemu.services.ResourceService;
import yuemu.services.Service;
import yuemu.services.VideoService;

@SuppressWarnings("serial")
@WebServlet("/feedByAccount")
public class GetFeedByAccount extends HttpServlet {

	ResourceService resourceservice = new ResourceService();
	DocumentService documentservice = new DocumentService();
	ImageService imageservice = new ImageService();
	VideoService videoservice = new VideoService();
	MusicService musicservice = new MusicService();
	private int pageSize = 12;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;utf-8");

		if (req.getSession().getAttribute("account") != null) {

			Long id = ((Account) req.getSession().getAttribute("account"))
					.getId();

			int pageNum = 1;
			if (req.getParameter("page") != null) {
				pageNum = Integer.parseInt(req.getParameter("page"));
			}

			int type = Configure.VIDEO;
			if (req.getParameter("type") != null) {
				String t = req.getParameter("type");
				type = checkType(t);
			}
			
			PrintWriter pw = null;
			try {
				pw = resp.getWriter();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 对应type类型调用对应的实现方法
			switch (type) {
			case Configure.DOCUMENT:
				getJsonLatest(documentservice, pw, id, pageNum, pageSize);
				break;
			case Configure.IMAGE:
				getJsonLatest(imageservice, pw, id, pageNum, pageSize);
				break;
			case Configure.MUSIC:
				getJsonLatest(musicservice, pw, id, pageNum, pageSize);
				break;
			case Configure.VIDEO:
				getJsonLatest(videoservice, pw, id, pageNum, pageSize);
				break;
			case Configure.ALL:
				getJsonLatest(resourceservice, pw, id, pageNum, pageSize);
				break;
			}
		} else {

		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private void getJsonLatest(Service service, PrintWriter pw, Long id,
			int pageNum, int pageSize) {
		JSONObject object = new JSONObject();
		JSONArray array = service.getByAccount(id, ResourceStatus.ALL, pageNum, pageSize);
		if (array == null || array.length() == 0) {
			try {
				object.put("code", new Integer(-1));
				object.put("message", "over");
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
		pw.print(object);
		pw.close();
	}

	/**
	 * 用于将前台传回的type转换为int类型
	 * 
	 * @param type
	 * @return
	 */
	private int checkType(String type) {
		return Configure.typeOrderFromString(type);
	}
}
