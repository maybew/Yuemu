package yuemu.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuemu.core.ProjectException;

public class QueryBuffer<E> {

	private String queryClause = "";

	private final Map<String, Object> parameters = new HashMap<String, Object>();
	
	private int offset = 0;

	private int pageNum = 1;

	private int pageSize = JPAOperator.MAX_PAGE_SIZE;

	private Class<E> entityClass;
	
	public QueryBuffer(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public QueryBuffer(String queryClause, Class<E> entityClass) {
		this.queryClause = queryClause;
		this.entityClass = entityClass;
	}
	
	public void setClause(String clause) {
		this.queryClause = clause;
	}

	public String getClause() {
		return queryClause;
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > JPAOperator.MAX_PAGE_SIZE)
			throw new ProjectException("Cannot over max page size value!");
		this.pageSize = pageSize;
	}

	public List<E> query() {
		return JPAOperator.query(queryClause, parameters, entityClass, offset, pageNum,
				pageSize);
	}

	public E querySingle() {
		return JPAOperator.querySingle(queryClause, parameters, entityClass);
	}

	@Override
	public String toString() {
		return "QueryBuffer [queryClause=" + queryClause + ", parameters="
				+ parameters + ", pageNum=" + pageNum + ", pageSize="
				+ pageSize + "]";
	}
	
	public static <E> QueryBuffer<E> newInstance(Class<E> entityClass) {
		return new QueryBuffer<E>(entityClass);
	}

	public static <E> QueryBuffer<E> newInstance(String clause,
			Class<E> entityClass) {
		return new QueryBuffer<E>(clause, entityClass);
	}

}
