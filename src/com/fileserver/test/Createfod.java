package com.fileserver.test;

import java.io.File;

public class Createfod {
    public static void main(String[] args) {
        String str = "D:/index";
        File f = new File(str+"/upload/scenic");
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
