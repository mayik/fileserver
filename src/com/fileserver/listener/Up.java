package com.fileserver.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fileserver.service.Ket;

public class Up implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		System.out.println("开始启动--------->");
		Ket.startService();
		System.err.println("开启已完成<----------");
	}

}
