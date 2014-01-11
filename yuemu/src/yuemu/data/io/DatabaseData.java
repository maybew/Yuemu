package yuemu.data.io;

import java.io.Serializable;
import java.util.List;

import yuemu.core.Debug;
import yuemu.po.Account;
import yuemu.po.Document;
import yuemu.po.Image;
import yuemu.po.Music;
import yuemu.po.Resource;
import yuemu.po.Video;

public class DatabaseData implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Account> accountList;
	
	public List<Resource> resourceList;
	
	public List<Document> documentList;
	
	public List<Image> imageList;
	
	public List<Music> musicList;
	
	public List<Video> videoList;
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("Account List: ");
		strBuilder.append(Debug.collectionToString(accountList));
		strBuilder.append("---------------------------------------\n");
		
		strBuilder.append("Resource List: ");
		strBuilder.append(Debug.collectionToString(resourceList));
		strBuilder.append("---------------------------------------\n");
		
		strBuilder.append("Document List: ");
		strBuilder.append(Debug.collectionToString(documentList));
		strBuilder.append("----------------------------------------\n");
		
		strBuilder.append("Image List: ");
		strBuilder.append(Debug.collectionToString(imageList));
		strBuilder.append("----------------------------------------\n");
		
		strBuilder.append("Music List: ");
		strBuilder.append(Debug.collectionToString(musicList));
		strBuilder.append("----------------------------------------\n");
		
		strBuilder.append("Video List: ");
		strBuilder.append(Debug.collectionToString(videoList));
		strBuilder.append("----------------------------------------\n");
		
		return strBuilder.toString();
	}
	
}
