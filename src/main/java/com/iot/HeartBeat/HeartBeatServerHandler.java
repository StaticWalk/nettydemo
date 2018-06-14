package com.iot.HeartBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/14
 * Time:15:19
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter{

	private int loss_connect_time = 0;

	public void userEventTriggered(ChannelHandlerContext ctx,Object evt) throws Exception {

		//IdleState异常会触发自动调用userEventTriggered方法
		if (evt instanceof IdleStateEvent){
			IdleStateEvent event= (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE){
				loss_connect_time++;
				System.out.println("5s没有收到客户端信息了");
				if (loss_connect_time>2){
					System.out.println("关闭这个不活跃的channel");
					ctx.channel().close();
				}
			}
		}else {
			super.userEventTriggered(ctx,evt);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server channelRead.."+"接收时间:"+new Date());
		System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
