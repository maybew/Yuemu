package yuemu.services;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import yuemu.configure.Configure;
import yuemu.dao.DAOFactory;
import yuemu.po.Account;
import yuemu.po.Sex;

public class UserService {
	public boolean updateUserInfo(HttpServletRequest req, Account account) {

		account.setAddress(req.getParameter("address"));

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Timestamp ts = new Timestamp(format.parse(
					req.getParameter("birthday")).getTime());
			account.setBirthday(ts);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		account.setContact(req.getParameter("contact"));
		account.setName(req.getParameter("name"));
		account.setPassword(req.getParameter("password"));
		account.setProfession(req.getParameter("profession"));
		account.setSex(req.getParameter("sex").equals("0") ? Sex.MALE
				: Sex.FEMALE);
		DAOFactory.getAccountDAO().update(account);
		return true;
	}

	public String processUploadPortrait(List<FileItem> fileList) {
		String relativePath = Configure.MIRRORPATH + "/portrait";
		String extName = "";
		String absoName = Configure.ROOTPATH;
		String useremail = null;

		Iterator<FileItem> it = fileList.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				String oldName = item.getName();
				if (oldName == null || oldName.trim().equals("")) {
					continue;
				}
				if (oldName.lastIndexOf(".") >= 0) {
					extName = oldName.substring(oldName.lastIndexOf("."));
				}
				File savePath = new File(absoName + "\\" + relativePath);
				if (!savePath.exists())
					savePath.mkdirs();
				relativePath = relativePath + "/"
						+ UUID.randomUUID().toString() + extName;
				absoName += "\\" + relativePath;

				File outFile = new File(absoName);
				try {
					item.write(outFile);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				Account account = null;
				try {
					account = DAOFactory.getAccountDAO().findByEmail(useremail);
					File oldFile = new File(Configure.ROOTPATH
							+ account.getPortrait());
					if (oldFile.exists())
						oldFile.delete();
					account.setPortrait(relativePath);
					DAOFactory.getAccountDAO().update(account);
				} catch (Exception e) {
					if (outFile.exists()) {
						outFile.delete();
					}
					continue;
				}
			} else {
				String filedName = item.getFieldName();
				if (filedName.equals("uploaderEmail")) {
					useremail = item.getString();
				}
			}
		}
		return relativePath;
	}
}
