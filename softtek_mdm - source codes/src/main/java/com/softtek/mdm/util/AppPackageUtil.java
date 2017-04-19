package com.softtek.mdm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.softtek.mdm.status.TipKey;

import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;

/**
 * 提取包摘要
 * 
 * @author color.wu
 *
 */
public class AppPackageUtil {

	/**
	 * 判断是否是ipa包
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean isIPA(String filename) {
		if ("ipa".equals(getFix(filename).toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是apk包
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean isAPK(String filename) {
		if ("apk".equals(getFix(filename).toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 从原始文件名中获取文件名，不含后缀
	 * 
	 * @param originalFilename
	 * @return
	 */
	public static String getFileName(String originalFilename) {
		if (null != StringUtils.trimToNull(originalFilename)) {
			if (originalFilename.lastIndexOf(".") >= 0) {
				return originalFilename.substring(0, originalFilename.lastIndexOf("."));
			}
			return originalFilename;
		}
		return "";
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param originalFilename
	 * @return
	 */
	public static String getFix(String originalFilename) {
		if (null != StringUtils.trimToNull(originalFilename)) {
			if (originalFilename.lastIndexOf(".") >= 0) {
				return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			}
		}
		return "";
	}

	/**
	 * 提取ipa包的信息
	 * 
	 * @return
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws ParseException 
	 * @throws PropertyListFormatException 
	 * @throws IOException 
	 */
	public static Map<String, Object> extratIPA(String pathname){
		Map<String, Object> map = new HashMap<String, Object>();
		NSString iconName=null;
		try {
			File file = getIpaInfo(new File(pathname),null);
			// 需要第三方jar包dd-plist
			NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
			// 应用包名
			NSString parameters = (NSString) rootDict.objectForKey("CFBundleIdentifier");
			map.put(TipKey.TIP_PACKAGENAME.name(), parameters.toString());
			// 应用名称
			parameters = (NSString) rootDict.objectForKey("CFBundleName");
			if(StringUtils.trimToNull(parameters.toString())==null){
				parameters = (NSString) rootDict.objectForKey("CFBundleDisplayName");
			}
			map.put(TipKey.TIP_LABEL.name(), parameters.toString());
			// 应用版本
			parameters = (NSString) rootDict.objectForKey("CFBundleVersion");
			map.put(TipKey.TIP_VERSION.name(), parameters.toString());
			
			NSDictionary dictionary=(NSDictionary) rootDict.objectForKey("CFBundleIcons");
			if(dictionary!=null){
				dictionary=(NSDictionary) dictionary.objectForKey("CFBundlePrimaryIcon");
				if(dictionary!=null){
					NSArray array=(NSArray) dictionary.objectForKey("CFBundleIconFiles");
					if(array!=null&&array.count()>0){
						iconName=(NSString) array.objectAtIndex(0);
						File iconFile= getIpaInfo(new File(pathname),iconName.toString()+"@2x");
						if(iconFile!=null){
							byte[] datas=getContent(iconFile);
							map.put(TipKey.TIP_ICON.name(), datas);
							iconFile.delete();
							iconFile.getParentFile().delete();
						}
					}
				}
			}
			// 如果有必要，应该删除解压的结果文件
			file.delete();
			file.getParentFile().delete();
			File temp=new File(String.format("%s.zip", file.getParent()));
			temp.delete();
		} catch (IOException | PropertyListFormatException | ParseException | ParserConfigurationException
				| SAXException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 将File 2 byte[]
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static byte[] getContent(File file) throws IOException{
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {  
            return null;  
        }  
		 FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead = 0;  
        while (offset < buffer.length  
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        fi.close();  
        return buffer;  
	}

	/**
	 * 提取apk包的信息
	 * 
	 * @return
	 */
	public static Map<String, Object> extratAPK(String pathname) {
		Map<String, Object> map = new HashMap<>();
		try {
			ApkParser apkParser = new ApkParser(new File(pathname));
			ApkMeta apkMeta = apkParser.getApkMeta();
			if (apkMeta != null) {
				map.put(TipKey.TIP_PACKAGENAME.name(), apkMeta.getPackageName());
				map.put(TipKey.TIP_LABEL.name(), apkMeta.getLabel());
				map.put(TipKey.TIP_VERSION.name(), apkMeta.getVersionName());
			}
			byte[] datas = apkParser.getIconFile().getData();
			map.put(TipKey.TIP_ICON.name(), datas);
			apkParser.close();
		} catch (IOException e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}

	/**
	 * 解压IPA文件，只获取IPA文件的Info.plist文件存储指定位置
	 * 
	 * @param file
	 *            zip文件
	 * @param unzipDirectory
	 *            解压到的目录
	 * @throws Exception
	 */
	private static File getZipInfo(File file, String unzipDirectory,String FileName) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		File result = null;
		File unzipFile = null;
		ZipFile zipFile = null;
		try {
			// 创建zip文件对象
			zipFile = new ZipFile(file);
			// 创建本zip文件解压目录
			String name = file.getName().substring(0, file.getName().lastIndexOf("."));
			unzipFile = new File(unzipDirectory + "/" + name);
			if (unzipFile.exists()) {
				unzipFile.delete();
			}
			unzipFile.mkdir();
			// 得到zip文件条目枚举对象
			Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
			// 定义对象
			ZipEntry entry = null;
			String entryName = null;
			String names[] = null;
			int length;
			// 循环读取条目
			while (zipEnum.hasMoreElements()) {
				// 得到当前条目
				entry = zipEnum.nextElement();
				entryName = new String(entry.getName());
				// 用/分隔条目名称
				names = entryName.split("\\/");
				length = names.length;
				for (int v = 0; v < length; v++) {
					if (entryName.endsWith((StringUtils.trimToNull(FileName)==null?".app/Info.plist":String.format(".app/%s.png", FileName)))) { // 为Info.plist文件,则输出到文件
						input = zipFile.getInputStream(entry);
						result = new File(unzipFile.getAbsolutePath() + (StringUtils.trimToNull(FileName)==null?"/Info.plist":String.format("/%s.png", FileName)));
						output = new FileOutputStream(result);
						byte[] buffer = new byte[1024 * 8];
						int readLen = 0;
						while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
							output.write(buffer, 0, readLen);
						}
						break;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.flush();
				output.close();
			}
			// 必须关流，否则文件无法删除
			if (zipFile != null) {
				zipFile.close();
			}
		}
		return result;
	}

	/**
	 * IPA文件的拷贝，把一个IPA文件复制为Zip文件,同时返回Info.plist文件 参数 oldfile 为 IPA文件
	 */
	private static File getIpaInfo(File oldfile,String FileName) throws IOException {
		try {
			int byteread = 0;
			String absPath=oldfile.getAbsolutePath();
			String filename =absPath.substring(0,absPath.lastIndexOf(".")+1)+absPath.substring(absPath.lastIndexOf(".")+1).replace("ipa", "zip") ;
			File newfile = new File(filename);
			if (oldfile.exists()) {
				// 创建一个Zip文件
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				if (inStream != null) {
					inStream.close();
				}
				if (fs != null) {
					fs.close();
				}
				// 解析Zip文件
				return getZipInfo(newfile, newfile.getParent(),FileName);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	/**
	 * ipa的manifest文件和ipa文件同名
	 * @param plitPath 模板的manifest.plist文件地址
	 * @param downloadLink ipa包的下载地址，注意：https
	 * @param builderId
	 * @param version
	 * @param name
	 * @param fileName 创建的给用户下载的manifest plist文件名称
	 * @param imgdownloadUrl 图片(名称是image.57x57.png和image.512x512.png)的下载地址 https,没有就是null
	 * @return 返回创建的manifest plist文件的存储位置
	 */
	public static String ipaManifestDownloadUri(String plitPath,String downloadLink,String builderId,String version,String name,String fileName,String imgdownloadUrl){
		try {
			NSDictionary rootDict= (NSDictionary) PropertyListParser.parse(plitPath);
			String xml=rootDict.toXMLPropertyList();
			xml=xml.replace("[software-package]", downloadLink);
			if(null!=imgdownloadUrl){
				xml=xml.replace("[display-image]", String.format("%s/image.57x57.png", imgdownloadUrl));
				xml=xml.replace("[full-size-image]", String.format("%s/image.512x512.png", imgdownloadUrl));
			}else{
				xml=xml.replace("[display-image]", "");
				xml=xml.replace("[full-size-image]", "");
			}
			xml=xml.replace("[bundle-identifier]", builderId);
			xml=xml.replace("[bundle-version]", version);
			xml=xml.replace("[title]",name);
			
			String dateDir=CommUtil.getStringFromDate(new Date(), "yyyyMMdd");
			String manifestName=dateDir+File.separator+(StringUtils.isEmpty(fileName)?UUID.randomUUID().toString():fileName)+".plist";
			String manifestPath=CommUtil.MANIFESTFILE_STORE+manifestName;
			FileUtils.writeStringToFile(new File(manifestPath), xml);
			return "itms-services://?action=download-manifest&url="+CommUtil.MANIFESTFILE_URL+manifestName.replace("\\", "/");
		} catch (ParserConfigurationException | ParseException | SAXException | PropertyListFormatException
				| IOException e) {
			e.getMessage();
			return null;
		}
	}
}
