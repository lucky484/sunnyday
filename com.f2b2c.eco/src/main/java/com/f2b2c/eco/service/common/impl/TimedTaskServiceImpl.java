package com.f2b2c.eco.service.common.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.common.TimedTaskDao;
import com.f2b2c.eco.dao.market.BGoodsDao;
import com.f2b2c.eco.dao.market.BSalesOrderDao;
import com.f2b2c.eco.dao.platform.FGoodsDao;
import com.f2b2c.eco.dao.platform.FSalesOrderDao;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.service.common.TimedTaskService;

/**
 * @author josen.yang
 */
@Service("timedTaskJob")
public class TimedTaskServiceImpl implements TimedTaskService {

	@Autowired
	TimedTaskDao TimedTaskDao;
	@Autowired
	private BSalesOrderDao bSalesOrderDao;
	@Autowired
	private FSalesOrderDao fSalesOrderDao;
	@Autowired
	private BGoodsDao bGoodsDao;
	@Autowired
	private FGoodsDao FGoodsDao;
	
	/**
	 * 3点/5点备份订单用作分析数据
	 */
	@Override
	public void autoBakUpOrder() {
		TimedTaskDao.autoBakUpFSalesOrder();
		TimedTaskDao.autoBakUpFSalesOrderDetails();
	}

	@Override
	public void clearExpiredOrder() {
		//1.查询出下单时间到目前时间已经超过30分钟的未支付订单列表
		//订单分为b端的采购单和c端的购买单
		List<BSalesOrderModel> bSlist=bSalesOrderDao.findExpiredOrder();
		List<FSalesOrderModel> fSlist=fSalesOrderDao.findExpiredOrder();
		//对每一个采购单，对其中的商品库存进行还原
		if(CollectionUtils.isNotEmpty(bSlist)){
			for (BSalesOrderModel bs : bSlist) {
				List<BSalesOrderDetailsModel> details=bs.getDetails();
				if(CollectionUtils.isNotEmpty(details)){
					//恢复库存
					bGoodsDao.updateBatchGoodsQuantityPlus(details);
				}
			}
			//假删除订单
			bSalesOrderDao.deleteBatchOrders(bSlist);
		}
		
		if(CollectionUtils.isNotEmpty(fSlist)){
			for (FSalesOrderModel fs : fSlist) {
				List<FSalesOrderDetailsModel> details=fs.getfSalesOrderDetailsModel();
				if(CollectionUtils.isNotEmpty(details)){
					//恢复库存
					FGoodsDao.updateBatchGoodsQuantityPlus(details);
				}
			}
			//假删除订单
			fSalesOrderDao.deleteBatchOrders(fSlist);
		}
	}

}
