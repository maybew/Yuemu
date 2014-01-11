package yuemu.services;

import java.sql.Timestamp;
import java.util.List;

import yuemu.dao.DAOFactory;
import yuemu.dao.LogDAO;
import yuemu.po.Log;



public class LogService {
	
	LogDAO dao=	DAOFactory.getLogDAO();
	
	/**
	 * 生成日志信息
	 * @param log
	 * @return
	 */
	
	public static boolean log(String describe,String  mode){
		
		LogDAO dao=DAOFactory.getLogDAO();
		
		Log log= new Log();
		
		log.setDescribe(describe);
		log.setLogTime(new Timestamp(System.currentTimeMillis()));
		log.setMode(mode);
		
		dao.insert(log);
		return true;
	}
	
	
	/**
	 * 返回指定页码容量的日志信息
	 * @return
	 */
	public List<Log> findAllLog(int pageNum ,int pageSize){
		
		return dao.findAllByTime(pageNum, pageSize);
			
	}
	
	/**
	 * 返回日志的数量
	 */
	
	
	 public long  getAllPageCount(int pageSize){
		 long temp=dao.findAllLogCount().longValue();
		 if(temp%pageSize==0){
			return temp/pageSize;
		 }
		 else return temp/pageSize+1;
		 
	 }
	

}
