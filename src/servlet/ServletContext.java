package servlet;

import java.util.*;

public class ServletContext {
	//为servlet对象新建别名
	private final Map<String, String> servlet;
	//使url对应servlet
	private final Map<String, String> mapping;
	
	public ServletContext() {
		servlet = new HashMap<>();
		mapping = new HashMap<>();
	}

	public Map<String, String> getServlet() {
		return servlet;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}
	
}