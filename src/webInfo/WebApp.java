package webInfo;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import servlet.Servlet;
import servlet.ServletContext;


//客户端请求路径处理类
public class WebApp {
	private static  ServletContext context;
	static {
		try {
			//获取解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			//获取解析器
			SAXParser sax = factory.newSAXParser();
			//指定xml和处理器
			WebHandle web = new WebHandle();
			sax.parse(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("webInfo/web.xml"), web);
			
			context = new ServletContext();
			Map<String, String> servlet = context.getServlet();
			for(Entity entity:web.getEntityList()) {
				servlet.put(entity.getName(), entity.getClz());
			}
			
			Map<String, String> mapping = context.getMapping();
			for(Mapping map:web.getMappingList()) {
				List<String> urls = map.getUrlPattern();
				for(String url:urls)
				mapping.put(url, map.getName());
			}
			
		} catch (SAXException | ParserConfigurationException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public static Servlet getServlet(String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		if((url == null) || url.equals("/")|| !context.getMapping().containsKey(url)) return null;
		
		String name = context.getServlet().get(context.getMapping().get(url));
		return (Servlet) Class.forName(name).newInstance();
		
	}
}