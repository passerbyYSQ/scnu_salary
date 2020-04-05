package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.BaseDao;

public class TestBaseDao {

	@Test
	public void testSelect() {
		String sql = "select * from user where sex = ?";
		System.out.println(sql);
		List<User> userList = BaseDao.select(sql, new BaseDao.PackResult<List<User>>() {
			@Override
			public List<User> onResultReturn(ResultSet rs) throws SQLException {
				List<User> users = new ArrayList<>();
				while (rs.next()) {					
					String name = rs.getString("name");
					String sex = rs.getString("sex");
					Long birthday = rs.getLong("birthday"); // 时间戳
					// 由于密码是隐私数据，所以我们不将密码返回给前端页面
					users.add(new User(name, null, sex, birthday));					
				}				
				return users;
			}
			
		}, "男");
		
		for(User user : userList) {
			System.out.println(user);
		}
	}
	
//	@Test
//	public void testInsert() {
//		String sql = "insert into user values(NULL, ?, ?, ?, NULL)";
//		int count = BaseDao.insertOrUpdateOrDelete(sql, "efg", "123", "女");
//		System.out.println(count);
//	}
	
	
	class User {
		private String name;
		private String password;
		private String sex;
		private Long birthday;
		
		public User(String name, String password, String sex, Long birthday) {
			this.name = name;
			this.password = password;
			this.sex = sex;
			this.birthday = birthday;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public Long getBirthday() {
			return birthday;
		}

		public void setBirthday(Long birthday) {
			this.birthday = birthday;
		}

		@Override
		public String toString() {
			return "User [name=" + name + ", password=" + password + ", sex=" + sex + ", birthday=" + birthday + "]";
		}
		
	}
}
