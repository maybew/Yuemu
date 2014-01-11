package yuemu.services;

import yuemu.dao.statistics.UploadStatisticsDAO;
import yuemu.po.ResourceType;

public class UploadStatisticsService {

	public long[] getTotalVisit() {
		return UploadStatisticsDAO.totalNumberForEveryType();
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
		return StatisticsTool.getUploadArray(year, month, ResourceType.VIDEO);
	}

	private double[] getHalfYearAboutImage(int year, int month) {
		return StatisticsTool.getUploadArray(year, month, ResourceType.IMAGE);
	}

	private double[] getHalfYearAboutMusic(int year, int month) {
		return StatisticsTool.getUploadArray(year, month, ResourceType.MUSIC);
	}

	private double[] getHalfYearAboutDocument(int year, int month) {
		return StatisticsTool.getUploadArray(year, month, ResourceType.DOCUMENT);
	}

}
