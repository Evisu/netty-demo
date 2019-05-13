package com.zy.netty.example6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Person>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {
		
		System.out.println("收到客户端的消息：");
		System.out.println(msg.getName());
		System.out.println(msg.getAge());
		System.out.println(msg.getAddress());
		
	}

}
