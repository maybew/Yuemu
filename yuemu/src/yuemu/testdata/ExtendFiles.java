package yuemu.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**扩展文件夹下的文件数量. 
 * 
 * @author Administrator
 *
 */
public class ExtendFiles {

	public static void main(String[] args) throws Exception {
		File rootFile = new File("d:\\testdata\\test\\image");
		extend(rootFile, 8);
	}
	
	public static void extend(File rootDir, int num) throws IOException {
		if(num <= 0) return;
		File[] fileList = rootDir.listFiles();
		if(fileList != null) 
			for(int i = 0; i < fileList.length; ++i) {
				File f = fileList[i];
				if(f.isDirectory())
					extend(f, num);
				else if(f.isFile())
					copyFile(f, num);
				else
					System.err.println("What is it: " + f);
			}
	}

	private static void copyFile(File f, int num) throws IOException {
		for(int i = 0; i < num; ++i)
			copyFile(f, makeFileName(f, i));
	}

	private static String makeFileName(File f, int num) {
		File parent = f.getParentFile();
		String fileName = f.getName();
		int k = fileName.lastIndexOf(".");
		if(k < 0) k = fileName.length();
		String mainName = fileName.substring(0, k);
		String suffix = fileName.substring(k);
		return parent.getAbsolutePath() + "\\" + mainName + "_yuemu_" + num + suffix;
	}

	private static void copyFile(File f, String dstFile) throws IOException {
		InputStream in = new FileInputStream(f);
		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		in.close();
		OutputStream out = new FileOutputStream(dstFile);
		out.write(bytes);
		out.close();
	}
	
}
