package com.lemon.cases;

import java.util.List;

import org.testng.annotations.AfterSuite;
import org.testng.log4testng.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lemon.pojo.API;
import com.lemon.pojo.Case;
import com.lemon.pojo.JsonPathValidate;
import com.lemon.pojo.WriteBackData;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.HttpUtils;

public class BaseCase {
	public static Logger log = Logger.getLogger(BaseCase.class);
	/**
	 * 添加回写对象到回写集合中。
	 * @param rowNum			回写行号
	 * @param cellNum			回写列号
	 * @param body				回写内容
	 */
	public void addWBD(int rowNum,int cellNum, String body) {
		//创建一条回写的内容
		WriteBackData wbd = new WriteBackData(rowNum,cellNum,body);
		ExcelUtils.wbdList.add(wbd);
	}
	
	/**
	 * 	调用接口方法
	 * @param api				接口信息对象（url、type、contentType）
	 * @param c					用例信息对象（params）
	 * @param isAuthorization	接口是否需要验证
	 * @return
	 */
	public String call(API api, Case c,boolean isAuthorization) {
		String url = api.getUrl();
		String type = api.getMethod();
		String contentType = api.getContentType();
		String params = c.getParams();
		System.out.println("=============================================");
		String body = HttpUtils.call(url, type, params, contentType,isAuthorization);
		return body;
	}
	
	/**
	 * 		接口的响应内容断言
	 *   	如果case中ExpectValue是数组类型的json格式，那么采用多字段匹配断言逻辑。
	 *    	如果case中ExpectValue不是数组类型的json格式，那么采用等值匹配。
	 * @param c
	 * @param body
	 */
	public boolean assertResponse(Case c, String body) {
		//定义断言是否成功返回值
		boolean flag = false;
		//获取expectValue
		String expectValue = c.getExpectValue();
		//调用parse方法解析json
		Object jsonObj = JSONObject.parse(expectValue);
		//如果case中ExpectValue是数组类型的json格式，那么采用多字段匹配断言逻辑。
		//jsonObj instanceof JSONArray 翻译 jsonObj是JSONArray的对象吗？
		if(jsonObj instanceof JSONArray) {
			//多字段匹配断言逻辑
			List<JsonPathValidate> list = JSONObject.parseArray(expectValue,JsonPathValidate.class);
			for (JsonPathValidate jpv : list) {
				//一个jsonPath断言表达式
				String expression = jpv.getExpression();
				//表达式期望值
				String value = jpv.getValue();
				//对响应结果进行一个jsonPath寻找实际值。
				String actualValue = JSONPath.read(body, expression) == null 
						? "" : JSONPath.read(body, expression).toString();
				//期望值和实际值进行断言。
				flag = value.equals(actualValue);
				System.out.println("实际值:"+actualValue+"，预期值:"+value+",单次断言结果：" + value.equals(actualValue));
				if(flag == false) { 
					//说明断言失败
					break;
				}
			}
		//如果case中ExpectValue不是数组类型的json格式，那么采用等值匹配。
		}else if(jsonObj instanceof JSONObject) {
			flag = body.equals(expectValue);
		}
		
		return flag;
	}
	
	@AfterSuite
	/**
	 * 		套件执行完毕之后的操作。
	 */
	public void finish() {
		//所有的接口都已经执行完毕
		//执行批量回写
		ExcelUtils.batchWrite();
	}
}
