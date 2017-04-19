/**
* @ClassName: FileUtil
* @Description: 文件操作类
* @author Jacob Shen
* @date Sep 12, 2016 2:07:07 PM
*/
package com.f2b2c.eco.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 将附件复制到指定目录（包含文件名）
	 * 
	 * @param fileBytes
	 * @param path
	 */
	public static String copy(byte[] fileBytes, String pathStr, String folder, String fileType) {
		Path path = Paths.get(pathStr + folder + "/" + format.format(new Date()) + "/");
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
		// 文件夹名_时间_6位随机数字字符串
		String fileName = folder + "_" + format2.format(new Date()) + "_" + ((int) Math.ceil(Math.random() * 900000) + 100000) + fileType;
//		try {
//			FileCopyUtils.copy(fileBytes, new FileOutputStream(path.toString() + "/" + fileName));
//		} catch (FileNotFoundException e) {
//			logger.debug(e.getMessage());
//		} catch (IOException e) {
//			logger.debug(e.getMessage());
//		}
		ImageCompressUtil imageConpress = new ImageCompressUtil(fileBytes, path.toString() + "/" + fileName);
		imageConpress.resizeFix(600, 600);
		return folder + "/" + format.format(new Date()) + "/" + fileName;
	}
	
	/**
	 * 获取文件类型
	 * 
	 * @return
	 */
	public static String getFileType(String fileName) {
		int index = fileName.lastIndexOf("."); // 文件后缀名的点起始索引位置
		String extension = fileName.substring(index, fileName.length()); // 后缀名
		return extension;
	}
	
	public static String copyTo(byte[] fileBytes, String pathStr, String folder, String fileType) {
		Path path = Paths.get(pathStr + folder + "/" + format.format(new Date()) + "/");
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
		// 文件夹名_时间_6位随机数字字符串
		String fileName = folder + "_" + format2.format(new Date()) + "_" + ((int) Math.ceil(Math.random() * 900000) + 100000) + fileType;
		try {
			FileCopyUtils.copy(fileBytes, new FileOutputStream(path.toString() + "/" + fileName));
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return folder + "/" + format.format(new Date()) + "/" + fileName;
	}
}
