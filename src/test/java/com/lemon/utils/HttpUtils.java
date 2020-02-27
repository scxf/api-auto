package com.lemon.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.lemon.constant.Constants;

public class HttpUtils {
	
	public static String json2keyValue(String json) {
		//1、json转成map
		HashMap<String,String> map = JSONObject.parseObject(json, HashMap.class);
		//2、获取所有的key
		Set<String> keySet = map.keySet();
		//3、定义返回结果字符串
		String result = "";
		//4、循环遍历所有key和value
		for (String key : keySet) {
			//通过key找value
			String value = map.get(key);
			result += key + "=" + value + "&";
		}
		result = result.substring(0,result.length()-1);
//		for (String key : keySet) {
//			//通过key找value
//			String value = map.get(key);
//			if(result.length() > 0) {
//				result += "&";
//			}
//			result += key + "=" + value ;
//		}
		System.out.println(result);
		return result;
	} 
	
	/**
	 * call 发起http请求
	 * @param url			接口url地址
	 * @param type			接口请求method方法
	 * @param params		接口参数
	 * @param contentType	接口类型
	 */
	public static String call(String url,String type,String params,String contentType,boolean isAuthorization) {
		try {
			//如果是rest风格接口
			if("json".equalsIgnoreCase(contentType)) {
				//如果是post请求方式
				if("post".equalsIgnoreCase(type)) {
					return HttpUtils.jsonPost(url, params,isAuthorization);
				//如果是get请求方式
				}else if("get".equalsIgnoreCase(type)) {
					//处理url上的参数
					return HttpUtils.jsonGet(url);
				//如果是patch请求方式
				}else if("patch".equalsIgnoreCase(type)) {
					return HttpUtils.jsonPatch(url, params,isAuthorization);
				}
			//如果是表单类型的接口
			}else if("form".equalsIgnoreCase(contentType)) {
				//如果是post请求方式
				if("post".equalsIgnoreCase(type)) {
					//JSON参数转成form类型的参数
					params = json2keyValue(params);
					return HttpUtils.formPost(url, params,isAuthorization);
				//如果是get请求方式
				}else if("get".equalsIgnoreCase(type)) {
					return HttpUtils.formGet(url, params);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String jsonGet(String url) throws Exception {	
		//参数可能在中间也可能在最后，所以不好统一处理，那么传入url必须携带参数
		HttpGet get = new HttpGet(url);
		//4、有参传参有头加头
		get.setHeader(Constants.HEADER_MEDIA_TYPE_NAME, Constants.HEADER_MEDIA_TYPE_VALUE);
		//5、点击发送按钮
		CloseableHttpClient client = HttpClients.createDefault();
		//由客户端发出请求,6、接受响应
		//记录开始时间
		long startTime = System.currentTimeMillis();
		CloseableHttpResponse response = client.execute(get);
		long endTime = System.currentTimeMillis();
		System.out.println("接口响应时间：" + (endTime - startTime) + "ms");
		//记录结束时间
		//body
		HttpEntity entity = response.getEntity();
		//statuscode
		int statusCode = response.getStatusLine().getStatusCode();
		//headers
		Header[] allHeaders = response.getAllHeaders();
		//工具类的命名规则 = 处理对象 + Utils/s
		String body = EntityUtils.toString(entity);
		System.out.println(body);
		System.out.println(statusCode);
		System.out.println(Arrays.toString(allHeaders));
		return body;
	}
	
	public static String formGet(String url,String params) throws Exception {	
		//http://test.lemonban.com/futureloan/mvc/api/member/login?mobilephone=13212312333&pwd=123456
		HttpGet get = new HttpGet(url + "?" + params);
		//4、有参传参有头加头
		get.setHeader(Constants.HEADER_MEDIA_TYPE_NAME, Constants.HEADER_MEDIA_TYPE_VALUE);
		//5、点击发送按钮
		CloseableHttpClient client = HttpClients.createDefault();
		//由客户端发出请求,6、接受响应
		//记录开始时间
		long startTime = System.currentTimeMillis();
		CloseableHttpResponse response = client.execute(get);
		long endTime = System.currentTimeMillis();
		System.out.println("接口响应时间：" + (endTime - startTime) + "ms");
		//记录结束时间
		//body
		HttpEntity entity = response.getEntity();
		//statuscode
		int statusCode = response.getStatusLine().getStatusCode();
		//headers
		Header[] allHeaders = response.getAllHeaders();
		//工具类的命名规则 = 处理对象 + Utils/s
		String body = EntityUtils.toString(entity);
		System.out.println(body);
		System.out.println(statusCode);
		System.out.println(Arrays.toString(allHeaders));
		return body;
	}
	
	public static String jsonPost(String url,String params,boolean isAuthorization) throws Exception {
		//1、创建request（请求）
		HttpPost post = new HttpPost(url);
		//4、有参传参有头加头
		post.setHeader(Constants.HEADER_MEDIA_TYPE_NAME, Constants.HEADER_MEDIA_TYPE_VALUE);
		post.setHeader("Content-Type", "application/json");
		//添加鉴权头
		if(isAuthorization) {
			AuthorizationUtils.setTokenInRequest(post);
		}
		post.setEntity(new StringEntity(params,"UTF-8"));
		//5、点击发送按钮
		CloseableHttpClient client = HttpClients.createDefault();
		//6、接受响应
		CloseableHttpResponse response = client.execute(post);
		//7、格式化响应内容（body、statuscode、headers）
		HttpEntity entity = response.getEntity();
		//statuscode
		int statusCode = response.getStatusLine().getStatusCode();
		//headers
		Header[] allHeaders = response.getAllHeaders();
		//工具类的命名规则 = 处理对象 + Utils/s
		String body = EntityUtils.toString(entity);
		System.out.println(body);
		System.out.println(statusCode);
		System.out.println(Arrays.toString(allHeaders));
		return body;
	}
	
	public static String formPost(String url,String params,boolean isAuthorization) throws Exception {
		HttpPost post = new HttpPost(url);
		//4、有参传参有头加头
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		post.setEntity(new StringEntity(params,"UTF-8"));
		//5、点击发送按钮
		CloseableHttpClient client = HttpClients.createDefault();
		//6、接受响应
		CloseableHttpResponse response = client.execute(post);
		//7、格式化响应内容（body、statuscode、headers）
		HttpEntity entity = response.getEntity();
		//statuscode
		int statusCode = response.getStatusLine().getStatusCode();
		//headers
		Header[] allHeaders = response.getAllHeaders();
		//工具类的命名规则 = 处理对象 + Utils/s
		String body = EntityUtils.toString(entity);
		System.out.println(body);
		System.out.println(statusCode);
		System.out.println(Arrays.toString(allHeaders));
		return body;
	}
	
	public static String jsonPatch(String url,String params,boolean isAuthorization) throws Exception {
		HttpPatch patch = new HttpPatch(url);
		//4、有参传参有头加头
		patch.setHeader(Constants.HEADER_MEDIA_TYPE_NAME, Constants.HEADER_MEDIA_TYPE_VALUE);
		patch.setHeader("Content-Type", "application/json");
		patch.setEntity(new StringEntity(params,"UTF-8"));
		//添加鉴权头
		if(isAuthorization) {
			AuthorizationUtils.setTokenInRequest(patch);
		}
		//5、点击发送按钮
		CloseableHttpClient client = HttpClients.createDefault();
		//6、接受响应
		CloseableHttpResponse response = client.execute(patch);
		//7、格式化响应内容（body、statuscode、headers）
		HttpEntity entity = response.getEntity();
		//statuscode
		int statusCode = response.getStatusLine().getStatusCode();
		//headers
		Header[] allHeaders = response.getAllHeaders();
		//工具类的命名规则 = 处理对象 + Utils/s
		String body = EntityUtils.toString(entity);
		System.out.println(body);
		System.out.println(statusCode);
		System.out.println(Arrays.toString(allHeaders));
		return body;
	}
	
}
