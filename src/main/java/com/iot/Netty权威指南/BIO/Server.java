package com.iot.Netty权威指南.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/27
 * Time:20:37
 */
public class Server {

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = new ServerSocket(8080);
		Socket socket = null;

		int count = 0;
		System.out.println("server is starting");
		while (true){
			socket = serverSocket.accept();
			ServerThread thread = new ServerThread(socket);
			thread.start();
			count++;
			System.out.println(" the number of client is : " + count);
			System.out.println("the host id is: " + socket.getInetAddress());
		}
	}
}
