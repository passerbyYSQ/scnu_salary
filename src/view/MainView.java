package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import model.User;
import view.salary.pay.SalaryPayBoxPanel;
import view.salary.query.SalaryQueryPanel;
import view.user.info.UserInfoPanel;
import view.user.manager.UserManagerPanel;
import view.workload.WorkloadBoxPanel;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainView frame = new MainView();
//					frame.setVisible(true);
//					frame.setLocationRelativeTo(null);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MainView(User user) {
		setResizable(false);
		setTitle("SCNU薪酬管理系统 - Powered by passerbyYSQ 1127664027@qq.com");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 608);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel userInfoPanel = new UserInfoPanel(user);
		tabbedPane.addTab("用户信息", null, userInfoPanel, null);
		
		if (!"教师".equals(user.getLoginAuth())) {
			JPanel userManagerPanel = new UserManagerPanel();
			tabbedPane.addTab("用户管理", null, userManagerPanel, null);
		}
		
		
		JPanel salaryQueryPanel = new SalaryQueryPanel(user);
		tabbedPane.addTab("工资查询", null, salaryQueryPanel, null);
		
		if (!"教师".equals(user.getLoginAuth())) {
			JPanel workloadBoxPanel = new WorkloadBoxPanel(user);
			tabbedPane.addTab("工作量录入", null, workloadBoxPanel, null);
			
			JPanel salaryPayBoxPanel = new SalaryPayBoxPanel(user);
			tabbedPane.addTab("工资发放", null, salaryPayBoxPanel, null);		
		}
	}
}
