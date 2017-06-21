package com.java.proxyhttp;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketProxyMain {

	static final int listenPort = 80;

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServerSocket serverSocket = new ServerSocket(listenPort);
		final ExecutorService tpe = Executors.newCachedThreadPool();
		System.out.println("Proxy Server Start At " + sdf.format(new Date()));
		System.out.println("listening port:" + listenPort + "……");

		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				socket.setKeepAlive(true);
				// 加入任务列表，等待处理
				tpe.execute(new ProxyTask(socket));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
