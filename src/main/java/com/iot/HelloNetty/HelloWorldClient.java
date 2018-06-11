package com.iot.HelloNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * Created by xiongxiaoyu
 * Data:2018/6/11
 * Time:15:14
 */


public class HelloWorldClient {

	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
	static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

	public static void main(String[] args) {

		EventLoopGroup group=new NioEventLoopGroup();
		try{
			Bootstrap bootstrap= new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY,true)
					.handler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline p= socketChannel.pipeline();
							p.addLast("decoder", new StringDecoder());
							p.addLast("encoder", new StringEncoder());
//							p.addLast(new HelloWorldClientHandler());
							p.addLast(new BaseClient1Handler());
							p.addLast(new BaseClient2Handler());
						}
					});

			ChannelFuture future= bootstrap.connect(HOST, PORT).sync();
			future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
			future.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
