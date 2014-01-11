package yuemu.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.po.Account;
import yuemu.services.LogService;
import yuemu.services.UploadService;

@SuppressWarnings("serial")
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
	String storeName;
	UploadService us = new UploadService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("enter");
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (isMultipart) {

			System.out.println("upload");

			// get upload file list
			List<FileItem> fileList = null;
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("utf-8");

			try {
				fileList = upload.parseRequest(req);
			} catch (FileUploadException ex) {
				ex.printStackTrace();
				return;
			}

			storeName = us.processUploadFile(fileList);

			resp.getWriter().write(storeName);

		} else {
			Account user = (Account) req.getSession().getAttribute("account");
			if (user == null)
				return;
			resp.setContentType("application/json; charset=UTF-8");

			String email = user.getEmail();
			System.out.println(email);
			us.insertResourceInfo(req, email);
			JSONObject object = new JSONObject();
			try {
				object.put("code", new Integer(0));
				LogService.log("邮箱为" + user.getEmail() + "的用户上传了一些资源", "资源上传");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			PrintWriter pw = resp.getWriter();
			pw.print(object);
			pw.close();
		}

	}
}
