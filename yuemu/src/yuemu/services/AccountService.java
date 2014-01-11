package yuemu.services;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.PersistenceException;

import yuemu.dao.AccountDAO;
import yuemu.po.Account;
import yuemu.po.Sex;

public class AccountService {

	AccountDAO dao = AccountDAO.getInstance();

	public Account findByEmail(String email) {
		return dao.findByEmail(email);
	}

	public boolean vertify(String email, String password) {
		Account account = dao.findByEmail(email);
		if (account != null && account.getPassword().equals(password))
			return true;
		else
			return false;
	}

	public boolean check(String email) {
		if (dao.findByEmail(email) == null)
			return true;
		else
			return false;
	}

	public boolean insert(String email, String password, String name, int sex,
			String birthday, String occupation, String contact, String address) {
		Account account = new Account();
		account.setEmail(email);
		account.setPassword(password);
		account.setName(name);

		if (sex == 0)
			account.setSex(Sex.MALE);
		else
			account.setSex(Sex.FEMALE);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp ts;
		try {
			ts = new Timestamp(format.parse(birthday).getTime());
			account.setBirthday(ts);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		account.setProfession(occupation);
		account.setContact(contact);
		account.setAddress(address);
		try {
			dao.insert(account);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getAccountNum() {
		return dao.getAll().size();
	}

	public List<Account> getAccountPage(int pageNum, int pageSize) {
		return dao.getAll(pageNum, pageSize);
	}

	public int getAdminNum() {
		return (int) dao.getAllAdminstratorCount();
	}

	public int getCustomerNum() {
		return (int) dao.getAllCustomerCount();
	}

	public List<Account> getAdminPage(int pageNum, int pageSize) {
		return dao.getAllAdministrator(pageNum, pageSize);
	}

	public List<Account> getCustomerPage(int pageNum, int pageSize) {
		return dao.getAllCustomer(pageNum, pageSize);
	}
}
