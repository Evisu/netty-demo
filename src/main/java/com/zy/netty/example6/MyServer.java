package com.zy.netty.example6;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 	服务端示例
 * 
 * @author walkman
 *
 */
public class MyServer {
	public static void main(String[] args) throws Exception {
		// 事件循环组
		EventLoopGroup parentLoopGroup = new NioEventLoopGroup();
		EventLoopGroup childLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(parentLoopGroup, childLoopGroup).channel(NioServerSocketChannel.class)
			.handler( new LoggingHandler(LogLevel.INFO) ).childHandler(new MyServerInitializer());
			
			ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			parentLoopGroup.shutdownGracefully();
			childLoopGroup.shutdownGracefully();
		}
	}
}
