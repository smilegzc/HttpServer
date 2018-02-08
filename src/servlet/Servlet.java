package servlet;

import server.Request;
import server.Response;

//根据客户端请求类型生成动态响应内容
public abstract class Servlet{

	public void service(Response response, Request request) throws Exception{
		this.doGet(response, request);
		this.doPost(response, request);
		
	}
	
	protected abstract void doGet(Response response, Request request);
	protected abstract void doPost(Response response, Request request);
}