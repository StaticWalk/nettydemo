package com.iot.Netty博客学习.LengthFieldBasedFrameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/13
 * Time:22:37
 */
public class CustomServer {

	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
	//length的大小 int4个字节
	private static final int LENGTH_FIELD_LENGTH = 4;
	//length的起始位置  前面有两个byte
	private static final int LENGTH_FIELD_OFFSET = 2;
	//包含本身length长度
	private static final int LENGTH_ADJUSTMENT = 0;
	private static final int INITIAL_BYTES_TO_STRIP = 0;

	private int port;

	public CustomServer(int port) {
		this.port = port;
	}

	public void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
					.childHandler(new ChannelInitializer<SocketChannel>() {

						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new CustomDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false));
							ch.pipeline().addLast(new CustomServerHandler());
						};

					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			// 绑定端口，开始接收进来的连接
			ChannelFuture future = sbs.bind(port).sync();

			System.out.println("Server start listen at " + port );
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new CustomServer(port).start();
	}
}