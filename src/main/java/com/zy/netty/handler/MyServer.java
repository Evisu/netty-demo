package com.zy.netty.handler;

import java.util.ArrayList;
import java.util.List;

import com.zy.netty.base.BaseNettyServer;

import io.netty.channel.ChannelHandler;

public class MyServer extends BaseNettyServer {
	
	public static void main(String[] args) throws Exception {
		MyServer myServer = new MyServer();
		myServer.start(8899);
	}

	@Override
	public List<ChannelHandler> getHandlerList() {
		
		List<ChannelHandler> channelHandlers = new ArrayList<>();
		
		channelHandlers.add(new MyByteToLongDecoder());
		channelHandlers.add(new MyLongToByteEncoder());
		channelHandlers.add(new MyServerHandler());
		
		return channelHandlers;
	}

}
