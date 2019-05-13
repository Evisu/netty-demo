package com.zy.netty.base;

import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty 客户端基础类
 * 
 * @author walkman
 *
 */
public abstract class BaseNettyClient {
	
	/**
	 * 	处理器集合
	 * @return
	 */
	public abstract List<ChannelHandler> getHandlerList();
	
	/**
	 * 	启动服务端
	 * 
	 * @param port
	 * @throws Exception
	 */
	public void start(String host,int port) throws Exception {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
				protected void initChannel(Channel ch) throws Exception {
					for (ChannelHandler factory : getHandlerList()) {
						ch.pipeline().addLast(factory);
					}
				}
			});
			
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			channelFuture.channel().closeFuture().sync();
			
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}
