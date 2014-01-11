package yuemu.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class TestZip {

	public static void compress(ZipOutputStream out, File f, String path) throws IOException {
		if (f.isFile()) {
			ZipEntry entry = new ZipEntry(path);
			out.putNextEntry(entry);

			InputStream in = new FileInputStream(f);
			byte[] buf = new byte[4096];
			for (int n; (n = in.read(buf, 0, buf.length)) != -1;) {
				out.write(buf, 0, n);
			}
			in.close();

			out.closeEntry();
		} else {
			ZipEntry entry = new ZipEntry(path + "/");
			out.putNextEntry(entry);
			out.closeEntry();

			for (File sub : f.listFiles()) {
				compress(out, sub, (path.equals("") ? "" : path + "/") + sub.getName());
			}
		}
	}

	public static void extract(ZipFile in, String root) throws IOException {
		Enumeration<?> e = in.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			if (entry.isDirectory()) {
				File f = new File(root + entry.getName());
				f.mkdirs();
				continue;
			}

			File f = new File(root + entry.getName());
			f.getParentFile().mkdirs();
			OutputStream o = new BufferedOutputStream(new FileOutputStream(f));
			InputStream i = new BufferedInputStream(in.getInputStream(entry));
			byte[] buf = new byte[4096];
			for (int n; (n = i.read(buf, 0, buf.length)) != -1;) {
				o.write(buf, 0, n);
			}
			i.close();
			o.close();
		}
	}

	public static void testExport() throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream("/out.zip"));
		compress(out, new File("D:\\Work\\Current\\JEE\\yuemu\\WebContent"), "");
		out.close();
	}

	public static void testImport() throws Exception {
		ZipFile in = new ZipFile(new File("/out.zip"));
		extract(in, "/out/");
	}

	public static void main(String[] args) throws Exception {
		long t1 = System.currentTimeMillis();
		testImport();
		long t2 = System.currentTimeMillis();

		System.out.println((t2 - t1) / 1000 + " seconds");
	}
}
