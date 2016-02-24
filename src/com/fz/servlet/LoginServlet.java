package com.fz.servlet;

import com.fz.server.Request;
import com.fz.server.Response;

public class LoginServlet extends Servlet{

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (username!=null && password !=null) {
			if (username.equals("fangzheng") && password.equals("123456")) {
				rep.println("欢迎您："+username);
			}else {
				rep.println("登录失败！");
			}
		}
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
	}

}
