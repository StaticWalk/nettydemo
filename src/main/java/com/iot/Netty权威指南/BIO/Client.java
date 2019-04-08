package com.iot.Netty权威指南.BIO;

import java.io.*;
import java.net.Socket;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/27
 * Time:20:37
 */
public class Client {

	public static void main(String[] args) throws IOException {

		Socket socket = new Socket("localhost",8080);

		//客户端发送信息  使用socket的OutputStream PrintWriter包装类
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os);
		pw.write("hello");
		pw.flush();
		socket.shutdownOutput();

		//客户端发送信息
		// InputStream 所有input字节流的抽象父类
		// InputStreamReader 通过charset decode字节流 转成字符流
		// BufferedReader 从字符输入流中读取文本，缓冲字符，以便有效地读取字符，数组和行。
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String info = null;
		while ((info = br.readLine()) != null){
			System.out.println("i am client server said:" + info);
		}

		br.close();
		is.close();
		pw.close();
		os.close();
		socket.close();

	}
}
