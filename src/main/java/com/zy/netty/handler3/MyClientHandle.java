package com.zy.netty.handler3;

import java.nio.charset.Charset;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandle extends SimpleChannelInboundHandler<PersonProtocol> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
		
		int length = msg.getLength();
		byte[] content = msg.getContent();
		
		System.out.println("客户端收到的数据：");
		System.err.println("长度：" + length); 
		System.err.println("内容：" + new String(content,Charset.forName("utf-8"))); 
		
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端发送数据：");
		for (int i = 0; i < 10; i++) {
			String messageSendContent = "sent from client ";
			byte[] content = messageSendContent.getBytes(Charset.forName("utf-8"));
			int length = messageSendContent.getBytes(Charset.forName("utf-8")).length;
			
			PersonProtocol personProtocol = new PersonProtocol();
			personProtocol.setLength(length);
			personProtocol.setContent(content);
			
			ctx.channel().writeAndFlush(personProtocol);
		}
	}

}
