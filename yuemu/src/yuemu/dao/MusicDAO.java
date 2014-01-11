package yuemu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuemu.jpa.JPAOperator;
import yuemu.jpa.QueryBuffer;
import yuemu.po.Music;
import yuemu.po.Resource;

public class MusicDAO {

	private final static MusicDAO INSTANCE = new MusicDAO();

	private MusicDAO() {
	}

	/**
	 * 插入音頻信息,返回插入对象ID
	 * 
	 * @param account
	 * @return
	 */
	public Long insert(Music music) {
		return (Long)JPAOperator.insert(music);

	}

	/**
	 * 通过账户对象 删除账户信息
	 */
	@Deprecated
	public void delete(Music music) {
		
		Music tempMusic= JPAOperator.find(music.getId(),
				Music.class);
		
		Long id=tempMusic.getResource().getId();
		Resource resource=JPAOperator.find(id, Resource.class);

		ResourceDAO dao =ResourceDAO.getInstance();

		dao.delete(resource);
	}

	/**
	 * 通过Id删除账户
	 * 
	 * @param id
	 */
	@Deprecated
	public void deleteById(Long id) {
		JPAOperator.deleteById(id, Music.class);
	}

	public Music find(Long i) {
		return JPAOperator.find(i, Music.class);

	}
	
	public Music findByResourceId(long resourceId) {
		String clause = "select m from Music m " +
				" where m.resource.id = " + resourceId;
		return JPAOperator.querySingle(clause, Music.class);
	}

	public void update(Music music) {
		JPAOperator.update(music);
	}

	public List<Music> searchBySinger(String singer, int pageNum, int pageSize) {
		String sql = "select music from Music music where music.singer = :singer";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("singer", singer);
		List<Music> lm = JPAOperator.query(sql, map, Music.class, pageNum,
				pageSize);

		return lm;

	}

	public static MusicDAO getInstance() {
		return INSTANCE;
	}
	
	/**搜索最近的num个音乐资源
	 * 
	 * @param num
	 * @return
	 */
	public List<Music> searchTheLatest(int from, int to) {
		String queryClause = "select m from Music m " +
				" where m.resource.status = 0" +
				" order by m.resource.uploadTime DESC";
		QueryBuffer<Music> buffer = QueryBuffer.newInstance(queryClause, Music.class);
		buffer.setOffset(from);
		buffer.setPageSize(to - from + 1);
		return buffer.query();
	}

	/**查询最近的num个音乐, 但是数据的时间在拥有指定的id音乐资源之前. 
	 * 
	 * @param num
	 * @param id
	 * @return
	 */
	public List<Music> searchTheLatestAfterSpecifiedId(int num, Long id) {
		return ResourceDAOTool.searchTheLatestAfterSpecifiedId(num, id, Music.class);
	}

	public List<Music> getAll() {
		String sql ="select r from Music r ";
		
		return JPAOperator.query(sql, Music.class, 1, Integer.MAX_VALUE/2);
	}
}
