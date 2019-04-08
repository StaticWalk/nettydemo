package com.iot.Netty权威指南.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by xiongxiaoyu
 * Data:2019/4/4
 * Time:10:25
 */
public class Client {

	//多路复用器、选择器  底层实现因版本而定
	// select/poll 循环注册到这个复用器的所有相关事件FD
	// epoll 从fd就绪队列中获取事件
	private Selector selector;
	public Client init (String svrIp,int port) throws IOException {
		//获取socket
		//reactor中的资源 select的fd、epoll的handle(事件驱动的fd)
		SocketChannel socketChannel = SocketChannel.open();
		// 配置非阻塞
		socketChannel.configureBlocking(false);

		selector = Selector.open();
		socketChannel.connect(new InetSocketAddress(svrIp,port));
		// 为channel注册选择器事件
		socketChannel.register(selector, SelectionKey.OP_CONNECT);

		return this;
	}

	public void listen() throws IOException {
		System.out.println("client is starting");

		while (true){
			//选择注册过的io事件（第一次为SelectionKey.OPTION_CONNECT）
			selector.select();
			// 获取注册在这个复用器上的channel channelEvent
			// 由SelectionKey封装
			Iterator<SelectionKey> ites = selector.selectedKeys().iterator();

			while (ites.hasNext()){
				SelectionKey selectionKey = ites.next();
				ites.remove();

				if (selectionKey.isConnectable()){

					SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

					//如果正在连接 完成连接
					if (socketChannel.isConnectionPending()){
						socketChannel.finishConnect();
					}

					socketChannel.write(ByteBuffer.wrap(new String("send message to server ").getBytes()));

					socketChannel.register(selector,SelectionKey.OP_READ);
					System.out.println("client connection successful ");

				}else if (selectionKey.isReadable()){

					SocketChannel channel = (SocketChannel) selectionKey.channel();

					ByteBuffer buffer = ByteBuffer.allocate(1024);
					channel.read(buffer);
					byte[] data = buffer.array();
					String message = new String(data);
					System.out.println("receive message from server : size " + buffer.position() + " msg ; " + message);
					ByteBuffer outbuffer = ByteBuffer.wrap(("client.".concat(message)).getBytes());
					channel.write(outbuffer);

				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new Client().init("localhost",8080).listen();
	}

}
