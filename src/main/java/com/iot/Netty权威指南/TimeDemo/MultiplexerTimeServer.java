package com.iot.Netty权威指南.TimeDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/21
 * Time:16:16
 */
public class MultiplexerTimeServer implements Runnable{


	//1.打开serversocketchannel
	private ServerSocketChannel servChannel;

	private Selector selector;

	private volatile boolean stop;

	public MultiplexerTimeServer(int port) {
		try{
			selector=Selector.open();
			servChannel=ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			servChannel.socket().bind(new InetSocketAddress(port),1024);
			servChannel.register(selector, SelectionKey.OP_READ);
			System.out.println("The timr server is start in port "+port);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop(){
		this.stop=true;
	}


	@Override
	public void run() {
		while (!stop){
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys=selector.selectedKeys();
				Iterator<SelectionKey> it=selectionKeys.iterator();
				SelectionKey key=null;
				while (it.hasNext()){
					key=it.next();
					it.remove();
					try{
						handleInput(key);
					}catch (Exception e){
						if (key!=null){
							key.cancel();
							if (key.channel()!=null){
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//selector关闭后，上面的channel和pipe等资源会被自动注册

		if (selector!=null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {

		if (key.isValid()){
			//处理新连接的请求
			if (key.isAcceptable()){
				//accept the new connection
				ServerSocketChannel ssc= (ServerSocketChannel) key.channel();
				SocketChannel sc=ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector,SelectionKey.OP_READ);
			}
			if (key.isReadable()){
				//read the data
				SocketChannel sc= (SocketChannel) key.channel();


			}
		}

	}



	private void doWrite(SocketChannel channel,String response) throws IOException {

		if (response !=null && response.trim().length() > 0){
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);

		}

	}
}











