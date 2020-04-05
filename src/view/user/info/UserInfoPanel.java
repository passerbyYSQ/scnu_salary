package view.user.info;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

import dao.UserDao;
import model.User;
import view.UserTreePanel;
import view.user.manager.UserUpdatePanel;

/**
 * @author passerbyYSQ
 * @create 2020年3月22日 下午12:47:13
 */
public class UserInfoPanel extends JPanel {
	
	private JSplitPane splitPane;
	
	private UserDao userDao = new UserDao();

	/**
	 * Create the panel.
	 */
	public UserInfoPanel(User user) {
		
		setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		
		splitPane.setLeftComponent(new UserTreePanelImpl());
		splitPane.setRightComponent(new UserDetailsPanel(user));
		
		add(splitPane, BorderLayout.CENTER);
		
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
					splitPane.setRightComponent(new UserDetailsPanel(user));
				}
			}
		}
	}

}
