package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao.PackResult;
import model.Department;

/**
 * @author passerbyYSQ
 * @create 2020年3月31日 上午9:46:11
 */
public class DepartmentDao {
	
	/**
	 * 获取所有部门的信息
	 * @return
	 */
	public List<Department> getAllDeps() {
		String sql = "select * from department";
		return BaseDao.select(sql, new PackResult<List<Department>>() {
			@Override
			public List<Department> onResultReturn(ResultSet rs) throws Exception {
				List<Department> deps = new ArrayList<>();
				while (rs.next()) {	// 移动游标				
					deps.add(packToDep(rs));					
				}				
				return deps;
			}
		});
	}
	
	/**
	 * 修改部门的成员人数
	 * @param cnt
	 * @return
	 */
	public int addMemberCount(Integer cnt, Integer depId) {
		String sql = "update department set memberCount=memberCount+? where depId=?";
		return BaseDao.insertOrUpdateOrDelete(sql, cnt, depId);
	}
	
	/**
	 * 取出结果集【当前游标】所指向的那一行，并打包成Department 对象
	 * @param rs	ResultSet，数据库查询返回的结果集	
	 * @return
	 */
	private Department packToDep(ResultSet rs) {		
		try {
			Integer depId = rs.getInt("depId");
			String depName = rs.getString("depName");
			//Integer memberCount = rs.getInt("memberCount");
			// 注意密码我们不需要返回到界面中去
			return new Department(depId, depName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
