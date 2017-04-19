package com.softtek.mdm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


/**
 * 公共类
 * @author jing.liu
 *
 */
public class CommUtil {
	
	/**
	 * 日志
	 */
	private static Logger commUtillogger = Logger.getLogger(CommUtil.class);
	
	/**
	 * 设置密钥
	 */
	private static String securityKey="JXSbFfXYHr8mm6gHpjf4nZ5SIjEycRyY";
	
    private static int[] ls = new int[3000];
    
    private static int li = 0;
    
	/**
	 * apk文件保存路径
	 */
    public static String FILE_SAVE_PATH = "";
    
    /**
     * apk icon图标文件保存路径
     */
    public static String APP_ICON_PATH = "";
    
    /**
     * 显示apk icon访问路径
     */
    public static String DISPLAY_ICON_Path = "";
     
    /**
     * 显示apk访问路径
     */
    public static String DISPLAY_APP_PATH = "";

    public static String DISPLAY_LOGO_PATH = ""; 
    /**
     * aapt路径
     */
    public static String AAPT_PATH = "";
    
    public static String APP_NEW_PATH = "";
    
    public static String DOWNLOAD_URL = "";
    
    public static String IMAGE_URL = "";
    
    /**
     * 上传apk临时目录路径
     */
    public static String TEMP_PATH = "";
    
    public static String LOGO_PATH = "";
    
    /**
     * p12证书路径 
     */
    public static String CERTIFICATE_PATH = "";
    
    /**
     * p12读取证书路径公钥解密密码
     */
    public static String CERTIFICATE_PASSWORD;
    
    /**
     * 模板描述文件路径
     */
    public static String TEMPLATE_MOBIILECONFIG_PATH;
    
    /**
     * 临时存放证书文件
     */
    public static String TEMP_CERT_PATH;
    
    /**
     * 访问证书路径
     */
    public static String DISPLAY_CERT_PATH;
    
    public static String CHECK_IN_URL;
    
    public static String SERVER_URL;
    
    public static String PUSH_CERT_PATH = "";
    
    public static String PUSH_CERT_PATH_PASSWORD = "";
    
    public static String ROOT_PATH = "";
    
    public static String DOMIAN_PATH = "";
    
    public static String KEY_PATH = "";
    
    public static String SIGN_PATH = "";
    
    public static String ROOT_HTTP_PATH = "";
    
    public static String MANIFESTFILE_STORE="";
    
    public static String MANIFESTFILE_URL="";
    
