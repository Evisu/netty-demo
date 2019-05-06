package com.zy.netty.example3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyChatServer {
	public static void main(String[] args) throws Exception {
		// 事件循环组
			EventLoopGroup parentLoopGroup = new NioEventLoopGroup();
			EventLoopGroup childLoopGroup = new NioEventLoopGroup();
			try {
				ServerBootstrap serverBootstrap = new ServerBootstrap();
				serverBootstrap.group(parentLoopGroup, childLoopGroup).channel(NioServerSocketChannel.class).childHandler(new MyChatInitializer());
				
				ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
				channelFuture.channel().closeFuture().sync();
			} finally {
				parentLoopGroup.shutdownGracefully();
				childLoopGroup.shutdownGracefully();
			}
	}
}
