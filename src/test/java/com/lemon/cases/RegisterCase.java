package com.lemon.cases;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.constant.Constants;
import com.lemon.pojo.API;
import com.lemon.pojo.Case;
import com.lemon.utils.AuthorizationUtils;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.HttpUtils;
import com.lemon.utils.SqlUtils;

public class RegisterCase extends BaseCase {
	
	
	
	@Test(dataProvider = "datas")
	public void testRegister(API api,Case c) throws Exception {
		//1、参数化替换
		//2、数据库前置查询结果(数据断言必须在接口执行前后都查询) 
		Object beforeSqlResult = SqlUtils.querySingle(c.getSql());
		//3、调用接口
		String body = call(api, c, false);
		//4、断言响应结果
		boolean assertResponseFlag = assertResponse(c, body);
		//5、添加接口响应回写内容
		addWBD(Integer.parseInt(c.getId()),Constants.ACTUAL_WRITER_BACK_CELL_NUM,body);
		//6、数据库后置查询结果  
		Object afterSqlResult = SqlUtils.querySingle(c.getSql());
		//7、数据库断言
		boolean sqlFlag = sqlAssert(c.getSql(), beforeSqlResult, afterSqlResult);
		System.out.println("数据库断言结果：" + sqlFlag);
		//8、添加断言回写内容
		//9、添加日志
		//10、报表断言
	}
	
	/**
	 * 注册数据库断言方法
	 * @param sql
	 * @param beforeSqlResult
	 * @param afterSqlResult
	 * @return
	 */
	public boolean sqlAssert(String sql,Object beforeSqlResult,Object afterSqlResult) {
		//如果sql为空，说明不需要数据库断言。
		if(StringUtils.isBlank(sql)) {
			return true;
		}else {
			//注册断言逻辑：前置sql结果为0，后置sql结果为1那么断言成功，其他情况断言失败。
			long beforeValue = (Long)beforeSqlResult;
			long afterValue = (Long)afterSqlResult;
			System.out.println("SQL : " + sql);
			System.out.println("beforeValue : " + beforeValue);
			System.out.println("afterValue : " + afterValue);
			if(beforeValue == 0 && afterValue == 1) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	@DataProvider(name = "datas")
	public Object[][] datas() {
		
		//1、怎么把API和Case联系起来
		//2、把API和Case放入Object[][]
		Object[][] datas = ExcelUtils.getAPIandCaseByApiId("1");
		return datas;
	}
		
	
}
