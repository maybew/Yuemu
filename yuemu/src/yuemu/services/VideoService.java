package yuemu.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.core.JSONUtils;
import yuemu.dao.VideoDAO;
import yuemu.dao.manage.VideoManageDAO;
import yuemu.dao.statistics.DownloadStatisticsDAO;
import yuemu.dao.statistics.VisitStatisticsDAO;
import yuemu.po.Resource;
import yuemu.po.Video;

public class VideoService implements Service {
	VideoDAO dao = VideoDAO.getInstance();

	public JSONArray getLatest(int num, Long id) {
		List<Video> list = dao.searchTheLatestAfterSpecifiedId(num, id);
		if(list == null) {
			return null;
		}
		return JSONUtils.toJSONArray(list);
	}

	public JSONArray getByAccount(long id,int status, int pageNum,int pageSize){
		List<Video> list = VideoManageDAO.getByAccountId(id,status, pageNum, pageSize);
		JSONArray array = new JSONArray();
		long visitCount = 0l;
		long downloadCount = 0l;
		for (int i = 0; i < list.size(); i++) {
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
		List<Video> list = VideoManageDAO.getByStatus(status, pageNum, pageSize);
		List<Resource> l = new ArrayList<Resource>();
		for(int i=0;i<list.size();i++) {
			l.add(list.get(i).getResource());
		}
		return l;
	}
	
	public int countByStatus(int status) {
		return (int) VideoManageDAO.countOfByStatus(status);
	}
}
