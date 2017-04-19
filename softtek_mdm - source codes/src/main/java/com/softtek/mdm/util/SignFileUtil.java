package com.softtek.mdm.util;

import java.io.File;
import java.text.MessageFormat;
import org.apache.log4j.Logger;

/**
 * 签名工具类
 * @author jane.hui
 *
 */
public class SignFileUtil {

	private static Logger logger = Logger.getLogger(SignFileUtil.class);
	
	/**
	 * 将未签名文件的内容写入已签名的文件(用openssl签名)
	 * @param content:描述文件内容
	 * @param oldFilePath:原未签名文件
	 * @param newFilePath:已签名文件
	 * 
	 */
	public static void signFile(String content, String oldFilePath, String newFilePath){
	    // 创建未签名文件
		File unsignFile = FileUtils.createFile(CommUtil.SIGN_PATH + oldFilePath);
		// 创建已签名文件
		File signedFile = FileUtils.createFile(CommUtil.SIGN_PATH + newFilePath);
		try {
			// 将描述文件内容写入未签名文件里
			XmlUtils.writeFile(content, unsignFile);
			String signCmd = "openssl smime -sign -in {0} -out {1} -signer {2} -inkey {3} -certfile {4} -outform der -nodetach";
			String exeuCmd = MessageFormat.format(signCmd,unsignFile,signedFile,CommUtil.DOMIAN_PATH,CommUtil.KEY_PATH,CommUtil.ROOT_PATH);
			Runtime.getRuntime().exec(exeuCmd);
		} catch (Exception e) {
			logger.info("exec command fail, exception message:"+e.getMessage());
		}
	}
}
