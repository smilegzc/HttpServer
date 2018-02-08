package webInfo;

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class WebHandle extends DefaultHandler {
	private List<Entity> entityList;
	private List<Mapping> mappingList;
	private Entity entity;
	private Mapping mapping;
	private String beginTag;
	private boolean isMap;
	
	public void startDocument() {
		// 文档解析开始
		entityList = new ArrayList<>();
		mappingList = new ArrayList<>();
	}
	
	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) {
		// 元素开始
		if(arg2 != null){
			beginTag = arg2;
			
			if(arg2.equals("servlet")){
				isMap = false;
				entity = new Entity();
			} else if(arg2.equals("mapping")) {
				isMap = true;
				mapping = new Mapping();
			}
		}
	}
	
	public void characters(char[] arg0, int arg1, int arg2) {
		// 处理元素
		if(beginTag != null) {
			String str = new String(arg0, arg1, arg2);
			
			if(isMap){
				if(beginTag.equals("url-patten")) {
					mapping.getUrlPattern().add(str);
				} else if(beginTag.equals("servlet-name")) {
					mapping.setName(str);
				}
				
			} else {
				if(beginTag.equals("servlet-class")) {
					entity.setClz(str);
				} else if(beginTag.equals("servlet-name")) {
					entity.setName(str);
				}
			}
		}
	}
	
	
	public void endElement(String arg0, String arg1, String arg2) {
		// 元素结束
		if(arg2 != null) {
			
			if(arg2.equals("servlet")) {
				entityList.add(entity);
				
			} else if(arg2.equals("mapping")) {
				mappingList.add(mapping);
			}
		}
		
		beginTag = null;
	}

	public void endDocument() {
		// 文档解析结束
	}
	
	public List<Entity> getEntityList() {
		return entityList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}
}
