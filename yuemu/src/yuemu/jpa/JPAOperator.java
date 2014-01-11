package yuemu.jpa;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import yuemu.core.LoggerFactory;
import yuemu.core.ProjectException;

public class JPAOperator {
	
	public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE / 2;

	private JPAOperator() {
	}

	public static <E> E find(Object id, Class<E> clazz) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			E e = em.find(clazz, id);
			return e;
		} finally {
			if (em != null && em.isOpen()) 
				em.close();
		}
	}

	public static Object insert(Object obj) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			em.getTransaction().begin();
			try {
				obj = em.merge(obj);
				em.getTransaction().commit();
			} catch(PersistenceException ex) {
				em.getTransaction().rollback();
				throw ex;
			}
			try {
				Method method = obj.getClass().getMethod("getId");
				Object id = method.invoke(obj);
				return id;
			} catch (Exception ex) {
				throw new ProjectException(ex);
			}
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	public static void update(Object obj) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			em.getTransaction().begin();
			try {
				em.merge(obj);
				em.getTransaction().commit();
			} catch(PersistenceException ex) {
				em.getTransaction().rollback();
				throw ex;
			}
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	public static void delete(Object obj) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			em.getTransaction().begin();
			try {
			
				em.remove(em.merge(obj));
				em.getTransaction().commit();
			} catch(PersistenceException ex) {
				em.getTransaction().rollback();
				throw ex;
			}
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	public static void deleteById(Object id, Class<?> clazz) {
		EntityManager em = null;
		try {
			em = JPAConnection.getEntityManager();
			em.getTransaction().begin();
			try {
				Object obj = em.find(clazz, id);
				em.remove(obj);
				em.getTransaction().commit();
			} catch(PersistenceException ex) {
				em.getTransaction().rollback();
				throw ex;
			}
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}
	
	public static List<?> noTypeQuery(String queryClause, Map<String, ?> parameters, 
			int offset, int pageNum, int pageSize) {
		if(pageSize > MAX_PAGE_SIZE)
			throw new ProjectException("Page size over max value: " + pageSize);
		EntityManager em = null;
		try {
			LoggerFactory.getProjectLogger().finest("JPQL:" + queryClause);
			em = JPAConnection.getEntityManager();
			Query query = em.createQuery(queryClause);
			if (parameters != null)
				for (Entry<String, ?> entry : parameters.entrySet())
					query.setParameter(entry.getKey(), entry.getValue());
			query.setFirstResult((pageNum - 1) * pageSize + offset);
			query.setMaxResults(pageSize);
			List<?> list = query.getResultList();
			return list;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}
	
	public static List<?> noTypeQuery(String queryClause, Map<String, ?> parameters, 
			int pageNum, int pageSize) {
		return noTypeQuery(queryClause, parameters, 0, pageNum, pageSize);
	}
	
	public static List<?> noTypeQuery(String queryClause, int pageNum, int pageSize) {
		return noTypeQuery(queryClause, null, pageNum, pageSize);
	}
	
	public static Object noTypeQuerySingle(String queryClause, Map<String, ?> parameters) {
		EntityManager em = null;
		try {
			LoggerFactory.getProjectLogger().finest("JPQL:" + queryClause);
			em = JPAConnection.getEntityManager();
			Query query = em.createQuery(queryClause);
			if (parameters != null)
				for (Entry<String, ?> entry : parameters.entrySet())
					query.setParameter(entry.getKey(), entry.getValue());
			return query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}
	
	public static <E> List<E> query(String queryClause, Map<String, ?> parameters, 
			Class<E> entityClass, int offset, int pageNum, int pageSize) {
		if(pageSize > MAX_PAGE_SIZE)
			throw new ProjectException("Page size over max value: " + pageSize);
		EntityManager em = null;
		try {
			LoggerFactory.getProjectLogger().finest("JPQL:" + queryClause);
			em = JPAConnection.getEntityManager();
			TypedQuery<E> query = em.createQuery(queryClause, entityClass);
			if (parameters != null)
				for (Entry<String, ?> entry : parameters.entrySet())
					query.setParameter(entry.getKey(), entry.getValue());
			query.setFirstResult((pageNum - 1) * pageSize + offset);
			query.setMaxResults(pageSize);
			List<E> list = query.getResultList();
			return list;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	/**
	 * JPA语句查询, 并提供翻页功能
	 * 
	 * @param queryClause
	 * @param parameters
	 * @param entityClass
	 * @param pageNum
	 *            从1开始数的页码数
	 * @param pageSize
	 *            每页显示的数量
	 * @return s
	 */
	public static <E> List<E> query(String queryClause,
			Map<String, ?> parameters, Class<E> entityClass, int pageNum,
			int pageSize) {
		return query(queryClause, parameters, entityClass, 0, pageNum, pageSize);
	}

	public static <E> List<E> query(String queryClause, Class<E> entityClass,
			int pageNum, int pageSize) {
		return query(queryClause, null, entityClass, pageNum, pageSize);
	}

	public static <E> E querySingle(String queryClause,
			Map<String, Object> parameters, Class<E> entityClass) {

		EntityManager em = null;
		try {
			LoggerFactory.getProjectLogger().finest("JPQL:" + queryClause);
			em = JPAConnection.getEntityManager();
			TypedQuery<E> query = em.createQuery(queryClause, entityClass);
			if (parameters != null)
				for (Entry<String, ?> entry : parameters.entrySet())
					query.setParameter(entry.getKey(), entry.getValue());
			return query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	public static <E> E querySingle(String queryClause, Class<E> entityClass) {
		return querySingle(queryClause, null, entityClass);
	}

}
