package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.bean.CUserBean;
import com.f2b2c.eco.model.market.CUserModel;

public interface CUserDao extends CrudMapper<CUserModel, Serializable> {
    public int updatePassword(CUserModel cUser);

    CUserModel login(@Param("phone") String phone);
    
    public CUserModel queryInfoById(Integer userId);

    public CUserModel getCUserByPhone(String phone);
    
    /**
     * 主页统计
     * 
     * @return
     */
    int findAllCUserCount();
    
    /**
     * 根据用户id获取我的粉丝
     * 
     * @param userId:用户id
     * @return 返回我的粉丝
     */
    List<CUserBean> selectMyFanById(Page page);
    
    /**
     * 根据用户id获取我的粉丝
     * 
     * @param userId:用户Id
     * @param Type:推荐类型
     * @return 返回总粉丝数
     */
    Integer selectMyFanByIdCount(Integer userId,Integer type);


    int findAllShopFanByIdCount(Map<String, Object> map);

    List<CUserBean> findAllShopFanById(Map<String, Object> map);
    
    /**
     * 根据用户相关参数更新字段
     * @param user
     * @return
     */
    int updateByPrimaryKeySelective(CUserModel user);
}