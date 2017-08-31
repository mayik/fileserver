package com.fileserver.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;

import com.fileserver.commons.utils.StringUtils;
import com.fileserver.controller.FileController;

public class Ket {
	
	//文件路径
	public static String filePath;   
	//ip地址
	public static int port;
	//默认端口
	private static int portDefault = 10001;
	
	static {   
        Properties prop = new Properties();   
        InputStream in = FileController.class.getResourceAsStream("/config.properties");   
        try {   
            prop.load(in);   
            filePath = prop.getProperty("filePath").trim();   
            String portStr = prop.getProperty("port").trim(); 
            if (StringUtils.isValid(portStr)) {
            	try {
					port = Integer.parseInt(portStr);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					port = portDefault;
				}
            } else {
            	port = portDefault;
            }
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }   
	
	
	//做大线程数量
	public static int maxThread = 100;
	
	public static void main(String[] args) {
		startService();
	}
	
	public static void startService() {
		try {
			ServerSocket server = new ServerSocket(port); 
			create(server); 
		} catch (IOException e) { 
			e.printStackTrace();
			throw new RuntimeException("系统服务启动失败：System service startup failed.");
		}
	}
	
	public static void create(ServerSocket server) {
		for(int i = 0; i < maxThread; i++) {
			new Thread(new Server(server)).start();
		}
	}
}


