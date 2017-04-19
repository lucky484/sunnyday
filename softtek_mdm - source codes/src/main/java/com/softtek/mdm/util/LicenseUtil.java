package com.softtek.mdm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.softtek.mdm.model.LicenseFileObject;

import net.sf.json.JSONObject;

/**
 * 
 * Description: license公共类 date: May 23, 2016 5:34:09 PM
 *
 * @author brave.chen
 */
public class LicenseUtil {
	/**
	 * 日志记录器
	 */
	private static Logger log = Logger.getLogger(LicenseUtil.class);

	/**
	 * 获取公钥
	 *
	 * @author brave.chen
	 * @param privateActiveCode
	 *            激活码
	 * @return 公钥
	 */
	public static String getPublicKey(String activeCode) {
		if (StringUtils.isEmpty(activeCode)) {
			return "";
		} else {
			return activeCode.substring(32, activeCode.length() - 32);
		}
	}

	/**
	 * 获取机器码
	 *
	 * @author brave.chen
	 * @param publicKey
	 *            服务端提供公钥
	 * @param localPublicKey
	 *            本地公钥
	 */
	public static void generateMachine(String publicKey, String localPublicKey, String path) {
		byte[] encodedData = new byte[] {};
		String cpuOne = getCPUSerial();
		String mac = getLocalMac();
		StringBuffer buffer = new StringBuffer();
		buffer.append(StringUtils.trimToEmpty(cpuOne));
		buffer.append("|");
		buffer.append(StringUtils.trimToEmpty(mac));
		buffer.append("|");
		buffer.append(localPublicKey);
		buffer.append("|");
		buffer.append(StringUtils.trimToEmpty(publicKey));

		String code = buffer.toString();

		byte[] data = code.getBytes();
		FileOutputStream fos = null;
		OutputStream oos = null;

		try {
			String dir = path.substring(0, path.lastIndexOf(File.separator));
			File folder = new File(dir);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			oos = new FileOutputStream(file);
			encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
			oos.write(encodedData);
		} catch (Exception e) {
			log.error("something wrong when generate machine code, error message is " + e.getMessage());
		} finally {
			try {
				if (null != oos) {
					oos.close();
				}
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				log.error("close io exception, exception message is " + e.getMessage());
			}
		}
	}

	/**
	 * 获取CPU号,多CPU时,只取第一个
	 * 
	 * @return
	 */
	public static String getCPUSerial() {
		StringBuilder result = new StringBuilder();
		try {
			File file = File.createTempFile("tmp", ".vbs");
			file.deleteOnExit();
			try (FileWriter fw = new FileWriter(file)) {
				String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n"
						+ "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
						+ "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
						+ "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
						+ "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n "
						+ "    exit for  ' do the first cpu only! \r\n" + "Next                    ";

				fw.write(vbs);
			}
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String line;
				while ((line = input.readLine()) != null) {
					result.append(line);
				}
			}
			file.delete();
		} catch (Throwable e) {
			log.error("生成CPUSerial失败", e);
		}
		if (result.length() < 1) {
			log.info("无CPU_ID被读取");
		}
		return result.toString();
	}

	/**
	 * 获取机器mac地址
	 * 
	 * @author brave.chen
	 * @return mac地址
	 */
	public static String getLocalMac() {
		StringBuffer sb = new StringBuffer("");
		try {
			InetAddress ia = InetAddress.getLocalHost();
			// 获取网卡，获取地址
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// 字节转换为整数
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
		} catch (SocketException e) {
			log.error("failed get mac address！exception message is " + e.getMessage());
		} catch (UnknownHostException e) {
			log.error("failed get mac address！exception message is " + e.getMessage());
		}

		return StringUtils.lowerCase(sb.toString());
	}

	public static LicenseFileObject decodeLicneseFile(byte[] bt, String clientPrivateKey) {
		LicenseFileObject licenseFileObject = null;

		if (null != bt) {
			try {
				byte[] decodedData = RSAUtils.decryptByPrivateKey(bt, clientPrivateKey);
				String result = new String(decodedData);
				licenseFileObject = (LicenseFileObject) JSONObject.toBean(JSONObject.fromObject(result),
						LicenseFileObject.class);
			} catch (Exception e) {
				log.error("IO exception happened, and exception message is " + e.getMessage());
			}
		}

		return licenseFileObject;
	}
}
