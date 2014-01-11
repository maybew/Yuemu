package yuemu.dao.manage;

import java.util.List;

import yuemu.po.Image;

public class ImageManageDAO {
	private ImageManageDAO() {}
	
	public static List<Image> getByStatus(
			int status, int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByStatus(
				status, Image.class, pageNum, pageSize);
	}
	
	/**某状态资源总数
	 * 
	 * @return
	 */
	public static long countOfByStatus(int status) {
		return ResourceManageDAOTool.countOfByStatus(status, Image.class);
	}
	
	public static List<Image> getByAccountId(
			long accountId, int status, 
			int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByAccountId(
				accountId, status, Image.class, pageNum, pageSize);
	}	
	
	public static long countOfByAccountId(
			long accountId, int status) {
		return ResourceManageDAOTool.countOfByAccountId(
				accountId, status, Image.class);
	}
	
}
