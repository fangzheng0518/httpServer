package com.fz.server;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.fz.servlet.Servlet;

public class WebApp {
	private static ServletContext context;

	static {
		try {
			// 获取解析器工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 获取具体的解析器
			SAXParser sax = factory.newSAXParser();
			// 指定需要解析的xml和處理器
			WebHandler web = new WebHandler();
			sax.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB-INF/web.xml"),
					web);

			context = new ServletContext();
			// 放入servlet
			Map<String, String> servlet = context.getServlet();
			for (Entity entity : web.getEntityList()) {
				servlet.put(entity.getName(), entity.getClz());
			}
			// 放入mapping
			Map<String, String> mapping = context.getMapping();
			for (Mapping mapp : web.getMappingList()) {
				List<String> urls = mapp.getUrlPattern();
				for (String str : urls) {
					mapping.put(str, mapp.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Servlet getServlet(String url)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if ((null == url) || (url.trim().equals(""))) {
			return null;
		}
		String name = context.getServlet().get(context.getMapping().get(url));
		return (Servlet) Class.forName(name).newInstance();
	}

}
