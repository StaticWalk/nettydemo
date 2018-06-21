package com.iot.Netty博客学习.HelloNetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/11
 * Time:16:19
 */
public class BaseClient2Handler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("BaseClient2Handler channelActive");
	}


	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("BaseClient1Handler channelInactive");
	}


}