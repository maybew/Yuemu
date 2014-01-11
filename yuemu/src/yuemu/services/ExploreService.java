package yuemu.services;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.configure.Configure;
import yuemu.core.JSONUtils;
import yuemu.dao.DAOFactory;
import yuemu.dao.ResourceDAO;
import yuemu.po.Resource;

public class ExploreService {

	public boolean deleteItem(String dir) {
System.out.println("delete  " + dir);
		String relativePath = dir;
		String absoPath = Configure.ROOTPATH + relativePath;
		File root = new File(absoPath);
		if (root.isFile()) {
			try {
				Object object = DAOFactory.getResourceDAO().getConcreteResourceBySourceURL(
						relativePath);
				if(object != null)
				DAOFactory.getResourceDAO().delete(
						Resource.getResourceFromConcreteOne(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
			root.delete();

		} else if (root.isDirectory()) {
			File[] files = root.listFiles();
			for (File file : files) {
				this.deleteItem(dir + "/" + file.getName());
			}
			root.delete();
		}

		return true;
	}

	public boolean updateItem(String dir, HttpServletRequest req) {
		try {
			String relativePath = Configure.RESOURCEPATH + dir;
			Object object = DAOFactory.getResourceDAO().getConcreteResourceBySourceURL(
					relativePath);
			Resource resource = Resource.getResourceFromConcreteOne(object);
			resource.setTitle(req.getParameter("title"));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public JSONArray listAllItem(String dir) {
		JSONArray result = new JSONArray();

		String relativePath = Configure.RESOURCEPATH + dir;
		String absoPath = Configure.ROOTPATH + relativePath;
		File root = new File(absoPath);

		File[] items = root.listFiles();
		for (File file : items) {
			if (file.isDirectory()) {
				JSONObject object = new JSONObject();
				try {
					object.put("type", "dir");
					object.put("name", file.getName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.put(object);
			} else {
				String tempPath = relativePath + "/" + file.getName();
				Object object = null;
				try {
					object = DAOFactory.getResourceDAO().getConcreteResourceBySourceURL(
							tempPath);
				} catch (Exception e) {
					continue;
				}
				JSONObject jobject = JSONUtils.toJSON(object);
				result.put(jobject);
			}
		}
		return result;
	}

}
