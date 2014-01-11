package yuemu.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.core.JSONUtils;
import yuemu.dao.MusicDAO;
import yuemu.dao.manage.MusicManageDAO;
import yuemu.dao.statistics.DownloadStatisticsDAO;
import yuemu.dao.statistics.VisitStatisticsDAO;
import yuemu.po.Music;
import yuemu.po.Resource;

public class MusicService implements Service {
	MusicDAO dao = MusicDAO.getInstance();

	public JSONArray getLatest(int num, Long id) {
		List<Music> list = dao.searchTheLatestAfterSpecifiedId(num, id);
		if (list == null) {
			return null;
		}
		return JSONUtils.toJSONArray(list);
	}

	public JSONArray getByAccount(long id,int status, int pageNum, int pageSize) {
		List<Music> list = MusicManageDAO.getByAccountId(id,status, pageNum, pageSize);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject object = JSONUtils.toJSON(list.get(i));
			long resourceid = list.get(i).getResource().getId();
			long visitCount = VisitStatisticsDAO.countByResourceId(resourceid);
			long downloadCount = DownloadStatisticsDAO.countByResourceId(resourceid);
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
		List<Music> list =  MusicManageDAO.getByStatus(status, pageNum, pageSize);
		List<Resource> l = new ArrayList<Resource>();
		for(int i=0;i<list.size();i++) {
			l.add(list.get(i).getResource());
		}
		return l;
	}
	
	public int countByStatus(int status) {
		return (int)MusicManageDAO.countOfByStatus(status);
	}
}
