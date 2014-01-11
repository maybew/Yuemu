package yuemu.dao;

public class DAOFactory {

	public static AccountDAO getAccountDAO() {
		return AccountDAO.getInstance();
	}
	
	
	
	public static VideoDAO getVideoDAO() {
		return VideoDAO.getInstance();
	}
	
	
	
	public static MusicDAO getMusicDAO() {
		return  MusicDAO.getInstance();
	}
	
	
	public static DocumentDAO getDocumentDAO() {
		return  DocumentDAO.getInstance();
	}
	
	
	public static ResourceDAO getResourceDAO() {
		return ResourceDAO.getInstance();
	}
	
	
	public static ImageDAO getImageDAO() {
		return ImageDAO.getInstance();
	}
	
	public static DownloadDAO getDownloadDAO(){
		return DownloadDAO.getInstance();
	}
	public static LogDAO getLogDAO(){
		return LogDAO.getInstance();
	}
	
	public static BrowseDAO  getBrowseDAO(){
		
		return  BrowseDAO.getInstance();
	}
	
}
