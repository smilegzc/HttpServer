package server;

import java.io.*;
import java.util.Date;


//响应信息处理类
public class Response {
	private BufferedWriter bw = null;
	private final StringBuilder headInfo;		//存储响应头信息
	private final StringBuilder headText;		//存储响应正文内容
	private int len;		//存储正文长度
	
	public Response() {
		headInfo = new StringBuilder();
		headText = new StringBuilder();
		len = 0;
	}
	
	public Response(OutputStream os) {
		bw = new BufferedWriter(new OutputStreamWriter(os));
		headInfo = new StringBuilder();
		headText = new StringBuilder();
		len = 0;
	}
	
	public void setOutputStream(OutputStream os) {
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	//构造响应头信息
	private void createHandInfo(int code) {
		headInfo.append("HTTP/1.1 ");
		switch(code) {
			case 200:
				headInfo.append("200 OK\r\n");
				break;
			case 404:
				headInfo.append("404 NOT FOUND\r\n");
				break;
			case 500:
				headInfo.append("500 Internal Server Error\r\n");
		}
		//响应头 response head
		headInfo.append("Server: lenovo Server/0.0.1\r\n");
		//服务器时间
		headInfo.append("Date: ").append(new Date()).append("\r\n");
		//响应正文格式
		headInfo.append("Content-type: text/html;charset=utf-8\r\n");
		//正文长度
		headInfo.append("Content-length: ").append(len).append("\r\n");
		headInfo.append("\r\n");
	}
	
	public void print(String text) {
		headText.append(text);
		len = len + text.getBytes().length;
	}
	
	public Response println(String text) {
		headText.append(text);
		headText.append("\r\n");
		len = len + text.getBytes().length + "\r\n".getBytes().length;
		return this;
	}
	//将响应信息推送到客户端
	public void pushToClient(int code) {
		createHandInfo(code);
		try {
			bw.append(headInfo.toString());
			bw.append(headText.toString());
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}