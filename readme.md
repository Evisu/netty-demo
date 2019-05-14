# Netty 学习示例

##

## Netty处理器的重要概念

1. Netty处理器可以分为两类：入站处理器、出站处理器


2. 入站处理器的顶层是ChannelInboundHandler，出站处理器的顶层是ChannelOutboundHandler


3. 数据处理时常用的各种编码器本质上都是处理器


4. 编解码器：无论我们向网络中写入的数据类型时什么类型(int、char、string、二进制等)，数据在网络传递时，其都是以字节流的形式呈现的，将数据由原本的形式转换成字节流的操作称为编码（encode），将数据由字节转换成原本格式的操作称为解码（decode），编解码统一称为codec


5. 编码：本质上是一种出站处理器，因此，编码一定是一种ChannelOutboundHandler


6. 解码：本质上是一种入站处理器，因此，解码一定是一种ChannelInboundHandler

## TCP粘包与拆包

## 关于Netty编解码器：

1. 无论是编码器还是解码器，其所接受的消息类型必须要与待处理的参数类型一致，否则该编码器或解码器并不会执行。
