package com.fileserver.service;

import java.io.File;
import java.io.FileInputStream;

import com.fileserver.commons.utils.FileServiceUtil;

public class ThreadTest  {
    public static void main(String[] args) {
        for(int i=0;i<200;i++){
            
        ThreadTest1 mTh1=new ThreadTest1(""+i);
        mTh1.start();
        
        }
    }
    
}
class ThreadTest1 extends Thread{
    private String name;
    public ThreadTest1(String name){
        this.name=name;
    }
    public void run() {
        for (int i = 0; i < 20; i++) {
            //System.out.println(name + "运行  :  " + i);
            try {
                FileInputStream inputStream = new FileInputStream(new File("D:/工作文档咋/QQ表情/07764ss/男士牛仔裤2014秋季新款男装韩版修身小脚裤男休闲黑色潮男长裤子-淘宝网_files/T1t4bIFetgXXXXXXXX_!!1-item_pic.gif"));
                String messageString0 = FileServiceUtil.uploadFileToService("127.0.0.1", 18080, "AAAcopy211test"+i+".jpg","upload/fun/test", inputStream);
                //String messageString = FileServiceUtil.isImgRetureXY("127.0.0.1", 18080,"upload/fun/test"+"/AAAcopy211test"+i+".jpg");
                System.out.println(name + "运行  :  " + i+"-------"+messageString0+"----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
}
