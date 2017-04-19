package com.hd.client.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.dao.IPCInfoDao;
import com.hd.client.model.IPCInfoModel;
import com.hd.client.service.IPCInfoService;
import com.hd.client.util.IDCardUtil;

/**
 * 保存本地设备信息到数据库
 * 
 * @author ligang.yang
 *
 */
@Service
public class IPCInfoServiceImpl implements IPCInfoService {
	/**
	 * 身份证读卡器工具类
	 */
	@Autowired
	private IDCardUtil idCardUtil;

	/**
	 * 保存采集设备设备信息
	 */
	@Autowired
	private IPCInfoDao dao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean IdCardDevOK() {
		return idCardUtil.checkIdcardReaderOk();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean FaceDevOK() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveIPCInfo(IPCInfoModel model) {
		System.out.println(model);
		dao.saveIPCInfo(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getIPCInfo() {
		return dao.getAndUpdateIPCInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateIPCInfo(String MonitID, String relayFlag) {
		dao.updateIPInfo(MonitID, relayFlag);
	}

}
