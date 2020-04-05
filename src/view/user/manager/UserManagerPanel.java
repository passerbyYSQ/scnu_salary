package view.user.manager;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.User;
import view.UserTreePanel;

/**
 * @author passerbyYSQ
 * @create 2020年3月22日 上午10:03:54
 */
public class UserManagerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5718906807383143983L;
	
	private JSplitPane splitPane;

	/**
	 * Create the panel.
	 */
	public UserManagerPanel() {
		setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		
		splitPane.setLeftComponent(new UserTreePanelImpl());
		
		splitPane.setRightComponent(new UserCreatePanel());
		
		add(splitPane, BorderLayout.CENTER);
		
	}
	
	class UserTreePanelImpl extends UserTreePanel {
		@Override
		public void actionPerformed(ActionEvent e) {
			splitPane.setLeftComponent(new UserTreePanelImpl());	
		}

		@Override
		protected void onLeafNodeSelected(DefaultMutableTreeNode node) {
			// 注意叶子结点也可以能使部门节点（当部门节点下没有用户结点时）。此时强转就会抛出异常
			Object obj = node.getUserObject();
			if (obj instanceof User) {
				User user = (User) obj;
				if (user != null) {
					splitPane.setRightComponent(new UserUpdatePanel(user));
				}
			}
		}
		
	}

}
