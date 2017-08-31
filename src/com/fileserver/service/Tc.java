package com.fileserver.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fileserver.commons.utils.FileServiceUtil;

public class Tc {
	public static void main(String[] args) throws Exception {
		/*
		Socket socket = new Socket("192.168.0.172", 10001);
		PrintWriter o=new PrintWriter(socket.getOutputStream(),true);
		o.println("AAAcopy2.jpg");
		FileInputStream f = new FileInputStream(new File("F:/200711912453162_2.jpg"));
		OutputStream out = socket.getOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = f.read(b)) != -1) {
			out.write(b, 0, len);
		}
		socket.shutdownOutput();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String message = reader.readLine();
		System.out.println(message);
		System.out.println("结束");
		f.close();
		socket.close();
		*/
		
		
		
		
		
		// http://111.11.195.109/
		//String messageString = FileServiceUtil.uploadFileToService("127.0.0.1", 1000, "AAAcopy2.jpg","asa", inputStream);
//		String messageString = FileServiceUtil.uploadFileToService("127.0.0.1", 1000, "AAAcopy211.jpg","upload/fun", inputStream,400,300,200);
//		String messageString = FileServiceUtil.uploadFileToService("111.11.195.117", 8080, "AAAcopy211.jpg","upload/fun", inputStream,400,300,200);
		//String messageString = FileServiceUtil.deleteFileToService("127.0.0.1", 1000,"upload/fun/AAAcopy211.jpg");
		//String messageString = FileServiceUtil.uploadFileToService("111.11.195.117", 10001, "AAAcopy2.jpg", inputStream);
		
		
  //添加图片-需要缩略图		
	//			String messageString = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "AAAcopy2112.jpg","upload/fun", inputStream,400,300,200);
  //删除
		//		String messageString = FileServiceUtil.deleteSamllPic("127.0.0.1", 8070,"upload/fun/AAAcopy2112.jpg");
  //返回宽高 
		//String messageString = FileServiceUtil.isImgRetureXY("127.0.0.1", 18080,"upload/fun/AAAcopy2112.jpg");
  //返回目录下所有文件的大小
		//String messageString = FileServiceUtil.fileSize("127.0.0.1", 18080,"upload/fun");
	//	String messageString = FileServiceUtil.createFolder("127.0.0.1", 18080, "upload/store/27/2016/07/02");
		//for(int i=0;i<50;i++){
		    //FileInputStream inputStream = new FileInputStream(new File("D:/QQ表情/07764ss/1.jpg"));
	    //FileInputStream inputStream = new FileInputStream(new File("D:/工作文档咋/QQ表情/07764ss/男士牛仔裤2014秋季新款男装韩版修身小脚裤男休闲黑色潮男长裤子-淘宝网_files/T1t4bIFetgXXXXXXXX_!!1-item_pic.gif"));
	    FileInputStream inputStream = new FileInputStream(new File("D:/临时文件/20160908_161822_837.png"));
           
	    // Thread.sleep(1000);
		    //创建图片
	    //String messageString0 = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "AAAcopy211test"+i+".jpg","upload/fun/test", inputStream);
	    //String messageString0 = FileServiceUtil.uploadFileToService("111.11.195.117", 8080, "AAAcopy211test"+".jpg","upload/fun/test", inputStream);
	    //String messageString0 = FileServiceUtil.uploadFileToService("111.11.195.117", 8080, "AAAcopy211test"+".jpg","upload/fun/test", inputStream);
	    String messageString0 = FileServiceUtil.uploadFileToService("111.11.195.117", 18082, "test12121AA11"+".jpg","upload/fun/test", inputStream, 200, 300, 100);
	    //String messageString0 = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "test12121AA"+".jpg","upload/fun/test", inputStream, 200, 300, 100);
        //创建图片及缩略图
	    //创建图片及缩略图
		    //String messageString = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "AAAcopy211test2"+i+".gif","upload/fun", inputStream,400,300,200);
		    //删除服务器的文件
		    //String messageString = FileServiceUtil.deleteFileToService("127.0.0.1", 18080,"upload/bigImg/fun"+"/AAAcopy211test2.jpg");
		    // 删除制定路径的文件,并删除小图
		    //String messageString = FileServiceUtil.deleteSamllPic("127.0.0.1", 18080,"upload/fun"+"/AAAcopy2111"+".jpg");
		    // 创建缩略图（X）
		    //String messageString = FileServiceUtil.createSmall("127.0.0.1", 18080,"upload/fun"+"/AAAcopy211test2.jpg", "upload/fun/smal/1212.jpg", "upload/fun/smal",200);
		    // 创建缩略图（XY）
		    
		    //String messageString = FileServiceUtil.createSmall("111.11.195.117", 8080,"upload/fun"+"/AAAcopy2113"+".jpg", "upload/fun/smal/1212.jpg", 100,100);
//		    String messageString1 = FileServiceUtil.createFolder("127.0.0.1", 18080, "upload/fun/smal");
//		    String messageString = FileServiceUtil.createSmall("127.0.0.1", 18080,"upload/fun/test"+"/AAAcopy211test"+i+".jpg", "upload/fun/smal/"+i+".jpg", 100,120);
		    //String messageString1 = FileServiceUtil.createFolder("127.0.0.1", 18080, "upload/fun/smal");
		    //String messageString = FileServiceUtil.createSmall("127.0.0.1", 18080,"upload/fun/test"+"/AAAcopy211test.jpg", "upload/fun/smal/1212.jpg", 100,100);
		    
		    //返回宽高
	    //String messageString = FileServiceUtil.isImgRetureXY("127.0.0.1", 18080,"upload/fun/test"+"/AAAcopy211test"+i+".jpg");
		    //创建文件夹
		    //String messageString = FileServiceUtil.createFolder("111.11.195.117", 8080, "upload/store/27/2016/07/13");
		    //删除制定路径的文件夹
		    //String messageString = FileServiceUtil.deleteFolder("127.0.0.1", 18080,"upload/fun/smal");
		    //水印
		    //String messageString = FileServiceUtil.waterMark("127.0.0.1", 18080, filePath, outPath, pos, qualNum);
		    //获取path所在的文件夹下的文件大小
		    //String messageString = FileServiceUtil.fileSize("127.0.0.1", 18080, "upload/fun");
		    //String messageString = FileServiceUtil.createSmall("127.0.0.1", 18080, "upload/store/27/2016/07/06/1c562ef9-0d3f-4f82-acd3-746418d923e6.jpg", "upload/store/27/2016/07/06/1c562ef9-0d3f-4f82-acd3-746418d923e6.jpg_small.jpg", 120, 200);
		    //测试创建小图
//		    String messageString0 = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "AAAcopy211test"+".gif","upload/fun/test", inputStream);
//		    String messageString1 = FileServiceUtil.createFolder("127.0.0.1", 18080, "upload/fun/smal");
//            String messageString = FileServiceUtil.createSmall("127.0.0.1", 18080,"upload/fun/test"+"/AAAcopy211test"+".gif", "upload/fun/smal/"+"testsoml.gif", 100,120);
//            String messageString0 = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "AAAcopy211test"+".jpg","upload/fun/test", inputStream);
//            String messageString1 = FileServiceUtil.createFolder("127.0.0.1", 18080, "upload/fun/smal");
//            String messageString = FileServiceUtil.createSmall("127.0.0.1", 18080,"upload/fun/test"+"/AAAcopy211test"+".jpg", "upload/fun/smal/"+"testsoml.jpg", 100,120);
//		    String messageString0 = FileServiceUtil.uploadFileToService("111.11.195.117", 8080, "AAAcopy211test"+".gif","upload/fun/test", inputStream);
//            String messageString1 = FileServiceUtil.createFolder("111.11.195.117", 8080, "upload/fun/smal");
//            String messageString = FileServiceUtil.createSmall("111.11.195.117", 8080,"upload/fun/test"+"/AAAcopy211test"+".gif", "upload/fun/smal/"+"testsoml.gif", 80,120);
            
		    System.out.println(messageString0);
		    //System.out.println(messageString);
		    inputStream.close();
		//}
		/*if(messageString == null ){
		    System.out.println("msg is"+messageString); 
		    
		}else{
    		JSONObject jsono = JSON.parseObject(messageString);
    		if("success".equals(jsono.get("status"))){
    		    System.out.println("msg is"+"kuan-》"+jsono.get("width")+" gao-》"+jsono.get("height"));
    		}
		}*/
	}
}
