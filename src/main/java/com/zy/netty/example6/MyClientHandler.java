package com.zy.netty.example6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {
	
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("劳动南路").build();
		ctx.channel().writeAndFlush(person);
	}

}
