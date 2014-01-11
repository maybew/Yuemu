package yuemu.dao;

import java.util.List;

import yuemu.jpa.JPAOperator;
import yuemu.po.Log;

public class LogDAO {
	private final static LogDAO INSTANCE=new LogDAO();
	
	private LogDAO(){}
	
	public  static  LogDAO  getInstance(){
		return INSTANCE;
	}
	
	
	public void delete(Log log){
		JPAOperator.delete(log);
	}
	
	
	
	
	/***
	 * 插入日志信息
	 * @param log
	 * @return
	 */
	public Long insert(Log log){
		
		return (Long)JPAOperator.insert(log);
		
		
		
	}
	
	/**
	 * 通过Id查找日志信息
	 * @param id
	 * @return
	 */
	
	public  Log find(Long id){
		
		return JPAOperator.find(id, Log.class);
		
		
	}
	
	
	/**
	 * 按照时间,列出所有的日志.
	 * @return
	 */
	public List<Log> findAllByTime(int pageNum,int pageSize){
		String sql="select l from Log l  order by l.logTime DESC";
		
		return JPAOperator.query(sql, Log.class, pageNum, pageSize);
		
			
	}
	
	public Long findAllLogCount(){
		String sql="select count ( l ) from Log l ";
		
		return JPAOperator.querySingle(sql, Long.class);
			
		
	}

	public List<Log> getAll() {
		String sql = "select r from Log r ";

		return JPAOperator.query(sql, Log.class, 1, Integer.MAX_VALUE / 2);
	}
	
	
	
	
	
	
	
	

}
