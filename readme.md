# Netty 

## 什么是Netty

Netty是一款异步的事件驱动的网络应用框架，支持快速地开发可维护的高性能的面向协议的服务和客户端。

## Netty的核心组件

- Channel

	Channel是JAVA NIO的一个基本构造。
	
	它代表一个到实体（如一个硬件设备、一个文件、一个网络套接字或者一个能够执行一个或多个不同的I/O操作的程序组件）的开放连接，如读操作和写操作。                                                                                  

- 回调

	Netty在内部使用了回调来处理事件;一个回调被触发时，相关的事件可以被一个interface-ChannelHandler的实现处理。
	
	- channelRead0 当服务器接收到一条消息时被调用
	- channelActive 在到服务器的连接已经建立之后将被调用
	- channelInactive 在到服务器的连接已经断开之后将被调用
	- handlerAdded
	- handlerRemoved
	- exceptionCaught 在处理过程中引发异常时被调用

- Futrue

	Future提供了另一种操作完成时通知应用程序的方式。这个对象可以看做是一个异步操作结果的占位符;它将在未来某个时刻完成，并提供对其结果的访问。

	JDK预置了interface java.util.concurrent.Future,但是其所提供的实现只允许手动检查对应的操作是否已经完成，或者一直阻塞直到它完成。所以Netty提供了它的实现ChannelFuture,用于在执行异步操作的时候使用。

- 事件和ChannelHandle

	Netty使用不同的事件来通知我们状态的改变或是操作的状态。
	- 记录日志
	- 数据转换
	- 流控制
	- 应用程序逻辑

## Netty处理器的重要概念

1. Netty处理器可以分为两类：入站处理器、出站处理器


2. 入站处理器的顶层是ChannelInboundHandler，出站处理器的顶层是ChannelOutboundHandler


3. 数据处理时常用的各种编码器本质上都是处理器


4. 编解码器：无论我们向网络中写入的数据类型时什么类型(int、char、string、二进制等)，数据在网络传递时，其都是以字节流的形式呈现的，将数据由原本的形式转换成字节流的操作称为编码（encode），将数据由字节转换成原本格式的操作称为解码（decode），编解码统一称为codec


5. 编码：本质上是一种出站处理器，因此，编码一定是一种ChannelOutboundHandler


6. 解码：本质上是一种入站处理器，因此，解码一定是一种ChannelInboundHandler

## TCP粘包与拆包

1.常见解决方案

对于粘包和拆包问题，常见的解决方案有四种：

- 客户端在发送数据包的时候，每个包都固定长度，比如1024个字节大小，如果客户端发送的数据长度不足1024个字节，则通过补充空格的方式补全到指定长度


- 客户端在每个包的末尾使用固定的分隔符，例如\r\n，如果一个包被拆分了，则等待下一个包发送过来之后找到其中的\r\n，然后对其拆分后的头部部分与前一个包的剩余部分进行合并，这样就得到了一个完整的包


- 将消息分为头部和消息体，在头部中保存有当前整个消息的长度，只有在读取到足够长度的消息之后才算是读到了一个完整的消息


- 通过自定义协议进行粘包和拆包的处理

2.Netty提供的粘包拆包解决方案

- FixedLengthFrameDecoder

	 对于使用固定长度的粘包和拆包场景，可以使用FixedLengthFrameDecoder，该解码一器会每次读取固定长度的消息，如果当前读取到的消息不足指定长度，那么就会等待下一个消息到达后进行补足。其使用也比较简单，只需要在构造函数中指定每个消息的长度即可。这里需要注意的是，FixedLengthFrameDecoder只是一个解码一器，Netty也只提供了一个解码一器，这是因为对于解码是需要等待下一个包的进行补全的，代码相对复杂，而对于编码器，用户可以自行编写，因为编码时只需要将不足指定长度的部分进行补全即可
- LineBasedFrameDecoder与DelimiterBasedFrameDecoder

	对于通过分隔符进行粘包和拆包问题的处理，Netty提供了两个编解码的类，LineBasedFrameDecoder和DelimiterBasedFrameDecoder。这里LineBasedFrameDecoder的作用主要是通过换行符，即\n或者\r\n对数据进行处理；而DelimiterBasedFrameDecoder的作用则是通过用户指定的分隔符对数据进行粘包和拆包处理。同样的，这两个类都是解码一器类，而对于数据的编码，也即在每个数据包最后添加换行符或者指定分割符的部分需要用户自行进行处理。
- LengthFieldBasedFrameDecoder与LengthFieldPrepender

	这里LengthFieldBasedFrameDecoder与LengthFieldPrepender需要配合起来使用，其实本质上来讲，这两者一个是解码，一个是编码的关系。它们处理粘拆包的主要思想是在生成的数据包中添加一个长度字段，用于记录当前数据包的长度。LengthFieldBasedFrameDecoder会按照参数指定的包长度偏移量数据对接收到的数据进行解码，从而得到目标消息体数据；而LengthFieldPrepender则会在响应的数据前面添加指定的字节数据，这个字节数据中保存了当前消息体的整体字节数据长度。LengthFieldBasedFrameDecoder的解码过程如下图所示：

	![](https://upload-images.jianshu.io/upload_images/7944306-068ebfefef173526.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

	LengthFieldPrepender的编码过程如下图所示：

	![](https://upload-images.jianshu.io/upload_images/7944306-8570098660ffca88.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

	关于LengthFieldBasedFrameDecoder，这里需要对其构造函数参数进行介绍：

	

	- maxFrameLength：指定了每个包所能传递的最大数据包大小


	- lengthFieldOffset：指定了长度字段在字节码中的偏移量


	- lengthFieldLength：指定了长度字段所占用的字节长度


	- lengthAdjustment：对一些不仅包含有消息头和消息体的数据进行消息头的长度的调整，这样就可以只得到消息体的数据，这里的lengthAdjustment指定的就是消息头的长度


	- initialBytesToStrip：对于长度字段在消息头中间的情况，可以通过initialBytesToStrip忽略掉消息头以及长度字段占用的字节

3.自定义粘包与拆包器

对于粘包与拆包问题，其实前面三种基本上已经能够满足大多数情形了，但是对于一些更加复杂的协议，可能有一些定制化的需求。对于这些场景，其实本质上，我们也不需要手动从头开始写一份粘包与拆包处理器，而是通过继承LengthFieldBasedFrameDecoder和LengthFieldPrepender来实现粘包和拆包的处理。

如果用户确实需要不通过继承的方式实现自己的粘包和拆包处理器，这里可以通过实现MessageToByteEncoder和ByteToMessageDecoder来实现。这里MessageToByteEncoder的作用是将响应数据编码为一个ByteBuf对象，而ByteToMessageDecoder则是将接收到的ByteBuf数据转换为某个对象数据。通过实现这两个抽象类，用户就可以达到实现自定义粘包和拆包处理的目的。

## 关于Netty编解码器：

1. 无论是编码器还是解码器，其所接受的消息类型必须要与待处理的参数类型一致，否则该编码器或解码器并不会执行。
