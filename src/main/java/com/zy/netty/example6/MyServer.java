package com.zy.netty.example6;

import java.util.ArrayList;
import java.util.List;

import com.zy.netty.base.BaseNettyServer;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 	服务端示例
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
		handlerList.add(new ProtobufVarint32FrameDecoder());
		handlerList.add(new ProtobufDecoder(MyMessageInfo.MyMessage.getDefaultInstance()));
		handlerList.add(new ProtobufVarint32LengthFieldPrepender());
		handlerList.add(new ProtobufEncoder());
		handlerList.add(new MyServerHandler());
		return handlerList;
	}
}
