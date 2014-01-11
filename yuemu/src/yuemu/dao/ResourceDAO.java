package yuemu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import yuemu.jpa.JPAConnection;
import yuemu.jpa.JPAOperator;
import yuemu.po.Resource;
import yuemu.po.ResourceStatus;
import yuemu.po.ResourceType;

public class ResourceDAO {

	private final static ResourceDAO INSTANCE = new ResourceDAO();

	private ResourceDAO() {
	}

	/**
	 * 插入资源信息,返回插入对象ID
	 * 
	 * 应该直接插入具体的资源, 然后自动级联添加一个Resource. 
	 * 
	 * @param account
	 * @return
	 */
	@Deprecated
	public Long insert(Resource resource) {
		return (Long) JPAOperator.insert(resource);

	}

	/**
	 * 删除一个Resource, 实际上是将其状态置为不可用. 
	 */
	public void delete(Resource resource) {
		resource.setStatus(ResourceStatus.UNUSED);
		JPAOperator.update(resource);
	}

	/**
	 * 通过ID删除一个Resource, 实际上是将其状态置为不可用. 
	 * 
	 * @param id
	 */
	public void deleteById(Long id) {
		Resource resource = JPAOperator.find(id, Resource.class);
		resource.setStatus(ResourceStatus.UNUSED);
		JPAOperator.update(resource);
	}

	public Resource find(Long i) {
	Resource resource = JPAOperator.find(i, Resource.class);
	
		return resource;
	

	}
	/**
	 * 更新資源
	 * @param resource
	 */

	public void update(Resource resource) {
		JPAOperator.update(resource);
	}

	public static ResourceDAO getInstance() {
		return INSTANCE;
	}
	
	   
	/***
	 * 通过标题查询资源
	 */
	
	
	public List<Resource> getResourceByTitle(String keyWord ,ResourceType rt,int pageNum,int pageSize){
		
		String sql ="select r from Resource r where r.title like :keyWord AND r.type = :type";
		Map<String ,Object> map =new HashMap<String ,Object>();
		map.put("keyWord", "%"+keyWord+"%");
		map.put("type", rt);
		
		List<Resource> lr =JPAOperator.query(sql, map, Resource.class, pageNum, pageSize);
		
			return lr;
	}
	
	
	
	/**
	 * 通过标签查询资源
	 * @param tag
	 * @param rt
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Resource> getResourceByTag(String tag,ResourceType rt,int pageNum,int pageSize){
		String sql="select r from Resource  r where r.tag = :tag AND r.type = :type";
		Map<String ,Object> map =new HashMap<String, Object>();
		map.put("tag", tag);
		map.put("type",rt);
		
		List<Resource> lr=JPAOperator.query(sql, map, Resource.class, pageNum, pageSize);
	
		
		return lr;
		}
	
	/***
	 * 通過上傳時間查詢資源.
	 */
	
	public List<Resource> getResourceByUploadTime(ResourceType rt ,int pageNum,int pageSize){
		String sql="select r from Resource r where  r.type = :type order by r.uploadTime";
		Map<String ,Object> map =new HashMap<String ,Object>();
		
		map.put("type", rt);
		List<Resource> lr =JPAOperator.query(sql, map, Resource.class, pageNum, pageSize);
		return lr;
		
		
	}
	
	
	/***
	 * 通過訪問量查詢資源
	 */
	
	public List<Resource>  getResourceByVisitCount(ResourceType rt,int pageNum,int pageSize){
		String sql="select r from Resource r where r.type =:type order by r.visitCount";
		Map<String ,Object> map =new HashMap<String ,Object>();
		map.put("type", rt);
		List<Resource> lr=JPAOperator.query(sql, map, Resource.class, pageNum, pageSize);
		
		
		return lr;
	}
	
	/**搜索最近的num个各种资源, 返回的都是USING状态. 
	 * 
	 * @param num
	 * @return
	 */
	public List<Object> searchTheLatest(int from, int to) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			TypedQuery<Resource> query = em.createQuery(
					"select r from Resource r " +
					"where r.status = 0" +
					"order by r.uploadTime DESC", Resource.class);
			query.setFirstResult(from);
			query.setMaxResults(to - from + 1);
			List<Resource> resources = query.getResultList();
			return ResourceDAOTool.getConcreteResourcesFromAbstractOnes(resources);
		} finally {
			if(em != null && em.isOpen()) 
				em.close();
		}
	}
	
	/**查询最近的num个资源数据, 但是数据的时间在指定的id资源之前. 
	 * 
	 * @param num
	 * @param id
	 * @return
	 */
	public List<Object> searchTheLatestAfterSpecifiedId(int num, Long id) {
		List<Resource> list = ResourceDAOTool.searchTheLatestAfterSpecifiedId(num, id, Resource.class);
		return ResourceDAOTool.getConcreteResourcesFromAbstractOnes(list);
	}
	
	
	
	/**
	 * 通过路径查找到对应的资源,并返回.
	 */
	public Object getConcreteResourceBySourceURL(String path){
		String sql="select r from Resource r where r.sourceUrl = :path";
		Map<String ,Object> map =new HashMap<String ,Object>();
		
		map.put("path", path);
		
		Resource r = JPAOperator.querySingle(sql, map, Resource.class);
		return ResourceDAOTool.getConcreteResourceFromAbstractOne(r);
			
	}
	
	public Resource getResourceBySourceURL(String path) {
		String sql="select r from Resource r where r.sourceUrl = :path";
		Map<String ,Object> map =new HashMap<String ,Object>();
		
		map.put("path", path);
		
		Resource r = JPAOperator.querySingle(sql, map, Resource.class);
		
		return r;
	}
	
	public Resource getResourceByPreviewURL(String path) {
		String sql="select r from Resource r where r.previewUrl = :path";
		Map<String ,Object> map =new HashMap<String ,Object>();
		
		map.put("path", path);
		
		Resource r = JPAOperator.querySingle(sql, map, Resource.class);
		
		return r;
	}
	
	public long countOfAll() {
		String clause = "select count(r) from Resource r";
		return JPAOperator.querySingle(clause, Long.class);
	}
	
	
	public List<Resource> getAll(){
		
		String sql ="select r from Resource r ";
		
		return JPAOperator.query(sql, Resource.class, 1, Integer.MAX_VALUE/2);
		
		
	}
 	
}
