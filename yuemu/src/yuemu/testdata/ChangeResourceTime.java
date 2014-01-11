package yuemu.testdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import yuemu.jpa.JPAConnection;
import yuemu.po.Resource;


public class ChangeResourceTime {

	public static void main(String[] args) throws Exception {
		
		EntityManager em = JPAConnection.getEntityManager();
		em.getTransaction().begin();
		
		String clause = "select r from Resource r";
		Query query = em.createQuery(clause);
		List<Resource> resourceList = query.getResultList();
		List<Date> dates = getRandomDate(resourceList.size());
		Collections.sort(dates);
		for(int i = 0; i < resourceList.size(); ++i) {
			Resource r = resourceList.get(i);
			r.setUploadTime(dates.get(i));
		}
		
		em.getTransaction().commit();
		em.close();
		
	}

	private static List<Date> getRandomDate(int size) {
		List<Date> dates = new ArrayList<Date>(size);
		for(int i = 0; i < size; ++i) {
			long fromTime = 0;
			long toTime = System.currentTimeMillis();
			fromTime = 1325347200000L;	//20120101
			toTime = 1341072000000L;	//20120701
			long time =  (long) (Math.random() * (toTime - fromTime) + fromTime);
			dates.add(new Date(time));
		}
		return dates;
		
		
	}
	
}
