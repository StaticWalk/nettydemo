package com.iot.TCP_Package;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/13
 * Time:9:35
 *
 * TCP传输过程中的粘包和拆包
 *
 *
 */
public class BaseClientHandler extends ChannelInboundHandlerAdapter {

	private byte[] req;

	private int counter;

	public BaseClientHandler() {

		//粘包
        req = ("BazingaLyncc is learner" + System.getProperty("line.separator"))
            .getBytes();


		//拆包
//		req = ("In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. His book w"
//				+ "ill give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the process "
//				+ "of configuring and connecting all of Netty’s components to bring your learned about threading models in ge"
//				+ "neral and Netty’s threading model in particular, whose performance and consistency advantages we discuss"
//				+ "ed in detail In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. Hi"
//				+ "s book will give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the"
//				+ " process of configuring and connecting all of Netty’s components to bring your learned about threading "
//				+ "models in general and Netty’s threading model in particular, whose performance and consistency advantag"
//				+ "es we discussed in detailIn this chapter you general, we recommend Java Concurrency in Practice by Bri"
//				+ "an Goetz. His book will give We’ve reached an exciting point—in the next chapter;the counter is: 1 2222"
////				+ "sdsa ddasd asdsadas dsadasdas").getBytes();
//				// System.getProperty 使用结束标志来切分数据
//				+ "sdsa ddasd asdsadas dsadasdas"+System.getProperty("line.separator")).getBytes();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;

		//分一百次发送的数据包 数据量太小了会被粘在一起
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }

		//发送数据报，发了两次相同内容
		//内容是连到一起的没有变成4次接收？？ 缓冲区
//		message = Unpooled.buffer(req.length);
//		message.writeBytes(req);
//		ctx.writeAndFlush(message);
//		message = Unpooled.buffer(req.length);
//		message.writeBytes(req);
//		ctx.writeAndFlush(message);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String buf = (String) msg;
		System.out.println("Now is : " + buf + " ; the counter is : "+ ++counter);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}



}