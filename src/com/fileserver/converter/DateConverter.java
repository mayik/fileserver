package com.fileserver.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.fileserver.commons.utils.DateUtils;
import com.fileserver.commons.utils.StringUtils;


/**
 * 日期转换器
 */
public class DateConverter implements Converter<String,Date>{
	@Override
	public Date convert(String source) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		if (StringUtils.isValid(source)) {
			DateFormat dateFormat;
			if (source.indexOf(":") != -1) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			}
			//日期转换为严格模式
		    dateFormat.setLenient(false); 
	        try {
				return dateFormat.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		return null;
	}
	
	public static void main(String[] args) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(DateUtils.getNow()));
		System.out.println(format.parse("2009-9-9 12:12:12.00"));
	}
}
