package com.f2b2c.eco.service.platform.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FDiffenceDao;
import com.f2b2c.eco.dao.platform.FImgDao;
import com.f2b2c.eco.dao.platform.FSalesOrderDao;
import com.f2b2c.eco.model.platform.FDiffenceModel;
import com.f2b2c.eco.model.platform.FImgModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.service.platform.FDiffenceService;
import com.f2b2c.eco.util.CommonUtil;

@Service
public class FDiffenceServiceImpl implements FDiffenceService{
    
	@Autowired
	private FDiffenceDao fDiffenceDao;
	
	@Autowired
	private FImgDao fImgDao;
	
	@Autowired
	private FSalesOrderDao fSalesOrderDao;
	
	@Override
	public int insert(FDiffenceModel fDiffence,List<String> list) {
		int result = fDiffenceDao.insert(fDiffence);
		if(result == 1){
			if(fDiffence.getDiffenceType() == 0){
				//由于补的差价没有审核流程，所以在插入补差价的数据时，跟新订单的实际金额
				//更新订单的真实金额
				FSalesOrderModel fSalesOrder = new FSalesOrderModel();
				fSalesOrder.setOrderId(fDiffence.getOrderNo());
				fSalesOrder.setRealPay(fDiffence.getDiffenceAmount());
				fSalesOrderDao.updateRealPayByOrderNo(fSalesOrder);
			}
			FImgModel fImg = new FImgModel();
			if(list != null && list.size() > 0){
				for(String s : list){
					fImg.setId(CommonUtil.generate32UUID());
					fImg.setDiffenceId(fDiffence.getId());
					fImg.setImgUrl(s);
					fImgDao.insert(fImg);
				}
			}
		}
		return result;
	}

}
