package com.lemon.cases;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.constant.Constants;
import com.lemon.pojo.API;
import com.lemon.pojo.Case;
import com.lemon.utils.AuthorizationUtils;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.HttpUtils;

public class RechargeCase extends BaseCase {
	
	
	
	@Test(dataProvider = "datas")
	public void testRecharge(API api,Case c) throws Exception {
		//1、参数化替换
		//2、数据库前置查询结果(数据断言必须在接口执行前后都查询)
		//3、调用接口
		String body = call(api, c, true);
		//4、断言响应结果
		boolean assertResponseFlag = assertResponse(c, body);
		//5、添加接口响应回写内容
		addWBD(Integer.parseInt(c.getId()),Constants.ACTUAL_WRITER_BACK_CELL_NUM,body);
		//6、数据库后置查询结果
		//7、据库断言
		//8、添加断言回写内容
		//9、添加日志
		//10、报表断言
	}
	
	@DataProvider(name = "datas")
	public Object[][] datas() {
		//1、怎么把API和Case联系起来
		//2、把API和Case放入Object[][]
		Object[][] datas = ExcelUtils.getAPIandCaseByApiId("3");
		return datas;
	}
		
	
}
