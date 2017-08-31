package com.fileserver.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.fileserver.commons.utils.FileServiceUtil;
import com.fileserver.commons.utils.FileToolsUtil;

public class HandPic {
    public static void main(String[] args) {
        //handAllFile("/D:/tupianchuli/201609261010_17602_image/yasuo/oldpho");
        //copyAllFile("/D:/tupianchuli/201609261010_17602_image/oldpho","/D:/tupianchuli/201609261010_17602_image/newpho/picture");
        handAllFileOne("/D:/tupianchuli/test/oldpho/3100.jpg");
    }
    
    public static void copyAllFile(String oldpath,String newpath) 
    {
        File file = new File(oldpath); 
        File [] files = file.listFiles(); 
        for(File a:files) 
        { 
            if(a.isDirectory()) 
            { 
                copyAllFile(a.getAbsolutePath(),newpath); 
            }else{
                System.out.println(a.getAbsolutePath()+"<----------->"+a.getName());
                String real_old_path = a.getAbsolutePath();
                String file_name = a.getName();
                String real_new_path = newpath+File.separator+file_name;
                
                try {
                    
                InputStream inStream = new FileInputStream(real_old_path);
               
                FileOutputStream fs = new FileOutputStream(real_new_path); 
                byte[] buffer = new byte[1444]; 
                int bytesum = 0; 
                int byteread = 0; 
                while ( (byteread = inStream.read(buffer)) != -1) { 
                    bytesum += byteread; //字节数 文件大小 
                    //System.out.println(bytesum); 
                    fs.write(buffer, 0, byteread); 
                } 
                inStream.close(); 
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void handAllFile(String path) 

    { 
        File sourceFile = new File(path.replace("oldpho", "newpho"));
        if(!sourceFile.exists()){
            sourceFile.mkdirs();
        }
        File file = new File(path); 
        File [] files = file.listFiles(); 

        for(File a:files) 
        { 
            if(a.isDirectory()) 

            { 
                handAllFile(a.getAbsolutePath()); 
               
            }else{
                System.out.println(a.getAbsolutePath());
                
                //创建中图
                //FileToolsUtil.createSmall(a.getAbsolutePath(), a.getAbsolutePath().replace("E:", "\\D:")+"_middle."+a.getAbsolutePath().substring(a.getAbsolutePath().lastIndexOf(".") + 1), 300, 300);
                //FileToolsUtil.createSmall(a.getAbsolutePath(), a.getAbsolutePath().replace("E:", "\\D:")+"_small."+a.getAbsolutePath().substring(a.getAbsolutePath().lastIndexOf(".") + 1), 160, 160);
                FileToolsUtil.createSmall(a.getAbsolutePath(), a.getAbsolutePath().replace("oldpho", "newpho"), 600, 600);
                //FileToolsUtil.createSmall(a.getAbsolutePath(), a.getAbsolutePath().replace("D:", "\\E:"));
                
            }
        } 

    }
    public static void handAllFileOne(String path) 
    {
        //FileToolsUtil.createSmall(path,path.replace("oldpho", "newpho"),"/D:/tupianchuli/test/oldpho", 3200); 
        FileToolsUtil.createSmall(path,path.replace("oldpho", "newpho"), 600, 600);
    } 
}
