package yuemu.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.core.JSONUtils;
import yuemu.dao.ImageDAO;
import yuemu.dao.manage.ImageManageDAO;
import yuemu.dao.statistics.DownloadStatisticsDAO;
import yuemu.dao.statistics.VisitStatisticsDAO;
import yuemu.po.Image;
import yuemu.po.Resource;

public class ImageService implements Service{
	ImageDAO dao = ImageDAO.getInstance();
	
	public JSONArray getLatest(int num, Long id) {
		List<Image> list = dao.searchTheLatestAfterSpecifiedId(num, id);
		if(list == null) {
			return null;
		}
		return JSONUtils.toJSONArray(list);
	}
	
	public JSONArray getByAccount(long id,int status, int pageNum,int pageSize){
		List<Image> list = ImageManageDAO.getByAccountId(id, status, pageNum, pageSize);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			long visitCount = 0l;
			long downloadCount = 0l;
			JSONObject object = JSONUtils.toJSON(list.get(i));
			long resourceid = list.get(i).getResource().getId();
			visitCount = VisitStatisticsDAO.countByResourceId(resourceid);
			downloadCount = DownloadStatisticsDAO.countByResourceId(resourceid);
			try {
				object.put("visit", visitCount);
				object.put("download", downloadCount);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(object);
		}
		return array;
	}
	
	public List<Resource> getByStatus(int status, int pageNum, int pageSize) {
		List<Image> list =	ImageManageDAO.getByStatus(status, pageNum, pageSize);
		List<Resource> l = new ArrayList<Resource>();
		for(int i=0;i<list.size();i++) {
			l.add(list.get(i).getResource());
		}
		return l;
	}

	public int countByStatus(int status) {
		return (int) ImageManageDAO.countOfByStatus(status);
	}
	
	
}
