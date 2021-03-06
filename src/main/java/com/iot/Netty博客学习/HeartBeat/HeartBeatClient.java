package com.iot.Netty博客学习.HeartBeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/14
 * Time:15:56
 */
public class HeartBeatClient {


	public void connect(int port, String host) throws Exception {
		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		ChannelFuture future = null;
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new LoggingHandler(LogLevel.INFO))
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
							p.addLast("decoder", new StringDecoder());
							p.addLast("encoder", new StringEncoder());
							p.addLast(new HeartBeatClientHandler());
						}
					});

			future = b.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} finally {

			//常规情况下是断开连接后释放资源
//			group.shutdownGracefully();

			//尝试在finally中重连
			if (null != future) {
				if (future.channel() != null && future.channel().isOpen()) {
					future.channel().close();
				}
			}
			System.out.println("准备重连");
			connect(port, host);
			System.out.println("重连成功");
		}
	}


	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}
		new HeartBeatClient().connect(port, "127.0.0.1");
	}

}
