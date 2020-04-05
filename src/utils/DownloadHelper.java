package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author passerbyYSQ
 * @create 2020年3月26日 下午11:24:29
 */
public class DownloadHelper {
	
	private static int timeout = 3000;
	/*
	public static void main(String[] args) {
		//String urlStr = "https://img-blog.csdnimg.cn/20200228105746752.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMjkwMzE4,size_16,color_FFFFFF,t_70";
		//String urlStr = "https://salary-management.oss-cn-shenzhen.aliyuncs.com/img/photo/233.jpg";
		//String urlStr = "https://dldir1.qq.com/qqyy/pc/QQPlayerSetup4.6.2.1089.exe";
		String urlStr = "https://moodle.scnu.edu.cn/pluginfile.php/521241/mod_resource/content/0/%E7%AC%AC4%E5%91%A8%20%E8%AF%BE%E5%A0%82%E7%BB%83%E4%B9%A0.pptx";
		String savePath = "C:\\Users\\Administrator\\Desktop";
		try {
			System.out.println("开始下载...");
			Long sta = System.currentTimeMillis();
			boolean res = downloadFromUrl(urlStr, savePath);
			Long end = System.currentTimeMillis();
			if (res) {
				System.out.println("下载成功！！！");
			} else {
				System.out.println("下载失败！！！");
			}
			System.out.println("耗时：" + (end - sta) + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * @param urlStr
	 * @param savePath
	 * @return
	 * @throws IOException
	 */
	public static boolean downloadFromUrl(URL url, String savePath) throws IOException {
		File destDir = new File(savePath);
		// 如果保存的目录不存在，则创建
		if (!destDir.isDirectory()) {
			destDir.mkdirs();
		}
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(timeout);
		// 获取文件名
		String fileName = getFileName(conn);
		if (fileName == null) {
			return false;
		}
		
		//防止屏蔽程序抓取而返回403错误
        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		
        // 输入流
        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		
        File destFile = new File(destDir, fileName);
        FileOutputStream fos = new FileOutputStream(destFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        byte[] buf = new byte[1024];
        int len;
        while ((len = bis.read(buf)) != -1) {
        	bos.write(buf, 0, len);
        }
        
        // 关闭外层流，内层的流也会自动地关闭
        close(bos, bis);
		return true;
	}
	
	/**
	 * 返回下载的文件名
	 * @param urlStr
	 * @param savePath
	 * @return
	 * @throws IOException
	 */
	public static boolean downloadFromUrl(String urlStr, String savePath) throws IOException {
		return downloadFromUrl(new URL(urlStr), savePath);
	}
	
	public static String getFileName(HttpURLConnection conn) {
		String fileName = getFileName1(conn);
		if (fileName != null) {
			//System.out.println("方式一");
			return fileName;
		}
		fileName = getFileName2(conn);
		//System.out.println("方式二");
		return fileName;
	}
	
	private static String getFileName2(HttpURLConnection conn) {
		String raw = conn.getHeaderField("Content-Disposition");  
		//System.out.println("raw: " + raw);
		if (raw != null && raw.indexOf("=") > 0) {            
			String fileName = raw.split("=")[1];            
			try {
				return URLDecoder.decode(fileName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	private static String getFileName1(HttpURLConnection conn) {
		String newUrl = conn.getURL().getFile();  
		//System.out.println("newUrl: " + newUrl);
		if (newUrl != null && newUrl.length() > 0) { 
			try {
				newUrl = URLDecoder.decode(newUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}    
			int pos = newUrl.indexOf('?');            
			if (pos >= 0) {                
				newUrl = newUrl.substring(0, pos);            
			}        
			pos = newUrl.lastIndexOf('/');            
			return newUrl.substring(pos + 1);        
		}
		return null;
	}
	
	public static void close(Closeable... closeables) {
        if (closeables == null)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
