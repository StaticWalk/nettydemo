package com.iot.Netty博客学习.LengthFieldBasedFrameDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/13
 * Time:22:38
 */
public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {

	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof CustomMsg) {
			CustomMsg customMsg = (CustomMsg)msg;
			System.out.println("Client->Server:"+ctx.channel().remoteAddress()+" send "+customMsg.getBody());
		}

	}

	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

	}
}
