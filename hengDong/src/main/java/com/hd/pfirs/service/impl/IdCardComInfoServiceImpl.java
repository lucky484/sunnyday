package com.hd.pfirs.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.controller.IdCardInfoController;
import com.hd.pfirs.dao.IdCardComResultDao;
import com.hd.pfirs.model.IdCardComModel;
import com.hd.pfirs.model.IdCardComPoliceModel;
import com.hd.pfirs.service.FugitivesService;
import com.hd.pfirs.service.IdCardComInfoService;

/**
 * 
 * @author ligang.yang
 *
 */
@Service("idCardComJob")
public class IdCardComInfoServiceImpl implements IdCardComInfoService {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IdCardInfoController.class);

	@Autowired
	private IdCardComResultDao idCardComResultDao;

	@Autowired
	private FugitivesService fuServ;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendIdCardInfo2Com() {

		// 查一条记录
		Map<String, Object> resultMap = idCardComResultDao.getIdCardComMap();
		logger.debug("--------------------------------------" + resultMap + "------------------------------------");
		if (resultMap == null)
			return;
		String cardno = (String) resultMap.get("COLLECTIDCARDNO");
		BigDecimal bigcomid = (BigDecimal) resultMap.get("CARDCOMPRLTID");
		if (StringUtils.isBlank(cardno) || bigcomid == null)
			return;
		// 得到疾控库id
		String comid = bigcomid.toString();
		List<IdCardComPoliceModel> ctls = fuServ.comAtCtlLibs(cardno);
		// 新建model更新数据库
		IdCardComModel model = new IdCardComModel();
		model.setRelayTimeStamp(String.valueOf(System.currentTimeMillis()));
		model.setCardComprltId(Long.valueOf(comid));

		if (ctls == null || ctls.isEmpty() || ctls.get(0) == null) {
			model.setIsControlled("0");
			model.setFlag("1");
			idCardComResultDao.updateIdCardComModel(model);
			logger.debug("--------------------------------------" + model + "------------------------------------");
			return;
		} else {
			IdCardComPoliceModel ctl = ctls.get(0);
			model.setCtrlBaseID(ctl.getCtlId());
			model.setCompareBaseID(ctl.getTableName());
			model.setIsControlled("1");
			model.setFlag("0");
			logger.debug("--------------------------------------" + model + "------------------------------------");
			idCardComResultDao.updateIdCardComModel(model);
		}
	}

}
