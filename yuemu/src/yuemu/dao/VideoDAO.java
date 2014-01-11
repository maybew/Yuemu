package yuemu.dao;

import java.util.List;

import yuemu.jpa.JPAOperator;
import yuemu.jpa.QueryBuffer;
import yuemu.po.Resource;
import yuemu.po.Video;

public class VideoDAO {

	private final static VideoDAO INSTANCE = new VideoDAO();

	private VideoDAO() {
	}

	/**
	 * 插入视频信息,返回插入对象ID
	 * 
	 * @param account
	 * @return
	 */
	public Long insert(Video video) {
		return (Long) JPAOperator.insert(video);

	}

	/**
	 * 通过视频对象 删除视频信息
	 */
	@Deprecated
	public void delete(Video video) {
		Resource resource = JPAOperator.find(video.getResource().getId(),
				Resource.class);

		ResourceDAO dao = ResourceDAO.getInstance();

		dao.delete(resource);

	}

	/**
	 * 通过Id删除视频 s
	 * 
	 * @param id
	 */
	@Deprecated
	public void deleteById(Long id) {
		Resource resource = JPAOperator.find(id, Resource.class);
		ResourceDAO dao = ResourceDAO.getInstance();
		dao.delete(resource);
	}

	public Video find(Long i) {
		return JPAOperator.find(i, Video.class);

	}
	
	public Video findByResourceId(long id) {
		String clause = "select v from Video v" +
				" where v.resource.id = " + id;
		return JPAOperator.querySingle(clause, Video.class);
	}

	public void update(Video video) {
		JPAOperator.update(video);
	}

	public static VideoDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * 搜索最近的num个视频资源
	 * 
	 * @param num
	 * @return
	 */
	public List<Video> searchTheLatest(int from, int to) {
		String queryClause = "select v from Video v "
				+ " where v.resource.status = 0"
				+ " order by v.resource.uploadTime DESC";
		QueryBuffer<Video> buffer = QueryBuffer.newInstance(queryClause,
				Video.class);
		buffer.setOffset(from);
		buffer.setPageSize(to - from + 1);
		return buffer.query();
	}

	/**
	 * 查询最近的num个视频数据, 但是数据的时间在拥有的指定的id资源之前.
	 * 
	 * @param num
	 * @param id
	 * @return
	 */
	public List<Video> searchTheLatestAfterSpecifiedId(int num, Long id) {
		return ResourceDAOTool.searchTheLatestAfterSpecifiedId(num, id,
				Video.class);
	}

	public List<Video> getAll() {
		String sql = "select r from Video r ";

		return JPAOperator.query(sql, Video.class, 1, Integer.MAX_VALUE / 2);
	}
}
