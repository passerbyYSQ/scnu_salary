package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.UserDao;
import model.User;

public class LoginView extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userIdEdit;
	private JPasswordField passwordEdit;
	
	private UserDao userDao = new UserDao();
	
	// 主窗口
	private MainView mainView;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					// 让窗口在屏幕中间出现
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setResizable(false);
		setTitle("欢迎登录SCNU薪酬管理系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("工号：");
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setBounds(89, 55, 88, 21);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 18));
		label_1.setBounds(89, 109, 58, 21);
		contentPane.add(label_1);
		
		userIdEdit = new JTextField();
		userIdEdit.setBounds(157, 55, 177, 21);
		contentPane.add(userIdEdit);
		userIdEdit.setColumns(10);
		
		passwordEdit = new JPasswordField();
		passwordEdit.setColumns(10);
		passwordEdit.setBounds(157, 109, 177, 21);
		contentPane.add(passwordEdit);
		
		JButton loginBtn = new JButton("登录");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 登录按钮
				login();
			}
		});
		loginBtn.setBounds(237, 178, 97, 23);
		contentPane.add(loginBtn);
		
		btnNewButton = new JButton("忘记密码");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetPasswordFrame resetPswd = new ResetPasswordFrame();
				resetPswd.setLocationRelativeTo(null);
				resetPswd.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(108, 178, 97, 23);
		contentPane.add(btnNewButton);
	}
	
	private void login() {
		String userId = userIdEdit.getText().trim();
		String password = passwordEdit.getText().trim();
		
		// 做简单的参数判断
		if ("".equals(userId) || "".equals(password)) {
			JOptionPane.showMessageDialog(this, "用户名或密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);  
			return;
		}
		
		User user = userDao.login(userId, password);
		if (user != null) {
			mainView = new MainView(user);
			mainView.setLocationRelativeTo(null);
			mainView.setVisible(true);
			//System.out.println(this);
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "登录失败：用户名或密码错误！", "提示", JOptionPane.ERROR_MESSAGE);  
		}
		
	}
}
