package yuemu.dao.statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yuemu.jpa.NoTypeQueryBuffer;
import yuemu.jpa.QueryBuffer;
import yuemu.po.ResourceType;

public class StatisticsDAOTool {

	/**
	 * 返回各种类型的数据, 但是需要自己给定语句. 语句的返回值是Object数组[long, ResourceType]
	 * 
	 * @param clause
	 * @return
	 */
	public static long[] totalNumberForEveryType(String clause) {
		NoTypeQueryBuffer queryBuffer = new NoTypeQueryBuffer();
		queryBuffer.setClause(clause);
		List<?> list = queryBuffer.query();
		long[] data = new long[ResourceType.values().length];
		for (int i = 0; i < list.size(); ++i) {
			Object obj = list.get(i);
			Object[] objArray = (Object[]) obj;
			long num = (Long) objArray[0];
			ResourceType type = (ResourceType) objArray[1];
			data[type.ordinal()] = num;
		}

		return data;
	}

	public static long numberForMonthYear(int year, int month,
			ResourceType type, String clause) {
		QueryBuffer<Long> queryBuffer = QueryBuffer.newInstance(Long.class);
		queryBuffer.setClause(clause);
		long[] timeRange = timeRange(year, month);
		queryBuffer.addParameter("fromTime", new Date(timeRange[0]));
		queryBuffer.addParameter("toTime", new Date(timeRange[1]));
		queryBuffer.addParameter("type", type);
		Long num = queryBuffer.querySingle();
		return num;
	}

	private static long[] timeRange(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1, 0, 0, 0);
		long fromTime = calendar.getTime().getTime();
		calendar.add(Calendar.MONTH, 1);
		long toTime = calendar.getTime().getTime();
		return new long[] { fromTime, toTime };
	}

	public static List<ResourceStatistics> statisticsOfTop(int topNum,
			long fromTime, ResourceType type, String clause) {
		NoTypeQueryBuffer queryBuffer = new NoTypeQueryBuffer(clause);
		queryBuffer.addParameter("fromTime", new Date(fromTime));
		if (type != null)
			queryBuffer.addParameter("type", type);
		queryBuffer.setPageSize(topNum);
		List<?> resultList = queryBuffer.query();

		List<ResourceStatistics> dataList = new ArrayList<ResourceStatistics>(
				resultList.size());
		for (Object result : resultList) {
			Object[] resultArray = (Object[]) result;
			ResourceStatistics data = new ResourceStatistics();
			data.resourceId = (Long) resultArray[0];
			data.count = Long.parseLong(resultArray[1].toString());
			dataList.add(data);
		}

		return dataList;
	}

}
