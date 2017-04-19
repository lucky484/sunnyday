package com.hd.client.job;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hd.client.model.IPCInfoModel;
import com.hd.client.model.ParamSet;
import com.hd.client.service.ClientPostService;
import com.hd.client.service.FaceInfoService;
import com.hd.client.service.IDInfoService;
import com.hd.client.service.IPCInfoService;
import com.hd.client.service.ParamSetService;
import com.hd.client.util.SystemRuntimeUtil;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 采集设备的本机相关信息的存储与转发
 * 
 * @author ligang.yang
 *
 */
@Service("ipcInfoJob")
@Scope("prototype")
public class IPCInfoJob {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IPCInfoJob.class);

	/**
	 * 采集设备信息服务
	 */
	@Autowired
	private IPCInfoService ipcService;

	@Autowired
	private FaceInfoService faceServ;

	@Autowired
	private IDInfoService idServ;

	@Autowired
	private ParamSetService paramServ;

	/**
	 * 临时的id存放
	 */
	private static Integer tempId;

	/**
	 * 保存服务
	 */
	public void cycleSaveIPCInfo() {

		String bit = null;
		String diskSpace = null;
		// 获得内存使用率
		try {
			bit = SystemRuntimeUtil.getMemoryBit().setScale(1, BigDecimal.ROUND_HALF_UP).toString();
			diskSpace = SystemRuntimeUtil.getDiskSpace();
		} catch (SigarException e) {
			logger.error(e);
		}
		String faceConnState = null;
		// TODO 人脸连接状态

		String iDCardConnState = null;
		if (ipcService.IdCardDevOK()) {
			iDCardConnState = "正常";
		} else {
			iDCardConnState = "异常";
		}

		Map<String, Object> faceCountMap = faceServ.countFaceInfo();
		Map<String, Object> idCountMap = idServ.countIdCardInfo();
		Integer faceCollectNum = Integer.valueOf(faceCountMap.get("totalCollect").toString());
		Integer daceRelayNum = Integer.valueOf(faceCountMap.get("hasPushed").toString());
		Integer idCardCollectNum = Integer.valueOf(idCountMap.get("totalCollect").toString());
		Integer idCardRelayNum = Integer.valueOf(idCountMap.get("hasPushed").toString());
		String relayTimeStamp = String.valueOf(System.currentTimeMillis());
		Map<String, Object> dbsizeMap = paramServ.countDBSize();
		ParamSet params = paramServ.getParamSet();
		String deviceCode = params.getDeviceCode();
		String remark = params.getRemarkDesc();
		Integer dbSize = Integer.valueOf(dbsizeMap.get("dbsize").toString()) / (1024 * 1024);

		IPCInfoModel model = new IPCInfoModel(deviceCode, faceConnState, iDCardConnState, bit, diskSpace, dbSize,
				faceCollectNum, daceRelayNum, idCardCollectNum, idCardRelayNum, relayTimeStamp, /* 转发状态为未转发 */"0",
				remark, /* 删除状态为未删除 */"0", deviceCode, deviceCode);
		ipcService.saveIPCInfo(model);
	}

	/**
	 * 转发服务
	 */
	public void cyclePushIPCInfo() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://localhost:8080/hengdong").build();
		ClientPostService service = restAdapter.create(ClientPostService.class);
		Map<String, Object> paramsMap = ipcService.getIPCInfo();
		/**
		 * 先清空id
		 */
		tempId = null;
		if (paramsMap == null || paramsMap.isEmpty() || (tempId = (Integer) paramsMap.get("MonitID")) == null) {
			return;
		}

		service.sendRow(paramsMap, new Callback<String>() {

			@Override
			public void success(String t, Response response) {
				ipcService.updateIPCInfo(String.valueOf(tempId), t);
				logger.info("successPushIPCJob======" + t);
			}

			@Override
			public void failure(RetrofitError error) {
				ipcService.updateIPCInfo(String.valueOf(tempId), "-1");
				logger.error("failurePushIPCJob======" + error.getMessage());
			}
		});
	}

}
