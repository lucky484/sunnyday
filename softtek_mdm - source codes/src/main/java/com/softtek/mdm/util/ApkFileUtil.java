package com.softtek.mdm.util;

import com.softtek.apkUtil.utils.IconUtil;
import java.io.File;
import org.apache.log4j.Logger;
import com.softtek.apkUtil.entity.ApkInfo;
import com.softtek.apkUtil.utils.ApkUtil;

/**
 * 获取APK文件信息
 * @author jane.hui
 *
 */
public class ApkFileUtil {

	private static Logger logger = Logger.getLogger(ApkFileUtil.class);
	
	/**
	 * 获取APK文件信息
	 * @param apkPath
	 * @return
	 */
	public static ApkInfo ApkInfo(String apkPath) {
		ApkInfo apkInfo = null;
		try {
			apkInfo = new ApkUtil().getApkInfo(apkPath,CommUtil.AAPT_PATH);
			// 获取Icon并保存到指定位置
			String picName = CommUtil.getPicName()+Constant.EXT_NAME;
			apkInfo.setSignatureCertificate(IconUtil.getPublicKey(apkPath));
			String saveIconPath = CommUtil.APP_ICON_PATH+Constant.getModule.APPLICATION+Constant.SplitSymbol.SLASH;
			File file = new File(saveIconPath);
			if(!file.exists()&&!file.isDirectory()){
				// 创建APK ICON图标路径
				file.mkdir();
			}
			IconUtil.extractFileFromApk(apkPath,apkInfo.getApplicationIcon(), saveIconPath+picName);
			apkInfo.setApplicationIcon(picName);
		} catch (Exception e) {
			logger.error("get apk file info failed,error message is:"+e.getMessage());
		}
		return apkInfo;
	}
}
