package view.user.photo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import utils.DownloadHelper;
import utils.UploadHelper;

import javax.swing.JTextField;

/**
 * @author passerbyYSQ
 * @create 2020年3月22日 下午3:59:38
 */
public class PhotoDialog extends JDialog {

	private PhotoPanel contentPanel;
	private JTextField textField;

	private String userId;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			PhotoDialog dialog = new PhotoDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public PhotoDialog(String userId) {
		
		this.userId = userId;
		contentPanel = new PhotoPanel(userId);
		int width = 500;
		if (contentPanel.getImgWidth() > width) {
			width = contentPanel.getImgWidth();
		}
		setBounds(0, 0, width, contentPanel.getImgHeight() + 60);
		getContentPane().setLayout(new BorderLayout());
	
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				textField = new JTextField();
				buttonPane.add(textField);
				textField.setColumns(30);
			}
			{
				JButton updatePhotoBtn = new JButton("更新头像");
				updatePhotoBtn.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
						updatePhoto();
					}
				});
				updatePhotoBtn.setActionCommand("OK");
				buttonPane.add(updatePhotoBtn);
				getRootPane().setDefaultButton(updatePhotoBtn);
			}
			{
				JButton saveBtn = new JButton("另存为");
				buttonPane.add(saveBtn);
				saveBtn.addActionListener(new ActionListener() {				
					@Override
					public void actionPerformed(ActionEvent e) {
						savePhoto();
					}
				});
			}
		}
	}
	
	private void savePhoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("保存照片");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setDialogType(JFileChooser.SAVE_DIALOG); // 文件保存对话框
		
		int result = jfc.showOpenDialog(this);
		
		// 选中文件
		if (result == JFileChooser.APPROVE_OPTION) {
			File dir = jfc.getSelectedFile(); // 保存的目录
			//System.out.println(dir.getAbsolutePath());
			
			URL photoUrl = contentPanel.getUrl();
			String filename = null;
			try {
				filename = DownloadHelper.getFileName(
						(HttpURLConnection) photoUrl.openConnection());
				if (filename != null) {
					boolean res = DownloadHelper.downloadFromUrl(photoUrl, dir.getAbsolutePath());
					if (res) {
						JOptionPane.showMessageDialog(this, "保存照片成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "保存照片失败！", "提示", JOptionPane.ERROR_MESSAGE);  
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
		}
	}

	private void updatePhoto() {		
		JFileChooser jfc = new JFileChooser();
		// 这里需要限制文件格式
		jfc.setDialogTitle("选择照片");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(new PhotoFilter());
		
		int result = jfc.showOpenDialog(this);
		
		// 选中文件
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			
			// 获取选择文件的本地路径，显示在文本框中
			String localPath = selectedFile.getAbsolutePath();
			textField.setText(localPath);
			
			// 将照片上传到阿里oss
			URL url = UploadHelper.uploadPhoto(localPath, userId);
			
			// 上传成功，显示照片
			if (url != null) {
				JOptionPane.showMessageDialog(this, "上传照片成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				try {
					contentPanel.setImg(ImageIO.read(url));
					// contentPanel = new PhotoPanel(userId);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "上传照片失败！", "提示", JOptionPane.ERROR_MESSAGE);  
			}
		}
		
	}
	
	/**
	 * 照片格式的过滤器
	 * @author passerbyYSQ
	 */
	public class PhotoFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			String filename = f.getName();
			return (f.isDirectory()
					|| filename.endsWith("jpg")
					|| filename.endsWith("jpeg")
					|| filename.endsWith("png")); 
		}

		@Override
		public String getDescription() {
			return "jpg,jpeg,png";
		}
	}

}
