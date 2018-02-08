package servlet;

import server.Request;
import server.Response;

//注册处理类
public class RegisterServlet extends Servlet {

	//当请求方式为GET时调用
	public void doGet(Response response, Request request) {
		String userName = request.getParameter("userName");
		response.print("<html><head><title>欢迎注册</title></head><body>Welcome " + userName+ " register...");
		response.print("I am lenovo...</body></html>");
	}

	//当请求方式为POST时调用
	public void doPost(Response response, Request request) {
		
	}
	
}