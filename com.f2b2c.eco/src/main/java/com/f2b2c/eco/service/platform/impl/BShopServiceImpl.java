package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.bean.platform.BShopInfoBean;
import com.f2b2c.eco.dao.market.BGoodsDao;
import com.f2b2c.eco.dao.market.BShopInfoDao;
import com.f2b2c.eco.dao.market.BUserDao;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.BShopService;
import com.f2b2c.eco.status.EnableEnum;

@Service
public class BShopServiceImpl implements BShopService {

    @Autowired
    private BShopInfoDao bShopInfoDao;
    @Autowired
    private BUserDao bUserDao;
    
    @Autowired
    private BGoodsDao bGoodsDao;

    @Override
    public Page<BShopInfoModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
        
        int total = bShopInfoDao.countWithMap(paramMap);
        paramMap.put("pageable", pageable);
        List<BShopInfoModel> lists = bShopInfoDao.findWithPagination(paramMap);    
        Page<BShopInfoModel> pages = new Pagination<>(lists,pageable,total);
        return pages;
        
    }

    @Override
    public int saveBShopInfo(BShopInfoModel shopModel, FUserModel userModel) {
        
        BShopInfoModel bShopInfoModel = new BShopInfoModel();
        BUserModel bUserModel = bUserDao.getbUsyerById(Integer.parseInt(shopModel.getUserName()));// 门店店老板id
        bShopInfoModel.setUser(bUserModel);
        bShopInfoModel.setAddress(shopModel.getAddress());// 门店地址
        bShopInfoModel.setAuthCode(shopModel.getAuthCode());// 门店授权码
        bShopInfoModel.setCreatedTime(new Date());// 门店创建时间
        bShopInfoModel.setIdentityId(shopModel.getIdentityId());// 门店老板身份证号
        bShopInfoModel.setLocationX(shopModel.getLocationX());// 经纬度-x
        bShopInfoModel.setLocationY(shopModel.getLocationY());// 经纬度-y
        bShopInfoModel.setShopName(shopModel.getShopName());// 门店名称
        bShopInfoModel.setDetails(shopModel.getDetails());// 门店详情
        bShopInfoModel.setUserName(bUserModel.getRealName());// 门店老板姓名
        bShopInfoModel.setCreatedUser(userModel);// 门店创建人
        return bShopInfoDao.insert(bShopInfoModel);
    }

    @Override
    public int updateBShopInfo(BShopInfoModel shopModel, FUserModel userModel) {
        
        BShopInfoModel bShopInfoModel = new BShopInfoModel();
        bShopInfoModel.setId(shopModel.getId());// 门店id
        BUserModel bUserModel = bUserDao.getbUsyerById(Integer.parseInt(shopModel.getUserName()));// 门店店老板id
        bShopInfoModel.setUser(bUserModel);
        bShopInfoModel.setAddress(shopModel.getAddress());// 门店地址
        bShopInfoModel.setAuthCode(shopModel.getAuthCode());// 门店授权码
        bShopInfoModel.setUpdatedTime(new Date());// 门店更新时间
        bShopInfoModel.setIdentityId(shopModel.getIdentityId());// 门店老板身份证号
        bShopInfoModel.setLocationX(shopModel.getLocationX());// 经纬度-x
        bShopInfoModel.setLocationY(shopModel.getLocationY());// 经纬度-y
        bShopInfoModel.setShopName(shopModel.getShopName());// 门店名称
        bShopInfoModel.setDetails(shopModel.getDetails());// 门店详情
        bShopInfoModel.setUserName(bUserModel.getRealName());// 门店老板姓名
        bShopInfoModel.setUpdatedUser(userModel);// 门店更新人
        return bShopInfoDao.update(bShopInfoModel);
    }

    @Override
    public int deleteBShopInfoById(Integer id,Integer userId) {
        int result = bShopInfoDao.delete(id);
        bUserDao.deleteUserById(userId);
        return result;
    }

    @Override
    public BShopInfoModel getBShopInfoById(Integer id) {
        
        return bShopInfoDao.select(id);
    }

    /**
     * 启用禁用门店
     * 
     * @param id:门店id
     * @param tag:标识(1.启用门店
     *            0.禁用门店)
     * @return 返回操作结果返回
     */
    @Override
    public Integer enable(String id, String tag) {
        int size = 0;
        if(EnableEnum.disable.toString().equals(tag)){
	        size = bGoodsDao.updateGoodsByShopId(Integer.valueOf(id));
	        if(size ==0){
	            return size;
	        }
        }
        return bShopInfoDao.enable(id, tag);
    }

    /**
     * 根据店铺id获取店铺信息
     * 
     * @param id:店铺主键
     * @return 返回店铺信息
     */
    @Override
    public Object get(Integer id) {
        BShopInfoBean bShopInfoBean = bShopInfoDao.selectBShopInfoList(id);
        if(bShopInfoBean!=null){
            bShopInfoBean.setProvinceList(bShopInfoDao.selectProvinceList());
            bShopInfoBean.setCityList(bShopInfoDao.selectCityList(bShopInfoBean.getProvinceCode()));
            bShopInfoBean.setAreaList(bShopInfoDao.selectAreaList(bShopInfoBean.getCityCode()));
        }
        return bShopInfoBean;
    }

    /**
     * 修改店铺信息
     * 
     * @param id:主键
     * @param shopName:店铺名称
     * @param remark:描述
     * @param address:地址
     * @param locationX:经度
     * @param locationY:维度
     * @return 返回修改是否成功状态
     */
    @Override
    public Integer modify(String id, String shopName, String remark, String address, String areaCode,String phone) {
        Integer intId = Integer.valueOf(id);
        // 根据店铺id获取用户id
        Integer userId = bShopInfoDao.selectBUserIdBySId(intId);
        // 更新店老板用户表手机号
        BUserModel bUserModel = new BUserModel();
        bUserModel.setId(userId);
        bUserModel.setPhone(phone);
        int size = bUserDao.updatePhoneById(bUserModel);
        if(size==0){
            return size;
        }
        // 更新店铺信息表
        BShopInfoBean bShopInfoBean = new BShopInfoBean();
        bShopInfoBean.setId(intId);
        bShopInfoBean.setShopName(shopName);
        bShopInfoBean.setRemark(remark);
        bShopInfoBean.setAreaCode(areaCode);
        return bShopInfoDao.modifyShopById(bShopInfoBean);
    }

    /**
     * 选择省
     * 
     * @param code:省code
     * @return 返回市列表
     */
    @Override
    public Object chooseProvince(String code) {
        return bShopInfoDao.selectCityList(code);
    }

    /**
     * 选择市
     * 
     * @param code:市code
     * @return 返回区域列表
     */
    @Override
    public Object chooseCity(String code) {
        return bShopInfoDao.selectAreaList(code);
    }

    @Override
    public List<Integer> queryBUserIdByList(List<Integer> list) {
        return bShopInfoDao.queryBUserIdByList(list);
    }
}