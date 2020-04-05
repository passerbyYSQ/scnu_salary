package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao.PackResult;
import model.User;

/**
 * 组织sql语句进行有关于User的数据库操作
 * @author passerbyYSQ
 * @create 2020年3月21日 下午6:49:07
 */
public class UserDao {
	
	/**
	 * 用户登录
	 * @param userName		输入的用户名
	 * @param password	输入的密码
	 * @return
	 */
	public User login(String userId, String password) {
		String sql = "select user.*, depName "
				+ "from user, department dep "
				+ "where user.depId=dep.depId and userId=? and password=?";
		return BaseDao.select(sql, new PackResult<User>() {
			@Override
			public User onResultReturn(ResultSet rs) throws Exception {
				return rs.next() ? packToUser(rs) : null;
			}
		}, userId, password);
	}
	
	/**
	 * 通过工号和姓名检查用户是否存在
	 * @param userId	
	 * @param userName
	 * @param type		email或者phone
	 * @return			email或者phone
	 */
	public User checkUser(String userId, String userName) {
		String sql = "select * from user where userId=? and userName=?";
		return BaseDao.select(sql, new PackResult<User>() {
			@Override
			public User onResultReturn(ResultSet rs) throws Exception {
				return rs.next() ? packToUser(rs) : null;
			}
		}, userId, userName);
	}
	
	/**
	 * 重置某个用户的密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public int setPassword(String userId, String password) {
		String sql = "update user set password=? where userId=?";
		return BaseDao.insertOrUpdateOrDelete(sql, password, userId);
	}
	
	/**
	 * 添加一条User记录
	 * @param user	传递过来一个User，里面的属性值已经做好判断了
	 * @return		受影响的行数
	 */
	public int addUser(User user) {
		
		String sql = "insert into user(userId, userName, sex, depId, loginAuth, proTitle, post, "
				+ " salaryType, email, phone)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		return BaseDao.insertOrUpdateOrDelete(sql, user.getUserId(), user.getUserName(), 
				user.getSex(), user.getDepId(), user.getLoginAuth(), user.getProTitle(), 
				user.getPost(), user.getSalaryType(), user.getEmail(), user.getPhone());
	}
	
	/**
	 * 删除一个用户
	 * @param userId
	 * @return
	 */
	public int delUser(String userId) {
		String sql = "delete from user where userId=?";
		return BaseDao.insertOrUpdateOrDelete(sql, userId);
	}
	
	/**
	 * 根据用户Id获取单个User
	 * @param 	userId
	 * @return	User
	 */
	public User getUserById(String userId) {
		String sql = "select user.*, depName "
				+ "from user, department dep "
				+ "where user.depId=dep.depId and userId=?";
		return BaseDao.select(sql, new PackResult<User>() {
			@Override
			public User onResultReturn(ResultSet rs) throws Exception {
				return rs.next() ? packToUser(rs) : null;
			}
		} , userId);
	}
	
	/**
	 * 获取全部的User
	 * @return	List<User>
	 */
	public List<User> getAllUsers() {
		String sql = "select user.*, depName "
				+ " from user, department dep "
				+ " where user.depId=dep.depId";
		return BaseDao.select(sql, new PackResult<List<User>>() {
			@Override
			public List<User> onResultReturn(ResultSet rs) throws Exception {
				List<User> users = new ArrayList<>();
				while (rs.next()) {	// 移动游标				
					users.add(packToUser(rs));					
				}				
				return users;
			}
		});
	}
	
	/**
	 * 取出结果集【当前游标】所指向的那一行，并打包成User对象
	 * @param rs	ResultSet，数据库查询返回的结果集	
	 * @return
	 */
	private User packToUser(ResultSet rs) {		
		try {
			String userId = rs.getString("userId");
			//String password = rs.getString("password");
			String userName = rs.getString("userName");
			String sex = rs.getString("sex"); 
			Integer depId = rs.getInt("depId");
			String depName = null;
			if (isExistColumn(rs, "depName")) {
				depName = rs.getString("depName");
			}
			String loginAuth = rs.getString("loginAuth"); // 登陆权限
			String proTitle = rs.getString("proTitle"); // 职称
			String post = rs.getString("post"); // 职务
			String salaryType = rs.getString("salaryType"); // 工资发放类型
			String email = rs.getString("email"); 
			String phone = rs.getString("phone"); 
			// 注意密码我们不需要返回到界面中去
			return new User(userId, null, userName, sex, depId, depName, loginAuth, 
					proTitle, post, salaryType, email, phone);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 更新某个User的信息
	 * @param user
	 * @return
	 */
	public int updateUser(User user) {
		// 一旦创建用户，UserId固定，不更新UserId
		String sql = "update user set userId=?, userName=?, sex=?, depId=?, "
				+ "loginAuth=?, proTitle=?, post=?, salaryType=?, email=?, phone=? "
				+ "where userId=" + user.getUserId();
		return BaseDao.insertOrUpdateOrDelete(sql, user.getUserId(), user.getUserName(), 
				user.getSex(), user.getDepId(), user.getLoginAuth(), user.getProTitle(), 
				user.getPost(), user.getSalaryType(), user.getEmail(), user.getPhone());
	}
	
	/**
	 * 检查该结果集中是否有对应字段
	 * @param rs
	 * @param columnName
	 * @return
	 */
	public boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			if (rs.findColumn(columnName) > 0 ) {
				return true;
			} 
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	
	
}
