package yuemu.testdata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import yuemu.dao.AccountDAO;
import yuemu.dao.DocumentDAO;
import yuemu.dao.ImageDAO;
import yuemu.dao.MusicDAO;
import yuemu.dao.VideoDAO;
import yuemu.po.Account;
import yuemu.po.Document;
import yuemu.po.Image;
import yuemu.po.Music;
import yuemu.po.Resource;
import yuemu.po.ResourceType;
import yuemu.po.Video;

/**随机生成资源的工具. 
 * 其原理是将资源的根路径作为资源实体生成资源然后随机与
 * 用户关联插入数据库. 
 * 
 * 说明: 设置rootFile, fromTime, toTime. 
 * 说明: title与文件名相同, tag设置为与文件名切割. 
 * 
 * @author Administrator
 *
 */
public class ResourceTestData {
	
	private File rootFile;
	
	private long fromTime;
	
	private long toTime;

	public void generateTestData() {
		List<Object> resourceList = getResourceObjectList();
		sortByBirthday(resourceList);
		System.out.println("下面插入" + resourceList.size() + "个Resource!");
		insertResources(resourceList);
		System.out.println(resourceList.size() + "个Resource插入完毕!");
	}

	private List<Object> getResourceObjectList() {
		List<Resource> resourceList = getResourceList();
		
		List<Object> objList = new ArrayList<Object>(resourceList.size());
		for(Resource r: resourceList) {
			Object obj = getResourceObjectFromResource(r);
			if(obj != null)
				objList.add(obj);
		}
		return objList;
	}

	private Object getResourceObjectFromResource(Resource r) {
		String category = r.getCategory().toLowerCase();
		if(category.startsWith("document")) {
			Document d = new Document();
			d.setAuthor("沈志东");
			d.setResource(r);
			r.setType(ResourceType.getResourceType(d.getClass()));
			return d;
		} else if(category.startsWith("image")) {
			Image i = new Image();
			i.setResource(r);
			r.setType(ResourceType.getResourceType(i.getClass()));
			return i;
		} else if(category.startsWith("music")) {
			Music m = new Music();
			m.setAlbum("忘情水");
			m.setGenre("英雄");
			m.setSinger("貌似刘德华");
			m.setSong("还是忘情水");
			m.setResource(r);
			r.setType(ResourceType.getResourceType(m.getClass()));
			return m;
		} else if(category.startsWith("video")) {
			Video v = new Video();
			v.setResource(r);
			r.setType(ResourceType.getResourceType(v.getClass()));
			return v;
		} else {
			System.err.println("未知类别: " + r);
			return null;
		}
	}

	private List<Resource> getResourceList() {
		List<File> fileList = getFileListFromRoot(rootFile);
		List<Resource> resourceList = new ArrayList<Resource>(fileList.size());
		for(File file: fileList) {
			resourceList.add(getResourceFromFile(file));
		}
		return resourceList;
	}

	private Resource getResourceFromFile(File file) {
		Resource r = new Resource();

		r.setTitle(getResourceTitle(file));

		r.setTag(getResourceTag(file));
		
		String relativePath = file.getAbsolutePath()
				.substring(rootFile.getAbsolutePath().length());
		relativePath = relativePath.replace(File.separator, "/");
		int k = relativePath.lastIndexOf("/");
		r.setCategory(relativePath.substring(1, k));
		r.setPreviewUrl("/resource" + relativePath);
		r.setSnippetUrl("/resource" + relativePath);
		r.setSourceUrl("/resource" + relativePath);
		r.setDescription("这是什么? 皮卡丘.");
		r.setStatus(0);
		r.setUploader(randomUploader());
		r.setUploadTime(randomUploadTime());
		return r;
	}

	private String joinStringArray(String[] tags) {
		String s = "";
		for(String tag: tags)
			s += tag + " ";
		return s.trim();
	}

	private static Random random = new Random();
	
