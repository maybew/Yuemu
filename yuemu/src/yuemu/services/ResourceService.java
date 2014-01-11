package yuemu.services;

import java.util.List;

import org.json.JSONArray;

import yuemu.core.JSONUtils;
import yuemu.dao.ResourceDAO;
import yuemu.po.Resource;
import yuemu.po.ResourceStatus;

public class ResourceService implements Service{
	ResourceDAO dao = ResourceDAO.getInstance();
	
	public JSONArray getLatest(int num, Long id) {
		List<Object> list = dao.searchTheLatestAfterSpecifiedId(num, id);
		if(list == null) {
			return null;
		}
		return JSONUtils.toJSONArray(list);
	}
	
	public JSONArray getByAccount(long id,int Status, int pageNum,int pageSize){
		return null;
	}
	
	public List<Resource> getByStatus(int status, int pageNum, int pageSize) {
		return null;
	}
	
	public int countByStatus(int status) {
		return 0;
	}
	
	public Resource getById(long id){
		return dao.find(id);
	}
	
	public void publish(Long id){
		Resource resource = dao.find(id);
		if(resource.getStatus()==ResourceStatus.UNUSED){
			;
		} else {
			resource.setStatus(ResourceStatus.USING);
			dao.update(resource);
		}
	}
	
	public void delete(Long id){
		Resource resource = dao.find(id);
		if(resource.getStatus()==ResourceStatus.UNUSED){
			
		} else {
			resource.setStatus(ResourceStatus.UNUSED);
			dao.update(resource);
		}
	}
	
	public void update(Resource res) {
		dao.update(res);
	}
}
