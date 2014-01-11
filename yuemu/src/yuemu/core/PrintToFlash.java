package yuemu.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;

import yuemu.configure.P2FConst;

public class PrintToFlash {
	private String inFilePath = "";
	private String mirrorPath = "";
	private ExecutorService pool = Executors.newSingleThreadExecutor();
	private final static PrintToFlash printToFlash = new PrintToFlash();

	private PrintToFlash() {
	}

	public static PrintToFlash getInstance() {
		return printToFlash;
	}

	public void convertToFlash(String inFilePath, String mirrorPath) {
		this.inFilePath = inFilePath;
		this.mirrorPath = mirrorPath;

		PrintToFlashThread prft = new PrintToFlashThread();
		pool.execute(prft);
		
		System.out.println("Add to the queue : " + inFilePath);
	}
	
	public void moveToMirrorPath() {
		File out = new File(mirrorPath);
		
		File flashFile = new File(inFilePath + ".swf");
		File imgFile = new File(inFilePath + "_files/thumbnail_1.png");
		
		if(flashFile.exists()) 
			flashFile.renameTo(out);
		if(imgFile.exists()) {
			File imgFileDir = imgFile.getParentFile();
			File outImgDir = new File(imgFileDir.getAbsolutePath().replaceFirst("resource", "resource_mirror"));
			if(!outImgDir.exists())
				outImgDir.mkdirs();
			imgFile.renameTo(new File(outImgDir.getPath() + "/thumbnail_1.png"));
			imgFileDir.delete();
		}
			
	}

	// API转换线程
	class PrintToFlashThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
System.out.println("prepared to covert : " + inFilePath);				
				ComThread.InitSTA();

//模拟API转换错误
//int error = 1/0;

				// 创建服务实例
				ActiveXComponent p2f = new ActiveXComponent("Print2Flash3.Server"); 

				// 配置flash
				ActiveXComponent defProfile = new ActiveXComponent(p2f.getProperty("DefaultProfile").toDispatch());
				defProfile.setProperty("InterfaceOptions", P2FConst.INTZOOMBOX
						| P2FConst.INTZOOMSLIDER | P2FConst.INTFITPAGE
						| P2FConst.INTFITWIDTH | P2FConst.INTPREVPAGE
						| P2FConst.INTGOTOPAGE | P2FConst.INTNEXTPAGE
						| P2FConst.INTSEARCHBOX | P2FConst.INTSEARCHBUT
						| P2FConst.INTFULLSCREENAUTO);
				defProfile.setProperty("ProtectionOptions", P2FConst.PROTDISPRINT
						| P2FConst.PROTENAPI);
				defProfile.setProperty("ThumbnailPageRange", 1);
				defProfile.setProperty("ThumbnailImageWidth", 530);
				defProfile.setProperty("ThumbnailFormat", P2FConst.PNG);

				// 转换开始
				p2f.invoke("ConvertFile", inFilePath);
				
				// 移动flash、缩略图至mirror文件夹
				moveToMirrorPath();
				
System.out.println("Conversion completed successfully : " + inFilePath);
			} catch (Exception e) {
				
System.out.println("An error occurred at conversion: " + e.toString() + inFilePath);

				// API转换失败，准备开始CMD转换
				CMDPrintThread cp = new CMDPrintThread();
				
				// CMD转换开始
				pool.execute(cp);
			} finally {
				ComThread.Release();
			}

		}
		
	}

	// CMD转换线程
	class CMDPrintThread implements Runnable {
		private String defaultCommand = "p2fServer.exe";

		@Override
		public void run() {
System.out.println("try to excute cmd");
			// TODO Auto-generated method stub
			
			List<String> commandArray = new ArrayList<String>();

			commandArray.add(defaultCommand);
			commandArray.add(inFilePath);
			commandArray.add(mirrorPath);
			commandArray.add("/ThumbnailPageRange:1");
			commandArray.add("/ThumbnailFormat:2");
			commandArray.add("/ThumbnailImageWidth:530");

			ProcessBuilder pbObj = new ProcessBuilder();
			pbObj.command(commandArray);
			Map<String, String> envMap = pbObj.environment();
			envMap.clear();
			envMap.putAll(System.getenv());
			try {
				Process proObj = pbObj.start();
				final InputStream ins = proObj.getInputStream();
				final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				Thread th = new Thread() {
					public void run() {
						ReadableByteChannel rbcObj = Channels.newChannel(ins);
						try {
							while (rbcObj.read(byteBuffer) != -1) {
								byteBuffer.flip();
								byteBuffer.clear();
							}
						} catch (IOException e) {
						
						}
					}
				};
				th.setDaemon(true);
				th.start();
				try {
					proObj.waitFor();
				} catch (InterruptedException e) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	
}
