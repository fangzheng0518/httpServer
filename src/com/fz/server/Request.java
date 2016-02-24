package com.fz.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.fz.util.URLDecoderUtil;

/**
 * 封装resquest
 * @author FZ
 * @version 1.0  2015年11月20日 下午3:19:39
 */
public class Request {

	//请求方式
	private String method;
	//请求资源
	private String url;
	//请求参数
	private Map<String,List<String>> parameterMapValues;
	
	public static final String CRLF ="\r\n";//换行
	private String requestInfo;
	
	public Request() {
		method ="";
		url ="";
		requestInfo ="";
		parameterMapValues = new HashMap<String,List<String>>();
	}
	public Request(InputStream is) {
		this();
		try {
			byte[] data = new byte[20480];
			int len = is.read(data);
			requestInfo = new String(data,0,len);
		} catch (Exception e) {
			return ;
		}
		//分析头信息
		parseRequestInfo();
	}
	/**
	 * 解析请求信息
	 */
	private void parseRequestInfo(){
		if (null == requestInfo || (requestInfo=requestInfo.trim()).equals("")) {
			return;
		}
		String paramString = "";//接收请求参数
		//1.获取请求方式
		String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));//通过换行符截取第一行
		int idx = requestInfo.indexOf("/");//获取/的位置
		this.method = firstLine.substring(0, idx).trim();//从第一个/截取get或者post字符
		String urlStr = firstLine.substring(idx,firstLine.indexOf("HTTP/")).trim();
		if (this.method.equalsIgnoreCase("post")) {
			this.url = urlStr;
			paramString= requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();//最后一个换行符处的参数
		}else if (this.method.equalsIgnoreCase("get")) {
			if (urlStr.contains("?")) {//是否存在参数
				String[] urlArray = url.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1];//接收请求参数
			}else {
				this.url = urlStr;
			}
		}
		
		//2.解析请求参数：将参数添加到map中
		if (paramString.equals("")) {//参数为空直接返回
			return;
		}
		parseParams(paramString);
		
		
		
		
	}
	/**
	 * 解析请求参数
	 * @param paramString
	 */
	public void parseParams(String paramString){
		//将字符串转成数组
		StringTokenizer token = new StringTokenizer(paramString, "&");
		while (token.hasMoreTokens()) {
			String keyValue = token.nextToken();
			String[] keyValues = keyValue.split("=");
			if (keyValues.length==1) {//如果参数只有键没有值   username=
				//将值赋值为null
				keyValues = Arrays.copyOf(keyValues, 2);
				keyValues[1]=null;
			}
			//username=admin
			String key = keyValues[0].trim();//key：username
			String value = null==keyValues[1]?null:URLDecoderUtil.decode(keyValues[1].trim(),"UTF-8");//value：admin
			
			if (!parameterMapValues.containsKey(key)) {//如果map中不存在该参数
				parameterMapValues.put(key, new ArrayList<String>());
			}
			
			List<String> values = parameterMapValues.get(key);
			values.add(value);
		}
		
	}
	/**
	 * 根据name获取对应的多个值
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name){
		List<String> values = null;
		if ((values=parameterMapValues.get(name))==null) {
			return null;
		}else {
			return values.toArray(new String[0]);
		}
	}
	/**
	 * 根据name获取对应的单个值
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		String[] values = getParameterValues(name);
		if (null == values) {
			return null;
		}
		return values[0];
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
