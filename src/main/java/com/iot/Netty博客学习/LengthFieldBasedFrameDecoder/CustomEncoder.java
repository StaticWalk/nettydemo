package com.iot.Netty博客学习.LengthFieldBasedFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * Created by xiongxiaoyu
 * Data:2018/6/13
 * Time:22:40
 */
public class CustomEncoder extends MessageToByteEncoder<CustomMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, CustomMsg msg, ByteBuf out) throws Exception {
		if(null == msg){
			throw new Exception("msg is null");
		}

		String body = msg.getBody();
		byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
		out.writeByte(msg.getType());
		out.writeByte(msg.getFlag());
		out.writeInt(bodyBytes.length);
		out.writeBytes(bodyBytes);

	}

}