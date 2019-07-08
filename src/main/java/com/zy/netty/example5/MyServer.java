package com.zy.netty.example5;

import java.util.ArrayList;
import java.util.List;

import com.zy.netty.base.BaseNettyServer;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * websocket服务端示例
 * 
 * @author walkman
 *
 */
public class MyServer extends BaseNettyServer{
	public static void main(String[] args) throws Exception {
		new MyServer().start(8899);
	}

	@Override
	public List<ChannelHandler> getHandlerList() {
		List<ChannelHandler> handlerList = new ArrayList<ChannelHandler>();
		handlerList.add(new HttpServerCodec());
		handlerList.add(new ChunkedWriteHandler());
		handlerList.add(new HttpObjectAggregator(8192));
		handlerList.add(new WebSocketServerProtocolHandler("/ws"));
		handlerList.add(new TextWebSocketFrameHandle());
		return handlerList;
	}
}
