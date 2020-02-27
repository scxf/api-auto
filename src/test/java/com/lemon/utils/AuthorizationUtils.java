package com.lemon.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;

import com.alibaba.fastjson.JSONPath;

/**
 * 鉴权类
 * @author muji
 *
 */
public class AuthorizationUtils {
	//模拟环境变量
	public static final Map<String,String> env = new HashMap<String,String>();
	
	/**
	 * 1、从接口响应中获取token信息
	 * 2、把token信息存储到环境变量中
	 * @param response
	 */
	public static void storeTokenAndMemberId(String response) {
		//从接口响应中获取token信息
		Object token = JSONPath.read(response, "$.data.token_info.token");
		//token不等于空，说明登录成功
		if(token != null) {
			//存储token到环境变量中
			env.put("token", token.toString());
			//token不会为空那么获取id肯定是memberId
			Object memberId = JSONPath.read(response, "$.data.id");
			if(memberId != null) {
				//存储memberId
				env.put("memberId", memberId.toString());
			}
		}
	}
	
	/**
	 * 判断环境变量中是否存在token值，如果存在请求中设置token
	 * @param request
	 */
	public static void setTokenInRequest(HttpRequest request) {
		//从环境变量中取出token
		String token = env.get("token");
		//如果token存在
		if(StringUtils.isNotBlank(token)) {
			//添加鉴权头
			request.setHeader("Authorization", "Bearer " + token);
		}
	}
	
	
}
