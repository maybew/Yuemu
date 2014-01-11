package yuemu.services;

import java.util.List;

import org.json.JSONArray;

import yuemu.po.Resource;

public interface Service {
	
	public JSONArray getLatest(int num, Long id);

	public JSONArray getByAccount(long id, int status, int pageNum, int pageSize);

	public List<Resource> getByStatus(int status, int pageNum, int pageSize);

	public int countByStatus(int status);
}
