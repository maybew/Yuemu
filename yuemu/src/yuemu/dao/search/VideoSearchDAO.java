package yuemu.dao.search;

import java.util.Collection;
import java.util.List;

import yuemu.po.Video;

public class VideoSearchDAO {

	VideoSearchDAO() {}
	
	/**
	 * 搜索标题内含有指定关键字的视频资源(用AND连接). 如果传入的集合为空或为null, 返回一个空List.
	 * 
	 * @param titles
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static List<Video> searchByTitleKeywordsJoiningWithAnd(
			Collection<String> titles, int pageNum, int pageSize) {

		return ResourceSearchDAOTool.searchByTitleKeywordsJoiningWithAnd(
				titles, pageNum, pageSize, Video.class);

	}
	
	/**搜索标签中含有指定关键字的视频资源(用AND连接). 
	 * 如果传入的集合为空或者null, 返回一个空List. 
	 * 
	 * @param tags
	 * @param pageNum
	 * @param pageSize
	 * @param entityClass
	 * @return
	 */
	public static List<Video> searchByTagKeywordsJoiningWithAnd(
			Collection<String> tags, int pageNum, int pageSize) {
		
		return ResourceSearchDAOTool.searchByTagKeywordsJoiningWithAnd(
				tags, pageNum, pageSize, Video.class);
		
	}
	
	/**根据关键字(包括tag和title)搜索指定的XX资源(用AND连接). 
	 * 如果传入的集合为空或者null, 返回一个List. 
	 * 
	 * @param keywords
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static List<Video> searchByAllKeywordsJoiningWithAnd(
			Collection<String> keywords, int pageNum, int pageSize) {
		return ResourceSearchDAOTool.searchByKeywordsJoiningWithAnd(
				keywords, pageNum, pageSize, Video.class);
	}
	
}
