package com.netty.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 启动服务端DiscardServerHandler
 * @author xxx
 *
 */
public class DiscardServer {
	
	private int port;

	public DiscardServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		
		/**
		 * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器
		 * 2个NioEventLoopGroup
		 * 第一个叫boss，用来接收进来的连接
         * 第二个叫worker，用来处理已经被接收的连接，一旦boss接收到连接，就会把连接信息注册到worker上
         * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		System.out.println("server running at port: " + port);
		
		try {
			
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap = bootstrap.group(bossGroup, workerGroup);
			bootstrap = bootstrap.channel(NioServerSocketChannel.class);
			
			bootstrap = bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DiscardServerHandler());
				}
			});
			bootstrap = bootstrap.option(ChannelOption.SO_BACKLOG, 128);
			bootstrap = bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			/**绑定端口去接收客户端的连接*/
			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		
		new DiscardServer(8080).run();
		System.out.println("server is running...");
	}
	
}
