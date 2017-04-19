package com.softtek.mdm.abs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.thread.NotifyApnsServerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.web.http.BaseDTO;

/**
 * IOS通知抽象类
 * 
 * @author brave.chen
 *
 */
public abstract class AbstractIosPush {
	/**
	 * 日志记录对象
	 */
	private static Logger logger = Logger.getLogger(AbstractIosPush.class);

	/**
	 * 发送指令dao
	 */
	@Autowired
	private CommandDao commandDao;

	/**
	 * Spring线程池
	 */
	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * token信息dao
	 */
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;

	/**
	 * 通知苹果apns服务器
	 * 
	 * @param map
	 *            参数map
	 * @return 通知结果
	 */
	public String nofity(Map<String, Object> map) {
		try {
			/*
			 * 通知前的准备tokenList对象,
			 * 这儿的prepareTokenUpdateInfos方法需要各自去实现返回tokeUpdateInfo列表，处理过程中
			 * 需要将指令入库
			 */
			List<TokenUpdateInfo> tokenUpdateInfoList = prepareTokenUpdateInfos(map);

			// 若获取的token对象为空表示所选的手机等设备未认证mdm应用
			if (CollectionUtils.isEmpty(tokenUpdateInfoList)) {
				logger.error("Nofity apns failed, because the user's mobile is not authenticated!");
				return BaseDTO.SUCCESS;
			}

			// 通知apns 服务器
			iosNotify(tokenUpdateInfoList);

			// 通知完成的后续操作
			afterNotify(map);
		} catch (Exception e) {
			logger.error("Someting error, error message is " + e.getMessage());
			return BaseDTO.ERROR;
		}
		return BaseDTO.SUCCESS;
	}

	/**
	 * 消息推送前的准备工作返回token对象列表（子类必须实现该方法，方法中必须包含包成Command指令的代码块，并返回需要通知apns的
	 * tokenUpdateInfo对象列表）
	 * 
	 * @param map
	 *            参数map
	 * @return token更新对象列表
	 */
	public abstract List<TokenUpdateInfo> prepareTokenUpdateInfos(Map<String, Object> map);

	/**
	 * 通知完成后的操作
	 * 
	 * @param map
	 *            传入参数对象
	 */
	public void afterNotify(Map<String, Object> map) {
		// 默认为空方法，子类需要作操作的请重写该方法
	}

	/**
	 * 获取 token对象信息
	 * 
	 * @param udid
	 *            手机udids
	 * @return token对象信息
	 */
	public List<TokenUpdateInfo> getTokenUpdateInfos(List<String> udids) {
		List<TokenUpdateInfo> tokenUpdateInfoList = new ArrayList<TokenUpdateInfo>();
		if (CollectionUtils.isNotEmpty(udids)) {
			tokenUpdateInfoList = tokenUpdateInfoDao.selectTokenUpdateInfos(udids);
		}
		return tokenUpdateInfoList;
	}

	/**
	 * 通知ios apns
	 * 
	 * @param udid
	 *            手机唯一标志
	 */
	public void iosNotify(List<TokenUpdateInfo> tokenUpdateInfos) {
		// 获取p12格式的MDM推送证书路径。
		String pemPath = CommUtil.CERTIFICATE_PATH;
		if (CollectionUtils.isNotEmpty(tokenUpdateInfos)) {
			for (TokenUpdateInfo tokenUpdateInfo : tokenUpdateInfos) {
				NotifyApnsServerThread thread = new NotifyApnsServerThread(pemPath, tokenUpdateInfo);
				taskExecutor.execute(thread);
			}
		}
	}

	/**
	 * 插入或者更新指令
	 * 
	 * @param command
	 *            指令对象
	 */
	public void mergeCommand(Command command) {
		if (null != command) {
			commandDao.mergeIntoCommand(command);
		}
	}
}
