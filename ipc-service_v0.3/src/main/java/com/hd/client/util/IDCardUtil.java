package com.hd.client.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hd.client.service.impl.IdCardReadTaskImpl;

/**
 * 身份证验证的帮助类
 * 
 * @author Administrator
 *
 */
@Component
@Scope("prototype")
public class IDCardUtil {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IDCardUtil.class);

	/**
	 * 执行成功状态吗
	 */
	public static final int OK_EXECUTE = 0;
	// /**
	// * 身份证照片存放路径前缀
	// */
	// public static final String PATH_SUFFER = "./image/";
	/**
	 * 身份证照片存放路径后缀
	 */
	public static final String PATH_POSTFIX = ".bmp";
	/**
	 * IDCardReader读取状态
	 */
	public static String IDCARD_READ_BUTTON_STATE = "1";

	/**
	 * IDCardReader读取模式切换1
	 */
	public static final String IDCARD_READ_BUTTON_MODE1 = "1";

	/**
	 * IDCardReader读取模式切换2
	 */
	public static final String IDCARD_READ_BUTTON_MODE2 = "2";

	/**
	 * .dll文件的编码格式
	 */
	public static final String C_ENCODING_WAY = "GBK";

	/**
	 * 循环读身份证的毫秒数
	 */
	@Value("${milliseconds_for_idcard_read_unit}")
	private int MILLISECONDS_FOR_IDCARD_READ_UNIT;

	/**
	 * 初始化端口号为1001
	 */
	public static final int IDCARDREADER_PORT = 1001;

	/**
	 * 身份证读取的实现
	 */
	@Autowired
	private IdCardReadTaskImpl idCardReadTaskImpl;

	/**
	 * 读卡器读取的线程池
	 */
	public static final ScheduledExecutorService serviceReader = Executors.newSingleThreadScheduledExecutor();

	/**
	 * 将读取的身份证信息存放到bean中
	 * 
	 * @return IDCard
	 */
	public void cyclePrintIDcardInfo() {
		// 设置.dll编码类型
		System.setProperty("jna.encoding", C_ENCODING_WAY);
		logger.info("-------------------------身份证读卡设备初始化---------------------------");
		// 300ms读取数据
		serviceReader.scheduleAtFixedRate(idCardReadTaskImpl, 0, MILLISECONDS_FOR_IDCARD_READ_UNIT,
				TimeUnit.MICROSECONDS);
		logger.info("-------------------------身份证读卡设备初始化成功---------------------------");
	}

	/**
	 * @Description: 读卡设备开启
	 * @return: void
	 */
	public void openServiceReader() {
		idCardReadTaskImpl.SERVICE_STATE_OFF = false;
	}

	/**
	 * @Description: 读卡设备关闭
	 * @return: void
	 */
	public void closeServiceReader() {
		idCardReadTaskImpl.SERVICE_STATE_OFF = true;
	}

	/**
	 * 检测身份证设备是否连接好
	 * 
	 * @return boolean
	 */
	public boolean checkIdcardReaderOk() {
		boolean isOk = false;
		try {
			isOk = IdCardReadTaskImpl.OK == 0;
		} catch (Exception e) {
			logger.error("idcard dll path is error!");
		}
		return isOk;
	}

}
