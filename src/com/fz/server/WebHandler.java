package com.fz.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 利用java自带的SAX解析web.xml
 * @author Administrator
 *
 */
public class WebHandler extends DefaultHandler{
	private List<Entity> entityList;
	private List<Mapping> mappingList;
	private String beginTag;
	private boolean isMap;
	private Entity entity;
	private Mapping mapping;
	/**
	 * 开始解析文档
	 */
	@Override
	public void startDocument() throws SAXException {
		entityList = new ArrayList<Entity>();
		mappingList = new ArrayList<Mapping>();
	}
	/**
	 * 开始解析元素
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (null != qName) {
			beginTag = qName;//正在解析的标签
			if (qName.equals("servlet")) {
				isMap=false;
				entity = new Entity();
			}else if (qName.equals("servlet-mapping")) {
				isMap=true;
				mapping = new Mapping();
			}
		}
	}
	/**
	 * 处理元素内容
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (null !=beginTag) {
			String str = new String(ch, start, length);
			if (isMap) {
				if (beginTag.equals("servlet-name")) {
					mapping.setName(str);
				}else if (beginTag.equals("url-pattern")) {
					mapping.getUrlPattern().add(str);
				}
			}else{
				if (beginTag.equals("servlet-name")) {
					entity.setName(str);
				}else if (beginTag.equals("servlet-class")) {
					entity.setClz(str);
				}
			}
		}
	
	}
	/**
	 * 解析文档结束
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (null != qName) {
			if (qName.equals("servlet")) {
				entityList.add(entity);
			}else if (qName.equals("servlet-mapping")) {
				mappingList.add(mapping);
			}
		}
		beginTag=null;
	}
	/**
	 * 解析文档结束
	 */
	@Override
	public void endDocument() throws SAXException {
		
	}
	public List<Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}
	public List<Mapping> getMappingList() {
		return mappingList;
	}
	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//获取解析器工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//获取具体的解析器
		SAXParser sax = factory.newSAXParser();
		//指定需要解析的xml和處理器
		WebHandler web = new WebHandler();
		sax.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/fz/server/demo1/web.xml"), web);
		
		for (Entity ent : web.getEntityList()) {
			System.out.println(ent.getName()+"---"+ent.getClz());
		}
		for (Mapping mag : web.getMappingList()) {
			for (String str : mag.getUrlPattern()) {
				System.out.println(mag.getName()+"----"+str);
			}
		}
		
		
	}
	
}
