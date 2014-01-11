package yuemu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuemu.jpa.JPAOperator;
import yuemu.jpa.QueryBuffer;
import yuemu.po.Document;
import yuemu.po.Resource;

public class DocumentDAO {

	private final static DocumentDAO INSTANCE = new DocumentDAO();

	private DocumentDAO() {
	}

	public static DocumentDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * 插入音頻信息,返回插入对象ID
	 * 
	 * @param account
	 * @return
	 */
	public Long insert(Document document) {

		return (Long) JPAOperator.insert(document);

	}

	/**
	 * 通过账户对象 删除账户信息
	 */
	@Deprecated
	public void delete(Document document) {
		Document tempDocument = JPAOperator.find(document.getId(),
				Document.class);
	
		
		Resource resource =JPAOperator.find(tempDocument.getResource().getId(),Resource.class);
		
		
		ResourceDAO dao = ResourceDAO.getInstance();
		dao.delete(resource);
	}

	/**
	 * 通过Id删除账户
	 * 
	 * @param id
	 */
	@Deprecated
	public void deleteById(Long id) {
		JPAOperator.deleteById(id, Document.class);
	}

	public Document find(Long i) {

		return JPAOperator.find(i, Document.class);
	}
	
	public Document findByResourceId(Long id) {
		String clause = "select d from Documetn d " +
				"where d.resource.id" + id;
		return JPAOperator.querySingle(clause, Document.class);
	}

	public void update(Document document) {
		JPAOperator.update(document);
	}

	public List<Document> searchByAuthor(String author, int pageNum,
			int pageSize) {
		String sql = "select a from Document a where a.author = :author";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("author", author);
		List<Document> ld = JPAOperator.query(sql, map, Document.class,
				pageNum, pageSize);

		return ld;
	}
	
	///////////////////////////////////////////////////////////
	
	/**搜索最近的num个文档资源, FEED接口
	 * 
	 * @param num
	 * @return
	 */
	public List<Document> searchTheLatest(int from, int to) {
		String queryClause = "select d from Document d " +
				" where d.resource.status = 0" +
				" order by d.resource.uploadTime DESC";
		QueryBuffer<Document> buffer = QueryBuffer.newInstance(queryClause, Document.class);
		buffer.setOffset(from);
		buffer.setPageSize(to - from + 1);
		return buffer.query();
	}
	
	/**查询最近的num个文档, 但是数据的时间在拥有指定的id文档资源之前. FEED接口
	 * 
	 * @param num
	 * @param id
	 * @return
	 */
	public List<Document> searchTheLatestAfterSpecifiedId(int num, Long id) {
		return ResourceDAOTool.searchTheLatestAfterSpecifiedId(num, id, Document.class);
	}
	
	/////////////////////////////////////////////////////////////////
	
	/**
	 * 返回指定页码容量的所有的文档,
	 * @param pageNum 页码
	 * @param pageSize页容量
	 * @return
	 */
	
	public List<Document> getAll(int pageNum ,int pageSize){
		
		String sql ="select d from Document	d ";
		return JPAOperator.query(sql, Document.class, pageNum, pageSize);
		
	}
	
	public List<Document> getAll(){
		
		
		String sql="select d from Document d";
		return JPAOperator.query(sql, Document.class, 1, JPAOperator.MAX_PAGE_SIZE);
		
		
	}

}
