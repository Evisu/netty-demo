package com.zy.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "：" + msg);
		
		ctx.writeAndFlush(654321L);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}
}
