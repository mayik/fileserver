package com.fileserver.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fileserver.commons.constants.Constant;
import com.fileserver.commons.utils.DateUtils;
import com.fileserver.commons.utils.FileServiceUtil;
import com.fileserver.commons.utils.StringUtils;
import com.fileserver.commons.utils.WebUtils;
import com.fileserver.model.FileMessage;

/**
 * 文件，附件控制器
 * 2016年1月13日 下午4:06:40
 * @author zhouyi
 */
@Controller
@Scope("prototype")
@RequestMapping("file")
public class FileController {
	
	private static String filePath;   
	
	static {   
        Properties prop = new Properties();   
        InputStream in = FileController.class.getResourceAsStream("/config.properties");   
        try {   
            prop.load(in);   
            filePath = prop.getProperty("filePath").trim();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }   
	
	
	/**
	 * 上传
	 * 2016年6月1日 下午4:10:43
	 * @author zhouyi
	 */
	@RequestMapping(value="uploadFj")
	@ResponseBody
	public Object uploadFj(Model model,
			String type, //1文件 2图片
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		FileMessage message = new FileMessage();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		
		String messages = FileServiceUtil.uploadFileToService(
				"127.0.0.1", 18080,file.getOriginalFilename(),"upload" ,file);
		
		System.out.println(messages);
		
		return messages;
		/*
		if(file.getSize() == 0){
			message.setStatus(false);
			message.setMessage("文件不能为空");
			return message;
		}
		if(file.getSize() > 314572800){
			message.setStatus(false);
			message.setMessage("文件不能大于300MB");
			return message;
		}
		try {
			String fileName = file.getOriginalFilename();
			message.setFilename(fileName);
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String rename = System.currentTimeMillis() + "." + suffix;
			message.setRename(rename);
			String filePath = this.filePath;
			Date date = DateUtils.getNow();
			String year = DateUtils.format(date, "yyyy") + "/";
			String month = DateUtils.format(date, "MM") + "/";
			String day = DateUtils.format(date, "dd") + "/";
			filePath += year += month += day;
			message.setFilePath(filePath + rename);
			message.setSize(file.getSize());
			message.setUploadTime(DateUtils.getNow());
			message.setSuffix(suffix);
			//写入磁盘
			File f = new File(WebUtils.getRealPath(filePath),rename);
			if (!f.exists()) {
				f.mkdirs();
			}
			file.transferTo(f);
			message.setStatus(true);
			message.setMessage("上传成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(true);
			message.setMessage("上传文件到服务器异常");
			return message;
		}*/
	}
	
	/**
	 * 上传进度
	 * 2016年6月1日 下午4:10:59
	 * @author zhouyi
	 */
	@RequestMapping(value="progress",method=RequestMethod.GET)
	@ResponseBody
	public Object progress(HttpSession session) throws Exception{
		Object obj = session.getAttribute(Constant.PERCENTAGE);
		return WebUtils.getMap(Constant.PERCENTAGE, obj);
	}
	
	/**
	 * 下载
	 * 2016年6月1日 下午4:10:30
	 * @author zhouyi
	 */
	@RequestMapping(value="download",method={RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<byte[]> download(String path,String name) throws Exception {
		String suffix = path.substring(path.lastIndexOf("."));
		if (!StringUtils.isValid(name)) {
			name = DateUtils.format(DateUtils.getNow(), "yyyy-MM-dd HH:mm:ss") + suffix;
		} else {
			name += suffix;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//File currFile = new File(WebUtils.getRealPath(path));
		//test
		 
            path = filePath+path;
		//
		File currFile = new File(path);
		if (currFile.exists()) {
			byte[] b = FileUtils.readFileToByteArray(currFile);
			out.write(b, 0, b.length);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(name.getBytes("gbk"),"iso-8859-1"));
		return new ResponseEntity<byte[]>(out.toByteArray(),headers, HttpStatus.OK);
	}
	
	
}
