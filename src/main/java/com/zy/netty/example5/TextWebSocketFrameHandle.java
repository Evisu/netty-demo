package com.zy.netty.example5;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class TextWebSocketFrameHandle extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		
		System.out.println("收到消息：" + msg.text());
		
		Channel channel = ctx.channel();
		
		channelGroup.forEach(ch->{
			if( channel != ch ) {
				ch.writeAndFlush(new TextWebSocketFrame("【"+channel.remoteAddress()+"】：" + msg.text() + "\n"));		
			}else {
				ch.writeAndFlush(new TextWebSocketFrame("【自己】：" + msg.text() + "\n"));		
			}
		});
//		ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间："+LocalDateTime.now()));
		
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handleAdded："+ctx.channel().id().asLongText());
		
		Channel channel = ctx.channel();
		
		channelGroup.writeAndFlush(new TextWebSocketFrame("【服务器】-" + channel.remoteAddress() + "加入\n"));		
		
		channelGroup.add(channel);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved："+ctx.channel().id().asLongText());
		
		Channel channel = ctx.channel();
		
		channelGroup.writeAndFlush(new TextWebSocketFrame("【服务器】-" + channel.remoteAddress() + "离开\n"));		
		
		channelGroup.remove(channel);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		
		System.out.println(channel.remoteAddress() + "上线\n");		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		
		System.out.println(channel.remoteAddress() + "下线\n");		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("异常发生");
		ctx.channel().close();
	}

}
