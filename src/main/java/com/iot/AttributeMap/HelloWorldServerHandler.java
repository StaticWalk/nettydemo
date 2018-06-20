package com.iot.AttributeMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/19
 * Time:21:14
 */
public class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server channelRead..");
		System.out.println(ctx.channel().remoteAddress()+"->Server :"+ msg.toString());
		ctx.write("server write"+msg);
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
