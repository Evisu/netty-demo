package com.zy.netty.handler;

import java.util.ArrayList;
import java.util.List;

import com.zy.netty.base.BaseNettyClient;

import io.netty.channel.ChannelHandler;

public class MyClient extends BaseNettyClient {
	
	public static void main(String[] args) throws Exception {
		MyClient myClient = new MyClient();
		myClient.start("localhost",8899);
	}

	@Override
	public List<ChannelHandler> getHandlerList() {
		
		List<ChannelHandler> channelHandlers = new ArrayList<>();
		
		channelHandlers.add(new MyByteToLongDecoder());
		channelHandlers.add(new MyLongToByteEncoder());
		channelHandlers.add(new MyClientHandler());
		
		return channelHandlers;
	}

}
