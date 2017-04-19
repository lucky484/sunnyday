package com.softtek.mdm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.i18n.LocaleContextHolder;

import sun.misc.BASE64Decoder;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.status.DirectionStatus;

/**
 * XML操作共通类
 * @author jane.hui
 *
 */
public class XmlUtils {
	
	private static Logger logger = Logger.getLogger(XmlUtils.class);
	
	/**定义pList字符串解析正则式**/
    public static final String DATA = "\\<data>(.*?)\\</data>";
    public static final String STRING = "\\<string>(.*?)\\</string>";
    public static final String KEY = "\\<key>(.*?)\\</key>";
   
    /**
     * 获取字符串列表数据
     * @param tag(0:认证，推送  1:读取证书文件)
     * @param pattern
     * @param pList
     * @return
     */
    private static List<String> getList(String pattern,String pList,String tag){
        /**获取data列表数据**/
        List<String> dataList = new ArrayList<String>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pList);
        if(Constant.NO.equals(tag)){
	        while(m.find()) {
	        	String AwaitingConfiguration = m.group(1);
	        	if(!Constant.MobileConfig.AWAITING_CONFIGURATION.equals(AwaitingConfiguration)){
	        		dataList.add(m.group(1));
	        	}
	        }
        }
        return dataList;
    }
    
    /**
     * 获取Authenticate的pList文件Map数据
     * @param pList
     * @return
     */
    public static Map<String, String> parseAuthenticate(String pList){
    	String strBlank = replaceBlank(pList);
    	Map<String, String> plistMap = new HashMap<String, String>();
    	// 安装描述文件时候，认证
    	if(strBlank.indexOf(Constant.MESSAGE_TYPE.AUTHENTICATE)>0){
	    	/**获取key、string、data列表数据**/
	        List<String> keyList = getList(KEY,strBlank,Constant.NO);
	        List<String> stringList = getList(STRING,strBlank,Constant.NO);
	        if(stringList.size()==5){
	        	stringList = stringList.subList(0,3);
	        }
	        /**组装数据称plistMap**/
	        int stringNum = 0;
	        for(int i=0;i<keyList.size();i++){
	        	 plistMap.put(keyList.get(i), stringList.get(stringNum));stringNum++;
	        }
	      // 安装描述文件的时候，TokenUpdate
    	} else if(strBlank.indexOf(Constant.MESSAGE_TYPE.TokenUpdate)>0){
        	/**获取key、string、data列表数据**/
            List<String> keyList = getList(KEY,strBlank,Constant.NO);
            List<String> stringList = getList(STRING,strBlank,Constant.NO);
            List<String> dataList = getList(DATA,strBlank,Constant.NO);
            /**组装数据称plistMap**/
            int stringNum = 0;
            for(int i=0;i<keyList.size();i++){
                if(keyList.get(i).equals(Constant.MobileConfig.TOKEN)){
                    plistMap.put(Constant.MobileConfig.TOKEN, dataList.get(0));
                } else if(keyList.get(i).equals(Constant.MobileConfig.UNLOCK_TOKEN)){
                    plistMap.put(Constant.MobileConfig.UNLOCK_TOKEN, dataList.get(1));
                } else{
                    plistMap.put(keyList.get(i), stringList.get(stringNum));stringNum++;
                }
            }
            //卸载描述文件时
    	} else if(strBlank.indexOf(Constant.MESSAGE_TYPE.CheckOut)>0){
    		/**获取key、string、data列表数据**/
	        List<String> keyList = getList(KEY,strBlank,Constant.NO);
	        List<String> stringList = getList(STRING,strBlank,Constant.NO);
	        if(stringList.size()==5){
	        	stringList = stringList.subList(0,3);
	        }
	        /**组装数据称plistMap**/
	        int stringNum = 0;
	        for(int i=0;i<keyList.size();i++){
	        	 plistMap.put(keyList.get(i), stringList.get(stringNum));stringNum++;
	        }
    	}
        return plistMap;
    }
    
    /**
     * 获取苹果端返回服务器的状态
     * @param pList
     * @return
     */
    public static Map<String, String> parseState(String pList){
    	String strBlank = replaceBlank(pList);
    	Map<String, String> plistMap = new HashMap<String, String>();
    	/**获取key、string列表数据**/
        List<String> keyList = getList(KEY,strBlank,Constant.NO);
        List<String> stringList = getList(STRING,strBlank,Constant.NO);
        /**组装数据称plistMap**/
        int stringNum = 0;
        for(int i=0;i<keyList.size();i++){
        	 plistMap.put(keyList.get(i), stringList.get(stringNum));stringNum++;
        }
        return plistMap;
    }
    
    /**
     * 空的pList格式的文件（用户checkIn认证时候的返回）
     * @return
     */
    public static String getBlankPList(){
       StringBuffer backString = new StringBuffer();
       backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
       backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
       backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
       backString.append("<plist version=\"1.0\"><dict></dict></plist>");
       return backString.toString();
    }
    
    /**
     * 读取request中的流文件
     * @param is
     * @param contentLen
     * @return
     */
	public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
               while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen- readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                            break;
                    }
                    readLen += readLengthThisTime;
                    }
                    return message;
            } catch (IOException e) {
            	logger.error("read bytes failed,error message is:"+e.getMessage());
            }
        }
        return new byte[] {};
    } 
	
	/**
     * 将通过TokenUpdate获取的原始Token转化成16进制新的Token
     * @param OriToken
     * @return
     * @throws IOException
     */
    public static String parseToken(String OriToken) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(OriToken);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < decodedBytes.length; ++i) {
            buf.append(String.format("%02x", decodedBytes[i]));
        }
        String Token = buf.toString();
        return  Token;
    }
    
    // 替换空格
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }   

    /**
     * 向单个iPhone手机推送Lock消息
     * @param p12Path
     * @param info
     * @return
     */
    public static int singleMDMLockPush(String p12Path,TokenUpdateInfo info){
    	int pushState = 0;
    	try {
    	  ApnsService service = APNS.newService()
    			  .withCert(p12Path, CommUtil.CERTIFICATE_PASSWORD)
    			  .withProductionDestination()
    			  .build();
    	  String mdmPayload = APNS.newPayload().customField("mdm", info.getPushMagic()).build();
    	  service.push(info.getToken(),mdmPayload);
    	  pushState = 1;
    	} catch(Exception e){
		   logger.error("something wrong when push by token info id,id is "+info.getId()+"exception message is " + e.getMessage());
    	   pushState = 0;
    	}
    	return pushState;
    }
    
    /**
     * 读取苹果端推送的的xml内容
     * @param request
     * @return
     */
    public static String returnBody(HttpServletRequest request) {
		InputStream iStream=null;
		String res = Constant.EMPTY_STR;
		try {
			iStream = request.getInputStream();
	        int size = request.getContentLength();
	        byte[] reqBodyBytes = XmlUtils.readBytes(iStream, size);
	        res = new String(reqBodyBytes,"utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("something wrong when get push content, exception message is " + e.getMessage());
		} catch (IOException e) {
			logger.error("something wrong when get push content, exception message is " + e.getMessage());
		}finally {
			if(iStream!=null){
				try {
					iStream.close();
				} catch (IOException e) {
					logger.error("close inputstream error,cause:"+e.getMessage());
				}
			}
		}
        return res;
    }
   
    /**
     * 发送命令的pList格式的模板文件
     * @param command
     * @param commandUUID
     * @return
     */
    public static String getCommandPList(String command,String commandUUID){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\"><dict><key>Command</key><dict><key>RequestType</key><string>");
        backString.append(command);
        backString.append("</string></dict><key>CommandUUID</key><string>");
        backString.append(commandUUID);
        backString.append("</string></dict></plist>");
        return backString.toString();
    }
    
    /** 
     * 创建文件 
     * @param fileName s
     * @return 
     */  
    public static boolean createFile(File fileName)throws Exception{  
        try {  
           if(!fileName.exists()){  
               fileName.createNewFile(); 
               return true;  
           }  
        } catch(Exception e){  
	        logger.error("something wrong when create file, exception message is " + e.getMessage());
	        return false;
        }  
        return true;  
    } 
    
    /**
     * 将流写入文件中
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean writeFile(String content,File  fileName)throws Exception{  
       FileOutputStream o=null;  
       try {  
    	   o = new FileOutputStream(fileName);  
    	   o.write(content.getBytes("UTF-8"));  
    	   o.close();  
    	   return true;
       } catch (Exception e) {  
    	   logger.error("something wrong when write in to file, exception message is " + e.getMessage());
       } finally {
    	   if(null != o){
    		   o.close();
    	   }
	   }
       return false; 
    } 
    
    /**
     * 删除文件
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean deleteFile(File file) throws Exception{  
       try {  
    	   boolean result = file.delete();
    	   return result;
       } catch (Exception e) {  
    	   logger.error("something wrong when write in to file, exception message is " + e.getMessage());
       } 
       return false; 
    } 

    /**
     * 获取手机端要读取的描述文件
     * @param userId
     * @return
     */
    public static String createUrl(Integer userId,String deviceToken){
    	try {
    		String filePath=MDMProtocolUtils.getMobileConfigPath();
    		if(StringUtils.isEmpty(filePath)){
    			return null;
    		}
		    Document document=XMLUtil.getDefault(filePath);
    		String uuid = CommUtil.generate32UUID();
			Element identityElement = XMLUtil.findElement(document.getRootElement(), "/plist/dict/PayloadIdentifier", DirectionStatus.NEXT);
			String payloadIdentifier = identityElement.getText()+uuid;
			identityElement.setText(payloadIdentifier);
			String mobileConfig = document.asXML();
    		String checkInUrl = CommUtil.CHECK_IN_URL+"?userId="+userId+"&amp;uuid="+uuid+"&amp;deviceToken="+deviceToken.replaceAll(" ", "");
    		String serverUrl = CommUtil.SERVER_URL+"?userId="+userId+"&amp;uuid="+uuid+"&amp;deviceToken="+deviceToken.replaceAll(" ", "");
    		mobileConfig = mobileConfig.replace(CommUtil.CHECK_IN_URL, checkInUrl);
    		mobileConfig = mobileConfig.replace(CommUtil.SERVER_URL, serverUrl);
    		return mobileConfig;
		} catch (Exception e) {
			return null;
		}
    }
    
	public static Map<String,Object> parseXmlByDom4j(String plist){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Document doc;   
			doc = DocumentHelper.parseText(plist);
			List<DeviceAppInfoModel> appInfoList = new ArrayList<DeviceAppInfoModel>();
			List<Element> firstList = doc.selectNodes("/plist/dict/key");
			List<Element> list = doc.selectNodes("/plist/dict/dict/key");
			List<Element> appList = doc.selectNodes("/plist/dict/array/dict");
			int j = 0;
			int i = 0;
			int m = 0;
			for(Element ele : firstList){
			      j++;
		          Element nextEle = (Element) doc.selectSingleNode("/plist/dict/key["+j+"]/following-sibling::*");
		          if(ele.getName().equals("QueryResponses")){
		        	  map.put(ele.getText(),"");
		          }else{
		        	  map.put(ele.getText(),nextEle.getText());
		          }
			}
			if(list != null  && list.size() > 0){
				for(Element ele : list){
					i++;
					Element nextEle = (Element) doc.selectSingleNode("/plist/dict/dict/key["+i+"]/following-sibling::*");
					if(nextEle.getName().equals("true")){
						map.put(ele.getText(), nextEle.getName());
					}else if(nextEle.getName().equals("false")){
						map.put(ele.getText(), nextEle.getName());
					}else{
						map.put(ele.getText(),nextEle.getText());
					}
				}
			}
			if(appList != null && appList.size() > 0){
				for(Element ele : appList){
					m ++;
					DeviceAppInfoModel deviceAppInfo = new DeviceAppInfoModel();
					List<Element> subElementList = (List<Element>) doc.selectNodes("/plist/dict/array/dict["+m+"]/key");
					int n = 0;
					for(Element element : subElementList){
						n ++;
						Element nextEle = (Element) doc.selectSingleNode("/plist/dict/array/dict["+m+"]/key["+n+"]/following-sibling::*");
						if(element.getText().equals("Identifier")){
							deviceAppInfo.setAppid(nextEle.getText());
						}
						if(element.getText().equals("Name")){
							deviceAppInfo.setName(nextEle.getText());
						}
						if(element.getText().equals("ShortVersion")){
							deviceAppInfo.setApp_version(nextEle.getText());
						}
						if(element.getText().equals("IsValidated")){
							String appStatic = "";
							if(nextEle.getName().equals("true")){
								appStatic = InternationalizationUtil.getMessage("web.institution.dpt.islock.false.label", null,"",LocaleContextHolder.getLocale());
							}else{
							    appStatic = InternationalizationUtil.getMessage("tiles.views.institution.ios.app.status.exception", null,"",LocaleContextHolder.getLocale());
							}
							deviceAppInfo.setApp_status(appStatic);
						}
					}
					if(deviceAppInfo != null){
						appInfoList.add(deviceAppInfo);
					}
				}
			}
			map.put("appInfoList", appInfoList);
		} catch (DocumentException e) {
		}   
		return map;
	}
}
