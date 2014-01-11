package yuemu.testdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import yuemu.dao.AccountDAO;
import yuemu.dao.BrowseDAO;
import yuemu.dao.DownloadDAO;
import yuemu.dao.ResourceDAO;
import yuemu.po.Account;
import yuemu.po.Browse;
import yuemu.po.Download;
import yuemu.po.Resource;


public class StatisticsTestData {
	
	public static void execute() {
		insertBrowses();
		insertDonwloads();
	}

	public static void insertBrowses() {
		List<Browse> browseList = getBrowseList();
		for(Browse b: browseList) 
			BrowseDAO.getInstance().insert(b);
	}
	
	private static List<Browse> getBrowseList() {
		List<Long> times = getRandomTimes(100000);
		Collections.sort(times);
		List<Browse> browseList = new ArrayList<Browse>(times.size());
		for(Long t: times) {
			Browse b = new Browse();
			b.setAccount(getAccount());
			b.setResource(getResource());
			b.setBrowseTime(new Date(t));
			browseList.add(b);
		}
		return browseList;
	}
	
	private static Random random = new Random();
	
	private static Account getAccount() {
		long accountCount = getAccountCount();
		long accountId = (long)(Math.random() * accountCount) + 1;
		Account a = AccountDAO.getInstance().find(accountId);
		return a;
	}
	
	private static long accountCount = -1;
	
	private static long getAccountCount() {
		if(accountCount < 0)
			accountCount = AccountDAO.getInstance().getAllAccountCount();
		return accountCount;
	}

	private static Resource getResource() {
		long resourceId = 0;
		if(random.nextInt(2) > 0) {
			//选择11-20
			resourceId = 11 + random.nextInt(10);
		} else {
			//随机
			long resourceCount = getResourceCount();
			resourceId = (long)(Math.random() * resourceCount) + 1;
		}
		
		return ResourceDAO.getInstance().find(resourceId);
	}
	
	private static long resourceCount = -1;

	private static long getResourceCount() {
		if(resourceCount < 0)
			resourceCount = ResourceDAO.getInstance().countOfAll();
		return resourceCount;
	}

	public static void insertDonwloads() {
		List<Download> downloadList = getDonwloadList();
		for(Download d: downloadList)
			DownloadDAO.getInstance().insert(d);
	}
	
	private static List<Download> getDonwloadList() {
		List<Long> times = getRandomTimes(10000);
		Collections.sort(times);
		List<Download> downloadList = new ArrayList<Download>(times.size());
		for(Long t: times) {
			Download d = new Download();
			d.setAccount(getAccount());
			d.setResource(getResource());
			d.setDownloadTime(new Date(t));
			downloadList.add(d);
		}
		return downloadList;
	}

	private static List<Long> getRandomTimes(int num) {
		long to = System.currentTimeMillis();
		List<Long> list = new ArrayList<Long>(num);
		for(int i = 0;i < num; ++i) {
			long time = (long) (Math.random() * to);
			list.add(time);
		}
		return list;
	}

}
