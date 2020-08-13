package cn.itcast.travel.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
	1. 声明静态数据源成员变量
	2. 创建连接池对象
	3. 定义公有的得到数据源的方法
	4. 定义得到连接对象的方法
	5. 定义关闭资源的方法
 */
public class JDBCUtils {
	// 1.	声明静态数据源成员变量
	private static DataSource ds ;

	static {

		try {
			//1.加载配置文件
			Properties pro = new Properties();
			//使用ClassLoader加载配置文件，获取字节输入流
			InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
			pro.load(is);

			//2.初始化连接池对象
			ds = DruidDataSourceFactory.createDataSource(pro);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 3. 定义公有的得到数据源的方法
	public static DataSource getDataSource() {
		return ds;
	}

	// 4. 定义得到连接对象的方法
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	// 5.定义关闭资源的方法
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}
	}

	// 6.重载关闭方法
	public static void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}
}
