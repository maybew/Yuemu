package yuemu.dao.manage;

import java.util.List;

import yuemu.po.Video;

public class VideoManageDAO {
	
	private VideoManageDAO() {}

	public static List<Video> getByStatus(
			int status, int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByStatus(
				status, Video.class, pageNum, pageSize);
	}
	
	/**某状态资源总数
	 * 
	 * @return
	 */
	public static long countOfByStatus(int status) {
		return ResourceManageDAOTool.countOfByStatus(status, Video.class);
	}
	
	public static List<Video> getByAccountId(
			long accountId, int status, int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByAccountId(
				accountId, status, Video.class, pageNum, pageSize);
	}
	
	public static long countOfByAccountId(
			long accountId, int status) {
		return ResourceManageDAOTool.countOfByAccountId(
				accountId, status, Video.class);
	}
	
}
