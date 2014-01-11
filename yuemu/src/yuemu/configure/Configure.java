package yuemu.configure;


public class Configure {
	//int value of all type resource
	public final static int DOCUMENT = 0;
	public final static int IMAGE = 1;
	public final static int MUSIC = 2;
	public final static int VIDEO = 3;
	public final static int ALL = 4;
	
	public static String ROOTPATH = "D:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\yuemu";
	public static String MIRRORPATH = "/resource_mirror";
	public static String RESOURCEPATH = "/resource";
	public static String TEMPPATH = MIRRORPATH + "/temp";
	
	public final static String VIDEO_VALUE = "video";
	public final static String MUSIC_VALUE = "music";
	public final static String IMAGE_VALUE = "image";
	public final static String DOCUMENT_VALUE = "document";
	
	public final static String[] VIDEO_EXT = {".flv"};
	public final static String[] MUSIC_EXT = {".mp3"};
	public final static String[] IMAGE_EXT = {".jpg", ".jpeg", ".png", ".bmp"};
	public final static String[] DOCUMENT_EXT = {".doc", ".docx", ".ppt", ".pptx", ".xls", ".xlsx", ".txt"};
	
	/**字符串值转化为type序号值. 是不区分大小写的. 
	 * 
	 * @param value
	 * @return
	 */
	public static int typeOrderFromString(String value) {
		String type = value.toLowerCase();
		if (type.equals("document")) {
			return Configure.DOCUMENT;
		} else if (type.equals("music")) {
			return Configure.MUSIC;
		} else if (type.equals("video")) {
			return Configure.VIDEO;
		} else if (type.equals("image")) {
			return Configure.IMAGE;
		} else {
			return Configure.ALL;
		}
	}
	
	
}
