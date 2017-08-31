package com.fileserver.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 文件信息
 * 2016年6月1日 下午3:39:02
 * @author zhouyi
 */
public class FileMessage implements Serializable{
	private static final long serialVersionUID = -5453602160690764233L;
	
	
	private String filename;/**文件名*/
	
	private String filePath;/**文件路径*/
	
	private String suffix;/**文件后缀*/
	
	private String flag;//保存文件标志-0
	
	private byte[] byteArr; //文件 字节码
	
	private Long size;//长度
	
	private String filepath_name;//地址+file名
	
	private Map<String,Object> map;//其他非公共参数
	
	
	public String getFilepath_name() {
        return filepath_name;
    }
    public void setFilepath_name(String filepath_name) {
        this.filepath_name = filepath_name;
    }
    public Map<String, Object> getMap() {
        return map;
    }
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public byte[] getByteArr() {
		return byteArr;
	}
	public void setByteArr(byte[] byteArr) {
		this.byteArr = byteArr;
	}
}
