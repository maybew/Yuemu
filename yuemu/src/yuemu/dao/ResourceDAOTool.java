package yuemu.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import yuemu.core.LoggerFactory;
import yuemu.jpa.JPAConnection;
import yuemu.jpa.QueryBuffer;
import yuemu.po.Document;
import yuemu.po.Image;
import yuemu.po.Music;
import yuemu.po.Resource;
import yuemu.po.Video;

public class ResourceDAOTool {

	/**查询最新的数据, 一共返回num个. 但是所有数据的时间都要在指定id资源之前. 
	 * 
	 * @param num 查询的个数
	 * @param id 指定的ID, 查询的数据比其早存于数据库. 
	 * @param entityClass 查询的实体, 其应该是具体的资源实体. 
	 * @param queryClause 查询的语句
	 * @return
	 */
	public static <E> List<E> searchTheLatestAfterSpecifiedId(int num, Long id, 
			Class<E> entityClass) {
		QueryBuffer<E> qb = QueryBuffer.newInstance(entityClass);
		String queryClause = getQueryClauseFromEntityClass(entityClass);
		qb.setClause(queryClause);
		qb.addParameter("id", id);
		qb.setPageSize(num);
		return qb.query();
	}
	
	/*这个可以处理所有的那个资源, 包括具体类型和Resource. 
	 * 其中ID是ResourceId
	 * 
	 */
	private static String getQueryClauseFromEntityClass(Class<?> entityClass) {
		if(entityClass == Resource.class) {
			return String.format("select r from %s r where " +
					" r.status = 0 " +
					" AND r.id < :id " +
					" order by r.id DESC", 
					entityClass.getSimpleName());
		} else {
			return String.format("select r from %s r where " +
					" r.resource.status = 0 " +
					" AND r.resource.id < :id " +
					" order by r.resource.id DESC", 
					entityClass.getSimpleName());
		}
	}
	
	public static Object getConcreteResourceFromAbstractOne(Resource r) {
		if(r == null)
			throw new NullPointerException("Resource cannot be null!");
		List<Object> list = getConcreteResourcesFromAbstractOnes(Arrays.asList(r));
		if(list.isEmpty()) {
			LoggerFactory.getProjectLogger().warning(
					"Error when pass parameter r which is:\n\t" + 
					r.toString());
			return null;
		}
		else 
			return list.get(0);
	}
	
	public static List<Object> getConcreteResourcesFromAbstractOnes(List<Resource> resources) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			List<Object> objList = new ArrayList<Object>(resources.size());
			for(Resource r: resources) {
				try {
					switch(r.getType()) {
					case DOCUMENT: 
						TypedQuery<Document> dQuery = em.createQuery(
								"select d from Document d " +
								"where d.resource = :resource", Document.class);
						dQuery.setParameter("resource", r);
						objList.add(dQuery.getSingleResult());
						break;
					case IMAGE:
						String clause = "select i from Image i " +
								"where i.resource = :resource";
						TypedQuery<Image> iQuery = em.createQuery(clause, Image.class);
						iQuery.setParameter("resource", r);
						objList.add(iQuery.getSingleResult());
						break;
					case MUSIC: 
						clause = "select m from Music m " + 
								"where m.resource = :resource";
						TypedQuery<Music> mQuery = em.createQuery(clause, Music.class);
						mQuery.setParameter("resource", r);
						objList.add(mQuery.getSingleResult());
						break;
					case VIDEO: 
						clause = "select v from Video v " + 
								"where v.resource = :resource";
						TypedQuery<Video> vQuery = em.createQuery(clause, Video.class);
						vQuery.setParameter("resource", r);
						objList.add(vQuery.getSingleResult());
						break;
					default: 
						throw new IllegalStateException("Unsupported Resource Type: " + r.getType());
					}
				} catch(NoResultException ex) {
					LoggerFactory.getProjectLogger().warning("NO Result related with resource: " + r.getId());
				} catch(NullPointerException ex) {
					if(r.getType() == null)
						LoggerFactory.getProjectLogger().warning("Type of resource is null: " + r.getId());
					else throw ex;
				}
			}
			
			return objList;
		} finally {
			if(em != null && em.isOpen()) 
				em.close();
		}
	}
	
}
