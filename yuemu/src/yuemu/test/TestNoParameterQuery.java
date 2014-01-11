package yuemu.test;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import yuemu.core.Debug;
import yuemu.jpa.JPAConnection;

public class TestNoParameterQuery {
	
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		StringBuilder buffer = new StringBuilder();
		while(true) {
			String line = scan.nextLine();
			if(line.isEmpty()) break;
			buffer.append(line);
			buffer.append(" ");
		}
		String clause = buffer.toString();
		System.out.println("QueryClause: ");
		System.out.println(clause);
		System.out.println("-----------------------------");
		
		EntityManager em = JPAConnection.getEntityManager();
		Query query = em.createQuery(clause);
		List<?> list = query.getResultList();
		em.close();
		
		System.out.println("Results: ");
		Debug.showCollection(list);
	}
	
}
