package server;

import java.io.IOException;
import java.net.Socket;

import servlet.Servlet;
import webInfo.WebApp;

//客户端响应线程类
class Dispatcher implements Runnable {

	private final Socket client;
	private Response response = null;
	private Request request = null;
	private int code = 200;
	public Dispatcher(Socket client) {
		this.client = client;
		try {
			request = new Request(client.getInputStream());
			response = new Response(client.getOutputStream());
		} catch (IOException e) {
			this.code = 500;
			e.printStackTrace();
		}
		
	}
	//每当有一个客户端接入，则开一个新线程，处理客户端请求信息
	public void run() {
		Servlet servlet = null;
		
		try {
			servlet = WebApp.getServlet(request.getUrl());
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException e2) {
			
			e2.printStackTrace();
			
		}
		
		if(servlet == null) {
			
			this.code = 404;
		} else {
			try {
				
				servlet.service(response, request);
			} catch (Exception e1) {
				
				this.code = 500;
				e1.printStackTrace();
			}
		}

		response.pushToClient(code);
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}