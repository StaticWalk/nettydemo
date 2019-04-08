package com.iot.Netty权威指南.BIO;

import java.io.*;
import java.net.Socket;

/**
 * Created by xiongxiaoyu
 * Data:2019/4/3
 * Time:22:06
 */
public class ServerThread extends Thread{

	Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String info = null;
			while ((info = br.readLine()) != null){
				System.out.println("i am server client said:" + info);
			}

			socket.shutdownInput();
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			pw.write("welcome!");
			pw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//关闭资源
			try {
				if(pw!=null)
					pw.close();
				if(os!=null)
					os.close();
				if(br!=null)
					br.close();
				if(isr!=null)
					isr.close();
				if(is!=null)
					is.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