    static {
        try(InputStream resourceAsStream = CommUtil.class.getClassLoader().getResourceAsStream("file.properties")) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            String fileServer=properties.getProperty("FILESERVER");
            String fileStore=properties.getProperty("FILESTORE");
            String fileServerSSL=properties.getProperty("FILESERVER_SSL");
            MANIFESTFILE_STORE=String.format("%s/file/", fileStore);
            MANIFESTFILE_URL=String.format("%s/file/", fileServerSSL);
            TEMP_CERT_PATH=String.format("%s/certificate/temp/", fileStore);
            FILE_SAVE_PATH = String.format("%s/file/application/", fileStore);
            APP_ICON_PATH = String.format("%s/icon/", fileStore);
            DISPLAY_APP_PATH = String.format("%s/file/application/", fileServer);
            DISPLAY_ICON_Path = String.format("%s/icon/", fileServer);
            DISPLAY_LOGO_PATH = String.format("%s/logo/", fileServer);
            AAPT_PATH = String.format("%s/", properties.getProperty("AAPTPATH"));  
            APP_NEW_PATH = String.format("%s/file/", fileStore);
            DOWNLOAD_URL = String.format("%s/file/", fileServer);
            IMAGE_URL = String.format("%s/file/", fileServer);
            TEMP_PATH = String.format("%s/file/temp/", fileStore);
            LOGO_PATH = String.format("%s/logo/", fileStore);
            CERTIFICATE_PATH = String.format("%s/certificate/akCertificates.p12", fileStore);
            CERTIFICATE_PASSWORD = properties.getProperty("CERTIFICATEPASSWORD");
            TEMPLATE_MOBIILECONFIG_PATH = String.format("%s/certificate/common.mobileconfig", fileStore);
            DISPLAY_CERT_PATH = String.format("%s/certificate/temp/", fileServer);
            CHECK_IN_URL = properties.getProperty("CHECKINURL");
            SERVER_URL = properties.getProperty("SERVERURL");
            PUSH_CERT_PATH = String.format("%s/certificate/appCertificates.p12", fileStore);
            PUSH_CERT_PATH_PASSWORD = properties.getProperty("PUSHCERTPATHPASSWORD");
            ROOT_PATH = String.format("%s/certificate/1_root_bundle.crt", fileStore);
            DOMIAN_PATH = String.format("%s/certificate/1_yx.msofttek.com_bundle.crt", fileStore);
            KEY_PATH = String.format("%s/certificate/2_yx.msofttek.com.key", fileStore);
            SIGN_PATH = String.format("%s/", fileStore);;
            ROOT_HTTP_PATH = String.format("%s/", fileServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getPK(){
    	int lo = getpk();
    	for(int i=0;i<3000;i++){
    		int lt = ls[i];
    		if(lt == lo){
    			lo = getPK();
    			break; 
    		}
    	}
    	ls[li] = lo;
    	li++;
    	if(li == 3000){
    		li = 0;
    	}
    	return lo;
    }
    
    /**
     * 使用md5对str进行加密
     * @param str 需要加密的明文
     * @return 不加盐加密之后之后的密文
     */
    public static String md5PasswordEncoderEncode(String str){
    	Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		return md5PasswordEncoder.encodePassword(str, null);
    }
	
	/**
	 * 生成token
	 * @param username
	 * @param pwd
	 * @return
	 */
	public static String generateToken(String username,String pwd,String orgId){
		Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
		String password=md5PasswordEncoder.encodePassword(username, pwd);
		String token=md5PasswordEncoder.encodePassword(password, orgId);
		return md5PasswordEncoder.encodePassword(token, CommUtil.securityKey);
	}
	
	
	/**
	 * 获取客户端ip（包括代理和反向代理或多次反向代理模式）
	 * @param request
	 * @return
	 */
	 public static String getIp(HttpServletRequest request) {
		 String ipAddress = request.getHeader("X-Forwarded-For");
		 if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			 ipAddress = request.getHeader("Proxy-Client-IP");
		 }
		 if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			 ipAddress = request.getHeader("WL-Proxy-Client-IP");  
		 }
		 if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			 ipAddress = request.getRemoteAddr();
			 if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
				 InetAddress res = null;
				 try {
					res = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}  
				 ipAddress= res.getHostAddress();
			 }
		 }
		 if(ipAddress!=null && ipAddress.length()>15){
			 if(ipAddress.indexOf(",")>0){
				 ipAddress = ipAddress.substring(0,ipAddress.indexOf(",")); 
			 }
		 }
		 return ipAddress;
	 }
	
	 
    /**
     * 设置主键  
     * @return
     */
	public static Integer getPrimaryKey() {
		
		Long time = new Date().getTime();
		String time1 = String.valueOf(time);
		String time2 = time1.substring(time1.length() - 9, time1.length());
		return Integer.parseInt(time2);
	}
	
	
	/**
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isNULL(Long val) {
		if (val == null || val == 0l) {
			return true;
		}
		return false;
	}
	
	/**
	 * 字符串空判断
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return StringUtils.isEmpty(param);
	}
	
	/**
	 * 是否返回数字
	 * @param param
	 * @return
	 */
	public static boolean isNumber(String param) {
		return NumberUtils.isNumber(param);
	}
	
	public static String generate32UUID()
	{
		return UUID.randomUUID().toString().trim().replaceAll("-", "");    
	}
	
    public static String longToDate(){
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
    
    @SuppressWarnings("finally")
	public static Date stringToDate(String timeStr)
    {
    	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = null;
    	try
		{
    		date = sd.parse(timeStr);
		}
		catch (ParseException e)
		{
			commUtillogger.error("Timer Parse error, time is :" + timeStr);
			e.printStackTrace();
		}
    	finally
    	{
    		return date;
    	}
    }	
    public static int getpk(){
    	String b = (String.valueOf(Math.random())).substring(2,8);
    	String c = String.valueOf(System.currentTimeMillis());
    	String d = c.substring(c.length() - 9,c.length());
    	return Integer.valueOf(d)+Integer.valueOf(b);
    }
    
    /**
     * 生成图片名称
     * @return
     */
    public static String getPicName(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    	String datestr = sdf.format(new Date());
    	datestr += getRandomNum();
    	return datestr;
    }
    
    /**
     * 生成两位随机数
     */
    public static String getRandomNum(){
    	Random rand = new Random();
    	int num = rand.nextInt(999);
    	String numStr = String.valueOf(num);
    	if(numStr.length()!=3){
    		return getRandomNum();
    	} 
    	return numStr;
    }
    //joda time获取当前的时间
    public static String getCurrentDay(){
    	DateTime dt = DateTime.now();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
    	return dt.toString(fmt);
    }
    
    //joda time获取当周第一天的时间
    public static String getFirstOfWeek(){
    	DateTime dt = DateTime.now();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dt.dayOfWeek().withMinimumValue().toString(fmt);
    }
    //joda time获取当周最后一天的时间
    public static String getLastOfWeek(){
    	DateTime dt = DateTime.now();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dt.dayOfWeek().withMaximumValue().toString(fmt);
    }
    //joda time获取当月的第一天时间
    public static String getFirstOfMonth(){
    	DateTime dt = DateTime.now();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
    	return dt.dayOfMonth().withMinimumValue().toString(fmt);
    }
    //joda time获取当月的最后一天时间
    public static String getLastOfMonth(){
    	DateTime dt = DateTime.now();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
    	return dt.dayOfMonth().withMaximumValue().toString(fmt);
    }
    
    public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook){
    	// 设置字体  
        HSSFFont font = workbook.createFont();  
        //设置字体大小  
        font.setFontHeightInPoints((short)11);  
        //字体加粗  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //设置字体名字   
        font.setFontName("Courier New");  
        //设置样式;   
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置底边框;   
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        //设置底边框颜色;    
        style.setBottomBorderColor(HSSFColor.BLACK.index);  
        //设置左边框;     
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        //设置左边框颜色;   
        style.setLeftBorderColor(HSSFColor.BLACK.index);  
        //设置右边框;   
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        //设置右边框颜色;   
        style.setRightBorderColor(HSSFColor.BLACK.index);  
        //设置顶边框;   
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        //设置顶边框颜色;    
        style.setTopBorderColor(HSSFColor.BLACK.index);  
        //在样式用应用设置的字体;    
        style.setFont(font);  
        //设置自动换行;   
        style.setWrapText(false);  
        //设置水平对齐的样式为居中对齐;    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //设置垂直对齐的样式为居中对齐;   
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        return style;  
    }
    
    public static HSSFCellStyle getStyle(HSSFWorkbook workbook){
    	// 设置字体  
        HSSFFont font = workbook.createFont();  
        //设置字体大小  
        //font.setFontHeightInPoints((short)10);  
        //字体加粗  
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //设置字体名字   
        font.setFontName("Courier New");  
        //设置样式;   
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置底边框;   
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        //设置底边框颜色;    
        style.setBottomBorderColor(HSSFColor.BLACK.index);  
        //设置左边框;     
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        //设置左边框颜色;   
        style.setLeftBorderColor(HSSFColor.BLACK.index);  
        //设置右边框;   
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        //设置右边框颜色;   
        style.setRightBorderColor(HSSFColor.BLACK.index);  
        //设置顶边框;   
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        //设置顶边框颜色;    
        style.setTopBorderColor(HSSFColor.BLACK.index);  
        //在样式用应用设置的字体;    
        style.setFont(font);  
        //设置自动换行;   
        style.setWrapText(false);  
        //设置水平对齐的样式为居中对齐;    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //设置垂直对齐的样式为居中对齐;   
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        return style;  
    }
    public static String Date2String(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(date);
    }
    /**
     * 读取文件的大小
     * @throws IOException 
     */
	public static String getFileSize(String path) throws IOException{
    	Long size = 0L;
    	String sizeString = "";
    	if(path != ""){
    		File file = new File(path);
    		FileInputStream  fis = null;
    		fis = new FileInputStream(file);
    		size = (long) fis.available();
    	    sizeString = FormatFileSize(size);
    	    fis.close();
    	}
    	return sizeString;
    }
    
    /**
     * 换算文件的大小
     * @param size
     * @return
     */
    public static String FormatFileSize(Long size){
    	DecimalFormat format = new DecimalFormat("#.00");
    	String fileSizeString = "";
    	if(size < 1024){
    		 fileSizeString = format.format((double) size) + "B";
    	}else if(size < (1048576)){
    		 fileSizeString = format.format((double) size / 1024) + "K";
    	}else if(size < (1073741824)){
    		fileSizeString = format.format((double) size / (1048576)) + "M";
    	}else{
    		fileSizeString = format.format((double) size / (1073741824)) +"G";
    	}
    	return fileSizeString;
    }
    
    /**
     * 比较两个list是否相等
     */
    public static <T extends Comparable<T>> boolean compare(List<T> list1,List<T> list2){
    	if(list1.size() != list2.size())
    		 return false;
    		 Collections.sort(list1);
    		 Collections.sort(list2);
    		 for(int i=0;i<list1.size();i++){
    		        if(!list1.get(i).equals(list2.get(i)))
    		            return false;
    		    }
    		    return true;
    	} 
    
    /**
     * 比较两个版本号是否相等
     * @return
     */
    public static boolean compareArray(String oldVersion,String newVersion){
    	return oldVersion.compareTo(newVersion)==0?true:false;
    }
    
    /**
     * 
     * @param sourcePath:原路径图片
     * @param destPath：目标路径图片
     */
    public static boolean copyFiles(String sourcePath,String destPath){
    	File oldFile = new File(sourcePath);
    	File newFile = new File(destPath);
        oldFile.renameTo(newFile);
    	return oldFile.delete();
    	
    }
    
    /**
     * 将源文件拷贝到目标路径
     * @param srcPath
     * @param targetPath
     * @throws IOException
     */
    public static void copy(String srcPath,String targetPath) throws IOException{
    	File srcFile = new File(srcPath);
        File targetFolder = new File(targetPath);
        InputStream ins = null;
        OutputStream ots = null;
        ins = new FileInputStream(srcFile);
        ots = new FileOutputStream(targetFolder);
        int reader = -1;
        byte[] readByte = new byte[1024];
        while((reader=ins.read(readByte))!=-1){
            ots.write(readByte,0,reader);
        }
        if(ots!=null){
            ots.close();
        }
        if(ins!=null){
            ins.close();
        }
    }
    
    public static String getStringFromDate(Date date, String formatStr)
	{
		DateTime dt = new DateTime(date);
		return dt.toString(formatStr);
	}
}
