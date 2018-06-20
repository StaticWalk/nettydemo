package com.iot.AttributeMap;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;

import static com.iot.AttributeMap.AttributeMapConstant.NETTY_CHANNEL_KEY;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/19
 * Time:21:15
 */
public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {


	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		Attribute<NettyChannel> attr = ctx.attr(NETTY_CHANNEL_KEY);
//		Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);
		NettyChannel nChannel = attr.get();
		if (nChannel == null) {
			NettyChannel newNChannel = new NettyChannel("HelloWorldClient", new Date());
			nChannel = attr.setIfAbsent(newNChannel);
			System.out.println("handler 注入");
		} else {
			System.out.println("channelActive attributeMap 中是有值的");
			System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
		}
		System.out.println("HelloWorldClientHandler Active");
		ctx.fireChannelActive();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Attribute<NettyChannel> attr = ctx.attr(NETTY_CHANNEL_KEY);
//		Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);
		NettyChannel nChannel = attr.get();
		if (nChannel == null) {
			NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
			nChannel = attr.setIfAbsent(newNChannel);
		} else {
			System.out.println("channelRead attributeMap 中是有值的");
			System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
		}
		System.out.println("HelloWorldClientHandler read Message:" + msg);

		ctx.fireChannelRead(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}