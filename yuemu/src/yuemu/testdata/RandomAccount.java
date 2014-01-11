package yuemu.testdata;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import yuemu.po.Account;
import yuemu.po.Sex;
import yuemu.po.UserType;

/**可以从一篇英文文章中获取用户名, 并结合中文姓名随机生成工具. 
 * 生成的用户总数逾英文文章的用户名数相等. 
 * 
 * @author Administrator
 *
 */
public class RandomAccount {
	
	private String address = "从哪里来到哪里去";
	
	private Date birthday;
	
	private String contact = "17843542670";
	
	private String name;
	
	private String password = "helloworld"; 
	
	private String profession = "学生";
	
	private Sex sex;
	
	private int currentIndex = -1;
	
	private List<String> userNameList;
	
	public RandomAccount() {}

	public RandomAccount init(String filePath) {
		generateUserNameList(filePath);
		
		return this;
	}
	
	private void generateUserNameList(String filePath) {
		
		try {
			Scanner scanner = new Scanner(new FileInputStream(filePath));
			scanner.useDelimiter("[^A-Za-z0-9_]");
			Set<String> set = new HashSet<String>();
			while(scanner.hasNext()) {
				String word = scanner.next().trim();
				if(!word.isEmpty())
					set.add(word);
			}
			userNameList = new ArrayList<String>(set);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Account> randomAccountList() {
		List<Account> accountList = new ArrayList<Account>(getSize());
		for(int i = 0; i < userNameList.size(); ++i) {
			Account a = generateAccount();
			accountList.add(a);
		}
		
		return accountList;
	}
	
	public Account generateAccount() {
		Account a = new Account();
		generate();
		a.setAddress(getAddress());
		a.setBirthday(getBirthday());
		a.setContact(getContact());
		a.setEmail(getEmail());
		a.setName(getName());
		a.setPassword(getPassword());
		a.setProfession(getProfession());
		a.setSex(getSex());
		a.setUserType(UserType.CUSTOMER);
		return a;
	}
	
	public void generate() {
		if(currentIndex >= userNameList.size())
			throw new RuntimeException("Cannot generate any account!");
		NameMaker.generateName();
		name = NameMaker.fullName;
		sex = Sex.valueOf(NameMaker.sex);
		currentIndex++;
		birthday = new Date((long)(Math.random() * System.currentTimeMillis()));
	}

	public int getSize() {
		return userNameList.size();
	}

	public String getAddress() {
		return address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getContact() {
		return contact;
	}

	public String getEmail() {
		return userNameList.get(currentIndex) + "@yuemu.com";
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getProfession() {
		return profession;
	}

	public Sex getSex() {
		return sex;
	}
	
}
