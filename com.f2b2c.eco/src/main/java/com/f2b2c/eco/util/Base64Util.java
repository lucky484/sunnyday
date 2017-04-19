/**
* @ClassName: Base64Until
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Sep 14, 2016 5:49:57 PM
*/
package com.f2b2c.eco.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author jacob.shen
 *
 */
public class Base64Util {
	private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");

	// 图片转化成base64字符串
	public static String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "d://test.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static String GenerateImage(String imgStr, String savePath, String folder) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) {
			// 图像数据为空
			return null;
		}
		String path = savePath + folder + "/" + format.format(new Date());
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			// 如果不存在，创建目录
			file.mkdirs();
		}
		String fileName = folder + "_" + format2.format(new Date()) + "_" + ((int) Math.ceil(Math.random() * 900000) + 100000) + ".jpg";
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			out = new FileOutputStream(path + "/" + fileName);
			out.write(b);
			out.flush();
			return folder + "/" + format.format(new Date()) + "/" + fileName;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
}
