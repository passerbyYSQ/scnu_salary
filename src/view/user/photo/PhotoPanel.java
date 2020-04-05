package view.user.photo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author passerbyYSQ
 * @create 2020年3月22日 下午3:26:07
 */
public class PhotoPanel extends JPanel {
	
	private Image img;
	private URL url;
	
	private int imgWidth;
	private int imgHeight;
	
	private static final String URL_PREFIX =  
			"https://salary-management.oss-cn-shenzhen.aliyuncs.com/img/photo/";
	
	public PhotoPanel(Image img) {
		this.img = img;
		// 重置宽高
		imgWidth = img.getWidth(this);
		imgHeight = img.getWidth(this);
	}
	
	/**
	 * 照片的路径可以是本地路径也可以是网络路径
	 * @param path 
	 * @wbp.parser.constructor
	 */
	public PhotoPanel(String userId) {
		url = null;
		try {
			// 将网络路径转成URL对象
			url = new URL(URL_PREFIX + userId + ".jpg");
			// 从阿里云oss获取Image对象。如果用户之前没上传过照片，就会获取不到，会抛出异常
			img = ImageIO.read(url);
			imgWidth = img.getWidth(this);
			imgHeight = img.getWidth(this);
		} catch (Exception e) {
			// e.printStackTrace();
			JOptionPane.showMessageDialog(this, "当前用户未设置照片，尝试获取默认照片！", "提示", JOptionPane.INFORMATION_MESSAGE); 
			// 从阿里云OSS获取默认的图片
			try {
				url = new URL(URL_PREFIX + "passerby.jpg");
				img = ImageIO.read(url);
				imgWidth = img.getWidth(this);
				imgHeight = img.getWidth(this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}
	
	/**
	 * 复写基类的paint方法，该当前面板中绘制图像（显示）
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D) g).drawImage(img, 0, 0, imgWidth, imgHeight, this);	
	}
	
	@Override
	public void repaint() {
		super.repaint(0, 0, imgWidth, imgHeight);
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
		// 重置宽高
		imgWidth = img.getWidth(this);
		imgHeight = img.getWidth(this);
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
