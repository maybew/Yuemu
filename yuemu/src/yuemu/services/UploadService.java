package yuemu.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import yuemu.configure.Configure;
import yuemu.core.PrintToFlash;
import yuemu.dao.DAOFactory;
import yuemu.po.Document;
import yuemu.po.Image;
import yuemu.po.Music;
import yuemu.po.Resource;
import yuemu.po.ResourceStatus;
import yuemu.po.ResourceType;
import yuemu.po.Video;
import yuemu.servlets.ExportServlet;

public class UploadService {

	public String processUploadFile(List<FileItem> fileList) {

		Iterator<FileItem> it = fileList.iterator();
		String savePath = Configure.ROOTPATH + Configure.TEMPPATH + "/";
		String oldName = "";
		String newName = "";
		String extName = "";

		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				oldName = item.getName();

				if (oldName == null || oldName.trim().equals("")) {
					continue;
				}
				// 扩展名格式：
				if (oldName.lastIndexOf(".") >= 0) {
					extName = oldName.substring(oldName.lastIndexOf("."));
				}

				File savePathFile = new File(savePath);
				if (!savePathFile.exists())
					savePathFile.mkdirs();

				File file = null;

				do {
					// 生成文件名：
					newName = UUID.randomUUID().toString();
					file = new File(savePath + newName + extName);
					System.out.println(file.getPath());
					System.out.println(file.getAbsolutePath());
				} while (file.exists());
				String fullPath = savePath + newName + extName;
				File saveFile = new File(fullPath);
				try {
					item.write(saveFile);

				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				System.out.println("this is upload");

				if (extName.equals(".zip")) {
					ExportServlet.dataHandler(saveFile);
				} else if (extName.equals(".dat")) {
					ExportServlet.dbHandler(saveFile);
				}
			}
		}
		return newName + extName;
	}

	public boolean insertResourceInfo(HttpServletRequest request, String email) {

		String type = request.getParameter("type");
		String storeName = request.getParameter("file");

		// Y.L. Wu
		// String category = request.getParameter("category");
		String category = request.getParameter("first") + "/" + request.getParameter("second");

		String inPath = Configure.TEMPPATH + "/" + storeName;
		String path = Configure.RESOURCEPATH + "/" + type + "/" + category + "/";
		String mirrorPath = Configure.MIRRORPATH + "/" + type + "/" + category + "/";

		// 准备目录
		File inFile = new File(Configure.ROOTPATH + inPath);
		File outDest = new File(Configure.ROOTPATH + path);
		if (!outDest.exists())
			outDest.mkdirs();

		path = path + storeName;
		File outFile = new File(Configure.ROOTPATH + path);
		inFile.renameTo(outFile);

		// 文档转换
		if (type.equals(Configure.DOCUMENT_VALUE)) {
			File mirrorFile = new File(Configure.ROOTPATH + mirrorPath);
			if (!mirrorFile.exists())
				mirrorFile.mkdirs();
			mirrorPath = mirrorPath + storeName + ".swf";
			PrintToFlash.getInstance().convertToFlash(Configure.ROOTPATH + path, Configure.ROOTPATH + mirrorPath);
		}

		Resource resource = new Resource();

		resource.setCategory(category);
		resource.setDescription(request.getParameter("description"));
		resource.setSourceUrl(path);
		resource.setStatus(ResourceStatus.CHECKING);
		resource.setTag(request.getParameter("tag"));
		resource.setTitle(request.getParameter("title"));
		resource.setUploader(DAOFactory.getAccountDAO().findByEmail(email));
		resource.setUploadTime(new Timestamp(new Date().getTime()));

		if (type.equals(Configure.DOCUMENT_VALUE)) {
			resource.setType(ResourceType.DOCUMENT);
			resource.setPreviewUrl(mirrorPath);
			resource.setSnippetUrl(path.replaceFirst("resource", "resource_mirror") + "_files\\thumbnail_1.png");

			return this.insertDocumentInfo(request, resource);
		} else if (type.equals(Configure.IMAGE_VALUE)) {
			resource.setType(ResourceType.IMAGE);
			resource.setPreviewUrl(path);
			resource.setSnippetUrl(path);

			return this.insertImageInfo(request, resource, path);
		} else if (type.equals(Configure.VIDEO_VALUE)) {
			resource.setType(ResourceType.VIDEO);
			resource.setPreviewUrl(path);
			resource.setSnippetUrl(path);

			return this.insertVideoInfo(request, resource);
		} else if (type.equals(Configure.MUSIC_VALUE)) {
			resource.setType(ResourceType.MUSIC);
			resource.setPreviewUrl(path);
			resource.setSnippetUrl(path);
			// resourceId = DAOFactory.getResourceDAO().insert(resource);
			return this.insertMusicInfo(request, resource);
		}

		return false;

	}

	private boolean insertMusicInfo(HttpServletRequest request, Resource resource) {

		Music music = new Music();
		music.setResource(resource);
		music.setSong(request.getParameter("song"));
		music.setSinger(request.getParameter("singer"));
		music.setAlbum(request.getParameter("album"));
		music.setGenre(request.getParameter("genre"));

		Long musicId = DAOFactory.getMusicDAO().insert(music);
		if (musicId == null)
			return false;
		else
			return true;
	}

	private boolean insertVideoInfo(HttpServletRequest request, Resource resource) {

		Video video = new Video();
		video.setResource(resource);

		Long videoId = DAOFactory.getVideoDAO().insert(video);
		if (videoId == null)
			return false;
		else
			return true;
	}

	private boolean insertImageInfo(HttpServletRequest request, Resource resource, String path) {

		Image image = new Image();
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(Configure.ROOTPATH + path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		image.setResource(resource);
		image.setHeight(bufferedImage.getHeight());
		image.setWidth(bufferedImage.getWidth());

		Long imageId = DAOFactory.getImageDAO().insert(image);
		if (imageId == null)
			return false;
		else
			return true;
	}

	private boolean insertDocumentInfo(HttpServletRequest request, Resource resource) {

		Document document = new Document();

		document.setAuthor(request.getParameter("author"));
		document.setResource(resource);

		Long documentId = DAOFactory.getDocumentDAO().insert(document);
		if (documentId == null)
			return false;
		else
			return true;
	}

}
