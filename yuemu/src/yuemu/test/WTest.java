package yuemu.test;

import yuemu.dao.DAOFactory;
import yuemu.po.Resource;

public class WTest {
	public static void main(String args[]) {
		Resource r = DAOFactory.getResourceDAO().find(19l);
		System.out.println(r);
	}
}
