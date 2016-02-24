package com.fz.server;

/**
<servlet>
	<servlet-name>login</servlet-name>
	<servlet-class>com.fz.server.demo1.LoginServlet</servlet-class>
<servlet>
 * @author Administrator
 *
 */
public class Entity {
	/**
	 * 存储servlet-name
	 */
	private String name;
	/**
	 * 存储servlel-class
	 */
	private String clz;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClz() {
		return clz;
	}
	public void setClz(String clz) {
		this.clz = clz;
	}
	
}
