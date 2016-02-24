package com.fz.util;

import java.io.UnsupportedEncodingException;

public class URLDecoderUtil {
	/**
	 * 对中文转换编码
	 * @param value
	 * @param code
	 * @return
	 */
	public static String decode(String value,String code){
		try {
			return java.net.URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
		}
		return null;		
	}
}
