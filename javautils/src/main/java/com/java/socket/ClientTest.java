package com.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 简单client客户端
 * @author Tim
 *
 */
public class ClientTest {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 8081);
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);

			// 获取输入流
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// 进行写操作
			String info = "用户名：Tom，密码：123456";
			pw.write(info);
			pw.flush();
			socket.shutdownOutput();

			// 接收服务端响应
			String reply = null;
			while (!((reply = br.readLine()) == null)) {
				System.out.println("我是客户端，服务端响应是：" + reply);
			}
			// 关闭流
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
