package yuemu.test;

import java.io.File;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import yuemu.servlets.ExportServlet;


public class YeRunTest {
	
	public static void main(String[] args) throws Exception {

		EntityManagerFactory emf = Persistence.
				createEntityManagerFactory("createdrop");
		emf.close();
		
		ExportServlet.dbHandler(new File("d:\\db.dat"));
		
	}
	
	
}
