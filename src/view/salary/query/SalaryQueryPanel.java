package view.salary.query;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.User;
import view.UserTreePanel;
import view.user.info.UserDetailsPanel;

/**
 * @author passerbyYSQ
 * @create 2020年3月24日 上午10:53:21
 */
public class SalaryQueryPanel extends JPanel {
	
	private JSplitPane splitPane;

	/**
	 * Create the panel.
	 */
	public SalaryQueryPanel(User user) {
		setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		if (!"教师".equals(user.getLoginAuth())) {
			splitPane.setLeftComponent(new UserTreePanelImpl());
		} else {
			splitPane.setLeftComponent(null);
		}
		splitPane.setRightComponent(new SalaryDetailsPanel(user));

	}
	
	class UserTreePanelImpl extends UserTreePanel {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 是否需要移除之前的panel？
			splitPane.setLeftComponent(new UserTreePanelImpl());
		}

		@Override
		protected void onLeafNodeSelected(DefaultMutableTreeNode node) {
			// 注意叶子结点也可以能使部门节点（当部门节点下没有用户结点时）。此时强转就会抛出异常
			Object obj = node.getUserObject();
			if (obj instanceof User) {
				User user = (User) obj;
				if (user != null) {
					splitPane.setRightComponent(new SalaryDetailsPanel(user));
				}
			}
		}
		
	}

}
