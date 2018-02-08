package servlet;

import server.Request;
import server.Response;

//登陆处理类
public class LoginServlet extends Servlet{
	
	//当请求方式为GET时调用
	public void doGet(Response response, Request request) {
		String userName = request.getParameter("userName");
		response.print("<html><head><title>欢迎登陆</title></head><body>Welcome " + userName+ " login...");
		response.print("I am lenovo...</body></html>");
	}
	
	//当请求方式为POST时调用
	public void doPost(Response response, Request request) {
		
	}
}