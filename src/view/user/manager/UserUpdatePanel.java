package view.user.manager;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import dao.DepartmentDao;
import dao.UserDao;
import model.Department;
import model.User;
import utils.TextHelper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * @author passerbyYSQ
 * @create 2020年3月21日 下午8:51:12
 */
public class UserUpdatePanel extends JPanel {
	private static final long serialVersionUID = -1180493806729119050L;	
	
	private JTextField userIdEdit;
	private JTextField userNameEdit;
	private JComboBox sexSelect;
	private JTextField emailEdit;
	private JTextField phoneEdit;
	
	private JComboBox postSelect;
	private JComboBox depSelect;
	private JComboBox proTitleSelect;
	private JComboBox salaryTypeSelect;
	private JComboBox loginAuthSelect;
	
	
	private UserDao userDao = new UserDao();
	private DepartmentDao depDao = new DepartmentDao();
	private List<Department> deps;
	

	/**
	 * Create the panel.
	 */
	public UserUpdatePanel(User user) {
		setLayout(null);
		setLayout(null);
		
		JLabel label = new JLabel("修改用户信息");
		label.setFont(new Font("宋体", Font.BOLD, 22));
		label.setBounds(315, 32, 155, 34);
		add(label);
		
		JLabel lblNewLabel = new JLabel("编号：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(86, 104, 79, 22);
		add(lblNewLabel);
		
		userIdEdit = new JTextField();
		userIdEdit.setBounds(146, 104, 155, 21);
		userIdEdit.setText(user.getUserId());
		add(userIdEdit);
		userIdEdit.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("职称级别：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(399, 229, 103, 22);
		add(lblNewLabel_1);
		
		userNameEdit = new JTextField();
		userNameEdit.setColumns(10);
		userNameEdit.setText(user.getUserName());
		userNameEdit.setBounds(146, 162, 155, 21);
		add(userNameEdit);
		
		JLabel lblNewLabel_2 = new JLabel("姓名：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(86, 162, 55, 22);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("职务：");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(399, 104, 79, 22);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("登录权限：");
		lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(399, 356, 103, 22);
		add(lblNewLabel_4);
		
		sexSelect = new JComboBox();
		sexSelect.setModel(new DefaultComboBoxModel(new String[] {"请选择", "男", "女"}));
		setComboBoxValue(sexSelect, user.getSex());
		sexSelect.setBounds(146, 229, 155, 23);
		add(sexSelect);
		
		JLabel lblNewLabel_1_1 = new JLabel("性别：");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(86, 229, 68, 22);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("部门：");
		lblNewLabel_1_1_3.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_3.setBounds(399, 162, 103, 22);
		add(lblNewLabel_1_1_3);
		
		loginAuthSelect = new JComboBox();
		loginAuthSelect.setModel(new DefaultComboBoxModel(new String[] {"教师", "财务员", "财务主管领导"}));
		setComboBoxValue(loginAuthSelect, user.getLoginAuth());
		loginAuthSelect.setBounds(538, 356, 155, 23);
		add(loginAuthSelect);
		
		JButton createUserBtn = new JButton("创建用户");
		createUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 创建新用户
				// 获取参数值，封装成User对象
				User user = packToUser();
				if (user == null || userDao.getUserById(user.getUserId()) != null) {
					JOptionPane.showMessageDialog(null, "参数不正确或者工号已存在！", "提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int count = userDao.addUser(user);
				if (count == 1) {
					JOptionPane.showMessageDialog(null, "添加用户成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "添加用户失败！", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		createUserBtn.setBounds(345, 430, 97, 23);
		add(createUserBtn);
		
		JLabel lblNewLabel_2_1 = new JLabel("邮箱：");
		lblNewLabel_2_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2_1.setBounds(86, 293, 55, 22);
		add(lblNewLabel_2_1);
		
		emailEdit = new JTextField();
		emailEdit.setColumns(10);
		emailEdit.setText(user.getEmail());
		emailEdit.setBounds(146, 293, 155, 21);
		add(emailEdit);
		
		JLabel lblNewLabel_2_2 = new JLabel("手机：");
		lblNewLabel_2_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2_2.setBounds(86, 356, 55, 22);
		add(lblNewLabel_2_2);
		
		phoneEdit = new JTextField();
		phoneEdit.setColumns(10);
		phoneEdit.setText(user.getPhone());
		phoneEdit.setBounds(146, 356, 155, 21);
		add(phoneEdit);
		
		JLabel lblNewLabel_1_2 = new JLabel("工资发放类型：");
		lblNewLabel_1_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(399, 293, 129, 22);
		add(lblNewLabel_1_2);
		
		salaryTypeSelect = new JComboBox();
		salaryTypeSelect.setModel(new DefaultComboBoxModel(new String[] {"专任教师", "非专任教师"}));
		setComboBoxValue(salaryTypeSelect, user.getSalaryType());
		salaryTypeSelect.setBounds(538, 293, 155, 23);
		add(salaryTypeSelect);
		
		proTitleSelect = new JComboBox();
		proTitleSelect.setModel(new DefaultComboBoxModel(new String[] {"讲师", "教授", "副教授", "助教"}));
		setComboBoxValue(proTitleSelect, user.getProTitle());
		proTitleSelect.setBounds(538, 229, 155, 23);
		add(proTitleSelect);
		
		// 从数据库查询出所有部门
		deps = depDao.getAllDeps();
		depSelect = new JComboBox();
		// deps.toArray(new Department[0]) 是将List<Department>转换成Department[]
		// 也可以用集合工具类CollectionUtil实现
		depSelect.setModel(new DefaultComboBoxModel(deps.toArray(new Department[0])));
		setComboBoxValue(depSelect, String.valueOf(user.getDepId()));
		depSelect.setBounds(538, 162, 155, 23);
		add(depSelect);
		
		postSelect = new JComboBox();
		postSelect.setModel(new DefaultComboBoxModel(new String[] {"其他", "院长/书记", "副院长/副书记", "系/部/中心主任", "系/部/中心副主任"}));
		setComboBoxValue(postSelect, user.getPost());
		postSelect.setBounds(538, 104, 155, 23);
		add(postSelect);
		
		JButton updateUserBtn = new JButton("更新用户");
		updateUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = packToUser();
				if (user == null) {
					JOptionPane.showMessageDialog(null, "参数不正确或者工号已存在！", "提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int count = userDao.updateUser(user);
				if (count == 1) {
					JOptionPane.showMessageDialog(null, "更新用户信息成功！刷新左边用户树后才可查看更新后的信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
					
					// TODO 界面不更新。原因是内存中的User是程序启动时查询出来的。
					
				} else {
					JOptionPane.showMessageDialog(null, "更新用户信息失败！", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		updateUserBtn.setBounds(484, 430, 97, 23);
		add(updateUserBtn);
		
		JButton delUserBtn = new JButton("删除用户");
		delUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 暂时不做。因为删除用户，应该考虑他的工资发放记录是否删除
			}
		});
		delUserBtn.setBounds(197, 430, 97, 23);
		add(delUserBtn);

	}

	// 设置下拉菜单的选项
	private void setComboBoxValue(JComboBox comboBox, String value) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			Object item = comboBox.getItemAt(i);
			
			if (item instanceof String) {
				String itemStr = (String) item;
				if (itemStr.equals(value)) {
					comboBox.setSelectedIndex(i);
					break;
				}
			} else if (item instanceof Department) {
				Department dep = (Department) item;
				if (String.valueOf(dep.getDepId()).equals(value)) {
					comboBox.setSelectedIndex(i);
					break;
				}
			}
		}
	}
	
	private User packToUser() {
		String userId = userIdEdit.getText().trim();
		String userName = userNameEdit.getText().trim();
		String sex = sexSelect.getSelectedItem().toString();
		String email = emailEdit.getText().trim();
		String phone = phoneEdit.getText().trim();
		String post = postSelect.getSelectedItem().toString();
		// 部门
		Department dep = (Department) depSelect.getSelectedItem();
		Integer depId = dep.getDepId();
		
		String proTitle = proTitleSelect.getSelectedItem().toString();
		String salaryType = salaryTypeSelect.getSelectedItem().toString();
		String loginAuth = loginAuthSelect.getSelectedItem().toString();
		
		// 参数判断
		if (TextHelper.isNullOrEmpty(userId)
				|| TextHelper.isNullOrEmpty(userName)
				|| "请选择".equals(sex)
				|| (!"".equals(email) && TextHelper.isEmail(email))
				|| (!"".equals(phone) && TextHelper.isPhone(phone))) {
			return null;
		}
		
		// 无需设置密码。密码字段有默认值为：123456
		return new User(userId, null, userName, sex, depId, null, loginAuth,
				proTitle, post, salaryType, email, phone);
	}
}


