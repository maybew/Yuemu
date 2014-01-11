package yuemu.dao.manage;

import java.util.List;

import yuemu.po.Document;

public class DocumentManageDAO {
	
	private DocumentManageDAO() {}

	public static List<Document> getByStatus(
			int status, int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByStatus(
				status, Document.class, pageNum, pageSize);
	}
	
	/**某状态资源总数
	 * 
	 * @return
	 */
	public static long countOfByStatus(int status) {
		return ResourceManageDAOTool.countOfByStatus(status, Document.class);
	}
	
	public static List<Document> getByAccountId(
			long accountId, int status, 
			int pageNum, int pageSize) {
		return ResourceManageDAOTool.getByAccountId(
				accountId, status, Document.class, pageNum, pageSize);
	}
	
	public static long countOfByAccountId(
			long accountId, int status) {
		return ResourceManageDAOTool.countOfByAccountId(
				accountId, status, Document.class);
	}
	
}
