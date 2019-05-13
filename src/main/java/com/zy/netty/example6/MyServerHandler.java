package com.zy.netty.example6;

import com.zy.netty.example6.MyMessageInfo.MyMessage.DataType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<MyMessageInfo.MyMessage>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyMessageInfo.MyMessage msg) throws Exception {
		
		System.out.println("收到客户端的消息：");
		
		switch (msg.getDataType().getNumber()) {
			case DataType.PersonType_VALUE:
				MyMessageInfo.Person person = msg.getPerson();
				System.out.println(person.getName());
				System.out.println(person.getAge());
				break;
			case DataType.DogType_VALUE:
				MyMessageInfo.Dog dog = msg.getDog();
				System.out.println(dog.getName());
				System.out.println(dog.getAge());
				break;
			case DataType.CatType_VALUE:
				MyMessageInfo.Cat cat = msg.getCat();
				System.out.println(cat.getName());
				System.out.println(cat.getAge());
				break;
	
			default:
				break;
		}
	}

}
