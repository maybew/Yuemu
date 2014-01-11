package yuemu.dao;

import yuemu.jpa.JPAOperator;
import yuemu.po.Browse;

public class BrowseDAO {
	
	
private final static BrowseDAO INSTANCE = new BrowseDAO();
	
	private BrowseDAO() {}
	  
	
	public static BrowseDAO getInstance() {
		return INSTANCE;
	}
	
	
	
	/***
	 * 通过一个browse Id,查询到一个浏览信息.返回一个browse对象.
	 * @param i
	 * @return
	 */
	public Browse find(Long i){
			return JPAOperator.find(i, Browse.class);
			
		
	}
	
	
	
	public Long insert(Browse browse){
			return (Long)JPAOperator.insert(browse);
	}

	
	

}
