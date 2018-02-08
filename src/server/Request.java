package server;

import java.io.*;
import java.util.*;


//请求信息处理类
public class Request {
	
	private String url = null;	//存储请求资源路径
	private final Map<String,List<String>> parameterMapValues;	//存储请求参数
	
	private String requestBuff = null;	//缓存客户端请求信息的字符态
	
	public Request(InputStream is) {
		parameterMapValues = new HashMap<>();
		byte[] buf = new byte[1024*1024];	//缓存客户端请求信息的字节态
		
		try {
			int length = is.read(buf);
			if(length <= 0) return;
			requestBuff = new String(buf, 0, length);	//将客户端请求信息从字节转换为字符
		} catch (IOException e) {
			return;
		}
		
		parseRequestInfo();
	}
	
	//解析请求信息
	private void parseRequestInfo() {
		if(requestBuff == null || requestBuff.trim().equals("")) return;
		
		//截取客户端请求信息第一行
		String firstLine = requestBuff.substring(0, requestBuff.indexOf("\r\n")).trim();
		//截取请求方式
		String paramString = null;
		String method = firstLine.substring(0, requestBuff.indexOf("/")).trim();
		
		//根据请求方式截取请求路径和请求参数
		if(method.equalsIgnoreCase("GET")) {
			if(firstLine.contains("?")) {
				String[] urlBuf = firstLine.substring(requestBuff.indexOf("/"), requestBuff.indexOf("HTTP")).trim().split("\\?");
				url = urlBuf[0];
				paramString = urlBuf[1];
			} else {
				url = firstLine.substring(requestBuff.indexOf("/"), requestBuff.indexOf("HTTP")).trim();
			}
		} else if(method.equalsIgnoreCase("POST")) {
			url = firstLine.substring(requestBuff.indexOf("/"), requestBuff.indexOf("HTTP")).trim();
			paramString = firstLine.substring(requestBuff.lastIndexOf("\r\n")).trim();
		}
		if(paramString != null)
			parseParam(paramString);
	}
	//解析请求参数
	private void parseParam(String param){
		
		StringTokenizer token = new StringTokenizer(param, "&");
		
		while(token.hasMoreTokens()) {
			String[] keyValues = token.nextToken().split("=");
			if (keyValues.length == 1) {
				keyValues = Arrays.copyOf(keyValues, 2);
				keyValues[1] = null;
			}
			
			String key = keyValues[0].trim();
			String value = keyValues[1]==null? null:decode(keyValues[1].trim());
			
			if(!parameterMapValues.containsKey(key)) {
				parameterMapValues.put(key, new ArrayList<>());
			}
			
			List<String> values = parameterMapValues.get(key);
			values.add(value);
		}
	}
	
	//处理中文显示
	private String decode(String str) {
		
		try {
			return java.net.URLDecoder.decode(str, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//返回请求信息中的参数
	public String getParameter(String name) {
		if(!parameterMapValues.containsKey(name)) return null;
		String values = parameterMapValues.get(name).get(0);
		
		if(values != null) {
			return values;
		} else {
			return null;
		}
	}
	//返回请求资源路径
	public String getUrl(){
		return url;
	}
}