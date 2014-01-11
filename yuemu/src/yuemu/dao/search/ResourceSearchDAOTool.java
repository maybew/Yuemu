package yuemu.dao.search;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import yuemu.jpa.JPAConnection;
import yuemu.po.Resource;

public class ResourceSearchDAOTool {

	/**在指定的具体资源实体内搜索, 要求搜索的结果的标题包含所有关键字. 
	 * 其中支持的类型可谓Document, Video, Music, Image和Resource; 
	 * 
	 * @param titles
	 * @param pageNum
	 * @param pageSize
	 * @param entityClass
	 * @return
	 */
	public static <E> List<E> searchByTitleKeywordsJoiningWithAnd(
			Collection<String> titles, int pageNum, int pageSize, 
			Class<E> entityClass) {
		String queryClause = getTitleSearchQueryClause(entityClass, titles.size());
		return searchByKeywordsWithLikeParameters(
				queryClause, titles, pageNum, pageSize, entityClass);
		
	}
	
	/**在指定的具体资源实体内搜索, 要求搜索的结果的标签中包含所有关键字. 
	 * 其中支持的类型可谓Document, Video, Music, Image和Resource; 
	 * 
	 * @param tags
	 * @param pageNum
	 * @param pageSize
	 * @param entityClass
	 * @return
	 */
	public static <E> List<E> searchByTagKeywordsJoiningWithAnd(
			Collection<String> tags, int pageNum, int pageSize, 
			Class<E> entityClass) {
		String queryClause = getTagSearchQueryClause(entityClass, tags.size());
		return searchByKeywordsWithLikeParameters(
				queryClause, tags, pageNum, pageSize, entityClass);
	}
	
	public static <E> List<E> searchByKeywordsJoiningWithAnd(
			Collection<String> keywords, int pageNum, int pageSize, 
			Class<E> entityClass) {
		String queryClause = getKeywordsSearchQueryClause(entityClass, keywords.size());
		return searchByKeywordsWithLikeParameters(
				queryClause, keywords, pageNum, pageSize, entityClass);
	}
	
	private static <E> List<E> searchByKeywordsWithLikeParameters(
			String queryClause, 
			Collection<String> keywords, int pageNum, int pageSize, 
			Class<E> entityClass
			) {
		if(keywords == null || keywords.size() == 0)
			return Collections.emptyList();
		
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			TypedQuery<E> query = em.createQuery(queryClause, entityClass);
			int i = 1;
			for(String title: keywords) {
				query.setParameter(i, "%" + title + "%");
				++i;
			}
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.getResultList();
		} finally {
			if(em != null && em.isOpen())
				em.close();
		}
	}
	
	private static String getTitleSearchQueryClause(Class<?> entityClass, int size) {
		StringBuilder clauseBuffer = new StringBuilder("select r from " + 
				entityClass.getSimpleName() + " r where ");
		if(entityClass == Resource.class) {
			for(int i = 1; i <= size; ++i) {
				clauseBuffer.append(" r.title like ?" + i + " AND ");
			}
			clauseBuffer.append(" r.status = 0");
		} else {
			for(int i = 1; i <= size; ++i) {
				clauseBuffer.append(" r.resource.title like ?" + i + " AND ");
			}
			clauseBuffer.append(" r.resource.status = 0");
		}
		return clauseBuffer.toString();
	}
	
	private static String getTagSearchQueryClause(Class<?> entityClass, int size) {
		StringBuilder clauseBuffer = new StringBuilder("select r from " + 
				entityClass.getSimpleName() + " r where ");
		if(entityClass == Resource.class) {
			for(int i = 1; i <= size; ++i) {
				clauseBuffer.append(" r.tag like ?" + i + " AND ");
			}
			clauseBuffer.append(" r.status = 0");
		} else {
			for(int i = 1; i <= size; ++i) {
				clauseBuffer.append(" r.resource.tag like ?" + i + " AND ");
			}
			clauseBuffer.append(" r.resource.status = 0");
		}
		return clauseBuffer.toString();
	}
	
	private static String getKeywordsSearchQueryClause(Class<?> entityClass, int size) {
		StringBuilder clauseBuffer = new StringBuilder("select r from " + 
				entityClass.getSimpleName() + " r where ");
		if(entityClass == Resource.class) {
			for(int i = 1; i <= size; ++i) {
				clauseBuffer.append(" (r.title like ?" + i + " OR ");
				clauseBuffer.append(" r.tag like ?" + i + ") AND ");
			}
			clauseBuffer.append(" r.status = 0");
		} else {
			for(int i = 1; i <= size; ++i) {
				clauseBuffer.append(" (r.resource.title like ?" + i + " OR ");
				clauseBuffer.append(" r.resource.tag like ?" + i + ") AND ");
			}
			clauseBuffer.append(" r.resource.status = 0");
		}
		return clauseBuffer.toString();
	}
	
}
