package view.salary.pay;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.PayDao;
import dao.UserDao;
import dao.WorkloadDao;
import model.FullTimeTeacherSalary;
import model.ManageTeacherSalary;
import model.Pay;
import model.User;
import model.Workload;
import utils.TextHelper;

/**
 * @author passerbyYSQ
 * @create 2020年3月21日 下午8:51:12
 */
public class SalaryPayPanel extends JPanel {
	private static final long serialVersionUID = -1180493806729119050L;
	
	private DecimalFormat df = new DecimalFormat("0.##");
	
	private UserDao userDao = new UserDao();
	private WorkloadDao workloadDao = new WorkloadDao();
	private PayDao payDao = new PayDao();
	
	private JTextField userNameEdit;
	private JTextField salaryTypeEdit;
	private JTextField userIdEdit;
	private JTextField postWageEdit;
	private JTextField performWageEdit;
	private JTextField tempWageEdit;
	private JTextField extraEdit;
	private JTextField lessonWageEdit;
	private JTextField manageWageEdit;
	private JTextField manageSubsidyEdit;
	private JTextField postscriptEdit;
	private JTextField yearEdit;
	private JComboBox monthSelect;
	private JButton updateBtn;
	private JButton submitBtn;
	
	private User user;
	private FullTimeTeacherSalary salary1;
	private ManageTeacherSalary salary2;
	private Pay pay;
	
	private boolean isInited = false;
	
