package yuemu.testdata;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import yuemu.jpa.JPAConnection;
import yuemu.po.Resource;
import yuemu.po.ResourceType;

public class UpdateResourcePreviewUrl {

	public static void main(String[] args) throws Exception {
		EntityManager em = JPAConnection.getEntityManager();
		em.getTransaction().begin();
		String clause = 
				"select r from Resource r" +
				" where r.type = :type";
		Query query = em.createQuery(clause);
		query.setParameter("type", ResourceType.DOCUMENT);
		List<Resource> list = query.getResultList();
		System.out.println(list.size());
		for(Resource r: list) {
			String path = r.getPreviewUrl();
			int j = path.lastIndexOf(".");
			path = path.substring(0, j) + ".swf";
			r.setPreviewUrl(path);
		}
		em.getTransaction().commit();
		em.close();
	}
	
}
