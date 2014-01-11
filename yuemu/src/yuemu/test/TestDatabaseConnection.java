package yuemu.test;

import yuemu.dao.AccountDAO;
import yuemu.po.Account;

public class TestDatabaseConnection {

	public static void main(String[] args) {
		Account a = AccountDAO.getInstance().find(1l);
		System.out.println(a);
	}
	
}
