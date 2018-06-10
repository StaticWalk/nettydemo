package com.iot;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by xiongxiaoyu
 * Data:2018/3/28
 * Time:20:58
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		//discard service
		ByteBuf in = (ByteBuf) msg;
		try {
			while (in.isReadable()) { // (1)
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		} finally {
			ReferenceCountUtil.release(msg); // (2)
		}

		//echo service
//		ctx.write(msg);
//		ctx.flush();

		//TIME protoclo


	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception  { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}