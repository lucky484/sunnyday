package com.f2b2c.eco.service.market;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.bean.CUserBean;
import com.f2b2c.eco.model.market.CUserModel;

public interface CUserService {

    public int insertCUser(CUserModel cUser);

    public int updatePassword(CUserModel cUser);

    int updateCUser(CUserModel cUser);

    CUserModel login(String phone);

    CUserModel getCUserInfo(Integer cUserId);

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
     * @param userId
     * @return
     */
    List<CUserBean> getMyFans(Integer userId,String type,Integer start,Integer length);
    
    /**
     * 根据用户id获取我的粉丝
     * 
     * @param userId:用户外键
     * @param type
     *            0代表用户推荐 1.代表商家推荐
     */
    Integer getMyFansCount(Integer userId,Integer type);

    Page<CUserBean> getMyFans(Integer id, Pageable pageable);
}
