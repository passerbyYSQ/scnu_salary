package view.user.info;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.UserDao;
import model.User;
import view.user.photo.PhotoDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author passerbyYSQ
 * @create 2020年3月21日 下午8:51:12
 */
public class UserDetailsPanel extends JPanel {
	private static final long serialVersionUID = -1180493806729119050L;
	private JTextField userIdEdit;
	private JTextField userNameEdit;
	private JTextField phoneEdit;
	private static String DEFAULT_PASSWORD = "123456";
	
	private UserDao userDao = new UserDao();
	private JTextField postEdit;
	private JTextField depEdit;
	private JTextField proTitleEdit;
	private JTextField salaryTypeEdit;
	private JTextField loginAuthEdit;
	private JTextField sexEdit;
	private JTextField emailEdit;

	/**
	 * Create the panel.
	 */
	public UserDetailsPanel(User user) {
		setLayout(null);
		setLayout(null);
		
		JLabel label = new JLabel("用户信息");
		label.setFont(new Font("宋体", Font.BOLD, 22));
		label.setBounds(324, 32, 136, 34);
		add(label);
		
		JLabel lblNewLabel = new JLabel("编号：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(86, 104, 79, 22);
		add(lblNewLabel);
		
		userIdEdit = new JTextField();
		userIdEdit.setEditable(false);
		userIdEdit.setBounds(146, 104, 155, 21);
		userIdEdit.setText(user.getUserId());
		add(userIdEdit);
		userIdEdit.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("职务：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(399, 104, 103, 22);
		add(lblNewLabel_1);
		
		userNameEdit = new JTextField();
		userNameEdit.setEditable(false);
		userNameEdit.setColumns(10);
		userNameEdit.setBounds(146, 164, 155, 21);
		userNameEdit.setText(user.getUserName());
		add(userNameEdit);
		
		JLabel lblNewLabel_2 = new JLabel("姓名：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(86, 164, 55, 22);
		add(lblNewLabel_2);
		
		phoneEdit = new JTextField();
		phoneEdit.setEditable(false);
		phoneEdit.setColumns(10);
		phoneEdit.setBounds(146, 356, 155, 21);
		phoneEdit.setText(user.getPhone());
		add(phoneEdit);
		
		JLabel lblNewLabel_3 = new JLabel("手机：");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(86, 356, 79, 22);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_1_1 = new JLabel("性别：");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(86, 229, 68, 22);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("职称级别：");
		lblNewLabel_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(399, 229, 129, 22);
		add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("邮箱：");
		lblNewLabel_1_1_3.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_3.setBounds(86, 294, 103, 22);
		add(lblNewLabel_1_1_3);
		
		JLabel lblNewLabel_1_2 = new JLabel("部门：");
		lblNewLabel_1_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(399, 164, 129, 22);
		add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("工资发放类型：");
		lblNewLabel_1_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1.setBounds(399, 294, 129, 22);
		add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("登录权限：");
		lblNewLabel_1_1_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1.setBounds(399, 356, 129, 22);
		add(lblNewLabel_1_1_1_1_1);

		
		postEdit = new JTextField();
		postEdit.setEditable(false);
		postEdit.setColumns(10);
		postEdit.setBounds(534, 104, 155, 21);
		postEdit.setText(user.getPost());
		add(postEdit);
		
		depEdit = new JTextField();
		depEdit.setEditable(false);
		depEdit.setColumns(10);
		depEdit.setBounds(534, 164, 155, 21);
		depEdit.setText(user.getDepName()); 
		add(depEdit);
		
		proTitleEdit = new JTextField();
		proTitleEdit.setEditable(false);
		proTitleEdit.setColumns(10);
		proTitleEdit.setBounds(534, 229, 155, 21);
		proTitleEdit.setText(user.getProTitle());
		add(proTitleEdit);
		
		salaryTypeEdit = new JTextField();
		salaryTypeEdit.setEditable(false);
		salaryTypeEdit.setColumns(10);
		salaryTypeEdit.setBounds(534, 294, 155, 21);
		salaryTypeEdit.setText(user.getSalaryType());
		add(salaryTypeEdit);
		
		loginAuthEdit = new JTextField();
		loginAuthEdit.setEditable(false);
		loginAuthEdit.setColumns(10);
		loginAuthEdit.setBounds(534, 356, 155, 21);
		loginAuthEdit.setText(user.getLoginAuth());
		add(loginAuthEdit);
		
		sexEdit = new JTextField();
		sexEdit.setEditable(false);
		sexEdit.setColumns(10);
		sexEdit.setBounds(146, 229, 155, 21);
		sexEdit.setText(user.getSex());
		add(sexEdit);
		
		emailEdit = new JTextField();
		emailEdit.setEditable(false);
		emailEdit.setColumns(10);
		emailEdit.setBounds(146, 294, 155, 21);
		emailEdit.setText(user.getEmail());
		add(emailEdit);
		
		JButton photoBtn = new JButton("查看照片");
		photoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PhotoDialog dialog = new PhotoDialog(user.getUserId());
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		photoBtn.setBounds(324, 418, 97, 23);
		add(photoBtn);
	}
}
