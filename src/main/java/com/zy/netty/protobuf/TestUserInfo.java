package com.zy.netty.protobuf;

public class TestUserInfo {
	
	public static void main(String[] args) throws Exception {
		
		UserInfo.User user = UserInfo.User.newBuilder().setName("张三").setAddress("劳动南路").setAge(20).build();
		
		byte[] userByteArray = user.toByteArray();
		
		UserInfo.User user2 = UserInfo.User.parseFrom(userByteArray);
		
		System.out.println(user2.getName());
		System.out.println(user2.getAge());
		System.out.println(user2.getAddress());
	}

}
