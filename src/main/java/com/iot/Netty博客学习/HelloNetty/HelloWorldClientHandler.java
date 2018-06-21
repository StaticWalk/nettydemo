package com.iot.Netty博客学习.HelloNetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/11
 * Time:15:11
 */
public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter{


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("HelloWorldClientHandler Active");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("HelloWorldClientHandler read Message "+msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
