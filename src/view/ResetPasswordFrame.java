package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;

import dao.UserDao;
import model.User;
import utils.EmailHelper;
import utils.RandomUtils;
import view.widget.CountDownButton;

/**
 * @author passerbyYSQ
 * @create 2020年3月31日 下午4:10:40
 */
public class ResetPasswordFrame extends JFrame {

	private JPanel contentPane;
	private JTextField contactEdit;
	private JTextField userIdEdit;
	private JTextField userNameEdit;
	private JPasswordField passwordEdit;
	private JPasswordField confirmPswdEdit;
	private JTextField codeEdit;
	private JComboBox contactSelect;
	private CountDownButton sendCodeBtn;
	
	private UserDao userDao = new UserDao();
	
	private User user;
	private String contact; // 邮箱地址或者手机号
	private String type; // email还是phone
	private String code;
	private String password;

	/**
	 * Create the frame.
	 */
	public ResetPasswordFrame() {
		
		setResizable(false);
		setTitle("重置密码");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 474, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contactSelect = new JComboBox();
		contactSelect.setModel(new DefaultComboBoxModel(new String[] {"邮箱", "手机"}));
		contactSelect.setBounds(80, 90, 76, 23);
		contentPane.add(contactSelect);
		
		JButton resetBtn = new JButton("重置密码");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkPassword() && checkCode()) {
					// 重置密码
					int count = userDao.setPassword(user.getUserId(), password);
					String msg = null;
					if (count == 1) {
						msg = "重置密码成功！";
						JOptionPane.showMessageDialog(ResetPasswordFrame.this, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
						toLogin();
						dispose();
					} else {
						msg = "重置密码失败！";
						JOptionPane.showMessageDialog(ResetPasswordFrame.this, msg, "提示", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		resetBtn.setEnabled(false);
		resetBtn.setBounds(189, 250, 97, 23);
		contentPane.add(resetBtn);
		
		JLabel lblNewLabel = new JLabel("姓名：");
		lblNewLabel.setBounds(80, 52, 58, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("新密码：");
		lblNewLabel_1.setBounds(80, 129, 58, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("工号：");
		lblNewLabel_2.setBounds(80, 13, 58, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("确认密码：");
		lblNewLabel_1_1.setBounds(80, 167, 76, 15);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_3 = new JLabel("验证码：");
		lblNewLabel_3.setBounds(80, 209, 58, 15);
		contentPane.add(lblNewLabel_3);
		
		sendCodeBtn = new CountDownButton(60, "发送验证码", 
				new CountDownButton.Action() {
			@Override
			public void beforeCountDown() {
				boolean isSuccessSend;
				if (type == "email") {
					isSuccessSend = sendCodeByEmail();
				} else {
					isSuccessSend = sendCodeByMsg();
				}
				resetBtn.setEnabled(isSuccessSend);
			}
		});
		sendCodeBtn.setBounds(338, 90, 97, 23);
		sendCodeBtn.setEnabled(false);
		contentPane.add(sendCodeBtn);
		
		contactEdit = new JTextField();
		contactEdit.setEditable(false);
		contactEdit.setBounds(166, 91, 161, 21);
		contentPane.add(contactEdit);
		contactEdit.setColumns(10);
		
		userIdEdit = new JTextField();
		userIdEdit.setColumns(10);
		userIdEdit.setBounds(166, 10, 161, 21);
		contentPane.add(userIdEdit);
		
		userNameEdit = new JTextField();
		userNameEdit.setColumns(10);
		userNameEdit.setBounds(166, 49, 161, 21);
		contentPane.add(userNameEdit);
		
		passwordEdit = new JPasswordField();
		passwordEdit.setColumns(10);
		passwordEdit.setBounds(166, 126, 161, 21);
		contentPane.add(passwordEdit);
		
		confirmPswdEdit = new JPasswordField();
		confirmPswdEdit.setColumns(10);
		confirmPswdEdit.setBounds(166, 164, 161, 21);
		contentPane.add(confirmPswdEdit);
		
		codeEdit = new JTextField();
		codeEdit.setColumns(10);
		codeEdit.setBounds(166, 206, 161, 21);
		contentPane.add(codeEdit);
		
		JButton checkUserBtn = new JButton("检查用户");
		checkUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUser();
			}
		});
		checkUserBtn.setBounds(338, 31, 97, 23);
		contentPane.add(checkUserBtn);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				toLogin();
			}
		});
	}
	
	private void checkUser() {
		String userId = userIdEdit.getText().trim();
		String userName = userNameEdit.getText().trim();
		
		String selectedItem = contactSelect.getSelectedItem().toString();
		
		user = userDao.checkUser(userId, userName);
		String msg = null;
		if (user != null) {
			String contact = null;
			if ("邮箱".equals(selectedItem)) {
				type = "email";
				contact = user.getEmail();
			} else {
				type = "phone";
				contact = user.getPhone();
			}
			
			if (contact != null) {
				this.contact = contact;
				contactEdit.setText(this.contact);
				sendCodeBtn.setEnabled(true);
			} else {
				 msg = "该用户尚未绑定邮箱或手机！";
				JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			msg = "用户不存在，请检查工号和姓名是否正确！";
			JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean sendCodeByEmail() {
		// 生成随机的验证码
		code = RandomUtils.getMixedStr(6);
		String htmlBody = String.format(
				"<p>您正在尝试重置登录密码，验证码为：<b>%s</b>！</p>", code);
		
		EmailHelper.EmailBean email = new EmailHelper.EmailBean
				("SCNU薪酬管理系统", contact, "重置密码", htmlBody);
		SingleSendMailResponse response = EmailHelper.singleSend(email);
		String msg = null;
		if (response != null) {
			msg = "验证码已发送至您的邮箱" + contact + "，请注意查收！注意邮件是否被拦截至垃圾箱！";
			JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			msg = "邮件发送失败！";
			JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private boolean sendCodeByMsg() {
		return false;
	}
	
	private boolean checkPassword() {
		String pswd = passwordEdit.getText().trim();
		String confirmPswd = confirmPswdEdit.getText().trim();
		
		String msg = null;
		if (!pswd.equals(confirmPswd)) {
			msg = "输入的两次密码不一致！";
			JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if ("".equals(pswd) || "".equals(confirmPswd)) {
			msg = "密码不能为空！";
			JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		password = pswd;
		return true;
	}
	
	private boolean checkCode() {
		String code1 = codeEdit.getText().trim();
		if (!code.equals(code1)) {
			String msg = "验证码错误！";
			JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void toLogin() {
		LoginView frame = new LoginView();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
	
}
