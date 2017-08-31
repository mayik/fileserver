package com.fileserver.commons.constants;

/**
 * 常量池
 * 
 * @author 姓名 工号
 * @version [版本号, 2015年12月22日]
 */
public class Constant {

	/** 上传进度 */
	public static final String PERCENTAGE = "percentage";

	/****** flag标记 *********************************************/
	// 普通文件保存
	public static final String NORMAL_FILE = "0";
	// 创建文件（需要缩略图-包含原文件和缩略图）
	public static final String NEEDSMALL_PIC = "5";
	// 创建缩略图（X）
	public static final String SMOLL_PIC_X = "10";
	// 创建缩略图（X,Y）N
	public static final String SMOLL_PIC_XY = "15";
	// 删除文件
	public static final String DELETE_FILE = "20";
	// 删除缩略图（原 大 中 小）
	public static final String DELETESMALL_PIC = "25";
	// 删除制定路径的文件夹 N
	public static final String DELETE_FOLDER = "30";
	// 创建文件夹 N
	public static final String CREATE_FOLDER = "35";
	// 删除制定路径的目录，包括子目录
	public static final String DELETE_DIRECTORY = "40";
	// 拷贝文件 N
	public static final String COPY_FILE = "45";
	// 如果是图片返回宽高
	public static final String RETURN_XY = "50";
	// 获取path所在的文件夹下的文件大小
	public static final String FOLDER_FILE_SIZE = "55";
	// 添加水印-商城
	public static final String WATER_MARK = "60";
	// 上传处理PDF文件
	public static final String UP_PDF_FILE = "65";
	// PDF文件-生成JPG及缩略图
	//public static final String PDF_TO_JPG = "66";
	//PDF文件-生成压缩包
	//public static final String PDF_TO_ZIP = "67";
	//获取PDF的流
	public static final String READ_PDF_FILE ="70";
	
	/****** flag end标记 *********************************************/

	/******* 子项 开始 ************************************************/

	// 添加水印-商城-子项(文字)
	public static final String WATER_MARK_TEXT = "1";
	// 添加水印-商城-子项(图片)
	public static final String WATER_MARK_IMAGE = "2";

	/******* 子项 end ************************************************/
	// 文件上传路径
	public static final String UPLOAD_FILE_PATH = "upload";
	// 缩略图路径
	public static final String UPLOAD_DEMOIMG_BIG = "upload/bigImg";
	public static final String UPLOAD_DEMOIMG_MIDDLE = "upload/middleImg";
	public static final String UPLOAD_DEMOIMG_SMALL = "upload/smallImg";

	// 文件源路径
	public static final String SOURCE_PATH_NAME = "source";
	public static final String TARGET_PATH_NAME = "target";
	public static final String TARGET_PATH = "targetSource";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";

	// 图片的宽
	public static final String BIG_WIDTH = "bigWidth";
	public static final String MIDDLE_WIDTH = "middleWidth";
	public static final String SMALL_WIDTH = "smallWidth";

}
