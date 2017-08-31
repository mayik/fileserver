package com.fileserver.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * web常用工具包
 * 2015年8月31日 下午6:19:32
 * @author zhouyi
 */
public class WebUtils {
	/**
	 * 获取一个键值对的map
	 * @param keys 键数据
	 * @param values 值数组
	 * @return 返回一个完整的map
	 */
	public static Map<String,Object> getMap(String[] keys,Object[] values) {
		if (keys != null && values != null && keys.length == values.length) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0, len = keys.length; i < len; i++) {
				map.put(keys[i], values[i]);
			}
			return map;
		}
		return null;
	}
	
	public static Map<String,Object> getMap(String keys,Object values) {
		return getMap(new String[]{keys}, new Object[]{values});
	}
	
	//获得指定文件夹在服务器的真是路径，通过纯粹的java方法获取
	public static String getRealPath(String dir){
		return Thread.currentThread().getContextClassLoader().getResource("/").getPath().replace("WEB-INF/classes/", dir);
	}
	
	//获取文件基础路径,以http模式的路径，浏览器可以直接访问
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme()+"://"
					+request.getServerName()+":"
					+request.getServerPort()+path+"/";
	}
	
	
	public static int getRandom(int baseMinNum,int baseBigNum) {
		//int random = (int) ((Math.random()*(endNum - beginNum + 1)) - beginNum );
		int random=(int) Math.round(Math.random()*(baseBigNum-baseMinNum)+baseMinNum);
		return random;
	}
	
	
	
	/**获取给定文件名称的后缀*/
	public static String getSuffix(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")); 
	}
	
	/**
	 * 获取UUID
	 * 2015年9月11日 上午10:22:45
	 * @return 
	 * @author zhouyi
	 */
	public static String getUUID() {
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString().replaceAll("-", "");
	}
	
	public static void rollback() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
	
	/**
	 * bui，返回分页页码是从0开始，我们分页数据库页码按照从1开始，此处针对bui处理
	 * 2015年9月16日 下午5:15:20
	 * @param pageIndex 分页页码
	 * @return 返回正常页码
	 * @author zhouyi
	 */
	public static Integer getPageNo(Integer pageNo) {
		if (StringUtils.isValid(pageNo)) {
			++pageNo;
		}
		return pageNo;
	}
	
	/**
	 * 得到request对象
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 得到response对象
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}
	
	/**
	 * 获取ip地址
	 * 2015年9月21日 下午2:24:46
	 * @return 返回ip地址
	 * @author zhouyi
	 */
	public static String getRemoteHost() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
	
	
	/**返回jsonobject对象*/
	public static JSONObject getJSONObject(String message,boolean flag) {
		JSONObject obj = new JSONObject();
		obj.put("message", message);
		obj.put("flag", flag);
		return obj;
	}
	
}
