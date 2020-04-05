package view.salary.query;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.BaseDao;
import dao.PayDao;
import model.Pay;
import model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * @author passerbyYSQ
 * @create 2020年3月24日 上午10:10:14
 */
public class SalaryDetailsPanel extends JPanel {
	private JTable table;
	private JTextField year;
	private JTextField month;
	
	private DecimalFormat df = new DecimalFormat("0.##");
	private String[] tableHead = new String[]{"发放时间", "总共发放", "岗位工资", "基础绩效", "加班报酬", "补或扣", 
			"课酬", "管理绩效", "管理补助", "备注"};
	
	private String queryType = "default";
	private int count = 2; // 每页显示的行数
	private int total; // 总记录数
	private int pages; // 总页数
	private int curPage = 0; // 当前所在页。0：第一页

	private String[][] data = new String[count][tableHead.length];
	
	private DefaultTableModel model;
	
	private PayDao payDao = new PayDao(count);
	private ButtonGroup group;
	private User user;
	
	/**
	 * Create the panel.
	 */
	public SalaryDetailsPanel(User user) {
		this.user = user;
		setLayout(null);
		
		JButton preBtn = new JButton("上一页");
		JButton nextBtn = new JButton("下一页");
		
		JLabel curPageLabel = new JLabel("");
		JLabel pagesCount = new JLabel("");
		JLabel totalCount = new JLabel("");
		
		ButtonGroup group = new ButtonGroup();
		table = new JTable(model);
		
		// 上一页
		preBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curPage--;
				curPageLabel.setText(String.valueOf(curPage + 1));
				
				//System.out.println(curPage);
				
				if (curPage < pages - 1) {
					nextBtn.setEnabled(true);
				}
				
				// 不断点击上一页可能会越界
				if (curPage <= 0) {
					preBtn.setEnabled(false);
				}
				
				updateWithPreOrNext();
			}
		});
		
		// 下一页
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curPage++;
				curPageLabel.setText(String.valueOf(curPage + 1));
				
				//System.out.println(curPage);
				
				if (curPage > 0) {
					preBtn.setEnabled(true);
				}
				
				// 不断点击下一页可能会越界
				if (curPage >= pages - 1) {
					nextBtn.setEnabled(false);
				}
				
				updateWithPreOrNext();
			}
		});
		
		
		// **********初始化数据**********
		/*
		 注意：total和pages在【第一次】初始化页面或者点击查询按钮就已经确定下来了。点击上一页和下一页，
		 total和pages是不会改变的。除非再一次点击查询或者切换到另一个用户的工资详情页面
		 */
		total = BaseDao.getCount("pay", "userId=?", user.getUserId());
		pages = (int) Math.ceil((double) total / count);
		List<Pay> pays = payDao.getPayRecords(user.getUserId(), curPage);
		refreshTable(pays);
		
		// 根据curPage和pages判断上一页和下一页是否可以点击
		if (curPage <= 0) {
			preBtn.setEnabled(false);
		} else {
			preBtn.setEnabled(true); // 不能少！！
			// 假如某个人的记录只有一页。两个都被禁用了，我切换到另外一个人，两个按钮不会被解除禁用
		}
		if (curPage >= pages - 1) {
			nextBtn.setEnabled(false);
		} else {
			nextBtn.setEnabled(true); // 不能少！！
		}
		curPageLabel.setText(String.valueOf(curPage + 1));
		pagesCount.setText(String.valueOf(pages));
		totalCount.setText(String.valueOf(total));
		// **********初始化数据**********
		
		model = new DefaultTableModel(data, tableHead);
		table.setBounds(10, 68, 732, 420);
		TableColumnModel colmodel = table.getColumnModel();
		colmodel.getColumn(0).setPreferredWidth(125);
		
		preBtn.setBounds(102, 498, 97, 23);
		add(preBtn);
		
		nextBtn.setBounds(554, 498, 97, 23);
		add(nextBtn);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 87, 732, 401);
		add(scrollPane);
		
		JRadioButton recentThreeMon = new JRadioButton("最近3个月");
		recentThreeMon.setBounds(44, 25, 85, 23);
		add(recentThreeMon);
		
		JRadioButton recentHalfYear = new JRadioButton("最近半年");
		recentHalfYear.setBounds(131, 25, 85, 23);
		add(recentHalfYear);
		
		JRadioButton recentOneYear = new JRadioButton("最近1年");
		recentOneYear.setBounds(218, 25, 85, 23);
		add(recentOneYear);
		
		JRadioButton customize = new JRadioButton("自定义");
		customize.setBounds(305, 25, 61, 23);
		add(customize);
		
		year = new JTextField();
		year.setBounds(372, 25, 66, 21);
		add(year);
		year.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("年");
		lblNewLabel.setBounds(445, 29, 12, 15);
		add(lblNewLabel);
		
		month = new JTextField();
		month.setColumns(10);
		month.setBounds(467, 25, 66, 21);
		add(month);
		
		JLabel lblNewLabel_1 = new JLabel("月");
		lblNewLabel_1.setBounds(540, 29, 12, 15);
		add(lblNewLabel_1);
		
		JButton queryBtn = new JButton("查询");
		queryBtn.setBounds(586, 25, 97, 23);
		add(queryBtn);
		
		group.add(recentThreeMon);
		group.add(recentHalfYear);
		group.add(recentOneYear);
		group.add(customize);
		
		// 只有选择了自定义的radio，两个文本编辑框才能可编辑
		year.setEditable(false);
		month.setEditable(false);
		
		JLabel lblNewLabel_2 = new JLabel("工号：");
		lblNewLabel_2.setBounds(10, 54, 36, 15);
		add(lblNewLabel_2);
		
		JLabel userId = new JLabel("");
		userId.setBounds(44, 54, 113, 15);
		userId.setText(user.getUserId());
		add(userId);
		
		JLabel userName = new JLabel("");
		userName.setBounds(195, 54, 108, 15);
		userName.setText(user.getUserName());
		add(userName);
		
		JLabel lblNewLabel_2_1 = new JLabel("姓名：");
		lblNewLabel_2_1.setBounds(161, 54, 36, 15);
		add(lblNewLabel_2_1);
		
		JLabel label = new JLabel("第");
		label.setBounds(263, 506, 17, 15);
		add(label);
		
		JLabel lblNewLabel_3 = new JLabel("页");
		lblNewLabel_3.setBounds(320, 506, 17, 15);
		add(lblNewLabel_3);
		
		
		curPageLabel.setBounds(296, 506, 27, 15);
		add(curPageLabel);
		
		JLabel lblNewLabel_3_1 = new JLabel("页");
		lblNewLabel_3_1.setBounds(463, 506, 17, 15);
		add(lblNewLabel_3_1);
		
		pagesCount.setBounds(439, 506, 17, 15);
		add(pagesCount);
		
		JLabel label_1 = new JLabel("共");
		label_1.setBounds(406, 506, 17, 15);
		add(label_1);
		
		JLabel lblNewLabel_4 = new JLabel("共有");
		lblNewLabel_4.setBounds(614, 58, 27, 15);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("条记录");
		lblNewLabel_5.setBounds(678, 58, 36, 15);
		add(lblNewLabel_5);
		
		
		totalCount.setBounds(653, 58, 17, 15);
		add(totalCount);
		
		// 单独监听自定义的radio
		customize.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (customize.isSelected()) {
					year.setEditable(true);
					month.setEditable(true);
				} else {
					year.setEditable(false);
					month.setEditable(false);
				}
			}
		});
		
		queryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				curPage = 0; // ！！！不要忘了！！！
				// System.out.println(curPage);
				
				boolean hasSelected = false;
				JRadioButton rb = null;
				for (Enumeration ele = group.getElements(); ele.hasMoreElements();) {
				      rb = (JRadioButton) ele.nextElement();
				      if (rb.isSelected()) {
				    	  hasSelected = true;
				    	  break;
				      }
				    }
				if (!hasSelected) {
					// 未选择任何一个单选按钮就点击了查询按钮
					JOptionPane.showMessageDialog(SalaryDetailsPanel.this, "请选中单选按钮！", 
							"提示", JOptionPane.WARNING_MESSAGE); 
				} else {
					List<Pay> pays = null;
					queryType = rb.getText();
					// 通过查询操作，更新表格数据
					switch(queryType) { // 要明确当前是哪种查询类型
						case "最近3个月": 
						case "最近半年":
						case "最近1年": {
							total = payDao.getTotal(user.getUserId(), queryType);
							pages = (int) Math.ceil((double) total / count);
							pays = payDao.getPayRecords(user.getUserId(), queryType, curPage);
							break;
						}
						case "自定义": {
							// 如果用户填写的年月非法，则在转换过程中就会抛出异常。之后代码都不会被执行
							int year1 = Integer.parseInt(year.getText());
							int month1 = Integer.parseInt(month.getText());
							total = BaseDao.getCount("pay", "userId=? and YEAR(payTime)=? and MONTH(payTime)=?",
									user.getUserId(), year1, month1);
							pages = (int) Math.ceil((double) total / count);
							pays = payDao.getPayRecords(user.getUserId(), year1, month1, curPage);
							break;
						}
					}
					
					// 根据curPage和pages判断上一页和下一页是否可以点击
					// 注意pages是在switch中才更新的
					if (curPage <= 0) {
						preBtn.setEnabled(false);
					} else {
						preBtn.setEnabled(true); // 不能少！
						// 假如只有1页，两个按钮都被禁用了。我再点击另外的查询，那么两个按钮不会被解除禁用
					}
					if (curPage >= pages - 1) {
						nextBtn.setEnabled(false);
					} else {
						nextBtn.setEnabled(true); // 不能少！
					}
					
					curPageLabel.setText(String.valueOf(curPage + 1));
					pagesCount.setText(String.valueOf(pages));
					totalCount.setText(String.valueOf(total));
					// 将数据设置到二维的字符串数组data中去
					refreshTable(pays);
				}				
			}
		});
	}
	
	private void updateWithPreOrNext() {
		List<Pay> pays = null;	
		switch(queryType) { // 要明确当前是哪种查询类型
			case "最近3个月": 
			case "最近半年":
			case "最近1年": {
				pays = payDao.getPayRecords(user.getUserId(), queryType, curPage);
				break;
			}
			case "自定义": {
				int year1 = Integer.parseInt(year.getText());
				int month1 = Integer.parseInt(year.getText());
				pays = payDao.getPayRecords(user.getUserId(), year1, month1, curPage);
				break;
			}
			default: {
				pays = payDao.getPayRecords(user.getUserId(), curPage);
			}
		}
		// 将数据设置到二维的字符串数组data中去
		refreshTable(pays);
	}
	
	/**
	 * 将List<Pay>的数据放到Stringp[][] data中去
	 * @param pays
	 */
	private void refreshTable(List<Pay> pays) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = "";
			}
		}
		
		for (int i = 0; i < pays.size(); i++) {	
			Pay pay = pays.get(i);
			int col = 0;
			data[i][col++] = pay.getPayTime();
			data[i][col++] = df.format(pay.getResult());
			data[i][col++] = df.format(pay.getPostWage()); //pay.getPostWage().toString();
			data[i][col++] = df.format(pay.getPerformWage()); // pay.getPerformWage().toString();
			data[i][col++] = df.format(pay.getTempWage());//pay.getTempWage().toString();
			data[i][col++] = df.format(pay.getExtra());//pay.getExtra().toString();
			data[i][col++] = df.format(pay.getLessonWage());//pay.getLessonWage().toString();
			data[i][col++] = df.format(pay.getManageWage());//pay.getManageWage().toString();
			data[i][col++] = df.format(pay.getManageSubsidy());//pay.getManageSubsidy().toString();
			data[i][col++] = pay.getPostscript();//pay.getPostscript();
		}
		model = new DefaultTableModel(data, tableHead);
		table.setModel(model);
		TableColumnModel colmodel = table.getColumnModel();
		colmodel.getColumn(0).setPreferredWidth(125);
	}
	
	
}
