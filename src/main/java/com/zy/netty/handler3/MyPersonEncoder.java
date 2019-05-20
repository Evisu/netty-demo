package com.zy.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义协议编码器
 * 
 * @author walkman
 *
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {

	@Override
	protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
		System.out.println("MyPersonEncoder encode invoked");
		out.writeInt(msg.getLength());
		out.writeBytes(msg.getContent());
	}

}
