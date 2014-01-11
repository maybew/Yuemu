package yuemu.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import yuemu.core.Debug;
import yuemu.dao.DAOFactory;
import yuemu.jpa.JPAConnection;
import yuemu.po.Account;
import yuemu.po.Document;
import yuemu.po.Resource;

public class Test {

	public static void main(String[] args) {

	/*	DataOut od = new DataOut("d:\\dabase\\");
		od.AccountDataOutput();

		od.BrowseDataOutput();

		od.DocumentDataOutput();

		od.DownloadDataOutput();

		od.ImageDataOutput();

		od.ResourceDataOutput();

		od.LogDataOutput();

		od.MusicDataOutput();

		od.VideoDataOutput();*/
		
//		Account a;
//		
//		Resource r = ResourceDAO.getInstance().find(2L);
//		Account a = AccountDAO.getInstance().find(40L);

		EntityManager em = JPAConnection.getEntityManager();
		TypedQuery<Account> accountQuery = 
				em.createQuery("select a from Account a", Account.class);
		List<Account> accountList = accountQuery.getResultList();
		TypedQuery<Document> documentQuery = 
				em.createQuery("select d from Document", Document.class);
		List<Document> documentList = documentQuery.getResultList();
		
		Resource r = em.find(Resource.class, 2L);
		em.close();
		
//		DataIn di = new DataIn();
//		di.putIntoDatabase();

		// writeAllData();

		// readAllData();

	}

	public static void writeAllData() {

		List<Account> al = DAOFactory.getAccountDAO().getAll();

		try {

			FileOutputStream fos = new FileOutputStream("Account.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// for(Account a:al){
			//
			// oos.writeObject(a);
			// }
			oos.writeObject(al);

			fos.close();
			oos.close();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {

		}

	}

	public static List<Account> readAllData() {

		List<Account> al = new ArrayList<Account>();

		try {
			FileInputStream fis = new FileInputStream("Account.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			// while(ois.available()!=-1){
			// Account a=(Account)ois.readObject();
			//
			// al.add(a);}
			// Account a = (Account)ois.readObject();
			al = (List<Account>) ois.readObject();
			Debug.showCollection(al);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return al;

	}

}
