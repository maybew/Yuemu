package yuemu.dao.manage;

import java.util.List;

import yuemu.jpa.JPAOperator;
import yuemu.jpa.QueryBuffer;
import yuemu.po.ResourceStatus;

public class ResourceManageDAOTool {

	public static <E> List<E> getByStatus(
			int status, Class<E> entityClass, 
			int pageNum, int pageSize) {
		String clause = getByStatusClause(status, entityClass);
		QueryBuffer<E> queryBuffer = QueryBuffer.newInstance(entityClass);
		queryBuffer.setClause(clause);
		queryBuffer.setPageNum(pageNum);
		queryBuffer.setPageSize(pageSize);
		return queryBuffer.query();
	}
//	public static <E> List<E> getByCheckingAndUsing(int status,Class<E> entityClass,
//			int pageNum,int pageSize){
//		String clause = getByCheckingAndUsingClause(status, entityClass);
//		QueryBuffer<E> queryBuffer = QueryBuffer.newInstance(entityClass);
//		queryBuffer.setClause(clause);
//		queryBuffer.setPageNum(pageNum);
//		queryBuffer.setPageSize(pageSize);
//		return queryBuffer.query();
//		
//	}
			
	
	private static <E> String getByStatusClause(int status, Class<E> entityClass) {
		String clause = "select r from " + entityClass.getSimpleName() + " r "; 
		if(status != ResourceStatus.ALL)
			clause += "where r.resource.status = " + status;
		return clause;
	}
	
//	private static <E> String getByCheckingAndUsingClause(int status ,Class<E>entityClass){
//		String clause ="select r from"+entityClass.getSimpleName()+"r";
//		clause+="where (r.resource.status= "+ResourceStatus.CHECKING +" OR r.resource.status="+ResourceStatus.USING+")";
//		return clause;
//	}
	
	public static <E> long countOfByStatus(int status, Class<E> entityClass) {
		String clause = countOfByStatusClause(status, entityClass);
		return JPAOperator.querySingle(clause, Long.class);
	}

	// Y.L. Wu
	private static <E> String countOfByStatusClause(
			int status, Class<E> entityClass) {
		String selectClause = "select count(r) from " + 
			entityClass.getSimpleName() + " r " + " where r.resource.status != " + ResourceStatus.UNUSED;
		if(status != ResourceStatus.ALL)
			selectClause += " and r.resource.status = " + status;
		return selectClause;
	}
	
	public static <E> List<E> getByAccountId(
			long accountId, int status, Class<E> entityClass, 
			int pageNum, int pageSize) {
		String clause = getByAccountIdClause(accountId, status, entityClass);
		QueryBuffer<E> queryBuffer = QueryBuffer.newInstance(entityClass);
		queryBuffer.setClause(clause);
		queryBuffer.setPageNum(pageNum);
		queryBuffer.setPageSize(pageSize);
		return queryBuffer.query();
	}
	
	// Y.L. Wu
	private static <E> String getByAccountIdClause(
			long accountId, int status, Class<E> entityClass) {
		String clause = "select r from " + entityClass.getSimpleName() + " r " +
				"where r.resource.uploader.id = " + accountId + " AND r.resource.status <> " + ResourceStatus.UNUSED ;
		if(status != ResourceStatus.ALL) 
			clause += " AND r.resource.status "+status;
		return clause;
	}	

	public static <E> long countOfByAccountId(
			long accountId, int status, Class<E> entityClass) {
		String clause = countOfByAccountIdClause(accountId, status, entityClass);
		return JPAOperator.querySingle(clause, Long.class);
	}
	
	private static <E> String countOfByAccountIdClause(
			long accountId, int status, Class<E> entityClass) {
		String clause = "select count(r) from " + entityClass.getSimpleName() + " r " +
			"where r.resource.uploader.id = " + accountId + " AND r.resource.status <> " + ResourceStatus.UNUSED;
		if(status != ResourceStatus.ALL)
			clause += " AND r.resource.status = " + status;
		return clause;
	}
	
}
