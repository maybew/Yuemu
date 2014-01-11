package yuemu.services;

import java.util.Date;

import yuemu.dao.AccountDAO;
import yuemu.jpa.JPAConnection;
import yuemu.po.Account;
import yuemu.po.Sex;
import yuemu.po.UserType;

public class DatabaseService {

	public static void insertRootAdministrator() {
		try {
			if(AccountDAO.getInstance().findByEmail("root") == null) {
				Account root = new Account();
				root.setEmail("root");
				root.setPassword("root");
				root.setName("root");
				root.setSex(Sex.MALE);
				root.setProfession("root");
				root.setUserType(UserType.ADMIN);
				root.setBirthday(new Date());
				root.setContact("root");
				root.setAddress("root");
				AccountDAO.getInstance().insert(root);
			}
		} catch(Exception ex) {
			LogService.log("插入root管理员失败", "系统");
			ex.printStackTrace();
		}
	}
	
	public static void emptyDatabase() {
		JPAConnection.emptyDatabase();
	}
	
}
