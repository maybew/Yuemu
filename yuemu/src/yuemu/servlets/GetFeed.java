package yuemu.servlets;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.configure.Configure;
import yuemu.services.DocumentService;
import yuemu.services.ImageService;
import yuemu.services.MusicService;
import yuemu.services.ResourceService;
import yuemu.services.Service;
import yuemu.services.VideoService;

@SuppressWarnings("serial")
@WebServlet("/feed")
public class GetFeed extends HttpServlet {

	ResourceService resourceservice = new ResourceService();
	DocumentService documentservice = new DocumentService();
	ImageService imageservice = new ImageService();
	VideoService videoservice = new VideoService();
	MusicService musicservice = new MusicService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;UTF-8");
		if (req.getParameter("accountID") == null) {
			// 查询从last开始想上查询8个对象
			Long last = 10000000L;
			if (req.getParameter("last") != null
					&& !req.getParameter("last").equals("-1")) {
				last = Long.parseLong(req.getParameter("last"));
			}
			// 传递查询类型type
			int type = Configure.ALL;
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
				getJsonLatest(documentservice, pw, last);
				break;
			case Configure.IMAGE:
				getJsonLatest(imageservice, pw, last);
				break;
			case Configure.MUSIC:
				getJsonLatest(musicservice, pw, last);
				break;
			case Configure.VIDEO:
				getJsonLatest(videoservice, pw, last);
				break;
			case Configure.ALL:
				getJsonLatest(resourceservice, pw, last);
				break;
			}
		} else {
			
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		doGet(req, resp);
	}

	/**
	 * 该方法用于service调用自己的getLatest方法，从调用各个po的toJson方法
	 * 
	 * @param service
	 * @param pw
	 * @param id
	 */
	private void getJsonLatest(Service service, PrintWriter pw, Long id) {
		JSONObject object = new JSONObject();
		JSONArray array = service.getLatest(8, id);

		if (array.length() == 0) {
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
