package com.softtek.mdm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
/**
 * 将指定文件夹下的所有文件压缩成tar.gz的压缩包
 * @author Jackson.zhu
 * @version 1.0
 *
 */
public class TarAndGzipUtil {
	private static TarAndGzipUtil util;
	private static final Logger logger = Logger.getLogger(TarAndGzipUtil.class);
	private TarAndGzipUtil(){}
	
	public static TarAndGzipUtil getInstance() {
		if(util==null){
			util = new TarAndGzipUtil();
		} 
		return util;
	}
	//打包成tar文件
	public void tarFile(String path) {
		//自动将路径的最后一个路径作为文件名
		String tarFile = path+".tar";
		tarFile(path,tarFile);
	}
	
	public void tarFile(String path,String tarFile) {
		TarArchiveOutputStream taos = null;
		try {
			File f = new File(path);
			int len = f.getParent().length();
			taos = new TarArchiveOutputStream(new FileOutputStream(tarFile));
			taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
			tarFile(new File(path),taos,len);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(taos!=null){
					taos.close();
				} 
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		gzipFile(new File(tarFile));
	}
	//将tar文件解压缩
	public void unTarFile(File file,String path) {
		TarArchiveInputStream tais = null;
		File tf = null;
		try {
			tf = unGzipFile(file);
			tais = new TarArchiveInputStream(new FileInputStream(tf));
			TarArchiveEntry tae = null;
			while((tae=tais.getNextTarEntry())!=null) {
				if(!tae.isDirectory()) {
					String name = path+File.separator+tae.getName();
					FileOutputStream fos = null;
					File ff = new File(name);
					if(!ff.getParentFile().exists()){
						ff.getParentFile().mkdirs();
					} 
					try {
						fos = new FileOutputStream(ff);
						IOUtils.copy(tais, fos);
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						if(fos!=null) fos.close();
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(tais!=null){
					 tais.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			if(tf!=null) {
				tf.delete();
				tf.deleteOnExit();
			}
		}
	}
	//将gz文件解压缩
	public File unGzipFile(File file) {
		GZIPInputStream gis = null;
		FileOutputStream fos = null;
		try {
			gis = new GZIPInputStream(new FileInputStream(file));
			String path = file.getAbsolutePath();
			path = path.substring(0,path.lastIndexOf("."));
			//要返回的文件
			File f = new File(path);
			fos = new FileOutputStream(f);
			IOUtils.copy(gis, fos);
			return f;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(gis!=null){
					gis.close();
				} 
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			try {
				if(fos!=null){
					 fos.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
		}
		return null;
	}
	//将文件压缩成gz文件
	public void gzipFile(File file) {
		GZIPOutputStream gos = null;
		FileInputStream fis = null;
		try {
			gos = new GZIPOutputStream(new FileOutputStream(file.getAbsolutePath()+".gz"));
			fis = new FileInputStream(file);
			IOUtils.copy(fis, gos);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(gos!=null){
					gos.close();
				} 
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			try {
				if(fis!=null){
					 fis.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			file.delete();
		}
	}
	//循环遍历指定目录下的文件，并生成指定名称的tar文件
	private void tarFile(File file, TarArchiveOutputStream taos,int len) {
		if(file.isDirectory()) {
			File[] fs = file.listFiles();
			if(fs!=null && fs.length>0){
				for(File f:fs) {
					tarFile(f,taos,len);
				}
			}
		} else {
			FileInputStream fis = null;
			try {
				TarArchiveEntry tae = new TarArchiveEntry(file.getParent().substring(len)+File.separator+file.getName());
				tae.setSize(file.length());
				fis = new FileInputStream(file);
				taos.putArchiveEntry(tae);
				IOUtils.copy(fis, taos);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			} finally {
				try {
					if(fis!=null){
						fis.close();	
					} 
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				try {
					taos.closeArchiveEntry();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
	
}
