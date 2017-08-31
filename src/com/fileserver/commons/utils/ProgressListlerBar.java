package com.fileserver.commons.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.ProgressListener;
import com.fileserver.commons.constants.Constant;

/**
 * spring上传监听
 * 2015年8月31日 下午6:18:41
 * @author zhouyi
 */
public class ProgressListlerBar implements ProgressListener{

	private NumberFormat format = new DecimalFormat("0.00%");
	private long tempSize;
	private HttpSession session;
	
	public ProgressListlerBar(HttpSession session){
		this.session=session;
	}
	
	@Override
	public void update(long currentSize, long totalSize, int arg2) {
		if (currentSize != tempSize) {
			//存储百分比
			session.setAttribute(Constant.PERCENTAGE, format.format((double)currentSize / totalSize));
			//存储剩余时间
			tempSize = currentSize;
		}
	}

}
