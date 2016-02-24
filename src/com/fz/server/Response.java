package com.fz.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import com.fz.util.CloseUtil;
import com.fz.util.URLDecoderUtil;

/**
 * 封装响应信息
 * @author FZ
 * @version 1.0  2015年11月20日 下午2:35:36
 */
public class Response {
	public static final String CRLF ="\r\n";//换行
	public static final String BLANK =" ";//空格
	
	private StringBuilder context;//正文
	private StringBuilder headInfo;//响应头信息
	private int len;//正文长度
	private BufferedWriter bw;//用于向客户端输出信息的流
	
	
	public Response() {
		headInfo = new StringBuilder();
		context = new StringBuilder();
		len = 0;
	}
	/**
	 * 使用一个Socket来构建Response对象
	 * @param os
	 */
	public Response(Socket client){
		this();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			headInfo = null;//如果构建出错，则将headInfo置为空
		}
	}
	/**
	 * 使用一个流来构建Response对象
	 * @param os
	 */
	public Response(OutputStream os){
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	/**
	 * 根据不同的状态码，构建不同的响应头信息
	 * @param code 应答码：200 404 500
	 */
	private void createHeadInfo(int code){
		//1.HTTP协议版本，状态吗，描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
			case 200:
				headInfo.append("OK");
				break;
			case 404:
				headInfo.append("NOT FOUND");
				break;
			case 500:
				headInfo.append("SERVER ERROR");
				break;
			default:
				break;
		}
		headInfo.append(CRLF);
		//2.响应头 Response head
		headInfo.append("Server:fangzheng Server/0.1").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
		//正文长度
		headInfo.append("Content-Length:").append(len).append(CRLF);
		headInfo.append(CRLF);//正文和响应头之间有一个换行
	}
	/**
	 * 构建正文
	 * @param info  正文信息
	 * @return
	 */
	public Response print(String info){
		context.append(info);
		len +=info.getBytes().length;//给正文长度赋值
		return this;
	}
	/**
	 * 构建带换行的正文
	 * @param info  正文信息
	 * @return
	 */
	public Response println(String info){
		context.append(info).append(CRLF);
		len +=(info+CRLF).getBytes().length;//给正文长度赋值
		return this;
	}
	
	/**
	 * 将信息推送到客户端
	 * @param code
	 * @throws IOException 
	 */
	void pushToClient(int code) throws IOException{
		if (null == headInfo) {
			code = 500;
		}
		createHeadInfo(code);
		bw.append(headInfo);//响应头+换行符
		bw.append(URLDecoderUtil.decode(context.toString(),"UTF-8"));//正文
		bw.flush();
	}
	/**
	 * 关闭流
	 */
	public void close(){
		CloseUtil.closeIO(bw);
	}
}
