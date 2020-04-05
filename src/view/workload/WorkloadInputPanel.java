package view.workload;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.UserDao;
import dao.WorkloadDao;
import model.User;
import model.Workload;
import utils.CollectionUtil;
import utils.TextHelper;
import view.user.photo.PhotoDialog;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * @author passerbyYSQ
 * @create 2020年3月21日 下午8:51:12
 */
public class WorkloadInputPanel extends JPanel
		implements ItemListener {
	private static final long serialVersionUID = -1180493806729119050L;
	
	private UserDao userDao = new UserDao();
	private WorkloadDao workloadDao = new WorkloadDao();
	
	private User user;
	
	private JTextField userNameEdit;
	private JTextField userIdEdit;
	private JTextField lessonHourEdit;
	private JTextField attendCntEdit;
	private JComboBox termSelect;
	private JButton submitBtn;
	private JButton updateBtn;
	
	/**
	 * Create the panel.
	 */
	public WorkloadInputPanel(User user) {
		this.user = user;
		
		setLayout(null);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("工号：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(172, 129, 55, 22);
		add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("姓名：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(376, 129, 55, 22);
		add(lblNewLabel_2);
		
		submitBtn = new JButton("提交");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Workload workload = packToWorkload();
				if (workload == null) {
					return;
				}
				
				int count = workloadDao.addWorkload(workload);
				if (count == 1) {
					JOptionPane.showMessageDialog(WorkloadInputPanel.this, 
							"录入成功！", "提示", JOptionPane.INFORMATION_MESSAGE); 
					submitBtn.setEnabled(false);
					updateBtn.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(WorkloadInputPanel.this, 
							"录入失败！", "提示", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		submitBtn.setBounds(243, 415, 97, 23);
		add(submitBtn);
		
		userNameEdit = new JTextField();
		userNameEdit.setEditable(false);
		userNameEdit.setText(user.getUserName());
		userNameEdit.setColumns(10);
		userNameEdit.setBounds(433, 129, 117, 21);
		add(userNameEdit);
		
		userIdEdit = new JTextField();
		userIdEdit.setEditable(false);
		userIdEdit.setText(user.getUserId());
		userIdEdit.setColumns(10);
		userIdEdit.setBounds(233, 129, 117, 21);
		add(userIdEdit);
		
		lessonHourEdit = new JTextField();
		lessonHourEdit.setText((String) null);
		lessonHourEdit.setColumns(10);
		lessonHourEdit.setBounds(325, 261, 117, 21);
		add(lessonHourEdit);
		
		JLabel lblNewLabel_1_2 = new JLabel("全日制本科课时：");
		lblNewLabel_1_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(172, 261, 161, 22);
		add(lblNewLabel_1_2);
		
		updateBtn = new JButton("更新");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Workload workload = packToWorkload();
				if (workload == null) {
					return;
				}
				int count = workloadDao.updateWorkload(workload);
				if (count == 1) {
					JOptionPane.showMessageDialog(WorkloadInputPanel.this, 
							"修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE); 
				} else {
					JOptionPane.showMessageDialog(WorkloadInputPanel.this, 
							"修改失败！", "提示", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		updateBtn.setBounds(397, 415, 97, 23);
		add(updateBtn);
		
		JLabel lblNewLabel_1_4 = new JLabel("学年学期：");
		lblNewLabel_1_4.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_4.setBounds(174, 190, 92, 22);
		add(lblNewLabel_1_4);
		
		termSelect = new JComboBox();
		termSelect.setModel(new DefaultComboBoxModel(getLastCntTerms(6)));
		termSelect.setBounds(276, 190, 139, 23);
		termSelect.addItemListener(this);
		add(termSelect);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("上课人数：");
		lblNewLabel_1_2_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1_2_1.setBounds(172, 333, 161, 22);
		add(lblNewLabel_1_2_1);
		
		attendCntEdit = new JTextField();
		attendCntEdit.setText((String) null);
		attendCntEdit.setColumns(10);
		attendCntEdit.setBounds(325, 333, 117, 21);
		add(attendCntEdit);
		
		JLabel label = new JLabel("工作量录入");
		label.setFont(new Font("宋体", Font.BOLD, 22));
		label.setBounds(297, 36, 136, 34);
		add(label);
		
		JLabel lblNewLabel_1 = new JLabel("系统会自动生成最近的6个学期");
		lblNewLabel_1.setBounds(434, 195, 180, 15);
		add(lblNewLabel_1);
		
		// **********
		checkState(termSelect.getSelectedItem().toString());
	}
	
	/**
	 * 获取之前cnt个学期（包含当前这个学期）
	 * @param cnt
	 * @return
	 */
	private String[] getLastCntTerms(int cnt) {
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH) + 1; // 别忘了+1
		
		String[] terms = new String[cnt];
		TermBean currentTerm = new TermBean(year, month);
		for (int i = 0; i < cnt; i++) {
			terms[i] = currentTerm.toString();
			currentTerm = currentTerm.getPreTermBean();
		}
		return terms;
	}
	
	
	/**
	 * 检验页面参数，并打包
	 * @return
	 */
	private Workload packToWorkload() {
		String term = termSelect.getSelectedItem().toString();
		String lessonHour = lessonHourEdit.getText().trim();
		String attendCnt = attendCntEdit.getText().trim();
		
		// 校验界面参数
		if (!TextHelper.isPositiveInteger(lessonHour)
				|| !TextHelper.isPositiveInteger(attendCnt)) {
			JOptionPane.showMessageDialog(WorkloadInputPanel.this, 
					"提交失败：请正确填写数据！", "提示", JOptionPane.ERROR_MESSAGE); 
			return null; 
		}
		
		return new Workload(null, user.getUserId(), term, 
				Integer.valueOf(lessonHour), Integer.valueOf(attendCnt));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
	          checkState(e.getItem().toString());
	    }
	}
	
	private void checkState(String term) {
		Workload workload = workloadDao.getWorkload(user.getUserId(), term);
		if (workload != null) {
			submitBtn.setEnabled(false);
			updateBtn.setEnabled(true);
			// 更新界面
			lessonHourEdit.setText(String.valueOf(workload.getLessonHour()));
			attendCntEdit.setText(String.valueOf(workload.getAttendCnt()));
		} else {
			submitBtn.setEnabled(true);
			updateBtn.setEnabled(false);
			lessonHourEdit.setText("");
			attendCntEdit.setText("");
		}
	}
	
	class TermBean {
		private Integer year1;
		private Integer year2;
		private Integer term;
		
		/**
		 * 当前的年份和月份
		 * @param year
		 * @param month
		 */
		public TermBean(Integer year, Integer month) {
			if (month >= 9) {
				// 第一学期
				year1 = year;
				year2 = year + 1;
				term = 1;
			} else if (month >= 2) {
				// 第二学期
				year1 = year - 1;
				year2 = year;
				term = 2;
			}
		}
		
		public TermBean(Integer year1, Integer year2, Integer term) {
			super();
			this.year1 = year1;
			this.year2 = year2;
			this.term = term;
		}

		/**
		 * 获取上一个学期对象
		 * @return
		 */
		public TermBean getPreTermBean() {
			TermBean perTermBean = null; 
			if (term == 1) {
				perTermBean = new TermBean(year1 - 1, year2 - 1, 2);
			} else {
				perTermBean = new TermBean(year1, year2, 1);
			}
			return perTermBean;
		}

		@Override
		public String toString() {
			return  year1 + "-" + year2 + "（" + term + "）";
		}

		public Integer getYear1() {
			return year1;
		}

		public void setYear1(Integer year1) {
			this.year1 = year1;
		}

		public Integer getYear2() {
			return year2;
		}

		public void setYear2(Integer year2) {
			this.year2 = year2;
		}

		public Integer getTerm() {
			return term;
		}

		public void setTerm(Integer term) {
			this.term = term;
		}
	}
}
