package com.fz.server;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
	/**
	 * 为每一个servlet取对应的别名
	 * login--->loginServlet
	 */
	private Map<String,String> servlet;
	/**
	 * 对应每个servlet的url
	 */
	private Map<String,String> mapping;
	public ServletContext(){
		servlet = new HashMap<String,String>();
		mapping = new HashMap<String,String>();
	}
	public Map<String, String> getServlet() {
		return servlet;
	}
	public void setServlet(Map<String, String> servlet) {
		this.servlet = servlet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
}
