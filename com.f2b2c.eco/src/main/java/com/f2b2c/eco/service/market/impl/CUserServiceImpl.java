package com.f2b2c.eco.service.market.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserDao;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.bean.CUserBean;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.market.CUserService;

@Service
public class CUserServiceImpl implements CUserService {

    @Autowired
    private CUserDao cUserDao;
    
    @Autowired
    private CUserBalanceDao cUserBalanceDao;

    @Override
    public int insertCUser(CUserModel cUser) {
        cUser.setRegisterTime(new Date());
       int result = cUserDao.insertSelective(cUser);
       if(result == 1){
    	   CUserBalanceModel cUserBalance = new CUserBalanceModel();
    	   cUserBalance.setcUserId(cUser.getId());
    	   cUserBalance.setAccountBalance(0);
    	   cUserBalance.setVersion(0);
    	   cUserBalanceDao.insert(cUserBalance);
       }
       return result;
    }
    @Override
    public CUserModel login(String phone) {
        return cUserDao.login(phone);
    }

    @Override
    public int updateCUser(CUserModel cUser) {
        cUser.setUpdatedTime(new Date());
        return cUserDao.updateByPrimaryKeySelective(cUser);
    }

    @Override
    public CUserModel getCUserInfo(Integer cUserId) {
        return cUserDao.select(cUserId);
    }
    @Override
    public int updatePassword(CUserModel cUser) {
        return cUserDao.updatePassword(cUser);
    }
    @Override
    public CUserModel queryInfoById(Integer userId) {
        
        return cUserDao.queryInfoById(userId);
    }
    @Override
    public CUserModel getCUserByPhone(String phone) {
        
        return cUserDao.getCUserByPhone(phone);
    }
    
    /**
     * 主页统计
     * 
     * @return
     */
    @Override
    public int findAllCUserCount() {
        // TODO Auto-generated method stub
        return cUserDao.findAllCUserCount();
    }
    
    /**
     * 根据用户id我的粉丝
     * 
     * @param userId:用户id
     * @return 我的粉丝
     */
    @Override
    public List<CUserBean> getMyFans(Integer userId,String type,Integer start,Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("type", type);
        page.getParams().put("userId", userId);
        return cUserDao.selectMyFanById(page);
    }
    
    /**
     * 获取我的粉丝
     */
	@Override
	public Integer getMyFansCount(Integer userId, Integer type) {
		return cUserDao.selectMyFanByIdCount(userId, type);
	}

    @Override
    public org.springframework.data.domain.Page<CUserBean> getMyFans(Integer id, Pageable pageable) {
        Integer start = pageable.getPageNumber();
        Integer length = pageable.getPageSize();
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("type", 1);
        map.put("length", length);
        map.put("userId", id);
        int total = cUserDao.findAllShopFanByIdCount(map);
        List<CUserBean> userList = cUserDao.findAllShopFanById(map);
        org.springframework.data.domain.Page<CUserBean> pages = new Pagination<>(userList, pageable, total);
        return pages;
    }
}