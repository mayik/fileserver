package com.fileserver.commons.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;


/**
 * spring 工具包
 * 2015年9月9日 下午1:32:57
 * @author zhouyi
 */
@SuppressWarnings("all")
public class SpringUtils implements ApplicationContextAware{
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtils.context = context;
	}
	
	/**
	 * 取得Bean
	 * @param id
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(String id, Class<?> cls){
		return (T)context.getBean(id, cls);
	}
	
	/**
	 * 取得Bean
	 * @param id
	 * @return
	 */
	public static <T> T getBean(String id){
		return (T)context.getBean(id);
	}
	
	/**
	 * 取得Bean
	 * @param type
	 * @return
	 */
	public static <T> T getBean(Class<?> type){
		return (T)context.getBean(type);
	}
	
	/**
	 * 判断Bean是否存在
	 * @param id
	 * @return
	 */
	public static boolean isBeanExist(String id){
		return context.containsBean(id);
	}
	
	/**
	 * 动态创建Bean
	 * @param clazz
	 * @return
	 */
	public static Object createBean(Class<?> clazz){
		String beanName = StringUtils.firstCharLowerCase(clazz.getSimpleName());
		return createBean(clazz, beanName);
	}
	
	/**
	 * 动态创建Bean
	 * @param clazz
	 * @param beanName
	 * @return
	 */
	public static Object createBean(Class<?> clazz, String beanName){
		return createBean(clazz, beanName, new HashMap<String, Object>());
	}
	
	/**
	 * 动态创建Bean
	 * @param clazz
	 * @param beanName
	 * @param properties
	 * @return
	 */
	public static Object createBean(Class<?> clazz, String beanName, Map<String,Object> properties){
		GenericApplicationContext ctx = (GenericApplicationContext)context;
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
		for (Map.Entry<String,Object> entry : properties.entrySet()) {
			builder.addPropertyValue(entry.getKey(), entry.getValue());
		}
		ctx.registerBeanDefinition(beanName, builder.getBeanDefinition());
		return ctx.getBean(beanName);
	}
}

