package com.lemon.pojo;

/**
 * JSONPath多关键字段匹配实体类
 * @author muji
 *
 */
public class JsonPathValidate {
	// jsonpath 表达式
	private String expression;
	// jsonpath 表达式期望找到的值
	private String value;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
