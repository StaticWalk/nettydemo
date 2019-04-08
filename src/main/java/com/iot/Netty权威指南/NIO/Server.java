package com.iot.Netty权威指南.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by xiongxiaoyu
 * Data:2019/4/4
 * Time:10:25
 *
 *  Reactor		Java Nio
 *  资源      	channel
 *  选择器    	selector
 *  分发器		按照事件类型对事件进行处理
 *  处理器 		对事件的具体处理
 *
 *
 *
 */
public class Server {

	//多路复用器
	private Selector selector;

	public Server init(int port) throws IOException {

		//
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		return this;

	}

	public void listen() throws IOException {
		System.out.println("server is started");
		while (true){
			selector.select();
			Iterator<SelectionKey> ites= selector.selectedKeys().iterator();
			while (ites.hasNext()){
				SelectionKey key = ites.next();
				ites.remove();
				if (key.isAcceptable()){
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					channel.configureBlocking(false);

					channel.write(ByteBuffer.wrap(new String("send message to client").getBytes()));

					channel.register(selector,SelectionKey.OP_READ);
				}else if (key.isReadable()){
					SocketChannel channel = (SocketChannel) key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					channel.read(buffer);
					byte[] data = buffer.array();
					String msg = new String(data);
					ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
					channel.write(outBuffer);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new Server().init(8080).listen();
	}

}
