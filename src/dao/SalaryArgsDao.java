package dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BaseDao.PackResult;
import model.SalaryArgs;

/**
 * @author passerbyYSQ
 * @create 2020年4月3日 下午3:39:06
 */
public class SalaryArgsDao {
	/**
	 * 获取工资发放的参数列表
	 * @return
	 */
	public Map<String, SalaryArgs> getAllSalaryArgs() {
		String sql = "select * from salary_args";
		return BaseDao.select(sql, new PackResult<Map<String, SalaryArgs>>() {
			@Override
			public Map<String, SalaryArgs> onResultReturn(ResultSet rs) throws Exception {
				Map<String, SalaryArgs> args = new HashMap<>();
				while (rs.next()) {	// 移动游标
					try {
						String title = rs.getString("title");
						BigDecimal postWage = rs.getBigDecimal("postWage");
						BigDecimal performWage = rs.getBigDecimal("performWage");
						BigDecimal titleFactor = rs.getBigDecimal("titleFactor");
						BigDecimal perLessonWage = rs.getBigDecimal("perLessonWage");
						
						SalaryArgs arg = new SalaryArgs(title, postWage, performWage, 
								titleFactor, perLessonWage);
						args.put(title, arg);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}				
				return args;
			}
		});
	}
	
}
