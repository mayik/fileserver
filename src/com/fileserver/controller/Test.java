package com.fileserver.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Test ts = new Test();
        ts.test1();
        
    }
    
    public void test1(){
        Properties prop = new Properties();   
        InputStream in = Object.class.getResourceAsStream("/config.properties");   
        try {
            prop.load(in);
            String param1 = prop.getProperty("filePath").trim();  
            System.out.println("param1"+param1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
      
    }
    
    }
