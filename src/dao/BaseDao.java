package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 基础的数据库操作类
 * 简化通用的基础的增删改查的操作，与具体业务逻辑无关
 * @author passerbyYSQ
 */
public class BaseDao {
	// 数据库连接对象
	private static Connection conn;
	
	private static PreparedStatement pstmt;
	
	// 结果集
	private static ResultSet resultSet;
	
	public interface PackResult<T> {
		// 将ResultSet回调给子类，让子类来处理ResultSet中的数据
        T onResultReturn(ResultSet rs) throws Exception; 
	}
	
	/**
	 * 通用的查询操作
	 * @param <T>	查询返回的数据，基类中无法确定，需要通过泛型抛给子类
	 * @param sql	sql语句（里面有占位符）
	 * @param pack	一个接口，该接口有个抽象方法将ResultSet回调给子类，让子类来处理ResultSet中数据
	 * @param args	与占位符一一对应的参数值。如果是基本数据类型，最好转成对应的包装类
	 * @return		封装后的查询结果
	 */
	public static <T> T select(String sql, PackResult<T> pack, Object... args) {	
		conn = null;
		pstmt = null;
		resultSet = null;
		T result = null;
		try {
			conn = DbConnector.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			// 将参数设置到sql语句的占位符中
			setValue(pstmt, args);
//			System.out.println(pstmt);
			
			resultSet = pstmt.executeQuery();
			// 由于不同的表对应不同的实体类，在基类中无法实现将resultSet封装成对应的实体类返回
			// 故只能通过接口中的回调函数交给子类去实现
			result = pack.onResultReturn(resultSet);	
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DbConnector.close(resultSet, pstmt, conn);
		}		
	}
	
	/**
	 * 获取结果集数量
	 * @param from	from部分，传递时不需要带"from"。之所以传递from，而不是表名，是考虑到多表查询
	 * @param where	where部分，传递时不需要带"where"，里面含有占位符
	 * @param args	代替占位符的参数值
	 * @return		记录数
	 */
	public static int getCount(String from, String where, Object... args) {
		String sql = "select count(*) from " + from + " where " + where;  
		
		conn = null;
		pstmt = null;
		resultSet = null;
		try {
			conn = DbConnector.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			// 将参数设置到sql语句的占位符中
			setValue(pstmt, args);
			
			resultSet = pstmt.executeQuery();
			return resultSet.next() ? resultSet.getInt(1) : -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			DbConnector.close(resultSet, pstmt, conn);
		}	
	}
	
	/**
	 * 插入纪录，比较适用于插入一条或几条数据。不适合插入一个集合的数据
	 * @param sql	sql语句（里面有占位符）
	 * @param args	与占位符一一对应的参数值
	 * @return		受影响的行数
	 */
	public static int insertOrUpdateOrDelete(String sql, Object... args) {
		conn = null;
		pstmt = null;
		int count = 0;
		try {
			conn = DbConnector.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			// 将参数设置到sql语句的占位符中
			setValue(pstmt, args);
			System.out.println(pstmt);
			
			// 受影响的行数
			count = pstmt.executeUpdate();
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			DbConnector.close(pstmt, conn);
		}
	}
	
	/**
	 * 将参数设置到sql语句的占位符中
	 * @param pstmt
	 * @param args
	 * @throws SQLException
	 */
	private static void setValue(PreparedStatement pstmt, Object... args) throws SQLException {
		for (int i = 0; i < args.length; i++) {	
			if (args[i] instanceof String) { // 主要
				pstmt.setString(i + 1, (String) args[i]);
			} else  if (args[i] instanceof Integer) { // 主要
				pstmt.setInt(i + 1, (int) args[i]);
			} else if (args[i] instanceof Double) {  // 主要
				pstmt.setDouble(i + 1, (int) args[i]);
			} else if (args[i] instanceof Float) {
				pstmt.setFloat(i + 1, (int) args[i]);
			} else if (args[i] instanceof BigDecimal) { // 主要
				pstmt.setBigDecimal(i + 1, (BigDecimal) args[i]);
			} else if (args[i] instanceof Boolean) {
				pstmt.setBoolean(i + 1, (boolean) args[i]);
			} else if (args[i] == null) {
				pstmt.setObject(i+1, null);
			}
		}
	}
}
