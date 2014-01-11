package yuemu.dao.statistics;

import java.util.List;

import yuemu.jpa.JPAOperator;
import yuemu.po.ResourceType;

public class VisitStatisticsDAO {

	/**各类资源的访问量, 按类型分. 
	 * 
	 * 
	 * @return
	 */
	public static long[] totalNumberForEveryType() {
		String clause = "select count(b), b.resource.type from Browse b " +
				"group by b.resource.type";	
		return StatisticsDAOTool.totalNumberForEveryType(clause);
	}
	
	/**返回指定年月的数据值. 有类型的. 
	 * 
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 */
	public static long numberForMonthYear(
			int year, int month, 
			ResourceType type) {
		String clause = "select count(b) from Browse b " +
				" where b.browseTime between :fromTime and :toTime " +
				" AND b.resource.type = :type";
		return StatisticsDAOTool.
				numberForMonthYear(year, month, type, clause);
	}
	
	/**资源的TOP X. 
	 * 
	 * @param topNum top的数量. 
	 * @param fromTime 从此时间开始至今
	 * @param resourceClass 要查询的具体实体类. 不支持Resource. 
	 * @return 统计数据. 参看类ResourceStatistics
	 */
	public static List<ResourceStatistics> statisticsOfTop(
			int topNum, long fromTime, 
			ResourceType type) {
		String clause = "select b.resource.id, count(b) from Browse b " + 
				"where b.resource.status = 0 " +
				" AND b.resource.type = :type" + 
				" AND b.browseTime > :fromTime" + 
				" group by b.resource.id " + 
				" order by count(b) DESC";
		return StatisticsDAOTool.statisticsOfTop(
				topNum, fromTime, type, clause);
	}
	
	
	/**资源的总TOP X. 是数据库的总数据. 不分时间的. 
	 * 
	 * @param topNum top的数量. 
	 * @param resourceClass 要查询的具体实体类. 不支持Resource. 
	 * @return 统计数据. 参看类ResourceStatistics
	 */
	public static List<ResourceStatistics> statisticsOfTop(
			int topNum, ResourceType type) {
		return statisticsOfTop(topNum, 0, type);
	}
	
	/**资源的TOP X. 这是囊括所有资源类型的. 
	 * 
	 * @param topNum top的数量. 
	 * @param fromTime 从此时间开始至今
	 * @return 统计数据. 参看类ResourceStatistics
	 */
	public static List<ResourceStatistics> statisticsOfAllTypeTop(
			int topNum, long fromTime) {
		String clause = "select b.resource.id, count(b) from Browse b " + 
				" where b.resource.status = 0 " +
				" AND b.browseTime > :fromTime" + 
				" group by b.resource.id " + 
				" order by count(b) DESC";
		return StatisticsDAOTool.statisticsOfTop(
				topNum, fromTime, null, clause);
	}
	
	/**资源的TOP X. 这是囊括所有资源类型的. 
	 * 
	 * @param topNum top的数量. 
	 * @param fromTime 从此时间开始至今
	 * @return 统计数据. 参看类ResourceStatistics
	 */
	public static List<ResourceStatistics> statisticsOfAllTypeTop(
			int topNum) {
		return statisticsOfAllTypeTop(topNum, 0);
	}
	
	/**某用户资源的TOP X. 
	 * 
	 * @param accountId 用户ID
	 * @param topNum top的数量. 
	 * @param fromTime 从此时间开始至今
	 * @param resourceClass 要查询的具体实体类. 不支持Resource. 
	 * @return 统计数据. 参看类ResourceStatistics
	 */
	public static List<ResourceStatistics> statisticsOfTopByAccountId(
			long accountId, int topNum, long fromTime, 
			ResourceType type) {
		String clause = "select b.resource.id, count(b) from Browse b " + 
				" where b.resource.status = 0 " +
				" AND b.resource.uploader.id = " + accountId +
				" AND b.resource.type = :type" + 
				" AND b.browseTime > :fromTime" + 
				" group by b.resource.id " + 
				" order by count(b) DESC";
		return StatisticsDAOTool.statisticsOfTop(
				topNum, fromTime, type, clause);
	}
	
	/**某用户资源的总TOP X. 是数据库的总数据. 不分时间的. 
	 * 
	 * @param accountId 用户ID
	 * @param topNum top的数量. 
	 * @param resourceClass 要查询的具体实体类. 不支持Resource. 
	 * @return 统计数据. 参看类ResourceStatistics
	 */
	public static List<ResourceStatistics> statisticsOfTopByAccountId(
			long accountId, int topNum, 
			ResourceType type) {
		return statisticsOfTopByAccountId(accountId, topNum, 0, type);
	}
	
	public static List<ResourceStatistics> statisticsOfAllTypeTopByAccountId(
			long accountId, int topNum, long fromTime) {
		String clause = "select b.resource.id, count(b) from Browse b " + 
				" where b.resource.status = 0 " +
				" AND b.resource.uploader.id = " + accountId +
				" AND b.browseTime > :fromTime" + 
				" group by b.resource.id " + 
				" order by count(b) DESC";
		return StatisticsDAOTool.statisticsOfTop(topNum, fromTime, null, clause);
	}
	
	public static List<ResourceStatistics> statisticsOfAllTypeTopByAccountId(
			long accountId, int topNum) {
		return statisticsOfAllTypeTopByAccountId(accountId, topNum, 0);
	}
	
	public static long countByResourceId(
			long resourceId) {
		String clause = "select count(b) from Browse b " +
				"where b.resource.id = " + resourceId;
//		if(status != ResourceStatus.ALL)
//			clause += " AND b.resource.status = " + status;
		return JPAOperator.querySingle(clause, Long.class);
	}
}
