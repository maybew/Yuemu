package yuemu.data.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import yuemu.core.ProjectException;
import yuemu.dao.AccountDAO;
import yuemu.dao.DocumentDAO;
import yuemu.dao.ImageDAO;
import yuemu.dao.MusicDAO;
import yuemu.dao.ResourceDAO;
import yuemu.dao.VideoDAO;
import yuemu.jpa.JPAConnection;
import yuemu.po.Account;
import yuemu.po.Document;
import yuemu.po.Image;
import yuemu.po.Music;
import yuemu.po.Resource;
import yuemu.po.Video;

public class DatabaseMove {
	
	@SuppressWarnings("deprecation")
	public static void writeToDatabase(InputStream outerIn) {
		ObjectInputStream in = null;
		DatabaseData dd = null;
		try {
			in = new ObjectInputStream(
					new BufferedInputStream(
							outerIn));
			dd = (DatabaseData) in.readObject();
		} catch (Exception e) {
			throw new ProjectException(e);
		} finally {
			try {
				if(in != null)
					in.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		//下面将生成的对象写入数据库.
		for(Account a: dd.accountList) {
			a.setId(AccountDAO.getInstance().insert(a));
		}
		for(Resource r: dd.resourceList) {
			r.setId(ResourceDAO.getInstance().insert(r));
		}
		for(Document d: dd.documentList) {
			d.setId(DocumentDAO.getInstance().insert(d));
		}
		for(Image i: dd.imageList) {
			i.setId(ImageDAO.getInstance().insert(i));
		}
		for(Music m: dd.musicList) {
			m.setId(MusicDAO.getInstance().insert(m));
		}
		for(Video v: dd.videoList) {
			v.setId(VideoDAO.getInstance().insert(v));
		}
	}

	public static void databaseWriteTo(OutputStream outerOut) {
		DatabaseData dd = readFromDatabase();
		ObjectOutputStream out = null;
	
		try {
			out = new ObjectOutputStream(
					new BufferedOutputStream(
							outerOut));
			out.writeObject(dd);
		} catch (Exception e) {
			throw new ProjectException(e);
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//从数据库读出内容
	private static DatabaseData readFromDatabase() {
		EntityManager em = null;
		List<Account> accountList = null;
		List<Resource> resourceList = null;
		List<Document> documentList = null;
		List<Image> imageList = null;
		List<Music> musicList = null;
		List<Video> videoList = null;
		try {
			em = JPAConnection.getEntityManager();
			
			TypedQuery<Account> accountQuery = em.createQuery(
					"select a from Account a", Account.class);
			accountList = accountQuery.getResultList();
			
			TypedQuery<Resource> resourceQuery = em.createQuery(
					"select r from Resource r", Resource.class);
			resourceList = resourceQuery.getResultList();
			
			TypedQuery<Document> documentQuery = em.createQuery(
					"select d from Document d", Document.class);
			documentList = documentQuery.getResultList();
			
			TypedQuery<Image> imageQuery = em.createQuery(
					"select i from Image i", Image.class);
			imageList = imageQuery.getResultList();
			
			TypedQuery<Music> musicQuery = em.createQuery(
					"select m from Music m", Music.class);
			musicList = musicQuery.getResultList();
			
			TypedQuery<Video> videoQuery = em.createQuery(
					"select v from Video v", Video.class);
			videoList = videoQuery.getResultList();
		} finally {
			if(em != null && em.isOpen())
				em.close();
		}
		
		allIdSetToNull(accountList, 
						resourceList, 
						documentList, 
						imageList, 
						musicList, 
						videoList);
		DatabaseData dd = new DatabaseData();
		dd.accountList = accountList;
		dd.resourceList = resourceList;
		dd.documentList = documentList;
		dd.imageList = imageList;
		dd.musicList = musicList;
		dd.videoList = videoList;
		
		return dd;
		
	}
	
	//所有ID置为null
	private static void allIdSetToNull(Collection<?>... collections) {
		try {
			for(Collection<?> c: collections) {
				for(Object obj: c) {
					Method m = obj.getClass().getMethod("setId", Long.class);
					m.invoke(obj, (Long)null);
				}
			}
		} catch(Exception ex) {
			throw new ProjectException(ex);
		}
	}
	
}
