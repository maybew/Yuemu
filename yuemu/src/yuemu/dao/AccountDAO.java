package yuemu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuemu.jpa.JPAOperator;
import yuemu.po.Account;
import yuemu.po.UserType;

public class AccountDAO {

	private final static AccountDAO INSTANCE = new AccountDAO();

	private AccountDAO() {
	}

	public static AccountDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * 插入账户信息,返回插入对象ID
	 * 
	 * @param account
	 * @return
	 */
	public Long insert(Account account) {
			return (Long) JPAOperator.insert(account);
	}

	/**
	 * 通过账户对象 删除账户信息
	 */
	public void delete(Account account) {
		JPAOperator.delete(account);
	}

	/**
	 * 通过Id删除账户
	 * 
	 * @param id
	 */
	public void deleteById(Long id) {
		JPAOperator.deleteById(id, Account.class);
	}

	public Account find(Long i) {
		return JPAOperator.find(i, Account.class);

	}

	public void update(Account account) {

		JPAOperator.update(account);
	}

	public Account findByEmail(String userName) {
		String sql = "select a from Account a where a.email = :userName";

		//QueryBuffer<Account> qb = QueryBuffer.newInstance(sql, Account.class);

		//qb.addParameter("userName", userName);
		
		Map<String ,Object> map =new HashMap<String, Object>();
		
		map.put("userName", userName);
		Account a=JPAOperator.querySingle(sql, map, Account.class);
		return a;

	}

	/**
	 * 查找所有的账户,提供分页功能
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize每页容量
	 * @return
	 */
	public List<Account> getAll(int pageNum, int pageSize) {

		String sql = "select a from Account a ";
		return JPAOperator.query(sql, Account.class, pageNum, pageSize);

	}

	/**
	 * 返回所有的账户,不提供分页功能
	 * 
	 * @return
	 */

	public List<Account> getAll() {
		String sql = "select a from Account a";
		return JPAOperator.query(sql, Account.class, 1, Integer.MAX_VALUE / 2);
	}

	/**
	 * h获得所有的管理员账户,并提供翻页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Account> getAllAdministrator(int pageNum, int pageSize) {
		String sql = "select a from Account a where a.userType = :type";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", UserType.ADMIN);

		return JPAOperator.query(sql, map, Account.class, pageNum, pageSize);

	}

	/**
	 * 获得所有的普通注册用户账户,并提供翻页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Account> getAllCustomer(int pageNum, int pageSize) {
		String sql = "select a from Account a where a.userType = :type";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", UserType.CUSTOMER);

		return JPAOperator.query(sql, map, Account.class, pageNum, pageSize);
	}

	/**
	 * 查询所有的管理员数量
	 * 
	 * @return
	 */
	public long getAllAdminstratorCount() {

		String sql = "select count( a ) from  Account a where a.userType = :type ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", UserType.ADMIN);
		return JPAOperator.querySingle(sql, map, Long.class);
//		return 100L;

	}
	
	
	
	/**
	 * 查询所有的用户数量
	 * 
	 * @return
	 */
	public long getAllCustomerCount() {

		String sql = "select count( a ) from  Account a where a.userType = :type ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", UserType.CUSTOMER);
		return JPAOperator.querySingle(sql, map, Long.class);
		
//		return 100L;
		}
	
	public long getAllAccountCount() {
		String sql = "select count( a ) from  Account a ";
		return JPAOperator.querySingle(sql, Long.class);
	}
	

}
