package yuemu.services;

import java.util.List;

import javax.persistence.PersistenceException;

import org.json.JSONArray;

import yuemu.dao.DownloadDAO;
import yuemu.dao.statistics.DownloadStatisticsDAO;
import yuemu.dao.statistics.ResourceStatistics;
import yuemu.po.Account;
import yuemu.po.Download;
import yuemu.po.Resource;
import yuemu.po.ResourceType;

public class DownloadStatisticsService {
	
	DownloadDAO dao = DownloadDAO.getInstance();
	
	
	public long countByResourceId(long resourceId) {
		return DownloadStatisticsDAO.countByResourceId(resourceId);
	}
	
	public void addDownloadNum(Account account, Resource resource){
		Download download = new Download();
		try{
		download.setAccount(account);
		download.setResource(resource);
		dao.insert(download);
		}catch(PersistenceException e){
			//处理下载次数增加错误异常
		}
	}
	
	public JSONArray getTop(int topNum,ResourceType type) {
		List<ResourceStatistics> list = DownloadStatisticsDAO.statisticsOfTop(topNum, type);
		return StatisticsTool.getJSONArray(list);
	}
	
	public JSONArray getTopForAll(int topNum) {
		List<ResourceStatistics> list = DownloadStatisticsDAO.statisticsOfAllTypeTop(topNum);
		return StatisticsTool.getJSONArray(list);
	}
	
	public long[] getTotalVisit() {
		return DownloadStatisticsDAO.totalNumberForEveryType();
	}

	public double[][] getEveryMonthInYear(int year, int month) {
		double[][] values = new double[4][3];
		values[0] = getHalfYearAboutDocument(year, month);
		values[1] = getHalfYearAboutImage(year, month);
		values[2] = getHalfYearAboutMusic(year, month);
		values[3] = getHalfYearAboutVideo(year, month);
		return values;
	}

	private double[] getHalfYearAboutVideo(int year, int month) {
		return StatisticsTool.getDownloadArray(year, month, ResourceType.VIDEO);
	}

	private double[] getHalfYearAboutImage(int year, int month) {
		return StatisticsTool.getDownloadArray(year, month, ResourceType.IMAGE);
	}

	private double[] getHalfYearAboutMusic(int year, int month) {
		return StatisticsTool.getDownloadArray(year, month, ResourceType.MUSIC);
	}

	private double[] getHalfYearAboutDocument(int year, int month) {
		return StatisticsTool.getDownloadArray(year, month, ResourceType.DOCUMENT);
	}
}
