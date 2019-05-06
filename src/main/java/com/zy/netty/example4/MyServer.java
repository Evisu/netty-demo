package com.zy.netty.example4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyServer {
	public static void main(String[] args) throws Exception {
		// 事件循环组
			EventLoopGroup parentLoopGroup = new NioEventLoopGroup();
			EventLoopGroup childLoopGroup = new NioEventLoopGroup();
			try {
				ServerBootstrap serverBootstrap = new ServerBootstrap();
				serverBootstrap.group(parentLoopGroup, childLoopGroup).channel(NioServerSocketChannel.class)
				.handler( new LoggingHandler(LogLevel.INFO) ).childHandler(new MyServerInitializer());
				
				ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
				channelFuture.channel().closeFuture().sync();
			} finally {
				parentLoopGroup.shutdownGracefully();
				childLoopGroup.shutdownGracefully();
			}
	}
}
