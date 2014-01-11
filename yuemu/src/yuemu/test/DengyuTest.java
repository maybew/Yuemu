package yuemu.test;

import java.util.Calendar;



public class DengyuTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String sql = "select count(a) from  Account a where a.userType = :type ";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("type", UserType.ADMIN);
//		List<Long> count = JPAOperator.query(sql, map, Long.class, 1, 1);
//		System.out.println(count);
		
//		EntityManager em = JPAConnection.getEntityManager();
//		Query q = em.createQuery("select count(a) from Account " +
//				"a where a.userType = :type");
//		q.setParameter("type", UserType.ADMIN);
//		Object obj = q.getSingleResult();
//		System.out.println(obj);
		
//		String s = "asgdfag    sagfdh  dsagfdh  dghgfjfg          fghgfjfg";
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.YEAR));
	}

}
