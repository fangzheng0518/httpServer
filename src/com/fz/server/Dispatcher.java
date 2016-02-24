package com.fz.server;

import java.io.IOException;
import java.net.Socket;

import com.fz.servlet.Servlet;
import com.fz.util.CloseUtil;

/**
 * 一个请求对应一个dispatcher对象
 * @author Administrator
 *
 */
public class Dispatcher implements Runnable{
	private Socket client;
	private Request req;
	private Response rep;
	private int code=200;
	public Dispatcher(Socket client) {
		this.client = client;
		try {
			req = new Request(client.getInputStream());
			rep = new Response(client.getOutputStream());
		} catch (IOException e) {
			code=500;
			return;
		}
	}



	@Override
	public void run() {
		try {
			String url = req.getUrl();
			System.out.println("请求地址："+url);
			Servlet serv = WebApp.getServlet(url);
			if (null == serv) {
				this.code=404;
			}else {
				serv.service(req, rep);
			}
			rep.pushToClient(code);//发送到客户端
		} catch (Exception e) {
			this.code=500;
		}
		try {
			rep.pushToClient(500);//发送到客户端
		} catch (IOException e) {
			e.printStackTrace();
		}
		CloseUtil.closeSocket(client);
	}

}
