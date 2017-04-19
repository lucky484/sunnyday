package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BFreightTemplateDao;
import com.f2b2c.eco.dao.market.BShopFreightDao;
import com.f2b2c.eco.model.bean.BFreightBean;
import com.f2b2c.eco.model.market.BFreightTemplate;
import com.f2b2c.eco.model.market.BShopFreightModel;
import com.f2b2c.eco.service.market.BFreightService;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;

import jodd.util.StringUtil;

/**
 * 运费设置Service
 * @author jane.hui
 *
 */
@Service("bFreightService")
public class BFreightServiceImpl implements BFreightService{

    @Autowired
    private BShopFreightDao bShopFreightDao;
    
    @Autowired
    private BFreightTemplateDao queryFreightTemplate;
    
    /**
     * 根据店铺id获取运费
     */
    @Override
    public List<BFreightBean> queryFreight(Integer shopId) {
         
        return bShopFreightDao.selectFreightByShopId(shopId);
    }

    /**
     * 修改运费设置
     * @param freightStr:运费字符串
     * @param 店铺id
     * @return 返回修改结果
     */
    @Override
    public int modify(String freightStr, Integer shopId) {
        BShopFreightModel bShopFreight = null;
        List<BShopFreightModel> list = new ArrayList<BShopFreightModel>();
        String [] freArr = null;
        int i = 0;
        if(StringUtil.isNotEmpty(freightStr)){
            freightStr = freightStr.substring(0,freightStr.length()-1);
            String [] freightArray = freightStr.split("#");
            for (String s : freightArray) {
                freArr = s.split(",");
                bShopFreight = new BShopFreightModel();
                bShopFreight.setProvinceId(Integer.valueOf(freArr[0]));
                bShopFreight.setShopId(shopId.toString());
                if(StringUtil.isNotEmpty(freArr[2])){
                    bShopFreight.setFreight(MoneyUtil.fromYuanToIntFen(freArr[2]));
                } else {
                    bShopFreight.setFreight(0);
                }
                if(StringUtil.isNotEmpty(freArr[1])){
                    i = 1;
                    bShopFreight.setId(Integer.valueOf(freArr[1]));
                }
                bShopFreight.setCreateTime(new Date());
                list.add(bShopFreight);
            }
        }
        // 如果i=1表示批量更新  i=0表示批量插入
        int result = 0;
        if(i==1){
            result = bShopFreightDao.updateBatchFreight(list);
        } else {
            result = bShopFreightDao.insertBatchFreight(list);
        }
        return result;
    }

    /**
     * 获取运费模板列表
     * @param bUserId:B端用户外键
     * @return 返回模板列表
     */
    @Override
    public List<BFreightTemplate> queryFreightTemplate(Integer bUserId) {
        return queryFreightTemplate.selectFreightTemplateByUserId(bUserId);
    }
}