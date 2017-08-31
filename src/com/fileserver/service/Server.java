package com.fileserver.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;
import com.fileserver.commons.constants.Constant;
import com.fileserver.model.FileMessage;
import com.itextpdf.text.log.SysoLogger;
import com.fileserver.commons.utils.FileToolsUtil;

class Server implements Runnable {

	// 计算资源被使用使用状况
	public static int count = Ket.maxThread;
	private ServerSocket server;

	public Server(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		synchronized (this) {
			Socket socket = null;
			ObjectInputStream objStream = null;
			JSONObject obj = new JSONObject();
			try {
				try {
 					socket = server.accept();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					count--;// 资源使用状况加1
					//System.out.println("count--->"+count+"    "+this+"    "+this.hashCode());
				}

				objStream = new ObjectInputStream(socket.getInputStream());
				FileMessage fileMessage = (FileMessage) objStream.readObject();
				String flag_c = fileMessage.getFlag();
				if ("".equals(flag_c) || flag_c == null) {
					socket.close();
					return;
				}

				if (Constant.NORMAL_FILE.equals(flag_c)) {
					// 保存图片
					FileToolsUtil.saveFileToServer(fileMessage, socket, false);
					
				} else if (Constant.NEEDSMALL_PIC.equals(flag_c)) {
					// 保存带小图的图片
					FileToolsUtil.saveFileToServer(fileMessage, socket, true);
					
				} else if (Constant.RETURN_XY.equals(flag_c)) {
					// 返回图片的长宽
					FileToolsUtil.getPicXY(socket,fileMessage);
					
				} else if (Constant.SMOLL_PIC_X.equals(flag_c)) {
                    // 创建缩图(x)
                    FileToolsUtil.createSmallImgX(socket, fileMessage);
                   
                } else if (Constant.SMOLL_PIC_XY.equals(flag_c)) {
					// 创建缩图(xy)
					FileToolsUtil.createSmallImgXY(socket, fileMessage);
					
				} else if (Constant.CREATE_FOLDER.equals(flag_c)) {
					// 创建文件夹
					FileToolsUtil.createFolder(socket, fileMessage);
					
				} else if (Constant.DELETE_FOLDER.equals(flag_c)) {
                    // 删除文件夹
                    FileToolsUtil.deleteFolder(socket, fileMessage);
                    
                } else if (Constant.DELETE_FILE.equals(flag_c)) {
					// 删除文件
					FileToolsUtil.deleteFile(socket, fileMessage);
					
				} else if (Constant.DELETESMALL_PIC.equals(flag_c)) {
					// 删除小图
					FileToolsUtil.deleteSmallPic(socket, fileMessage);
					
				} else if (Constant.FOLDER_FILE_SIZE.equals(flag_c)) {
					// 获取文件大小
					FileToolsUtil.handFolderSize(socket, fileMessage);
					
				} else if (Constant.WATER_MARK.equals(flag_c)) {
					// 为图片加水印
					FileToolsUtil.waterMarkPic(socket, fileMessage);
					
				} else if (Constant.UP_PDF_FILE.equals(flag_c)) {
                    // 上传PDF
                    FileToolsUtil.uploadPDF(socket, fileMessage);
                    
                } else if (Constant.READ_PDF_FILE.equals(flag_c)) {
                    // 获取PDF流
                    FileToolsUtil.readPDF(socket, fileMessage);
                    
                } else {
					socket.close();
					return;
				}

			} catch (Exception e) {
				// System.out.println("---->"+"接收数据失败");
				e.printStackTrace();
				try {
					OutputStream pw = socket.getOutputStream();
					obj.put("status", "failed");
					pw.write(obj.toJSONString().getBytes());
					socket.shutdownOutput();
					objStream.close();
					socket.close();
				} catch (IOException e1) {
				    if(!socket.isClosed()){
				        try {
                            socket.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
				    }
					e1.printStackTrace();
				}
			} finally {
				// 判断线程是否已经快被使用完，如果使用完则新
				// 当线程剩余五十的时候，重新追加开启一次线程
				// System.out.println("资源使用："+count);
//			    if(!socket.isClosed()){
//                    try {
//                        socket.close();
//                    } catch (IOException e2) {
//                        e2.printStackTrace();
//                    }
//                }
              
				if (count < 50) {
					// 计数器重置为0
					count += Ket.maxThread;
					// 服务被使用完，重新新增服务
					Ket.create(server);
				}
			}
		}
	}
}