package com.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server端简单示例
 * @author Tim
 *
 */
public class ServerSocketTest {

	public static void main(String[] args) {

		try {
			// 建立一个服务器绑定指定端口Socket(ServerSocket)并开始监听
			ServerSocket serverSocket = new ServerSocket(8081);

			// 使用accept()方法阻塞等待监听，获得新的连接
			Socket socket = serverSocket.accept();

			// 获得输入流
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// 获得输出流
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);

			// 读取用户输入信息
			String info = null;
			while (!((info = br.readLine()) == null)) {
				System.out.println("我是服务器，用户信息为：" + info);
			}

			// 响应信息
			String reply = "welcome";
			pw.write(reply);
			pw.flush();

			// 关闭流
			pw.close();
			os.close();
			br.close();
			is.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
