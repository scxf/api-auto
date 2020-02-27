package com.lemon.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lemon.constant.Constants;


public class JDBCUtils {
	
	
	public static void main(String[] args) throws Exception {
		//创建QueryRunner对象,操作数据库
		QueryRunner runner = new QueryRunner();
		//调用查询方法，传入数据库连接、sql语句、返回值类型。
		Connection conn = JDBCUtils.getConnection();
		String sql = "select count(*) from member where mobile_phone = '15619088111'";
		Long result = runner.query(conn, sql, new ScalarHandler<Long>());
		System.out.println(result);
		
	}
	

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		//定义数据库连接对象
		Connection conn = null;
		try {
			//你导入的数据库驱动包 
			//jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8
			//jdbc:数据库名称://数据库IP:端口/数据库名称?参数
			conn = DriverManager.getConnection(
					Constants.JDBC_URL, 
					Constants.JDBC_USER, 
					Constants.JDBC_PASSWORD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}