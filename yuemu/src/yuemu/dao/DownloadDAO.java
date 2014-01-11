package yuemu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuemu.jpa.JPAOperator;
import yuemu.po.Download;
import yuemu.po.Resource;
import yuemu.po.ResourceType;

public class DownloadDAO {
	
	
private final static DownloadDAO INSTANCE = new DownloadDAO();
	
	private DownloadDAO() {}
	  
	
	public static DownloadDAO getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * 下载一个资源插入一个下载信息
	 * @param download
	 * @return
	 */
	public Long insert(Download download) {
		return (Long) JPAOperator.insert(download);
	}

	/**
	 * 通过Id查找下载信息.
	 * @param i
	 * @return
	 */
	public Download find(Long i) {
		return JPAOperator.find(i, Download.class);

	}

	/**
	 * 通过下载次数,返回资源
	 */
	
	public List<Resource> findbyDownloadCount(ResourceType rt ,int pageNum ,int pageSize){
		
		String sql="select r from Download d ,Resource r where d.resource.id=r.id AND r.type =:rt order by count(d.id) DESC ";
		Map<String ,Object> map =new HashMap<String ,Object>();
		
		map.put("rt", rt);
		
		List<Resource> lr =JPAOperator.query(sql, map, Resource.class, pageNum, pageSize);
		 
		
		return lr;
		
		}


	public List<Download> getAll() {
		String sql = "select r from Download r ";

		return JPAOperator.query(sql, Download.class, 1, Integer.MAX_VALUE / 2);
	}
	
	
	

}
