package dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库连接类的封装
 * 用于简化获取数据库连接和关闭数据库连接的操作
 * @author passerbyYSQ
 */
public class DbConnector {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
//	public static void main(String[] args) {
//		new DbConnector();
//	}
	
	/**
	 * 静态代码块
	 * 配置信息的获取和数据库驱动的注册，在第一次将JDBCUtil加载进内存时初始化一次即可
	 */
	static {
		try {
			
			ClassLoader classLoader = DbConnector.class.getClassLoader();
			// Eclipse中该配置文件位于src下
			URL resource = classLoader.getResource("JDBC.properties");
			// 使用URLDecoder.decode()解决路径中的空格和中文字符问题
			String path = URLDecoder.decode(resource.getPath(), "utf-8"); 
			Properties properties = new Properties();
			
			// new BufferedInputStream(new FileInputStream(path))
			// new BufferedReader(new FileReader(new File(path)))
			properties.load(new BufferedInputStream(new FileInputStream(path)));
			
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
	
			/*
			driver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://localhost:3306/scnu_salary?characterEncoding=utf8";
			user = "root";
			password = "ysqJYKL2010";
			*/
			// 注册数据库驱动
			Class.forName(driver); 
			//System.out.println("注册数据库驱动");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		//System.out.println("获取数据库连接");
		return DriverManager.getConnection(url, user, password);
	}
	
	/**
     * 释放资源
     * @param stmt 	执行sql语句的对象
     * @param conn 	数据库连接对象
     */
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }

    /**
     * 释放资源的重载形式
     * @param rs 	结果集对象
     * @param stmt 	执行sql语句的对象
     * @param conn	 数据库连接对象
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
}
