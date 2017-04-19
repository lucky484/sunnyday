package com.hd.client.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.hd.client.model.FaceInfoModel;

/**
 * 
 * @author curry.su
 *
 */
public class FaceInfoUtil {

	/**
	 * 解析头信息
	 * 
	 * @param String
	 *            temp
	 * @return
	 */
	public FaceInfoModel jxString(String temp) {
		if (temp == null) {
			return null;
		}

		FaceInfoModel person = new FaceInfoModel();
		String[] str = temp.split(" ");
		person.setPicType(str[1]);
		person.setPicNo(str[2]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = new Date();
		String tim = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String collectTimeStampstr = tim + " " + String.format("%02d", Integer.valueOf(str[4])) + ":"
				+ String.format("%02d", Integer.valueOf(str[5])) + ":" + String.format("%02d", Integer.valueOf(str[6]))
				+ "." + str[7];
		long collectTimeStamp;
		try {
			collectTimeStamp = sdf.parse(collectTimeStampstr).getTime();
			person.setCollectTimeStamp(String.valueOf(collectTimeStamp));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		person.setGroupCode(str[9]);
		person.setDirection(str[10]);
		person.setSpeed(str[11]);
		person.setIsLast(str[28].substring(0, 1));
		person.setRelayTime("");
		person.setRelayFlag("0");
		person.setRemarkDesc("");
		person.setDeleteStatus("0");
		return person;
	}

	/**
	 * 将图片转成二进制
	 * 
	 * @param file
	 * @return
	 */
	public byte[] getByteByImage(File file) {

		if (!file.isFile()) {
			return null;
		}
		byte[] image = null;
		try {
			InputStream is = new FileInputStream(file);
			// 动态创建byte数组长度
			int streamLength = (int) file.length();
			image = new byte[streamLength];
			is.read(image);
			is.close();
			/**
			 * 加载图片之后删除
			 */
			file.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * //测试图片是否转成二进制
	 * 
	 * @param image
	 * @param data
	 */
	public void getImageByByte(byte[] image, String data) {
		try {
			String path = "D://piccopy//" + data + ".jpg";
			ByteArrayInputStream bais = new ByteArrayInputStream(image);
			BufferedImage bi1 = ImageIO.read(bais);
			File w2 = new File(path);// 可以是jpg,png,gif格式
			ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到根据文件路径得到faceinfo对象
	 * 
	 * @param head
	 * @return
	 */
	public FaceInfoModel getPersonByCPI(String head) {
		FaceInfoModel faceInfoModel = new FaceInfoModel();
		int begin = head.indexOf("psndtc");
		String temp = head.substring(begin + 6);
		temp = temp.replaceAll("[\\[,\\],a-z]", "");
		// 解析字符串得到pojo对象
		faceInfoModel = this.jxString(temp);
		return faceInfoModel;
	}

}
