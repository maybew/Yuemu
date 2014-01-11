package yuemu.dao.statistics;

import yuemu.po.ResourceType;

public class UploadStatisticsDAO {
	/**各类资源的访问量, 按类型分. 
	 * 
	 * 
	 * @return
	 */
	public static long[] totalNumberForEveryType() {
		String clause = "select count(r), r.type from Resource r " +
				"group by r.type";
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
		String clause = "select count(r) from Resource r " +
				" where (r.uploadTime between :fromTime and :toTime) " +
				" AND (r.type = :type)";
		return StatisticsDAOTool.
				numberForMonthYear(year, month, type, clause);
	}
}
