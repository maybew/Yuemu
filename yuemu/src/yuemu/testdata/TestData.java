package yuemu.testdata;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.persistence.PersistenceException;

import yuemu.core.TimeFormatter;
import yuemu.dao.AccountDAO;
import yuemu.po.Account;

public class TestData {

	static Scanner scan = new Scanner(System.in);
	
	private static void generateAccountTestData() {
		System.out.println("首先生成保留用户");
		
		System.out.print("输入文件路劲: ");
		String line = scan.nextLine();
		File file = new File(line);
		if(!file.exists()) {
			System.err.println("路径不存在!");
			return;
		}
		
		//TODO 插入失败后访问countOfAccountByUserType时卡住. 
		System.out.println("下面开始插入Account实体: ");
		RandomAccount randomAccount = new RandomAccount();
		randomAccount.init(file.getAbsolutePath());
		List<Account> accountList = randomAccount.randomAccountList();
		System.out.println("一共生成" + accountList.size() + "个实体. ");
		AccountDAO accountDAO = AccountDAO.getInstance();
		for(int i = 0; i < accountList.size(); ++i) {
			Account a = accountList.get(i);
			System.out.println("插入第" + (i + 1) + "个Account: " + a);
			try {
				accountDAO.insert(a);
			} catch(PersistenceException ex) {
				try {
					System.err.println("插入失败, 下面尝试更新数据.");
					Account ap = accountDAO.findByEmail(a.getEmail());
					a.setId(ap.getId());
					accountDAO.update(a);
				} catch(PersistenceException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Account实体插入完毕!!!");
	}

	private static void generateResourceTestData() {
		System.out.println("GenerateResourceTestData");
		ResourceTestData testData = new ResourceTestData();
		
		String rootPath = null;
		System.out.print("输入资源根路径: ");
		rootPath = scan.nextLine();
		System.out.println("检测到资源总数: " + 
				ResourceTestData.getFileListFromRoot(new File(rootPath)).size());
		
		long fromTime = 0;
		long toTime = System.currentTimeMillis();
		fromTime = 1325347200000L;	//20120101
		toTime = 1341072000000L;	//20120701
		
		int times;
		System.out.print("输入执行次数: ");
		times = scan.nextInt();
		
		long range = (toTime - fromTime) / times;
		for(int i = 0; i < times; ++i) {
			System.out.println("上一次生成时间: " + 
					TimeFormatter.getDateTimeString(fromTime) + " 到 " + 
					TimeFormatter.getDateTimeString(toTime));
			fromTime = i * range;
			toTime = (i + 1) * range;
			testData.setFromTime(fromTime);
			testData.setToTime(toTime);
			testData.setRootFile(rootPath);
			System.out.println("第" + i + "次执行Resource插入.");
			System.out.println("这一次生成" + 
					TimeFormatter.getDateTimeString(fromTime) + " 到 " + 
					TimeFormatter.getDateTimeString(toTime));
			testData.generateTestData();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		generateAccountTestData();
		generateResourceTestData();
//		StatisticsTestData.execute();
	}
	
}
