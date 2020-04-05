package view;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import dao.DepartmentDao;
import dao.UserDao;
import model.Department;
import model.User;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * 树形用户目录面板
 * @author passerbyYSQ
 * @create 2020年3月21日 下午10:53:39
 */
public abstract class UserTreePanel extends JPanel
		implements ActionListener, TreeSelectionListener {

	// 之后添加一个功能：可以增加和删除一个部门。需要大改！！！
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4379656627114827706L;
	
	private JTree tree;
	
	private DepartmentDao depDao = new DepartmentDao();
	private UserDao userDao = new UserDao();
	
	private List<Department> deps;
	private List<User> users;
	private List<DefaultMutableTreeNode> depNodes = new ArrayList<>();
	/**
	 * Create the panel.
// 下拉选项卡的部门列表
学院领导
软件工程系
计算机基础部
数学教学部
学院办公室
学院实验中心
	 */
	public UserTreePanel() {
		DefaultMutableTreeNode	rootNode = new DefaultMutableTreeNode("软件学院");
		
		// 添加部门结点
		deps = depDao.getAllDeps();
		// 与下拉选项卡的部门列表不对应！！！
		for(Department dep : deps) {
			DefaultMutableTreeNode depNode = new DefaultMutableTreeNode(dep);
			depNodes.add(depNode); // 暂存起来
			rootNode.add(depNode);
		}
		
		users = userDao.getAllUsers();
		if (users != null) {
			for (User user : users) {
				// 根据depId判断该用户属于哪个部门结点
				for (DefaultMutableTreeNode depNode : depNodes) {
					Department dep = (Department) depNode.getUserObject();
					if (dep.getDepName().equals(user.getDepName())) {
						depNode.add(new DefaultMutableTreeNode(user));
					}
				}
			}
		}
		
		setLayout(new BorderLayout(0, 0));
		
		tree = new JTree(rootNode);
		tree.addTreeSelectionListener(this);
		add(tree, BorderLayout.CENTER);
		
		JButton button = new JButton("刷新");
		button.addActionListener(this);
		add(button, BorderLayout.SOUTH);
		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		
		if (node != null && node.isLeaf()) {
			onLeafNodeSelected(node);
		}
	}

	protected abstract void onLeafNodeSelected(DefaultMutableTreeNode node);

}
