package com.lemon.utils;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

	/**
	 * 	传入sql语句执行sql查询，并且返回查询结果
	 * @param sql
	 * @return
	 */
	public static Object querySingle(String sql) {
		Connection conn = null;
		//如果sql语句为空直接返回。
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		Object result = null;
		try {
			// 创建QueryRunner对象,操作数据库
			QueryRunner runner = new QueryRunner();
			// 调用查询方法，传入数据库连接、sql语句、返回值类型。
			conn = JDBCUtils.getConnection();
			result = runner.query(conn, sql, new ScalarHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn);
		}
		return result;
	}
}
