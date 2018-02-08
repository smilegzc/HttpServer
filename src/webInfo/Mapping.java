package webInfo;

import java.util.*;
/*
 * <mapping>
		<url-patten>/login</url-patten>
		<servlet-name>login</servlet-name>
	</mapping>
	<mapping>
		<url-patten>/log</url-patten>
		<servlet-name>login</servlet-name>
	</mapping>
 */
class Mapping {
	private String name;
	private List<String> urlPattern;
	
	public Mapping() {
		urlPattern = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(List<String> urlPattern) {
		this.urlPattern = urlPattern;
	}
	
}
