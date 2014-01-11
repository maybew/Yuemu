package yuemu.services;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuemu.dao.ResourceDAO;
import yuemu.dao.statistics.DownloadStatisticsDAO;
import yuemu.dao.statistics.ResourceStatistics;
import yuemu.dao.statistics.UploadStatisticsDAO;
import yuemu.dao.statistics.VisitStatisticsDAO;
import yuemu.po.Resource;
import yuemu.po.ResourceType;

public class StatisticsTool {
	
	private final static int JANUARY = 0;
	private final static int DECEMBER = 11;
	
	public static JSONArray getJSONArray(List<ResourceStatistics> list) {
		JSONArray array = new JSONArray();
		ResourceDAO dao = ResourceDAO.getInstance();
		for (ResourceStatistics rs : list) {
			JSONObject object = new JSONObject();
			Resource resource = dao.find(rs.resourceId);
			try {
				object = resource.toJSON();
				object.put("count", rs.count);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(object);
		}
		return array;
	}
	
	
	public static double[] getUploadArray(int year, int month, ResourceType type) {
		double[] values = new double[3];
		for (int i = 2; i >= 0; i--) {

			values[i] = UploadStatisticsDAO.numberForMonthYear(year, month,
					type);
			if (month == JANUARY) {
				month = DECEMBER;
				year = year - 1;
			} else {
				month = month - 1;
			}
		}
		return values;
	}
	
	public static double[] getDownloadArray(int year, int month, ResourceType type) {
		double[] values = new double[3];
		for (int i = 2; i >= 0; i--) {

			values[i] = DownloadStatisticsDAO.numberForMonthYear(year, month,
					type);
			if (month == JANUARY) {
				month = DECEMBER;
				year = year - 1;
			} else {
				month = month - 1;
			}
		}
		return values;
	}
	
	public static double[] getVisitArray(int year, int month, ResourceType type) {
		double[] values = new double[3];
		for (int i = 2; i >= 0; i--) {

			values[i] = VisitStatisticsDAO.numberForMonthYear(year, month,
					type);
			if (month == JANUARY) {
				month = DECEMBER;
				year = year - 1;
			} else {
				month = month - 1;
			}
		}
		return values;
	}
}
