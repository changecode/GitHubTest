package com.java.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * simple RPC
 * @author Tim
 *
 */
public class RpcSimple {

	/**
	 * export server
	 */
	public static void exportServer(final Object service, int port) throws Exception{
		if(null == service){
			throw new IllegalArgumentException("service is not null");
		}
		if(port > 65535 || port < 0){
			throw new IllegalArgumentException("port is not between 0 and 65535");
		}
		System.out.println("export service" + service.getClass().getName() 
				+ " on port " + port);
		
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
		for(;;){
			try {
				final Socket socket = serverSocket.accept();
				new Thread(new Runnable(){
					@Override
					public void run(){
						try {
							try {
								ObjectInputStream input = 
										new ObjectInputStream(socket.getInputStream());
								try {
									/**get object from socket inputstream*/
									String methodName = input.readUTF();
									Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
									Object[] arguments = (Object[]) input.readObject();
									ObjectOutputStream output = 
											new ObjectOutputStream(socket.getOutputStream());
									try {
										Method method = service.getClass().getMethod(methodName, parameterTypes);
										Object result = method.invoke(service, arguments);
										output.writeObject(result);
									} catch (Exception e) {
										output.writeObject(e);
									}finally {
										output.close();
									}
								}finally {
									input.close();
								}
							}finally{
								socket.close();
							}
						} catch (Exception e) {

						}
					}
				}).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * refer server
	 */
	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass, 
			final String host, final int port) throws Exception{
		if(null == interfaceClass){
			throw new IllegalArgumentException("interfaceClass is not null");
		}
		if(!interfaceClass.isInterface()){
			throw new IllegalArgumentException(interfaceClass.getName() 
					+ "must be interface class");
		}
		if(null == host || host.length() == 0){
			throw new IllegalArgumentException("host is null");
		}
		if(port > 65535 || port < 0) {
			throw new IllegalArgumentException("the port must in 0 - 65535");
		}
		System.out.println("get remote service" + interfaceClass.getName() 
			+ " from server" + host + ": " + port);
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
				new Class<?>[] {interfaceClass}, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
						Socket socket = new Socket(host, port);
						try {
							ObjectOutputStream output = 
									new ObjectOutputStream(socket.getOutputStream());
							try {
								output.writeUTF(method.getName());
								output.writeObject(method.getParameterTypes());
								output.writeObject(arguments);
								ObjectInputStream input = 
										new ObjectInputStream(socket.getInputStream());
								try {
									Object result = input.readObject();
									if(result instanceof Throwable){
										throw (Throwable)result;
									}
									return result;
								} finally{
									input.close();
								}
							} finally{
								output.close();
							}
						} finally{
							socket.close();
						}
					}
				});
	}
	
}