	/**
	 * Create the panel.
	 */
	public SalaryPayPanel(User user) {
		
		this.user = user;
		
		setLayout(null);
		setLayout(null);
		
		JLabel label = new JLabel("工资发放");
		label.setFont(new Font("宋体", Font.BOLD, 22));
		label.setBounds(324, 32, 136, 34);
		add(label);
		
		JLabel lblNewLabel = new JLabel("工号：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(118, 101, 55, 22);
		add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("姓名：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(321, 101, 55, 22);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("工资发放类型：");
		lblNewLabel_1_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1.setBounds(118, 151, 136, 22);
		add(lblNewLabel_1_1_1_1);
		
		submitBtn = new JButton("发放");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isInited) {
					JOptionPane.showMessageDialog(SalaryPayPanel.this, "参数不完整，不能发放！", "提示", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				if (!checkParameters(yearEdit.getText(), tempWageEdit.getText(), extraEdit.getText())) {
					JOptionPane.showMessageDialog(SalaryPayPanel.this, "请正确填写参数", "提示", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				
				int count = payDao.addPayRecord(packToPay());
				if (count == 1) {
					submitBtn.setEnabled(false);
					updateBtn.setEnabled(true);
					JOptionPane.showMessageDialog(null, "发放成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "发放失败！", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		submitBtn.setBounds(242, 464, 97, 23);
		add(submitBtn);
		
		userNameEdit = new JTextField();
		userNameEdit.setEditable(false);
		userNameEdit.setText(user.getUserName());
		userNameEdit.setColumns(10);
		userNameEdit.setBounds(371, 101, 117, 21);
		add(userNameEdit);
		
		salaryTypeEdit = new JTextField();
		salaryTypeEdit.setEditable(false);
		salaryTypeEdit.setText(user.getSalaryType());
		salaryTypeEdit.setColumns(10);
		salaryTypeEdit.setBounds(242, 151, 117, 21);
		add(salaryTypeEdit);
		
		userIdEdit = new JTextField();
		userIdEdit.setEditable(false);
		userIdEdit.setText(user.getUserId());
		userIdEdit.setColumns(10);
		userIdEdit.setBounds(183, 101, 117, 21);
		add(userIdEdit);
		
		postWageEdit = new JTextField();
		postWageEdit.setEditable(false);
		postWageEdit.setText((String) null);
		postWageEdit.setColumns(10);
		postWageEdit.setBounds(214, 204, 117, 21);
		add(postWageEdit);
		
		JLabel lblNewLabel_1 = new JLabel("岗位工资：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(118, 204, 92, 22);
		add(lblNewLabel_1);
		
		performWageEdit = new JTextField();
		performWageEdit.setEditable(false);
		performWageEdit.setText((String) null);
		performWageEdit.setColumns(10);
		performWageEdit.setBounds(478, 204, 117, 21);
		add(performWageEdit);
		
		JLabel lblNewLabel_1_1 = new JLabel("基础绩效：");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(382, 204, 92, 22);
		add(lblNewLabel_1_1);
		
		tempWageEdit = new JTextField();
		tempWageEdit.setText((String) null);
		tempWageEdit.setColumns(10);
		tempWageEdit.setBounds(214, 262, 117, 21);
		add(tempWageEdit);
		
		JLabel lblNewLabel_1_2 = new JLabel("加班报酬：");
		lblNewLabel_1_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(118, 262, 92, 22);
		add(lblNewLabel_1_2);
		
		extraEdit = new JTextField();
		extraEdit.setText((String) null);
		extraEdit.setColumns(10);
		extraEdit.setBounds(478, 262, 117, 21);
		add(extraEdit);
		
		JLabel lblNewLabel_1_3 = new JLabel("补或扣：");
		lblNewLabel_1_3.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_3.setBounds(382, 262, 92, 22);
		add(lblNewLabel_1_3);
		
		lessonWageEdit = new JTextField();
		lessonWageEdit.setEditable(false);
		lessonWageEdit.setText((String) null);
		lessonWageEdit.setColumns(10);
		lessonWageEdit.setBounds(214, 314, 117, 21);
		add(lessonWageEdit);
		
		JLabel lblNewLabel_1_4 = new JLabel("课酬：");
		lblNewLabel_1_4.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_4.setBounds(118, 314, 92, 22);
		add(lblNewLabel_1_4);
		
		manageWageEdit = new JTextField();
		manageWageEdit.setEditable(false);
		manageWageEdit.setText((String) null);
		manageWageEdit.setColumns(10);
		manageWageEdit.setBounds(214, 366, 117, 21);
		add(manageWageEdit);
		
		JLabel lblNewLabel_1_5 = new JLabel("管理绩效：");
		lblNewLabel_1_5.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_5.setBounds(118, 366, 92, 22);
		add(lblNewLabel_1_5);
		
		manageSubsidyEdit = new JTextField();
		manageSubsidyEdit.setEditable(false);
		manageSubsidyEdit.setText((String) null);
		manageSubsidyEdit.setColumns(10);
		manageSubsidyEdit.setBounds(478, 366, 117, 21);
		add(manageSubsidyEdit);
		
		JLabel lblNewLabel_1_6 = new JLabel("管理补助：");
		lblNewLabel_1_6.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_6.setBounds(382, 366, 92, 22);
		add(lblNewLabel_1_6);
		
		postscriptEdit = new JTextField();
		postscriptEdit.setText((String) null);
		postscriptEdit.setColumns(10);
		postscriptEdit.setBounds(214, 412, 381, 21);
		add(postscriptEdit);
		
		JLabel lblNewLabel_1_7 = new JLabel("备注：");
		lblNewLabel_1_7.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_7.setBounds(118, 412, 92, 22);
		add(lblNewLabel_1_7);
		
		updateBtn = new JButton("更新");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempWage = tempWageEdit.getText();
				String extra = extraEdit.getText();
				String postscript = postscriptEdit.getText().trim();
				
				if (!checkParameters(yearEdit.getText(), tempWage, extra)) {
					JOptionPane.showMessageDialog(SalaryPayPanel.this, "请正确填写参数", "提示", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				int count = payDao.updatePayRecord(new BigDecimal(tempWage), new BigDecimal(extra), 
						postscript, pay.getPayId());
				if (count == 1) {
					JOptionPane.showMessageDialog(null, "更新成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "更新失败！", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		updateBtn.setBounds(396, 464, 97, 23);
		add(updateBtn);
		
		yearEdit = new JTextField();
		yearEdit.setText((String) null);
		yearEdit.setColumns(10);
		yearEdit.setBounds(382, 150, 71, 21);
		add(yearEdit);
		
		monthSelect = new JComboBox();
		monthSelect.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		monthSelect.setBounds(490, 150, 55, 23);
		add(monthSelect);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("年");
		lblNewLabel_1_1_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1.setBounds(463, 150, 29, 22);
		add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("月");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1_1.setBounds(556, 150, 42, 22);
		add(lblNewLabel_1_1_1_1_1_1);
		
		JButton btnNewButton = new JButton("确认更改年月");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!TextHelper.isPositiveInteger(yearEdit.getText())) {
					JOptionPane.showMessageDialog(SalaryPayPanel.this, "请正确填写参数", "提示", JOptionPane.ERROR_MESSAGE); 
					return;
				}
				
				checkState();
			}
		});
		btnNewButton.setBounds(581, 149, 117, 23);
		add(btnNewButton);
		
		JLabel label_1 = new JLabel("负数表示扣除");
		label_1.setBounds(608, 265, 90, 15);
		add(label_1);
		
		
		setYearMonth();
		
		checkState();
	}
	
	/**
	 * 参数检查
	 * @return
	 */
	private boolean checkParameters(String year, String tempWage, String extra) {
		return TextHelper.isPositiveInteger(year)
				&& TextHelper.isPositiveDouble(tempWage)
				&& TextHelper.isDouble(extra);
	}
	
	/**
	 * 检查在该年该月份的工资是否已经发放
	 */
	private void checkState() {

		Integer year = Integer.valueOf(yearEdit.getText());
		Integer month = Integer.valueOf(monthSelect.getSelectedItem().toString());
		
		pay = payDao.getPayRecord(user.getUserId(), year, month);
		if (pay != null) { 
			// 已发放
			// 设置底部按钮状态
			submitBtn.setEnabled(false);
			updateBtn.setEnabled(true);
			
			refresh(pay);
			
		} else {
			// 未发放
			submitBtn.setEnabled(true);
			updateBtn.setEnabled(false);
			// 自动计算某些工资项
			initData(year, month);
		}
	}
	
	/**
	 * 将Pay中的数据设置到界面中
	 * @param pay
	 */
	private void refresh(Pay pay) {
		postWageEdit.setText(df.format(pay.getPostWage()));
		performWageEdit.setText(df.format(pay.getPerformWage()));
		tempWageEdit.setText(df.format(pay.getTempWage()));
		extraEdit.setText(df.format(pay.getExtra()));
		postscriptEdit.setText(pay.getPostscript());
		if ("专任教师".equals(user.getSalaryType())) {
			lessonWageEdit.setText(df.format(pay.getLessonWage()));
		} else if ("非专任教师".equals(user.getSalaryType())) {
			manageWageEdit.setText(df.format(pay.getManageWage()));
			manageSubsidyEdit.setText(df.format(pay.getManageSubsidy()));
		}
	}
	
	/**
	 * 检查界面参数，并打包成Pay对象
	 * @return
	 */
	private Pay packToPay() {
		Integer year = Integer.valueOf(yearEdit.getText());
		Integer month = Integer.valueOf(monthSelect.getSelectedItem().toString());
		String tempWage = tempWageEdit.getText().trim();
		String extra = extraEdit.getText().trim();
		String postscript = postscriptEdit.getText().trim();
		
		Pay pay = null;
		if ("专任教师".equals(user.getSalaryType())) {
			salary1 = new FullTimeTeacherSalary(user, new BigDecimal(tempWage), new BigDecimal(extra), year, month);
			pay = new Pay(salary1.getResult() , null, user.getUserId(), null, salary1.getPostWage(), salary1.getPerformWage(), 
					new BigDecimal(tempWage), new BigDecimal(extra), salary1.getLessonWage(), null, null, postscript);
		
		} else if ("非专任教师".equals(user.getSalaryType())) {
			salary2 = new ManageTeacherSalary(user, new BigDecimal(tempWage), new BigDecimal(extra), year, month);
			pay = new Pay(salary2.getResult(), null, user.getUserId(), null, salary2.getPostWage(), salary2.getPerformWage(),
					new BigDecimal(tempWage), new BigDecimal(extra), null, salary2.getManageWage(), salary2.getManageSubsidy(), postscript);
		}
		return pay;
	}
	
	/**
	 * 自动计算某些工资项，并显示到界面
	 * 一定要放在setYearMonth之后
	 * @param user
	 */
	private void initData(Integer year, Integer month) {
		
		// 工资尚未发放，那么加班报酬、补扣和备注肯定是空的。不管工作量有没有录入
		tempWageEdit.setText("");
		extraEdit.setText("");
		postscriptEdit.setText("");
		
		// 在计算数据之前，判断该用户的工作量是否已经被录入了
		// 只有他的工作量录入了，才去计算数据
		Workload workload = workloadDao.getWorkload(user.getUserId(), getTerm(year, month));
		if (workload == null) {
			return;
		}
		
		if ("专任教师".equals(user.getSalaryType())) {
			salary1 = new FullTimeTeacherSalary(user, year, month);
			// 岗位工资
			postWageEdit.setText(df.format(salary1.getPostWage()));
			// 基础绩效
			performWageEdit.setText(df.format(salary1.getPerformWage()));
			// 课酬
			lessonWageEdit.setText(df.format(salary1.getLessonWage()));
			
		} else if ("非专任教师".equals(user.getSalaryType())) {
			salary2 = new ManageTeacherSalary(user, year, month);
			// 岗位工资
			postWageEdit.setText(df.format(salary2.getPostWage()));
			// 基础绩效
			performWageEdit.setText(df.format(salary2.getPerformWage()));
			// 管理绩效
			manageWageEdit.setText(df.format(salary2.getManageWage()));
			// 管理补助
			manageSubsidyEdit.setText(df.format(salary2.getManageSubsidy()));
		}
		
		// 成功计算某些工资项
		isInited = true;

	}
	
	/**
	 * 根据年月得出学期的字符串
	 * 例如：2020年4月  -> 2019-2020（2）
	 * @param year
	 * @param month
	 * @return
	 */
	private String getTerm(Integer year, Integer month) {
		if (month >= 9) {
			// 第1学期
			return  year + "-" + (year+1) + "（1）";
		} else if (month >= 2) {
			// 第2学期
			return  (year-1) + "-" + year + "（2）";
		}
		return null;
	}
	
	/**
	 * 获取当前时间的年份和月份，更新到界面
	 */
	private void setYearMonth() {
		Calendar calendar = Calendar.getInstance();
		yearEdit.setText(String.valueOf(calendar.get(Calendar.YEAR)));
		setComboBoxValue(monthSelect, String.valueOf(calendar.get(Calendar.MONTH) + 1));
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
			}
		}
	}
}
