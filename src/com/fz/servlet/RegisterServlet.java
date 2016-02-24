package com.fz.servlet;

import com.fz.server.Request;
import com.fz.server.Response;

public class RegisterServlet extends Servlet{

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		doPost(req, rep);
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
		rep.println("<html><head><title>注册成功</title>");
		rep.println("</head><body>");
		rep.println("您的用户名是："+req.getParameter("username"));
		rep.println("</body></html>");
	}

}
