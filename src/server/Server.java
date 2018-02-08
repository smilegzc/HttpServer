package server;

import java.io.*;
import java.net.*;

class Server {
	private ServerSocket sSocket = null;
	private Socket cSocket = null;
	private boolean isStart = true;
	
	public static void main(String[] args) {
		
		Server s = new Server();
		s.start();
		
	}
	
	//启动服务器
	private void start() {
		try {
			sSocket = new ServerSocket(8080);
			this.connect();
		} catch (IOException e) {
			stop();
			e.printStackTrace();
		}
	}
	
	//监听客户端连接
	private void connect() {
		try {
			
			while(isStart){
				cSocket = sSocket.accept();
				new Thread(new Dispatcher(cSocket)).start();
			}
			
		} catch (IOException e) {
			stop();
			e.printStackTrace();
		}
	}
	
	//停止服务器
	private void stop() {
		try {
			isStart = false;
			if(!cSocket.isClosed())
				cSocket.close();
			if(!sSocket.isClosed())
				sSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}