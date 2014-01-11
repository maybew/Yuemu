package yuemu.dao.manage;

import java.util.List;

import yuemu.po.Music;

public class MusicManageDAO {
	private MusicManageDAO() {}
	
	public static List<Music> getByStatus(
			int status, int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByStatus(
				status, Music.class, pageNum, pageSize);
	}
	
	/**某状态资源总数
	 * 
	 * @return
	 */
	public static long countOfByStatus(int status) {
		return ResourceManageDAOTool.countOfByStatus(status, Music.class);
	}
	
	public static List<Music> getByAccountId(
			long accountId, int status, 
			int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByAccountId(
				accountId, status, Music.class, pageNum, pageSize);
	}
	
	public static long countOfByAccountId(
			long accountId, int status) {
		return ResourceManageDAOTool.countOfByAccountId(
				accountId, status, Music.class);
	}
}
