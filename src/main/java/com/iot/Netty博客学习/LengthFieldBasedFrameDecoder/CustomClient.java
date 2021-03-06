package com.iot.Netty博客学习.LengthFieldBasedFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/13
 * Time:22:39
 */
public class CustomClient {

	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
	static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

	public static void main(String[] args) throws Exception {

		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {

							//C/S模式中，各方都要指定自己的编解码器和Handler
							ch.pipeline().addLast(new CustomEncoder());
							ch.pipeline().addLast(new CustomClientHandler());
						}
					});

			ChannelFuture future = b.connect(HOST, PORT).sync();
			future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

}