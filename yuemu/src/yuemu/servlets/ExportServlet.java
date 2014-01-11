package yuemu.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuemu.configure.Configure;
import yuemu.core.ProjectException;
import yuemu.data.io.DatabaseMove;
import yuemu.services.DatabaseService;
import yuemu.services.LogService;

@SuppressWarnings("serial")
@WebServlet("/export")
public class ExportServlet extends HttpServlet {

	public static void compress(ZipOutputStream out, File f, String path)
			throws IOException {
		if (f.isFile()) {
			ZipEntry entry = new ZipEntry(path);
			out.putNextEntry(entry);

			InputStream in = new FileInputStream(f);
			byte[] buf = new byte[4096];
			for (int n; (n = in.read(buf, 0, buf.length)) != -1;) {
				out.write(buf, 0, n);
			}
			in.close();

			out.closeEntry();
		} else {
			ZipEntry entry = new ZipEntry(path + "/");
			out.putNextEntry(entry);
			out.closeEntry();

			for (File sub : f.listFiles()) {
				compress(out, sub,
						(path.equals("") ? "" : path + "/") + sub.getName());
			}
		}
	}

	public static void extract(ZipFile in, String root) throws IOException {
		Enumeration<?> e = in.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			if (entry.isDirectory()) {
				File f = new File(root + entry.getName());
				f.mkdirs();
				continue;
			}

			File f = new File(root + entry.getName());
			f.getParentFile().mkdirs();
			OutputStream o = new BufferedOutputStream(new FileOutputStream(f));
			InputStream i = new BufferedInputStream(in.getInputStream(entry));
			byte[] buf = new byte[4096];
			for (int n; (n = i.read(buf, 0, buf.length)) != -1;) {
				o.write(buf, 0, n);
			}
			i.close();
			o.close();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("type").equals("data")) {
			System.out.println("正在导出资源文件.");
			
			response.setContentType("application/x-zip-compressed");
			response.setHeader("Content-Disposition",
					"attachment; filename=data.zip");

			ZipOutputStream out = new ZipOutputStream(
					response.getOutputStream());
			for (File f : new File(Configure.ROOTPATH).listFiles()) {
				if (f.getName().equals("resource")
						|| f.getName().equals("resource_mirror")) {
					compress(out, f, f.getName());
				}
			}
			out.close();
			LogService.log("导出资源数据", "系统");
			
			System.out.println("导出资源文件完毕.");
		} else {
			System.out.println("正在导出数据库内容.");
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment; filename=db.dat");

			
			ServletOutputStream out = response.getOutputStream();
			try {
				DatabaseMove.databaseWriteTo(out);
				LogService.log("导出数据库", "系统");
			} finally {
				out.close();
			}
			
			System.out.println("导出数据库内容完毕.");
		}
	}

	public static void dataHandler(File saveFile) {
		try {
			ZipFile in = new ZipFile(saveFile);
			extract(in, Configure.ROOTPATH + "/");
			saveFile.delete();
			LogService.log("导入资源数据", "系统");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dbHandler(File saveFile) {
		System.out.println("导入到数据库, From: " + saveFile.getAbsolutePath());
		
		InputStream in = null;
		try {
			//首先清空
			DatabaseService.emptyDatabase();
			
			in = new FileInputStream(saveFile);
			DatabaseMove.writeToDatabase(in);
			DatabaseService.insertRootAdministrator();
			LogService.log("导入数据库", "系统");
		} catch (FileNotFoundException e) {
			throw new ProjectException(e);
		} finally {
			try {
				if(in != null)
					in.close();
			} catch(Exception ex) {
				throw new ProjectException(ex);
			}
		}
		saveFile.delete();

		System.out.println("导入到数据库完毕");
	}

}
