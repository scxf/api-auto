package com.lemon.constant;

/**
 * 常量类
 * @author muji
 *
 */
public class Constants {
	//常量命名规则：所有英文单词都大写用下划线分割。
	//final 修饰变量，变量成为常量，常量只能赋值一次
	//final 修饰类，类不能被继承
	//final 修饰方法，不能被重写。
	//EXCEL路径
	public static final String EXCEL_PATH = "src/test/resources/cases_v7.xlsx";
	//柠檬班token鉴权版本
	public static final String HEADER_MEDIA_TYPE_NAME = "X-Lemonban-Media-Type";
	public static final String HEADER_MEDIA_TYPE_VALUE = "lemonban.v2";
	//实际响应数据回写列
	public static final int ACTUAL_WRITER_BACK_CELL_NUM = 5;
	//数据库连接相关常量
	public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
	public static final String JDBC_USER = "future";
	public static final String JDBC_PASSWORD = "123456";

	
	
	
}
