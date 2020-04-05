package dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dao.BaseDao.PackResult;
import model.Pay;

/**
 * @author passerbyYSQ
 * @create 2020年3月23日 下午11:30:28
 */
public class PayDao {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// 由于不同条件的查询都采用同样的分页形式（即：每页多少条记录都一样）
	private int count = 20; // 每页显示多少条记录
	
	public PayDao() {
		
	}
	
	public PayDao(int count) {
		this.count = count;
	}
	
	public int updatePayRecord(BigDecimal tempWage, BigDecimal extra, 
			String postscript, Integer payId) {
		String sql = "update pay set tempWage=?, extra=?, postscript=? where payId=?";
		return BaseDao.insertOrUpdateOrDelete(sql, tempWage, extra, postscript, payId);
	}
	
	public int addPayRecord(Pay pay) {
		String sql = "insert into pay(userId, postWage, performWage, tempWage, extra, lessonWage, manageWage, "
				+ "manageSubsidy, postscript, result) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return BaseDao.insertOrUpdateOrDelete(sql, pay.getUserId(), pay.getPostWage(), pay.getPerformWage(),
				pay.getTempWage(), pay.getExtra(), pay.getLessonWage(), pay.getManageWage(), pay.getManageSubsidy(),
				pay.getPostscript(), pay.getResult());
	}
	
	/**
	 * 查询指定年月，所有专任教师的工资发放记录
	 * 每个教师都应该对应有一条
	 * @return
	 */
	public List<Pay> getFullTimeTeacherRecord(Integer year, Integer month) {
		String sql = "select pay.*, user.salaryType "
				+ "from pay, user "
				+ "where pay.userId=user.userId and salaryType='专任教师' and YEAR(payTime)=? and MONTH(payTime)=?";
		return select(sql, year, month);
	}
	
	/**
	 * 获取某个人指定年月的工资记录
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 */
	public Pay getPayRecord(String userId, Integer year, Integer month) {
		String sql = "select * from pay where userId=? and YEAR(payTime)=? and MONTH(payTime)=?";
		return BaseDao.select(sql, new PackResult<Pay>() {
			@Override
			public Pay onResultReturn(ResultSet rs) throws Exception {
				return rs.next() ?  packToPay(rs) : null;
			}
		}, userId, year, month);		
	}
	
	/**
	 * 第1种查询类型：只带有userId，并不带有其他条件
	 * 获取一个人全部的工资方法记录。并按时间倒序展示。
	 * @param userId	用户编号（工号）
	 * @param curPage	当前页的页码。注意从0开始！！！curPage的合法性交给前端界面去做判断
	 * @return			List<Pay>
	 */
	public List<Pay> getPayRecords(String userId, int curPage) {		
		//System.out.println(curPage);
		int sta = count * curPage;
		String sql = "select * from pay where userId=? order by UNIX_TIMESTAMP(payTime) desc limit ?,?";
		return select(sql, userId, (Integer) sta, (Integer) count);
	}
	
	/**
	 *  第2种查询类型：带有userId，并带时间段的限制
	 * @param userId
	 * @param option
	 * @param curPage
	 * @return
	 */
	public List<Pay> getPayRecords(String userId, String option, int curPage) {
		Calendar cur = Calendar.getInstance();
		switch(option) {
			case "最近3个月": {
				cur.add(Calendar.MONTH, -3);
				break;
			}
			case "最近半年": {
				cur.add(Calendar.MONTH, -6);
				break;
			}
			case "最近1年": {
				cur.add(Calendar.YEAR, -1);
				break;
			}
		}
		String limitTime = sdf.format(cur.getTime());
		//System.out.println(limitTime);
		
		int sta = count * curPage;
		String sql = "select * from pay where userId=? and UNIX_TIMESTAMP(payTime)>=UNIX_TIMESTAMP(?) "
				+ " order by UNIX_TIMESTAMP(payTime) desc limit ?,?";		
		
		return select(sql, userId, limitTime, (Integer) sta, (Integer) count);
	}
	
	/**
	 * 第3种查询类型：带有userId，指定年份和月份
	 * @param userId
	 * @param year
	 * @param month
	 * @param curPage
	 * @return
	 */
	public List<Pay> getPayRecords(String userId, int year, int month, int curPage) {		
		int sta = count * curPage;
		String sql = "select * from pay where userId=? and YEAR(payTime)=? and MONTH(payTime)=? "
				+ " order by UNIX_TIMESTAMP(payTime) desc limit ?,?";		
		
		return select(sql, userId, year, month, (Integer) sta, (Integer) count);
	}
	
	/**
	 * 为了防止前端页面需要总记录数，此处提供一个给前端页面调用的方法
	 * @param from
	 * @param where
	 * @param args
	 * @return
	 */
	public int getTotal(String userId, String text) {
		Calendar cur = Calendar.getInstance();
		switch(text) {
			case "最近3个月": {
				cur.add(Calendar.MONTH, -3);
				break;
			}
			case "最近半年": {
				cur.add(Calendar.MONTH, -6);
				break;
			}
			case "最近1年": {
				cur.add(Calendar.YEAR, -1);
				break;
			}
		}
		String limitTime = sdf.format(cur.getTime());
		return BaseDao.getCount("pay", "userId=? and UNIX_TIMESTAMP(payTime)>=UNIX_TIMESTAMP(?)",
				userId, limitTime);
	}
	
	/**
	 * 将结果集ResultSet中的内容取出，返回List<Pay>
	 * @param sql
	 * @param args
	 * @return
	 */
	private List<Pay> select(String sql, Object... args) {
		return BaseDao.select(sql, new PackResult<List<Pay>>() {
			@Override
			public List<Pay> onResultReturn(ResultSet rs) throws Exception {
				List<Pay> pays = new ArrayList<>();
				while (rs.next()) {	// 移动游标				
					pays.add(packToPay(rs));					
				}				
				return pays;
			}
		}, args);
	}
	
	
	/**
	 * 取出结果集【当前游标】所指向的那一行，并打包成Pay对象
	 * @param rs	ResultSet，数据库查询返回的结果集	
	 * @return
	 */
	private Pay packToPay(ResultSet rs) {		
		try {
			BigDecimal result = rs.getBigDecimal("result");
			Integer payId = rs.getInt("payId"); // 界面不需要
			//String userId = rs.getString("userId");
			// 取出MySQL中的datetime类型，并转成Java中的String
			String payTime = sdf.format(rs.getTimestamp("payTime")); 
			
			BigDecimal postWage = rs.getBigDecimal("postWage");
			BigDecimal performWage = rs.getBigDecimal("performWage");
			BigDecimal tempWage = rs.getBigDecimal("tempWage");
			BigDecimal extra = rs.getBigDecimal("extra");
			BigDecimal lessonWage = rs.getBigDecimal("lessonWage");
			BigDecimal manageWage = rs.getBigDecimal("manageWage");
			BigDecimal manageSubsidy = rs.getBigDecimal("manageSubsidy");
			String postscript = rs.getString("postscript");
			if (postscript == null) {
				postscript = "";
			}
			
			return new Pay(result, payId, null, payTime, postWage, performWage, tempWage, 
					extra, lessonWage, manageWage, manageSubsidy, postscript);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
