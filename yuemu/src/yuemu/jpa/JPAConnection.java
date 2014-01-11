package yuemu.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConnection {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("hibernate");

	private JPAConnection() {
	}

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void emptyDatabase() {
		emf.close();
		EntityManagerFactory emptyEmf = Persistence.
				createEntityManagerFactory("createdrop");
		emptyEmf.close();
		emf = Persistence
				.createEntityManagerFactory("hibernate");
	}

}
