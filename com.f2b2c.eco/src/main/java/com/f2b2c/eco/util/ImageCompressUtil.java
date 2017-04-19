/**
* @ClassName: ImageCompressUtil
* @Description: 图片压缩
* @author Jacob Shen
* @date Oct 12, 2016 9:47:10 AM
*/
package com.f2b2c.eco.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCompressUtil {
	private Image img;
	private int width;
	private int height;
	private String path;

	public static void main(String[] args) throws Exception {
		// System.out.println("开始：" + new Date());
		// ImageCompressUtil imgCom = new ImageCompressUtil("d:/1.jpg");
		// imgCom.resizeFix(400, 400);
		// System.out.println("结束：" + new Date());
	}

	/**
	 * 构造函数
	 */
	public ImageCompressUtil(byte[] fileBytes, String path) {
		// File file = new File(fileName);// 读入文件
		// img = ImageIO.read(file); // 构造Image对象
		try {
			img = ImageIO.read(new ByteArrayInputStream(fileBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
		this.path = path;
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 */
	public void resizeFix(int w, int h) {
		if (width / height > w / h) {
			resizeByWidth(w);
		} else {
			resizeByHeight(h);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public void resizeByWidth(int w) {
		int h = (int) (height * w / width);
		resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public void resizeByHeight(int h) {
		int w = (int) (width * h / height);
		resize(w, h);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public void resize(int w, int h) {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File(path);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(destFile);
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
