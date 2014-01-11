package yuemu.dao;

import java.util.List;

import yuemu.jpa.JPAOperator;
import yuemu.jpa.QueryBuffer;
import yuemu.po.Image;
import yuemu.po.Resource;

public class ImageDAO {

	private final static ImageDAO INSTANCE = new ImageDAO();

	private ImageDAO() {
	}

	/**
	 * 插入图片信息,返回插入对象ID
	 * 
	 * @param account
	 * @return
	 */
	public Long insert(Image image) {
		return (Long) JPAOperator.insert(image);

	}

	/**
	 * 通过账户对象 删除账户信息
	 */
	@Deprecated
	public void delete(Image image) {
		Resource resource = JPAOperator.find(image.getResource().getId(),
				Resource.class);
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
		JPAOperator.deleteById(id, Image.class);
	}

	public Image find(Long i) {
		return JPAOperator.find(i, Image.class);

	}
	
	public Image findByResourceId(long resourceId) {
		String clause = "select i from Image i " +
				" where i.resource.id = " + resourceId;
		return JPAOperator.querySingle(clause, Image.class);
	}

	public void update(Image image) {
		JPAOperator.update(image);
	}

	public static ImageDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * 搜索最近的num个图片资源
	 * 
	 * @param num
	 * @return
	 */
	public List<Image> searchTheLatest(int from, int to) {
		String queryClause = "select i from Image i "
				+ " where i.resource.status = 0"
				+ " order by i.resource.uploadTime DESC";
		QueryBuffer<Image> buffer = QueryBuffer.newInstance(queryClause,
				Image.class);
		buffer.setOffset(from);
		buffer.setPageSize(to - from + 1);
		return buffer.query();
	}

	/**
	 * 查询最近的num个图片资源, 但是数据的时间在指定的id资源之前.
	 * 
	 * @param num
	 * @param id
	 * @return
	 */
	public List<Image> searchTheLatestAfterSpecifiedId(int num, Long id) {
		return ResourceDAOTool.searchTheLatestAfterSpecifiedId(num, id,
				Image.class);
	}

	public List<Image> getAll() {
		String sql = "select r from Image r ";

		return JPAOperator.query(sql, Image.class, 1, Integer.MAX_VALUE / 2);
	}
}
