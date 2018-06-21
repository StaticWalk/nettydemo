package com.iot.Netty博客学习.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by xiongxiaoyu
 * Data:2018/3/28
 * Time:22:08
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
		ByteBuf m = (ByteBuf) msg; // (1)
		try {
			long currentTimeMills = (m.readUnsignedInt() - 2208988800L) * 1000L;
			System.out.println(new Date(currentTimeMills));
			ctx.close();
		} finally {
			m.release();
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		ctx.close();
	}
}
