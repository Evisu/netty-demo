package com.zy.netty.example6;

import java.util.Random;

import com.zy.netty.example6.MyMessageInfo.MyMessage.DataType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<MyMessageInfo.MyMessage>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyMessageInfo.MyMessage msg) throws Exception {
	
		System.out.println("收到服务器发送的消息：" + msg);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		int randomInt = new Random().nextInt(3);
		
		MyMessageInfo.MyMessage myMessage = null;
		if( 0 == randomInt ) {
			myMessage = MyMessageInfo.MyMessage.newBuilder()
					.setDataType(DataType.PersonType)
					.setPerson(MyMessageInfo.Person.newBuilder().setName("张三").setAge(30))
					.build();
		}else if( 1 == randomInt ){
			myMessage = MyMessageInfo.MyMessage.newBuilder()
					.setDataType(DataType.DogType)
					.setDog(MyMessageInfo.Dog.newBuilder().setName("一条狗").setAge(2))
					.build();
		}else {
			myMessage = MyMessageInfo.MyMessage.newBuilder()
					.setDataType(DataType.CatType)
					.setCat(MyMessageInfo.Cat.newBuilder().setName("一只猫").setAge(3))
					.build();
		}
		
		ctx.channel().writeAndFlush(myMessage);
	}

}
