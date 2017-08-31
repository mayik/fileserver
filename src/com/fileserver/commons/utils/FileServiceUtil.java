package com.fileserver.commons.utils;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fileserver.commons.constants.Constant;
import com.fileserver.model.FileMessage;

/**
 * 文件服务客户端
 * 2016年6月7日 上午11:42:56
 * @author zhouyi
 */
public class FileServiceUtil {
    
    
    
	/**
	 * 上传文件到服务器
	 * 2016年6月7日 上午11:22:59
	 * @param host 文件服务器ip
	 * @param port 文件服务器端口
	 * @param fileName 文件名称
	 * @param inputStream 文件流 FileInputStream 
	 * @return 
	 * @author zhouyi
	 * @throws Exception 
	 */
	public static String uploadFileToService(String host, int port, 
			String fileName,String filepath, MultipartFile file) throws Exception {
		if (fileName == null || "".equals(fileName.trim()) || host == null || "".equals(host.trim())) {
			throw new java.lang.Exception("file service : parameter exception");
		}
		if (file == null) {
			throw new java.lang.Exception("file service : spring MultipartFile is not null");
		}
		//将spring中的MultipartFile中文件流取出转为ByteArrayInputStream
		InputStream is = file.getInputStream();
		return uploadFileToService(host,port,fileName,filepath,is);
	}
	
	
	/**
	 * 上传文件到服务器
	 * 2016年6月7日 上午11:22:59
	 * @param host 文件服务器ip
	 * @param port 文件服务器端口
	 * @param fileName 文件名称
	 * @param inputStream 文件流 FileInputStream 
	 * @return 
	 * @author zhouyi
	 * @throws Exception 
	 */
	public static String uploadFileToService(String host, int port, 
			String fileName,String filepath,InputStream inputStream) throws Exception {
		if (fileName == null || "".equals(fileName.trim()) || host == null || "".equals(host.trim())) {
			throw new java.lang.Exception("file service : parameter exception");
		}
		if (inputStream == null) {
			throw new java.lang.Exception("file service : InputStream is not null");
		}
		Socket socket = new Socket(host, port);
		//写入文件名称
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilename(fileName);
        fileMseeage.setFilePath(filepath);
        fileMseeage.setFlag(Constant.NORMAL_FILE);
        fileMseeage.setByteArr(toByteArray(inputStream));
		//获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			//out.write(b, 0, len);
        out.writeObject(fileMseeage);
		socket.shutdownOutput();
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//得到响应信息
		String message = reader.readLine();
		inputStream.close();
		socket.close();
		return message;
	}
	/**
     * 上传文件到服务器-需要小图
     * 2016年6月7日 上午11:22:59
     * @param host 文件服务器ip
     * @param port 文件服务器端口
     * @param fileName 文件名称
     * @param inputStream 文件流 FileInputStream 
     * @return 
     * @author zhouyi
     * @throws Exception 
     */
    public static String uploadFileToService(String host, int port, 
            String fileName,String filepath,InputStream inputStream,Integer bigWidth,Integer middleWidth,Integer smallWidth) throws Exception {
        if (fileName == null || "".equals(fileName.trim()) || host == null || "".equals(host.trim())) {
            throw new java.lang.Exception("file service : parameter exception");
        }
        if (inputStream == null) {
            throw new java.lang.Exception("file service : InputStream is not null");
        }
        Socket socket = new Socket(host, port);
        //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put(Constant.BIG_WIDTH, bigWidth);
        map.put(Constant.MIDDLE_WIDTH, middleWidth);
        map.put(Constant.SMALL_WIDTH, smallWidth);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilename(fileName);
        fileMseeage.setFilePath(filepath);
        fileMseeage.setFlag(Constant.NEEDSMALL_PIC);
        fileMseeage.setByteArr(toByteArray(inputStream));
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        inputStream.close();
        socket.close();
        return message;
    }
    
   
    /**
     *  删除服务器的文件
     * @param host
     * @param port
     * @param filepath_name
     * @return
     * @throws Exception
     */
    public static String deleteFileToService(String host, int port, 
            String filepath_name) throws Exception {
       
        Socket socket = new Socket(host, port);
       
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilepath_name(filepath_name);
        fileMseeage.setFlag(Constant.DELETE_FILE);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
       
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 删除制定路径的文件,并删除小图
     * @param host
     * @param port
     * @param filepath_name
     * @return
     * @throws Exception
     */
    public static String deleteSamllPic(String host, int port, 
            String filepath_name) throws Exception {
       
        Socket socket = new Socket(host, port);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilepath_name(filepath_name);
        fileMseeage.setFlag(Constant.DELETESMALL_PIC);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    
    /**
     * 创建缩略图（X）
     * @param host
     * @param port
     * @param source 源文件路径和文件名
     * @param target 目标文件路径和文件名
     * @param targetSource 目标路径
     * @param width
     * @return
     * @throws Exception
     */
    public static String createSmall(String host, int port, 
            String source, String target, String targetSource,int width)throws Exception{
        
        Socket socket = new Socket(host, port);
        //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put("source", source);
        map.put("target", target);
        map.put("targetSource", targetSource);
        map.put("width", width);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        
        fileMseeage.setFlag(Constant.SMOLL_PIC_X);
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 创建缩略图（X,Y）
     * @param host
     * @param port
     * @param source
     * @param target
     * @param targetSource
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public static String createSmall(String host, int port, 
            String source, String target, int width,int height)throws Exception{
        
        Socket socket = new Socket(host, port);
      //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put("source", source);
        map.put("target", target);
        map.put("width", width);
        map.put("height", height);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        
        fileMseeage.setFlag(Constant.SMOLL_PIC_XY);
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 删除制定路径的文件夹
     * @param host
     * @param port
     * @param path
     * @return
     * @throws Exception
     */
    public static String deleteFolder(String host, int port, String path) throws Exception{
        Socket socket = new Socket(host, port);
      //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilePath(path);
        fileMseeage.setFlag(Constant.DELETE_FOLDER);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 创建文件夹
     * @param host
     * @param port
     * @param folderPath
     * @return
     * @throws Exception
     */
    public static String createFolder(String host, int port, String folderPath) throws Exception{
        Socket socket = new Socket(host, port);
      //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilePath(folderPath);
        fileMseeage.setFlag(Constant.CREATE_FOLDER);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 删除制定路径的目录，包括子目录
     * @param host
     * @param port
     * @param path /upload/...
     * @return
     * @throws Exception
     */
    public static String deleteDirectory(String host, int port, String path) throws Exception{
        Socket socket = new Socket(host, port);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilePath(path);
        fileMseeage.setFlag(Constant.DELETE_DIRECTORY);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
       
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 拷贝文件
     * @param host
     * @param port
     * @param oldPath
     * @param newPath
     * @return
     * @throws Exception
     */
    public static String copyFile(String host, int port, String oldPath, String newPath) throws Exception{
        Socket socket = new Socket(host, port);
      //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put("oldPath", oldPath);
        map.put("newPath", newPath);
       
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        
        fileMseeage.setFlag(Constant.COPY_FILE);
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 如果是图片返回宽高
     * @param path
     * @return
     */
    public static String isImgRetureXY(String host, int port,String path) throws Exception{
        Socket socket = new Socket(host, port);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilepath_name(path);
        fileMseeage.setFlag(Constant.RETURN_XY);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
    }
    /**
     * 获取path所在的文件夹下的文件大小
     * @param path
     * @return
     */
    public static String fileSize(String host, int port,String path) throws Exception{
        Socket socket = new Socket(host, port);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFilePath(path);
        fileMseeage.setFlag(Constant.FOLDER_FILE_SIZE);
       
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
        
    }
    /**
     * 添加水印
     * @param path
     * @return
     */
    public static String waterMark(String host, int port,String filePath, String outPath, String text, String markContentColor, Font font, int pos, float qualNum) throws Exception{
        Socket socket = new Socket(host, port);
      //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put("filePath", filePath);
        map.put("outPath", outPath);
        map.put("text", text);
        map.put("markContentColor", markContentColor);
        map.put("font", font);
        map.put("pos", pos);
        map.put("qualNum", qualNum);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFlag(Constant.WATER_MARK_TEXT);
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
        
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
        
    }
    /**
     * 添加水印
     * @param path
     * @return
     */
    public static String waterMark(String host, int port,String filePath, String outPath, int pos, float qualNum) throws Exception{
        Socket socket = new Socket(host, port);
      //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put("filePath", filePath);
        map.put("outPath", outPath);
        map.put("pos", pos);
        map.put("qualNum", qualNum);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFlag(Constant.WATER_MARK_IMAGE);
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
      
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
        
    }
    /**
     * 上传处理PDF文件
     * @param path
     * @return
     */
    public static String uploadPDF(String host, int port,String fileName,String filePath,InputStream inputStream) throws Exception{
        Socket socket = new Socket(host, port);
      //封装Map
        Map<String,Object> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("filePath", filePath);
        //封装文件对象
        FileMessage fileMseeage = new FileMessage();
        fileMseeage.setFlag(Constant.UP_PDF_FILE);
        fileMseeage.setByteArr(toByteArray(inputStream));
        fileMseeage.setMap(map);
        //获得输出流，写入文件
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //out.write(b, 0, len);
        out.writeObject(fileMseeage);
      
        //获得输出流，写入文件
        socket.shutdownOutput();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //得到响应信息
        String message = reader.readLine();
        socket.close();
        return message;
        
    }
    /**
     * 将输入流转换成byte[] 
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] toByteArray(InputStream inputStream) throws IOException{  
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        BufferedInputStream in = null;  
        try{  
            in = new BufferedInputStream(inputStream);  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while(-1 != (len = in.read(buffer,0,buf_size))){  
                bos.write(buffer,0,len);  
            }  
            return bos.toByteArray();  
        }catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        }finally{  
            try{  
                in.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            bos.close();  
        }  
    }  
}
