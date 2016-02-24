package com.fz.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {

	/**
	 * 使用泛型方法实现关闭IO流
	 * @param io
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Closeable> void closeIO(T... io){
		for (Closeable temp : io) {
				try {
					if (null != temp) {
						temp.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	public static void closeSocket(ServerSocket socket){
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (Exception e) {
		}
	}
	public static void closeSocket(Socket socket){
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (Exception e) {
		}
	}
	public static void closeSocket(DatagramSocket socket){
		try {
			if (null != socket) {
				socket.close();
			}
		} catch (Exception e) {
		}
	}
	
	
}
