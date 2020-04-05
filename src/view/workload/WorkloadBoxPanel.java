package view.workload;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.User;
import view.UserTreePanel;
import view.user.manager.UserUpdatePanel;

/**
 * @author passerbyYSQ
 * @create 2020年3月24日 上午10:53:21
 */
public class WorkloadBoxPanel extends JPanel {
	
	private JSplitPane splitPane;

	/**
	 * Create the panel.
	 */
	public WorkloadBoxPanel(User user) {
		setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		splitPane.setLeftComponent(new UserTreePanelImpl());
		splitPane.setRightComponent(new WorkloadInputPanel(user));

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
					splitPane.setRightComponent(new WorkloadInputPanel(user));
				}
			}
		}
	}

}
