package com.fileserver.commons.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;
import org.w3c.dom.Element;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.fileserver.commons.constants.Constant;
import com.fileserver.model.FileMessage;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;




public class FileToolsUtil {
    private static ImageFormat outputFormat = ImageFormat.JPEG;
    /**
     * 
     * 检查文件是否是图片类型
     * 
     * @param extend 文件后缀
     * @return
     * 
     */
    public static boolean isImg(String extend)
    {
        boolean ret = false;
        List<String> list = new ArrayList<String>();
        list.add("jpg");
        list.add("jpeg");
        list.add("bmp");
        list.add("gif");
        list.add("png");
        list.add("tif");
        for (String s : list)
        {
            if (s.equals(extend))
                ret = true;
        }
        return ret;
    }
    /**
     * 生成缩略图(大 中 小)
     * @param primaryPath
     * @param targetPath
     * @param targetSource
     * @param nw
     * @Title:        createSmall
     * @Description:  TODO(生成缩略图) 
     * @param:        @param primaryPath
     * @param:        @param targetPath
     * @param:        @param targetSource
     * @param:        @param nw 缩略宽   
     * @return:       void    
     * @throws 
     * @author        wanggaowei
     * @Date          2016年2月1日 下午5:01:55
     */
    public static boolean createSmall(String primaryPath,String targetPath,String targetSource,int nw) {
        try {
                File path = new File(targetSource);
                // 检查保存文件的路径是否存在，不存在则创建文件夹
                if (!path.exists())
                {
                    path.mkdirs();
                }
            
              File fi = new File(primaryPath); //大图文件  
              File fo = new File(targetPath); //将要转换出的小图文件  
              
              
              //int nw = 200;  
              /* 
              * AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D 
              * 坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系 
              * 列平移、缩放、翻转、旋转和剪切来构造仿射变换。 
              */
              AffineTransform transform = new AffineTransform();  
              BufferedImage bis = ImageIO.read(fi); //读取图片  
              int w = bis.getWidth();  
              int h = bis.getHeight();  
               //double scale = (double)w/h;  
              int nh = (nw*h)/w ;  
              double sx = (double)nw/w;  
              double sy = (double)nh/h;  
              transform.setToScale(sx,sy); //setToScale(double sx, double sy) 将此变换设置为缩放变换。  
              /* 
               * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 
               *  Raster 中 2D 坐标的线性映射。所使用的插值类型由构造方法通过 
               *  一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。 
               * 如果在构造方法中指定了 RenderingHints 对象，则使用插值提示和呈现 
               * 的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色 
               * 呈现提示和抖动提示。 注意，务必要满足以下约束：源图像与目标图像 
               * 必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中 
               * 的 band 数。 
              */
              AffineTransformOp ato = new AffineTransformOp(transform,null);  
              BufferedImage bid = new BufferedImage(nw,nh,BufferedImage.TYPE_3BYTE_BGR);  
              /* 
               * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像， 
               * 对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存 
               * 储的 Blue、Green 和 Red 三种颜色。 
              */  
              ato.filter(bis,bid);  
              ImageIO.write(bid,"jpg",fo);
                return true;
              } catch(Exception e) {  
                  e.printStackTrace();
                  return false;
              }
    }
    /**
     * 创建小图
     * @param source
     * @param target
     * @param width
     * @param height
     * @return
     */
    public static boolean createSmall(String source, String target, int width, int height)
    {   
        boolean fla = false;
        try{
            File sourceFile = new File(source);
            BufferedImage bis = ImageIO.read(sourceFile);
            int w = bis.getWidth();
            int h = bis.getHeight();
            int nw = width;
            int nh = nw * h / w;
            //source.substring(source.lastIndexOf(".") + 1);
            if(w>3000||h>3000){
                ImageScale2(source, target, width, height);
            }else{
                ImageScale(source, target, width, height);
            }
            fla = true;
        }catch (Exception e) {
            e.printStackTrace();
            fla = false;
          }
        return fla;
    }
    public static void ImageScale(String sourceImg, String targetImg, int width, int height) {
       /* File out = new File(targetImg); // 目的图片  
        FileOutputStream outStream = null;  
        File in = new File(sourceImg); // 原图片  
        FileInputStream inStream = null;  
        
        try {  
            inStream = new FileInputStream(in);  
            ImageWrapper imageWrapper = ImageReadHelper.read(inStream);  
  
            int w = imageWrapper.getWidth();  
            int h = imageWrapper.getHeight();  
              
            float w1= 0f, h1 = 0f;  
            float sp = (float) w / h;  
            float rp = (float) width / height;  
            if (sp > rp) {  
                w1 = (width * h) / (float)w;  
                h1 = width;  
  
            } else if(sp<rp){  
                h1 = (height * w) /(float) h;  
                w1 = width;  
            }else{  
                w1=width;  
                h1=height;  
            }  
            // 1.缩放  
            ScaleParameter scaleParam = new ScaleParameter((int)w1, (int)h1, Algorithm.LANCZOS); // 缩放参数  
            PlanarImage planrImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);  
            imageWrapper = new ImageWrapper(planrImage);  
            // 4.输出  
            outStream = new FileOutputStream(out);  
            String prefix = out.getName().substring(out.getName().lastIndexOf(".") + 1);  
            ImageWriteHelper.write(imageWrapper, outStream, outputFormat.getImageFormat(prefix), new WriteParameter());  
          
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (SimpleImageException e) {  
        } finally {  
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭  
            IOUtils.closeQuietly(outStream);  
  
        }  */
         WriteRender wr = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            Image image = ImageIO.read(new File(sourceImg));
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            float scale = getRatio(imageWidth, imageHeight, width, height);
            imageWidth = (int) (scale * imageWidth);
            imageHeight = (int) (scale * imageHeight);
            
            File in = new File(sourceImg);      //原图片
            File out = new File(targetImg);       //目的图片
            ScaleParameter scaleParam = new ScaleParameter(imageWidth, imageHeight);  //将图像缩略到1024x1024以内，不足1024x1024则不做任何处理
            
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageRender rr = new ReadRender(inStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, outStream);
            wr.render();                            //触发图像处理
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream);         //图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    wr.dispose();                   //释放simpleImage的内部资源
                } catch (SimpleImageException ignore) {
                    // skip ... 
                }
            }
        }
    }
    public static float getRatio(int width, int height, int maxWidth, int maxHeight) {
        float Ratio = 1.0F;

        float widthRatio = (float) maxWidth / width;
        float heightRatio = (float) maxHeight / height;
        if ((widthRatio < 1.0D) || (heightRatio < 1.0D)) {
            Ratio = widthRatio <= heightRatio ? widthRatio : heightRatio;
        }
        return Ratio;
    }
    /**
     * 添加文字水印-商城
     * @param filePath
     * @param outPath
     * @param text
     * @param markContentColor
     * @param font
     * @param pos
     * @param qualNum
     * @return
     */
    public static boolean waterMarkWithText(String filePath, String outPath, String text, String markContentColor, Font font, int pos, float qualNum)
    {
      ImageIcon imgIcon = new ImageIcon(filePath);
      Image theImg = imgIcon.getImage();
      int width = theImg.getWidth(null);
      int height = theImg.getHeight(null);
      BufferedImage bimage = new BufferedImage(width, height, 
        1);
      Graphics2D g = bimage.createGraphics();
      if (font == null) {
        font = new Font("黑体", 1, 30);
        g.setFont(font);
      } else {
        g.setFont(font);
      }
      g.setColor(getColor(markContentColor));
      g.setBackground(Color.white);
      g.drawImage(theImg, 0, 0, null);
      FontMetrics metrics = new FontMetrics(font)
      {
      };
      Rectangle2D bounds = metrics.getStringBounds(text, null);
      int widthInPixels = (int)bounds.getWidth();
      int heightInPixels = (int)bounds.getHeight();
      int left = 0;
      int top = heightInPixels;
  
      if (pos == 2) {
        left = width / 2;
        top = heightInPixels;
      }
      if (pos == 3) {
        left = width - widthInPixels;
        top = heightInPixels;
      }
      if (pos == 4) {
        left = width - widthInPixels;
        top = height / 2;
      }
      if (pos == 5) {
        left = width - widthInPixels;
        top = height - heightInPixels;
      }
      if (pos == 6) {
        left = width / 2;
        top = height - heightInPixels;
      }
      if (pos == 7) {
        left = 0;
        top = height - heightInPixels;
      }
      if (pos == 8) {
        left = 0;
        top = height / 2;
      }
      if (pos == 9) {
        left = width / 2;
        top = height / 2;
      }
      g.drawString(text, left, top);
      g.dispose();
      try {
        FileOutputStream out = new FileOutputStream(outPath);
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
//        param.setQuality(qualNum, true);
//        encoder.encode(bimage, param);
        saveAsJPEG(100, bimage, qualNum, out);
        out.close();
      } catch (Exception e) {
        return false;
      }
      return true;
    }
    /**
     * 添加图片水印
     * @param pressImg
     * @param targetImg
     * @param pos
     * @param alpha
     */
    public static boolean waterMarkWithImage(String pressImg, String targetImg, int pos, float alpha)
    {
      try
      {
    	  File _file = new File(targetImg);
          Image src = ImageIO.read(_file);
          int width = src.getWidth(null);
          int height = src.getHeight(null);
          BufferedImage image = new BufferedImage(width, height, 
            1);
          Graphics2D g = image.createGraphics();
          g.drawImage(src, 0, 0, width, height, null);
    
          File _filebiao = new File(pressImg);
          Image src_biao = ImageIO.read(_filebiao);
          g.setComposite(AlphaComposite.getInstance(10, 
            alpha / 100.0F));
          int width_biao = src_biao.getWidth(null);
          int height_biao = src_biao.getHeight(null);
          int x = 0;
          int y = 0;
    
          if (pos == 2) {
            x = (width - width_biao) / 2;
            y = 0;
          }
          if (pos == 3) {
            x = width - width_biao;
            y = 0;
          }
          if (pos == 4) {
            x = width - width_biao;
            y = (height - height_biao) / 2;
          }
          if (pos == 5) {
            x = width - width_biao;
            y = height - height_biao;
          }
          if (pos == 6) {
            x = (width - width_biao) / 2;
            y = height - height_biao;
          }
          if (pos == 7) {
            x = 0;
            y = height - height_biao;
          }
          if (pos == 8) {
            x = 0;
            y = (height - height_biao) / 2;
          }
          if (pos == 9) {
            x = (width - width_biao) / 2;
            y = (height - height_biao) / 2;
          }
          g.drawImage(src_biao, x, y, width_biao, height_biao, null);
    
          g.dispose();
          FileOutputStream out = new FileOutputStream(targetImg);

 //      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        encoder.encode(image);
        saveAsJPEG(100, image, alpha, out);
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    
    public static Color getColor(String color)
    {
      if (color.charAt(0) == '#') {
        color = color.substring(1);
      }
      if (color.length() != 6)
        return null;
      try
      {
        int r = Integer.parseInt(color.substring(0, 2), 16);
        int g = Integer.parseInt(color.substring(2, 4), 16);
        int b = Integer.parseInt(color.substring(4), 16);
        return new Color(r, g, b); } catch (NumberFormatException nfe) {
      }
      return null;
    }
    /**
     * 创建文件夹
     * @param path
     */
    public static void createFolder(String path) {
        File dir = new File(path);
        boolean ret =false;
        if((!dir.exists())||(!dir.isDirectory())){
             ret = dir.mkdirs();
        }
    }
    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path){
        File file = new File(path);
        if ((file.isFile()) && (file.exists()))
        {
            file.delete();
            return true;
        }else{
            return false;
            
        }
    }
    /**
     * 计算文件夹下的所有文件大小
     * @param folder
     * @return
     */
    public static double fileSize(File folder)
    {
      long foldersize = 0L;
      File[] filelist = folder.listFiles();
      if(filelist != null){
          for (int i = 0; i < filelist.length; i++) {
              if (filelist[i].isDirectory()) {
                  foldersize = (long)(foldersize + fileSize(filelist[i]));
              } else {
                  foldersize += filelist[i].length();
              }
          }
      }
      return div(Long.valueOf(foldersize), Integer.valueOf(1024));
    }
    /**
     * 精确除
     * @param a
     * @param b
     * @return
     */
    public static double div(Object a, Object b)
    {
      double ret = 0.0D;
      if ((!null2String(a).equals("")) && (!null2String(b).equals(""))) {
        BigDecimal e = new BigDecimal(null2String(a));
        BigDecimal f = new BigDecimal(null2String(b));
        if (null2Double(f) > 0.0D)
          ret = e.divide(f, 3, 1).doubleValue();
      }
      DecimalFormat df = new DecimalFormat("0.00");
      return Double.valueOf(df.format(ret)).doubleValue();
    }
    
 /******工具********************************************/
    public static String null2String(Object s) {
        return s == null ? "" : s.toString().trim();
      }
    public static double null2Double(Object s) {
        double v = 0.0D;
        if (s != null)
          try {
            v = Double.parseDouble(null2String(s));
          } catch (Exception localException) {
          }
        return v;
      }
    
    /**
     * 通过socket上传文件到文件服务器
     * @param fileMessage
     * @param socket
     * @param needSmallPic
     */
    public static void saveFileToServer(FileMessage fileMessage, Socket socket, Boolean needSmallPic) {
		// 返回保存文件信息
		JSONObject obj = new JSONObject();
		DataOutputStream out = null;
		String fileName = fileMessage.getFilename();
		String filePath = fileMessage.getFilePath();
		// 获得文件传递过来的字节码
		byte[] byteArr = fileMessage.getByteArr();
		// 获得文件传递过来的字节码
		String realPath = WebUtils.getRealPath(filePath);
		try {
			if (StringUtils.isBlank(fileName) || StringUtils.isBlank(filePath)) {
				socket.close();
				return;
			}
			File f = new File(realPath);
			if (!f.exists()) {
				f.mkdirs();
			}
			out = new DataOutputStream(new FileOutputStream(new File(f, fileName)));
			out.write(byteArr);
			OutputStream pw = socket.getOutputStream();
			obj.put("status", "success");
			obj.put("path", filePath);
			pw.write(obj.toJSONString().getBytes());
			out.close();
			socket.shutdownOutput();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 返回保存失败信息
				OutputStream pw = socket.getOutputStream();
				obj.put("status", "failed");
				pw.write(obj.toJSONString().getBytes());
				out.close();
				socket.shutdownOutput();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		if (needSmallPic) {
			Map<String, Object> map = fileMessage.getMap();
			String bigWidth_c =  map.get(Constant.BIG_WIDTH).toString().trim();
			String middleWidth_c =  map.get(Constant.MIDDLE_WIDTH).toString().trim();
			String smallWidth_c =  map.get(Constant.SMALL_WIDTH).toString().trim();
			String originalSource = realPath+"/" +fileName;
			
		    //大图保存
            String targetBigSource =  realPath.replace(Constant.UPLOAD_FILE_PATH, Constant.UPLOAD_DEMOIMG_BIG);
            String targetBigPath = targetBigSource + "/" +fileName;
            //创建路径
            createFolder(targetBigSource);
            //保存压缩图
            FileToolsUtil.createSmall(originalSource, targetBigPath,  Integer.parseInt(bigWidth_c),Integer.parseInt(bigWidth_c));
            
            //中图保存
            String targetMiddleSource =  realPath.replace(Constant.UPLOAD_FILE_PATH,  Constant.UPLOAD_DEMOIMG_MIDDLE);
            String targetMiddlePath = targetMiddleSource + "/" +fileName;
            //创建路径
            createFolder(targetMiddleSource);
            //保存压缩图
            FileToolsUtil.createSmall(originalSource, targetMiddlePath,Integer.parseInt(middleWidth_c), Integer.parseInt(middleWidth_c));
            
            //小图保存
            String targetSmallSource =  realPath.replace(Constant.UPLOAD_FILE_PATH,  Constant.UPLOAD_DEMOIMG_SMALL);
            String targetSmallPath = targetSmallSource + "/" +fileName;
            //创建路径
            createFolder(targetSmallSource);
            //保存压缩图
            FileToolsUtil.createSmall(originalSource, targetSmallPath, Integer.parseInt(smallWidth_c), Integer.parseInt(smallWidth_c));
		}

	}
    
    public static void getPicXY(Socket socket, FileMessage fileMessage) throws IOException {
		JSONObject obj = new JSONObject();
		String filePath = fileMessage.getFilepath_name();
		if (StringUtils.isEmpty(filePath)) {
			socket.close();
			return;
		}
		String realPath = WebUtils.getRealPath(filePath);
		File img = new File(realPath);
		BufferedImage bis = ImageIO.read(img);
		int w = bis.getWidth();
		int h = bis.getHeight();
		OutputStream pw = socket.getOutputStream();
		obj.put("status", "success");
		obj.put("width", Integer.valueOf(w));
		obj.put("height", Integer.valueOf(h));
		pw.write(obj.toJSONString().getBytes());
		  
		socket.shutdownOutput();
	}

    public static void main(String[] args) {
        String path = "E:/WorkSpace/EclipseWork/forlink/.metadata/.plugins/org.eclipse.wst.server.core/tmp4/wtpwebapps/fileserver/upload/scenic/cbd210ae-c409-453d-9b58-84d07c6682ce.gif";
        //String path = "D:/QQ表情/07764ss/男士牛仔裤2014秋季新款男装韩版修身小脚裤男休闲黑色潮男长裤子-淘宝网_files/T1t4bIFetgXXXXXXXX_!!1-item_pic.gif";
        File img = new File(path);
        BufferedImage bis;
        try {
            bis = ImageIO.read(img);
            int w = bis.getWidth();
            int h = bis.getHeight();
            //System.out.println("w:"+w+"   "+"h:"+h);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	/**
	 * 为图片加水印
	 * 
	 * @param socket
	 * @param fileMessage
	 * @throws IOException
	 */
    public static void waterMarkPic(Socket socket, FileMessage fileMessage) throws IOException {
		JSONObject obj = new JSONObject();
		String waterMarkType = fileMessage.getMap().get("waterMarkType").toString();
		if ("".equals(waterMarkType) || waterMarkType == null) {
			socket.close();
			return;
		}
		
		String filePath = WebUtils.getRealPath(fileMessage.getMap().get("filePath").toString());
		String outPath = WebUtils.getRealPath(fileMessage.getMap().get("outPath").toString());

		int pos =Integer.parseInt(fileMessage.getMap().get("pos").toString());
		float qualNum = Float.parseFloat(fileMessage.getMap().get("qualNum").toString());
		boolean reflag = true;
		if (Constant.WATER_MARK_TEXT.equals(waterMarkType)) {
			String text = fileMessage.getMap().get("text").toString();
			String markContentColor = fileMessage.getMap().get("markContentColor").toString();
			Font font =  (Font) fileMessage.getMap().get("font");
			reflag = FileToolsUtil.waterMarkWithText(filePath, outPath, text, markContentColor, font, pos, qualNum);
		} else {
			reflag = FileToolsUtil.waterMarkWithImage(filePath, outPath, pos, qualNum);
		}
		if (!reflag) {
			socket.close();
			return;
		}
		OutputStream pw = socket.getOutputStream();
		obj.put("status", "success");
		obj.put("path", outPath);
		pw.write(obj.toJSONString().getBytes());
		socket.shutdownOutput();
	}

	/**
	 * 获得文件路径大小
	 * 
	 * @param socket
	 * @param obj
	 * @throws IOException
	 */
    public static void handFolderSize(Socket socket, FileMessage fileMessage) throws IOException {
		JSONObject obj = new JSONObject();
		String folderPath = fileMessage.getFilePath();
		if ("".equals(folderPath) || folderPath == null) {
			socket.close();
			return;
		}
		String filePath = WebUtils.getRealPath(folderPath);
		Double sizeval = FileToolsUtil.fileSize(new File(filePath));
		OutputStream pw = socket.getOutputStream();
		obj.put("status", "success");
		obj.put("filesize", sizeval);
		obj.put("path", folderPath);
		pw.write(obj.toJSONString().getBytes());
		socket.shutdownOutput();
	}

	/**
	 * 删除小图包括原图
	 * 
	 * @param socket
	 * @param fileMessage
	 * @throws IOException
	 * @throws Exception
	 */
    public static void deleteSmallPic(Socket socket, FileMessage fileMessage) throws IOException, Exception {
		JSONObject obj = new JSONObject();
		String filePath_d = fileMessage.getFilepath_name();
		if (StringUtils.isBlank(filePath_d)) {
			socket.close();
			return;
		}
		String filePath = WebUtils.getRealPath(filePath_d);
		String bigPath = filePath.replace(Constant.UPLOAD_FILE_PATH, Constant.UPLOAD_DEMOIMG_BIG);
		String middlePath = filePath.replace(Constant.UPLOAD_FILE_PATH, Constant.UPLOAD_DEMOIMG_MIDDLE);
		String smallPath = filePath.replace(Constant.UPLOAD_FILE_PATH, Constant.UPLOAD_DEMOIMG_SMALL);
		FileToolsUtil.deleteFile(bigPath);
		FileToolsUtil.deleteFile(middlePath);
		FileToolsUtil.deleteFile(smallPath);
		if (FileToolsUtil.deleteFile(filePath) != true) {
			socket.close();
			throw new Exception("faild");
		}
		OutputStream pw = socket.getOutputStream();
		obj.put("status", "success");
		obj.put("path", filePath_d);
		pw.write(obj.toJSONString().getBytes());
		socket.shutdownOutput();
	}

	/**
	 * 删除文件
	 * 
	 * @param socket
	 * @param obj
	 * @param fileMessage
	 * @throws IOException
	 * @throws Exception
	 */
    public static void deleteFile(Socket socket, FileMessage fileMessage) throws IOException, Exception {
		JSONObject obj = new JSONObject();
		String filePath_d = fileMessage.getFilepath_name();
		if (StringUtils.isBlank(filePath_d)) {
			socket.close();
			return;
		}
		
		String filePath = WebUtils.getRealPath(filePath_d);
		//System.out.println("filePath:"+filePath);
		if (FileToolsUtil.deleteFile(filePath) != true) {
			socket.close();
			return;
		}
		OutputStream pw = socket.getOutputStream();
		obj.put("status", "success");
		obj.put("path", filePath_d);
		pw.write(obj.toJSONString().getBytes());
		socket.shutdownOutput();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param socket
	 * @param fileMessage
	 * @throws IOException
	 */
    public static void createFolder(Socket socket, FileMessage fileMessage) throws IOException {
		JSONObject obj = new JSONObject();
		String folderPath =  fileMessage.getFilePath().toString().trim();
		if (StringUtils.isBlank(folderPath)) {
			socket.close();
			return;
		}
		String realFolderPath = WebUtils.getRealPath(folderPath);
		boolean ret = false;
		File myFilePath = new File(realFolderPath);
		if ((!myFilePath.exists()) && (!myFilePath.isDirectory())) {
			ret = myFilePath.mkdirs();
		}else{
		    ret = true;
		}
		
		OutputStream pw = socket.getOutputStream();
		if (!ret) {
		    obj.put("status", "faild"); 
        }else{
            obj.put("status", "success"); 
        }
		
		obj.put("path", folderPath);
		pw.write(obj.toJSONString().getBytes());
		socket.shutdownOutput();
	}
    /**
     * 删除文件夹
     * 
     * @param socket
     * @param fileMessage
     * @throws IOException
     */
    public static void deleteFolder(Socket socket, FileMessage fileMessage) throws IOException {
        JSONObject obj = new JSONObject();
        String folderPath =  fileMessage.getFilePath().toString().trim();
        if (StringUtils.isBlank(folderPath)) {
            socket.close();
            return;
        }
        String realFolderPath = WebUtils.getRealPath(folderPath);
        boolean ret = false;
        File myFilePath = new File(realFolderPath);
        if ((myFilePath.exists()) && (myFilePath.isDirectory())) {
            ret = myFilePath.delete();
        }
        if (!ret) {
            socket.close();
            return;
        }
        OutputStream pw = socket.getOutputStream();
        obj.put("status", "success");
        obj.put("path", folderPath);
        pw.write(obj.toJSONString().getBytes());
          
        socket.shutdownOutput();
    }
    public static void createSmallImgX(Socket socket, FileMessage fileMessage) throws IOException {
        JSONObject obj = new JSONObject();
        Map<String, Object> map = fileMessage.getMap();
        String source_c =  map.get(Constant.SOURCE_PATH_NAME).toString().trim();
        String target_c =  map.get(Constant.TARGET_PATH_NAME).toString().trim();
        String targetSource_c =  map.get(Constant.TARGET_PATH).toString().trim();
        String Width_c =  map.get(Constant.WIDTH).toString().trim();
        String source =  WebUtils.getRealPath(source_c);
        String target =  WebUtils.getRealPath(target_c);
        String targetSource =  WebUtils.getRealPath(targetSource_c);
        //创建目录
        File f = new File(target);
        if(!f.exists()){
            f.mkdir();
        }
        //小图保存
        boolean createSmall_res= FileToolsUtil.createSmall(source, target, targetSource, Integer.parseInt(Width_c));
       if(createSmall_res){
           OutputStream pw = socket.getOutputStream();
           obj.put("status", "success");
           obj.put("path", target_c);
           pw.write(obj.toJSONString().getBytes());
           socket.shutdownOutput();
           return;
       }else{
           socket.close();
           return;
       }
        
    }
	/**
	 * 创建小图(XY)
	 * 
	 * @param socket
	 * @param fileMessage
	 * @throws IOException
	 */
    public static void createSmallImgXY(Socket socket, FileMessage fileMessage) throws IOException {
		JSONObject obj = new JSONObject();
		Map<String, Object> map = fileMessage.getMap();
		String source = (String) map.get(Constant.SOURCE_PATH_NAME);
		String target = (String) map.get(Constant.TARGET_PATH_NAME);
		String width = (String) map.get(Constant.WIDTH).toString().trim();
		String height = (String) map.get(Constant.HEIGHT).toString().trim();
		if (StringUtils.isBlank(source) || StringUtils.isBlank(target) || StringUtils.isBlank(width)
				|| StringUtils.isBlank(height)) {
			OutputStream pw = socket.getOutputStream();
			obj.put("status", "false");
			obj.put("path", target);
			pw.write(obj.toJSONString().getBytes());
			socket.shutdownOutput();
			return;
		}
		String sourcePath = WebUtils.getRealPath(source);
		String targetPath = WebUtils.getRealPath(target);
		boolean reflag = FileToolsUtil.createSmall(sourcePath, targetPath, Integer.parseInt(width),
				Integer.parseInt(height));
		if (!reflag) {
			OutputStream pw = socket.getOutputStream();
			obj.put("status", "false");
			obj.put("path", target);
			pw.write(obj.toJSONString().getBytes());
			socket.shutdownOutput();
			return;
		}
		OutputStream pw = socket.getOutputStream();
		obj.put("status", "success");
		obj.put("path", target);
		pw.write(obj.toJSONString().getBytes());
		socket.shutdownOutput();
	}
    
    /**
     * 以JPEG编码保存图片
     * @param dpi  分辨率
     * @param image_to_save  要处理的图像图片
     * @param JPEGcompression  压缩比
     * @param fos 文件输出流
     * @throws IOException
     */
    private static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {
          
        //old jpeg class
        //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder  =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
        //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam  =  jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);
      
        // Image writer
        JPEGImageWriter imageWriter  =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        //and metadata
        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
         
         
        if(dpi !=  null && !dpi.equals("")){
             
             //old metadata
            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
            //jpegEncodeParam.setXDensity(dpi);
            //jpegEncodeParam.setYDensity(dpi);
      
            //new metadata
            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(dpi) );
            jfif.setAttribute("Ydensity", Integer.toString(dpi));
             
        }
      
      
        if(JPEGcompression >= 0 && JPEGcompression <= 1f){
      
            //old compression
            //jpegEncodeParam.setQuality(JPEGcompression,false);
      
            // new Compression
            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);
      
        }
      
        //old write and clean
        //jpegEncoder.encode(image_to_save, jpegEncodeParam);
      
        //new Write and clean up
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();
      
    }
    
    
    //创建压缩图片方法2
    public static void ImageScale2(String sourceImg, String targetImg, int width, int height) {
        try {
            Image image = ImageIO.read(new File(sourceImg));
            //Image image = Toolkit.getDefaultToolkit().getImage(sourceImg);
           
            String suffix = sourceImg.substring(sourceImg.lastIndexOf(".")+1);
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            float scale = getRatio(imageWidth, imageHeight, width, height);
            imageWidth = (int) (scale * imageWidth);
            imageHeight = (int) (scale * imageHeight);

            image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_AREA_AVERAGING);

            BufferedImage mBufferedImage = new BufferedImage(imageWidth, imageHeight, 1);
            //BufferedImage mBufferedImage = new BufferedImage(imageWidth, imageHeight, 3);
            Graphics2D g2 = mBufferedImage.createGraphics();

            g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white, null);
            //g2.drawImage(image, 0, 0,null);
            
            g2.dispose();

            float[] kernelData2 = { -0.125F, -0.125F, -0.125F, -0.125F, 2.0F, -0.125F, -0.125F, -0.125F, -0.125F };
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, 1, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);
            FileOutputStream out = new FileOutputStream(targetImg);
            
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(mBufferedImage);
            saveAsJPEG(100, mBufferedImage, 0.7f, out);
            out.close();
        } catch (FileNotFoundException localFileNotFoundException) {
        } catch (IOException localIOException) {
        }
    }
    
 /****上传PDF开始***********************************************/
    
    /**
     * 上传PDF功能后处理成JPG
     * 
     * @param socket
     * @param fileMessage
     * @throws IOException
     */
    public static void uploadPDF(Socket socket, FileMessage fileMessage) throws IOException {
        JSONObject obj = new JSONObject();
        DataOutputStream out = null;
        Map<String, Object> map = fileMessage.getMap();
        String pinYinName = (String) map.get("fileName");
        String filePath = (String) map.get("filePath");
        // 获得文件传递过来的字节码
        byte[] byteArr = fileMessage.getByteArr();
        if (StringUtils.isBlank(pinYinName) || StringUtils.isBlank(filePath)) {
            socket.close();
            return;
        }
        String pdfBasePath = WebUtils.getRealPath(filePath)+"/";
        
        File file =new File(pdfBasePath); 
        if  (!file .exists()  && !file .isDirectory())
        {   
            //不存在创建
            file.mkdirs();
            File fileJpg =new File(pdfBasePath+"jpg");//JPG文件夹
            fileJpg.mkdir();
            File jpgDemo =new File(pdfBasePath+"jpgDemo");//缩略图压缩包
            jpgDemo.mkdir();
        } else
        {
            //存在就删除 再创建
            if(deleteDirectory(pdfBasePath))
            {
                file =new File(pdfBasePath); 
                file.mkdir();
                File fileJpg =new File(pdfBasePath+"jpg");//JPG文件夹
                fileJpg.mkdir();
                File jpgDemo =new File(pdfBasePath+"jpgDemo");//缩略图压缩包
                jpgDemo.mkdir();
            }
        }
        File mainPdf = new File(pdfBasePath+pinYinName+".pdf");//移动文件过去

        out = new DataOutputStream(new FileOutputStream(mainPdf));
        out.write(byteArr);
        //生成JPG及缩略图
        int mainPdfNum = pdfToJpg(pdfBasePath+pinYinName+".pdf",pdfBasePath);
        //生成压缩包
        fileToZip(pdfBasePath+"jpg",pdfBasePath,pinYinName);
        OutputStream pw = socket.getOutputStream();
        obj.put("status", "success");
        obj.put("path", filePath+"/"+pinYinName+".pdf");
        obj.put("mainPdfNum",mainPdfNum);
        pw.write(obj.toJSONString().getBytes());
        out.close();
        socket.shutdownOutput();
    }
    
    /**
     * 
     *
     * @param file
     * @Title:        pdfToJpg 
     * @Description:  TODO(PDF生成JPG) 
     * @param:        @param file    
     * @return:       void    
     * @throws 
     * @author        wanggaowei
     * @Date          2015年12月28日 下午5:09:34
     */
    public static int pdfToJpg(String pdfPath,String targedFilePath){
        /**instance of PdfDecoder to convert PDF into image*/
        PdfDecoder decode_pdf = new PdfDecoder(true);

        /**set mappings for non-embedded fonts to use*/
        FontMappings.setFontReplacements();

        /**open the PDF file - can also be a URL or a byte array*/
        try {
            decode_pdf.openPdfFile(pdfPath); // file

            /**get page 1 as an image*/
            // page range if you want to extract all pages with a loop
            int start = 1, end = decode_pdf.getPageCount();
            for (int i = start; i < end + 1; i++) {
                BufferedImage img = decode_pdf.getPageAsImage(i);
                try {
                    ImageIO.write(img, "jpg", new File(targedFilePath+"jpg/"+i+".jpg"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jpgToDemo(targedFilePath+"jpg/"+i+".jpg",targedFilePath+"jpgDemo/"+i+".jpg");
            }
            /**close the pdf file*/
            decode_pdf.closePdfFile();

            return end;
        } catch (PdfException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static void unmap(final Object buffer) {  
        AccessController.doPrivilegedWithCombiner(new PrivilegedAction<Object>() {  
        @SuppressWarnings("restriction")
        public Object run() {  
            try {  
                Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);  
                getCleanerMethod.setAccessible(true);  
                sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);  
                cleaner.clean();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }
            return null;  
        }  
        });  
    }
    
    /**
     * 
     *
     * @param primaryPath
     * @param targetPath
     * @Title:        jpgToDemo 
     * @Description:  TODO(把某个图片生成缩略图) 
     * @param:        @param primaryPath
     * @param:        @param targetPath    
     * @return:       void    
     * @throws 
     * @author        wanggaowei
     * @Date          2015年12月28日 下午6:01:21
     */
    public static void jpgToDemo(String primaryPath,String targetPath) {
        try {
              File fi = new File(primaryPath); //大图文件  
              File fo = new File(targetPath); //将要转换出的小图文件  
              int nw = 150;  
              /* 
              AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D 
                      坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系 
                      列平移、缩放、翻转、旋转和剪切来构造仿射变换。 
              */
              AffineTransform transform = new AffineTransform();  
              BufferedImage bis = ImageIO.read(fi); //读取图片  
              int w = bis.getWidth();  
              int h = bis.getHeight();  
               //double scale = (double)w/h;  
              int nh = (nw*h)/w ;  
              double sx = (double)nw/w;  
              double sy = (double)nh/h;  
              transform.setToScale(sx,sy); //setToScale(double sx, double sy) 将此变换设置为缩放变换。  
              /* 
               * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 
               *  Raster 中 2D 坐标的线性映射。所使用的插值类型由构造方法通过 
               *  一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。 
                      如果在构造方法中指定了 RenderingHints 对象，则使用插值提示和呈现 
                      的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色 
                      呈现提示和抖动提示。 注意，务必要满足以下约束：源图像与目标图像 
                      必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中 
                      的 band 数。 
              */
              AffineTransformOp ato = new AffineTransformOp(transform,null);  
              BufferedImage bid = new BufferedImage(nw,nh,BufferedImage.TYPE_3BYTE_BGR);  
              /* 
               * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像， 
               * 对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存 
               * 储的 Blue、Green 和 Red 三种颜色。 
              */  
              ato.filter(bis,bid);
              ImageIO.write(bid,"jpg",fo);
              } catch(Exception e) {  
                  e.printStackTrace();  
              }
        }

        /** 
         * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
         * @param sourceFilePath :待压缩的文件路径 
         * @param zipFilePath :压缩后存放路径 
         * @param fileName :压缩后文件的名称 
         * @return 
         */  
        public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
            boolean flag = false;  
            File sourceFile = new File(sourceFilePath);  
            FileInputStream fis = null;  
            BufferedInputStream bis = null;  
            FileOutputStream fos = null;  
            ZipOutputStream zos = null;  
             
            if(sourceFile.exists() == false){  
            }else{  
                try {  
                    File zipFile = new File(zipFilePath + "/" + fileName +".rar");  
                    if(zipFile.exists()){   
                        //存在就删除
                        deleteFile(zipFilePath+fileName +".rar");
                    }

                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){   
                    }else{
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                               zos.write(bufs,0,read);  
                            }
                            zos.closeEntry();
                            fis.close();//这个流必须关 否则JPG删除不了
                            //bis.close();
                        }
                       
                        flag = true;
                    }  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                } finally{  
                    //关闭流  
                    try {
                        if(null != bis) bis.close();  
                        if(null != zos) zos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                        throw new RuntimeException(e);  
                    }  
                }  
            }  
            return flag;  
       }

       

        /** 
         * 删除目录（文件夹）以及目录下的文件 
         * @param   sPath 被删除目录的文件路径 
         * @return  目录删除成功返回true，否则返回false 
         */  
        public static boolean deleteDirectory(String sPath) {  
            //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
            if (!sPath.endsWith(File.separator)) {  
                sPath = sPath + File.separator;  
            }  
            File dirFile = new File(sPath);  
            //如果dir对应的文件不存在，或者不是一个目录，则退出  
            if (!dirFile.exists() || !dirFile.isDirectory()) {  
                return false;  
            }
            Boolean flag = true;  
            //删除文件夹下的所有文件(包括子目录)  
            File[] files = dirFile.listFiles();  
            for (int i = 0; i < files.length; i++) {  
                //删除子文件  
                if (files[i].isFile()) {  
                    flag = deleteFile(files[i].getAbsolutePath());  
                    if (!flag) break;  
                } //删除子目录  
                else {  
                    flag = deleteDirectory(files[i].getAbsolutePath());  
                    if (!flag) break;  
                }
            }  
            if (!flag) return false;  
            //删除当前目录  
            if (dirFile.delete()) {  
                return true;  
            } else {  
                return false;  
            }  
        }
        
/****上传PDF结束***********************************************/
        /**
         * 获取PDF的流 
         * @param socket
         * @param fileMessage
         * @throws IOException
         */
        public static void readPDF(Socket socket, FileMessage fileMessage) throws Exception  {
           
            JSONObject obj = new JSONObject();
            InputStream fis= null;
            OutputStream fos =null;
            Map<String, Object> map = fileMessage.getMap();
            String filePath = (String) map.get("filePath");
            String realPath = WebUtils.getRealPath(filePath);
            File PDFile = new File(realPath);
            if (!PDFile.exists()) {
                socket.close();
                return;
            }else{
             fis = new BufferedInputStream(new FileInputStream(PDFile));
       
                FileMessage fileMessageout = new FileMessage();
                fileMessageout.setByteArr(toByteArray(fis));
              //获得输出流，写入文件
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    //out.write(b, 0, len);
                out.writeObject(fileMessageout);
              
                //获得输出流，写入文件
                socket.shutdownOutput();
              
            }
            
           
            
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
