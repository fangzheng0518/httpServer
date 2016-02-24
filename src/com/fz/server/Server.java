package com.fz.server;

import java.io.IOException;
import java.net.ServerSocket;

import com.fz.util.CloseUtil;

/**
 * 服务器，接收请求
 * 访问地址：http://localhost:8888/index
 * @author FZ
 * @version 1.0  2015年11月19日 下午3:33:39
 */
public class Server {
	private ServerSocket server;
	public static final String CRLF ="\r\n";//换行
	public static final String BLANK =" ";//空格
	private boolean isShutDown = false;
	/**
	 * 启动服务
	 */
	public void start(){
		this.start(8888);
	}
	/**
	 * 启动服务
	 */
	public void start(int port){
		try {
			server = new ServerSocket(port);
			this.receive();//启动服务后准备接收
		} catch (IOException e) {
			stop();
		}
	}
	/**
	 * 接收客户端请求
	 */
	private void receive(){
		try {
			while (!isShutDown) {
				new Thread(new Dispatcher(server.accept())).start();
			}
		} catch (IOException e) {
			stop();
		}
	}
	/**
	 * 停止服务
	 */
	public void stop(){
		isShutDown = true;
		CloseUtil.closeSocket(server);
	}
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		server.receive();
	}
}
