package com.netty.learn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 服务器处理通道
 * 
 * @author xxx
 *
 * DiscardServerHandler继承ChannelHandlerAdapter, ChannelHandlerAdapter实现了ChannelHandler接口
 */
public class DiscardServerHandler extends ChannelHandlerAdapter{

	/**
	 * 覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用
	 * 
	 * @param ctx 通道处理的上下文
	 * @param msg 接收的信息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		try {
			ByteBuf inputBuffer = (ByteBuf) msg;
			//不做处理、只打印接收的信息
			System.out.println("received data: " + inputBuffer.toString(CharsetUtil.UTF_8));
		} finally {
			/**
			 * ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放
			 * 处理器的职责是释放所有传递到处理器的引用计数对象
			 */
			ReferenceCountUtil.release(msg);
		}
	}

	/**
	 * 发生异常时触发
	 * 
	 * @param ctx 通道处理的上下文
	 * @param cause 异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		/**
		 * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 
	     * 由于 IO错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来 并且把关联的 channel
		 */
		cause.printStackTrace();
		ctx.close();
	}
}
