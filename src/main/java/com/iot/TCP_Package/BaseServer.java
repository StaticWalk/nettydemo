package com.iot.TCP_Package;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/13
 * Time:9:36
 */
public class BaseServer {

	private int port;

	public BaseServer(int port) {
		this.port = port;
	}

	public void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
					.childHandler(new ChannelInitializer<SocketChannel>() {

						protected void initChannel(SocketChannel ch) throws Exception {


							//解决拆包通过 Server的添加LineBasedFrameDecoder这个解码器handler配置参数解决
							//规定TCP传输的数据最大字节数  如果单次ctx.writeAndFlush(message)报错
//                            ch.pipeline().addLast(new LineBasedFrameDecoder(2048));

							//按照指定字符对收到帧进行分割 帧末尾指定字符之后的内容会被抛弃
//							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer("$$__".getBytes())));

							//解决粘包 Server端添加FixedLengthFrameDecoder来拆分帧数据
							ch.pipeline().addLast(new FixedLengthFrameDecoder(25));

							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new BaseServerHandler());
						}

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
		new BaseServer(port).start();
	}
}

