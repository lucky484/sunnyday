package com.softtek.mdm.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 处理文件的工具类
 * @author color.wu
 *
 */
public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class);
	
	/**
	 * 获取resources的绝对路径
	 * @return
	 */
	public static String getResourcesPath(){
		String sourcePath=FileUtils.class.getResource("/").getPath();
		if(sourcePath.indexOf("/WEB-INF")>0)
			return sourcePath.substring(0,sourcePath.indexOf("/WEB-INF"))+"/resources";
		else {
			return sourcePath;
		}
	}
	
	/**
	 * 获取web-info的绝对路径
	 * @return
	 */
	public static String getWebInfoPath(){
		String sourcePath=FileUtils.class.getResource("/").getPath();
		if(sourcePath.indexOf("/WEB-INF")>0)
			return sourcePath.substring(0,sourcePath.indexOf("/WEB-INF")+8);
		else {
			return sourcePath;
		}
	}
	
	/**
	 * 删除文件，不删除目录
	 * @param filePath 文件路径
	 * @return true | false
	 */
	public static boolean delete(String filePath){
		if(StringUtils.isEmpty(filePath)){
			return false;
		}
	    File file = new File(filePath);
	    if(file.exists()&&file.isFile()){
	    	return file.delete();
	    }
	    return false;
	}
	
	/**
	 * save the upload file.
	 * 
	 * @param input the input stream of the uploaded file
	 * @param fileName the file name
	 * @throws IOException exception
	 */
	public static final void saveUploadFile(final InputStream input, final String fileName) throws IOException {
		if (StringUtils.isEmpty(fileName)) {
			throw new IllegalStateException("uploadDir is not setting correctly.");
		}
		org.apache.commons.io.FileUtils.copyInputStreamToFile(input, new File(fileName));
	}
	
	/**
	 * edit the upload photo graph size.
	 * 
	 * @param infile InputStream
	 * @param outfile OutputStream
	 * @param destFormat 图片定义的格式
	 * @param height 高度
	 * @param width 宽度
	 * @return flag boolean
	 * @throws Exception Exception
	 */
	public static final boolean editPhotoGraph(InputStream infile, OutputStream outfile, String destFormat, int height,
			int width) throws Exception {
		boolean flag = false;
		BufferedImage src = ImageIO.read(infile);
		if (height > 0 && width > 0) {
			height = src.getHeight() > height ? height : src.getHeight();
			width = src.getWidth() > width ? width : src.getWidth();
			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			tag.flush();
			flag = ImageIO.write(tag, destFormat, outfile); // 输出经过压缩后的图像
		} else {
			flag = ImageIO.write(src, destFormat, outfile); // 输出经过原分辨率的图像
		}
		return flag;
	}
	
	/**
	 * 判断文件是否存在，如果不存在，则创建。
	 * @param path:文件路径
	 * @return 返回已创建路径
	 */
	public static File createFile(String path){
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error("create file failed,exception message:"+e.getMessage());
			}
		}
		return file;
	}
	

    /**
     * 读取文件的内容
     * 
     * @param file
     *            想要读取的文件对象
     * @return 返回文件内容
     */
    public static String file2String(File file)
    {
        StringBuilder result = new StringBuilder();
        try {
        	FileInputStream fis = new FileInputStream(file);   
        	InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
            BufferedReader br = new BufferedReader(isr);//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            logger.error("read file fail, exception message:" + e.getMessage());
            return "";
        }
        return result.toString();
    }
    
    /**
     * 创建文件的目录，如果不存在就创建，否则不创建
     * @param pathname
     * @return
     */
    public static boolean createDir(String pathname){
    	try {
    		if(StringUtils.isEmpty(pathname)){
        		return false;
        	}
        	return new File(pathname).mkdir();
		} catch (Exception e) {
			return false;
		}
    }
    
    /**
     * 将文件名称更换
     * @param oldname
     * @param newname
     * @return
     */
    public static boolean rename(String oldname,String newname){
    	try {
    		if(StringUtils.isEmpty(oldname)||StringUtils.isEmpty(newname)){
        		return false;
        	}
        	File f = new File(oldname);
        	if(f==null||f.isDirectory()){
        		return false;
        	}
        	File d= new File(newname);
        	if(d==null||d.isDirectory()){
        		return false;
        	}
        	return f.renameTo(d);
		} catch (Exception e) {
			return false;
		}
    }
    
    /**
     * copy file
     * @param srcFile
     * @param destFile
     * @return
     */
    public static boolean copy(String srcFileStr,String destFileStr){
    	try {
    		File srcFile=new File(srcFileStr);
    		File destFile=new File(destFileStr);
			org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
			return true;
		} catch (IOException e) {
			return false;
		}
    }
}
