package yuemu.services;

import java.util.List;

import javax.persistence.PersistenceException;

import org.json.JSONArray;

import yuemu.dao.BrowseDAO;
import yuemu.dao.statistics.ResourceStatistics;
import yuemu.dao.statistics.VisitStatisticsDAO;
import yuemu.po.Account;
import yuemu.po.Browse;
import yuemu.po.Resource;
import yuemu.po.ResourceType;

public class VisitStatisticsService {

	BrowseDAO dao = BrowseDAO.getInstance();

	public long countByResourceId(long resourceId) {
		return VisitStatisticsDAO.countByResourceId(resourceId);
	}
	
	public void addVisitedNum(Account account, Resource resource) {
		try {
			Browse browse = new Browse();
			browse.setAccount(account);
			browse.setResource(resource);
			dao.insert(browse);
		} catch (PersistenceException e) {
			//处理浏览增加异常
		}
	}

	public long[] getTotalVisit() {
		return VisitStatisticsDAO.totalNumberForEveryType();
	}

	public double[][] getEveryMonthInYear(int year, int month) {
		double[][] values = new double[4][3];
		values[0] = getHalfYearAboutDocument(year, month);
		values[1] = getHalfYearAboutImage(year, month);
		values[2] = getHalfYearAboutMusic(year, month);
		values[3] = getHalfYearAboutVideo(year, month);
		return values;
	}

	public JSONArray getTop(int topNum, ResourceType type) {
		List<ResourceStatistics> list= VisitStatisticsDAO.statisticsOfTop(topNum, type);
		return StatisticsTool.getJSONArray(list);
	}
	
	public JSONArray getTopForAll(int topNum) {
		List<ResourceStatistics> list= VisitStatisticsDAO.statisticsOfAllTypeTop(topNum);
		return StatisticsTool.getJSONArray(list);
	}
	
	public JSONArray getTopForAccount(long accountId, int topNum, ResourceType type) {
		List<ResourceStatistics> list = VisitStatisticsDAO.statisticsOfTopByAccountId(accountId, topNum, type);
		return StatisticsTool.getJSONArray(list);
	}
	
	public JSONArray getTopForAccountAll(long accountId, int topNum) {
		List<ResourceStatistics> list = VisitStatisticsDAO.statisticsOfAllTypeTopByAccountId(accountId, topNum);
		return StatisticsTool.getJSONArray(list);
	}

	private double[] getHalfYearAboutVideo(int year, int month) {
		return StatisticsTool.getVisitArray(year, month, ResourceType.VIDEO);
	}

	private double[] getHalfYearAboutImage(int year, int month) {
		return StatisticsTool.getVisitArray(year, month, ResourceType.IMAGE);
	}

	private double[] getHalfYearAboutMusic(int year, int month) {
		return StatisticsTool.getVisitArray(year, month, ResourceType.MUSIC);
	}

	private double[] getHalfYearAboutDocument(int year, int month) {
		return StatisticsTool.getVisitArray(year, month, ResourceType.DOCUMENT);
	}
}
