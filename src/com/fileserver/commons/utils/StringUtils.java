package com.fileserver.commons.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * 字符串处理工具包
 * 2015年8月31日 下午6:19:12
 * @author zhouyi
 */
public final class StringUtils {
	
	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";
	
	//private static Log log = LogFactory.getLog(StringUtils.class);

	private StringUtils() {
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharLowerCase(String s) {
		if (isValid(s)) {
			return s.substring(0, 1).toLowerCase() + s.substring(1);
		}
		return s;
	}
	
	/**
	 * 删除前缀
	 * @param s
	 * @param prefix
	 * @return
	 */
	public static String removePrefix(String s, String prefix) {
		int index = s.indexOf(prefix);
		return index == 0 ? s.substring(prefix.length()) : s;
	}
	
	/**
	 * 删除后缀
	 * @param s
	 * @param suffix
	 * @return
	 */
	public static String removeSuffix(String s, String suffix) {
		return s.endsWith(suffix) ? s.substring(0, s.length() - suffix.length()) : s;
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharUpperCase(String s) {
		if (isValid(s)) {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return s;
	}

	/**
	 * 检查对象是否有效 obj != null && obj.toString().length() > 0
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean isValid(Object obj) {
		return obj != null && obj.toString().trim().length() > 0;
	}

	/**
	 * 是否是空的
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || obj.toString().trim().length() == 0;
	}

	/**
	 * 转化为String对象
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static String asString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/**
	 * 返回其中一个有效的对象 value != null && value.toString().length() > 0
	 * 
	 * @param values
	 */
	public static String tryThese(String... values) {
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			if (value != null && value.trim().length() > 0) {
				return value;
			}
		}
		return null;
	}
	
	/**
	 * EL表达式提供的定义方法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String tryThese(String v1 , String v2) {
		return tryThese(new String[]{v1, v2});
	}

	/**
	 * 连接字符串
	 * 
	 * @param list
	 * @param split
	 * @return 字符串
	 */
	public static <T> String join(T[] list, String split) {
		return join(list, split, "");
	}
	
	/**
	 * 连接字符串
	 * 
	 * @param list
	 * @param split
	 * @return 字符串
	 */
	public static <T> String join(T[] list, String split, String wrap) {
		if (list == null)
			return null;
		StringBuilder s = new StringBuilder(128);
		for (int i = 0; i < list.length; i++) {
			if (i > 0) {
				s.append(split);
			}
			s.append(wrap + list[i] + wrap);
		}
		return s.toString();
	}
	
	/**
	 * 连接
	 * @param list
	 * @param split
	 * @param wrap
	 * @return
	 */
	public static <T> String join(List<T> list, String split, String wrap) {
		return join(list.toArray(), split, wrap);
	}

	/**
	 * 连接字符串
	 * 
	 * @param list
	 * @param split
	 * @return 字符串
	 */
	public static String join(List<?> list, String split) {
		return join(list.toArray(), split);
	}

	/**
	 * 取得匹配的字符串
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static List<String> matchs(String input, String regex) {
		return matchs(input, regex, 0);
	}

	/**
	 * 取得匹配的字符串
	 * 
	 * @param input
	 * @param regex
	 * @return
	 */
	public static List<String> matchs(String input, String regex, int group) {
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(input);
		List<String> matches = new ArrayList<String>();
		while (match.find()) {
			matches.add(match.group(group));
		}
		return matches;
	}
	
	/**
	 * 找到匹配的第一个字符串
	 * 
	 * @param input
	 * @param regex
	 * @param group
	 * @return
	 */
	public static String matchFirst(String input, String regex, int group) {
		List<String> matches = matchs(input, regex, group);
		return matches.isEmpty() ? null : matches.get(0);
	}

	/**
	 * 截取指定长度字符串
	 * 
	 * @param input
	 * @param tail
	 * @param length
	 * @return
	 */
	public static String getShorterString(String str, int maxLength) {
		return getShorterString(str, "...", maxLength);
	}

	/**
	 * 截取指定长度字符串
	 * 
	 * @param input
	 * @param tail
	 * @param length
	 * @return
	 */
	public static String getShorterString(String input, String tail, int length) {
		tail = isValid(tail) ? tail : "";
		StringBuffer buffer = new StringBuffer(512);
		try {
			int len = input.getBytes("GBK").length;
			if (len > length) {
				int ln = 0;
				for (int i = 0; ln < length; i++) {
					String temp = input.substring(i, i + 1);
					if (temp.getBytes("GBK").length == 2)
						ln += 2;
					else
						ln++;

					if (ln <= length)
						buffer.append(temp);
				}
			} else {
				return input;
			}
			buffer.append(tail);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 取得GBK编码
	 * 
	 * @return
	 */
	public static String getBytesString(String input, String code) {
		try {
			byte[] b = input.getBytes(code);
			return Arrays.toString(b);
		} catch (UnsupportedEncodingException e) {
			return String.valueOf(code.hashCode());
		}
	}

	/**
	 * 转换格式 CUST_INFO_ID - > custInfoId
	 * 
	 * @param input
	 * @return
	 */
	public static String getFieldString(String input) {
		String field = input.toLowerCase();
		String[] values = field.split("\\_");
		StringBuffer b = new StringBuffer(input.length());
		for (int i = 0; i < values.length; i++) {
			if (i == 0)
				b.append(values[i]);
			else
				b.append(firstCharUpperCase(values[i]));
		}
		return b.toString();
	}

	/**
	 * 转化为JSON值
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static String toJsonValue(Object value) throws IOException {
		if (value instanceof Number) {
			return value.toString();
		} else {
			return "'" + value.toString() + "'";
		}
	}

	/**
	 * 字符串转化为UUID
	 * 
	 * @param value
	 * @return
	 */
	public static String toUUID(String value) {
		if (value == null)
			throw new RuntimeException("value is null!");
		return UUID.nameUUIDFromBytes(value.getBytes()).toString();
	}

	/**
	 * 获取Style样式中样式的值
	 * 
	 * @param styleString
	 * @param styleName
	 * @return 相应的值
	 */
	public static String getStyleValue(String styleString, String styleName) {
		String[] styles = styleString.split(";");
		for (int i = 0; i < styles.length; i++) {
			String tempValue = styles[i].trim();
			if (tempValue.startsWith(styleName)) {
				String[] style = tempValue.split(":");
				return style[1];
			}
		}
		return "";
	}
	
	/**
	 * 生成重复次字符
	 * @param charactor
	 * @param repeat
	 * @return
	 */
	public static String getRepeat(String charactor, int repeat){
		StringBuilder s = new StringBuilder(charactor.length()*repeat);
		for (int i = 0; i < repeat; i++) {
			s.append(charactor);
		}
		return s.toString();
	}


	public static boolean containsOR(String str1, String ... args) {
		for (String arg : args) {
			if(str1.contains(arg)) return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(toUUID("1"));
		System.out.println(removePrefix("abcd123", "ab"));
		System.out.println(removeSuffix("abcd123", "123"));
	}
}
