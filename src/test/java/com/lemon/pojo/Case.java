package com.lemon.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Case {
	// 用例编号
	@Excel(name = "用例编号")
	private String id;
	// 用例描述
	@Excel(name = "用例描述")
	private String desc;
	// 参数
	@Excel(name = "参数")
	private String params;
	// 接口编号
	@Excel(name = "接口编号")
	private String apiId;
	// 期望响应数据
	@Excel(name = "期望响应数据")
	private String expectValue;
	@Excel(name = "检验SQL")
	private String sql;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getExpectValue() {
		return expectValue;
	}

	public void setExpectValue(String expectValue) {
		this.expectValue = expectValue;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "Case [id=" + id + ", desc=" + desc + ", params=" + params + ", apiId=" + apiId + "]";
	}

}