	private Account randomUploader() {
		int accountNumber = (int)AccountDAO.getInstance().getAllAccountCount();
		long accountId = random.nextInt(accountNumber) + 1;
		Account a = AccountDAO.getInstance().find(accountId);
		if(a == null) {
			System.err.println("Account未找到: " + accountId + ", 继续选择Account");
			return randomUploader();
		} else return a;
	}
	
	private Date randomUploadTime() {
		long from = fromTime;
		long to = toTime;
		return new Date((long)(Math.random() * (to - from) + from));
	}

	private void sortByBirthday(List<Object> resourceList) {
		Collections.sort(resourceList, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				long t1 = Resource.getResourceFromConcreteOne(o1)
						.getUploadTime().getTime();
				long t2 = Resource.getResourceFromConcreteOne(o2)
						.getUploadTime().getTime();
				if(t1 < t2)
					return -1;
				else if(t1 > t2)
					return 1;
				else
					return 0;
			}
		});
	}

	private void insertResources(List<Object> resourceList) {
		for(Object obj: resourceList) 
			insertResource(obj);
	}
	
	private long prevTime = 0;
	
	private void insertResource(Object obj) {
		Resource r = Resource.getResourceFromConcreteOne(obj);
		int k = r.getCategory().indexOf("/");
		if(k == -1)
			k = r.getCategory().length();
		String categoryType = r.getCategory().substring(0, k);

		//首先验证obj类与ResourceType一致
		if(ResourceType.getResourceType(obj.getClass()) != r.getType()) {
			System.err.println("obj类与ResourceType不一致");
			System.exit(-1);
		} 
		//接着验证categoryType与ResourceType一致
		if(ResourceType.valueOf(categoryType.toUpperCase()) != r.getType()) {
			System.err.println("categoryType与ResourceType不一致");
			System.exit(-1);
		}
		
		if(r.getUploadTime().getTime() < prevTime) {
			System.err.println("尝试穿越时空插入数据, 检查SORT方法");
		} else 
			prevTime = r.getUploadTime().getTime();
		
		try {
			switch(r.getType()) {
			case DOCUMENT:
				DocumentDAO.getInstance().insert((Document)obj);
				break;
			case IMAGE:
				ImageDAO.getInstance().insert((Image)obj);
				break;
			case MUSIC:
				MusicDAO.getInstance().insert((Music)obj);
				break;
			case VIDEO:
				VideoDAO.getInstance().insert((Video)obj);
				break;
			default: throw new RuntimeException();
			}
		} catch(Exception ex) {
			System.err.println("插入资源失败: " + obj);
		}
	}

	public static List<File> getFileListFromRoot(File root) {
		List<File> fileList = new ArrayList<File>();
		File[] listFiles = root.listFiles();
		if(listFiles == null) return fileList;
		for(File file: listFiles) {
			if(file.isDirectory())
				fileList.addAll(getFileListFromRoot(file));
			else if(file.isFile())
				fileList.add(file);
			else
				System.err.println("未知文件类型: " + file.getAbsolutePath());
		}
		return fileList;
	}
	
	private String getResourceTitle(File file) {
//		return strGenerator.generateTitle();
		String name = getResourceName(file);
		return name;
	}

	private String getResourceTag(File file) {
//		return strGenerator.generateTag();
		String name = getResourceName(file);
		String[] tags = name.split("[^A-Za-z]");
		String tag = joinStringArray(tags);
		System.out.println("File: " + file.getAbsolutePath() + " tag: " + tag);
		return tag;
	}

	private String getResourceName(File file) {
		int k = file.getName().lastIndexOf(".");
		if(k < 0) k = file.getName().length();
		return file.getName().substring(0, k);
	}
	
	public File getRootFile() {
		return rootFile;
	}

	public void setRootFile(String rootPath) {
		this.rootFile = new File(rootPath);
	}

	public long getFromTime() {
		return fromTime;
	}

	public void setFromTime(long from) {
		this.fromTime = from;
	}

	public long getToTime() {
		return toTime;
	}

	public void setToTime(long to) {
		this.toTime = to;
	}
	
}
