package yuemu.dao.search;

import java.util.Collection;
import java.util.List;

import yuemu.dao.ResourceDAOTool;
import yuemu.po.Resource;

public class ResourceSearchDAO {
	
	ResourceSearchDAO() {}
	
	/**
	 * 搜索标题内含有指定关键字的通用资源(用AND连接). 
	 * 如果传入的集合为空或为null, 返回一个空List.
	 * 
	 * @param titles
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static List<Object> searchByTitleKeywordsJoiningWithAnd(
			Collection<String> titles, int pageNum, int pageSize) {

		List<Resource> resources =  ResourceSearchDAOTool.searchByTitleKeywordsJoiningWithAnd(
				titles, pageNum, pageSize, Resource.class);
		return ResourceDAOTool.getConcreteResourcesFromAbstractOnes(resources);

	}
	
	/**搜索标签中含有指定关键字的所有类型资源(用AND连接). 
	 * 如果传入的集合为空或者null, 返回一个空List. 
	 * 
	 * @param tags
	 * @param pageNum
	 * @param pageSize
	 * @param entityClass
	 * @return
	 */
	public static List<Object> searchByTagKeywordsJoiningWithAnd(
			Collection<String> tags, int pageNum, int pageSize) {
		List<Resource> resources =  ResourceSearchDAOTool.searchByTagKeywordsJoiningWithAnd(
				tags, pageNum, pageSize, Resource.class);
		return ResourceDAOTool.getConcreteResourcesFromAbstractOnes(resources);
	}
	
	/**根据关键字(包括tag和title)搜索指定的XX资源(用AND连接). 
	 * 如果传入的集合为空或者null, 返回一个List. 
	 * 
	 * @param keywords
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static List<Object> searchByAllKeywordsJoiningWithAnd(
			Collection<String> keywords, int pageNum, int pageSize) {
		List<Resource> resources =  ResourceSearchDAOTool.searchByKeywordsJoiningWithAnd(
				keywords, pageNum, pageSize, Resource.class);
		return ResourceDAOTool.getConcreteResourcesFromAbstractOnes(resources);
	}
}
