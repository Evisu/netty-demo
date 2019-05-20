package com.zy.netty.handler3;

import java.nio.charset.Charset;
import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 自定义协议处理器
 * 
 * @author walkman
 *
 */
public class MyServerHandle extends SimpleChannelInboundHandler<PersonProtocol> {
	
	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
		
		int length = msg.getLength();
		byte[] content = msg.getContent();
		
		System.out.println("服务器收到数据：");
		System.out.println("长度：" + length);
		System.out.println("内容：" + new String(content, Charset.forName("utf-8")));
		
		System.out.println("服务器收到消息的个数：" + (++count));
		
		String responseMessage = UUID.randomUUID().toString();
		int responseLength = responseMessage.getBytes(Charset.forName("utf-8")).length;
		byte[] responseContent = responseMessage.getBytes(Charset.forName("utf-8"));
		PersonProtocol personProtocol = new PersonProtocol();
		personProtocol.setLength(responseLength);
		personProtocol.setContent(responseContent);
		
		ctx.writeAndFlush(personProtocol);
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}

}
