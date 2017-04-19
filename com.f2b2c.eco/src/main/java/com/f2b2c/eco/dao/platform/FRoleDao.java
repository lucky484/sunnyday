package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FRoleModel;

public interface FRoleDao extends CrudMapper<FRoleModel, Serializable> {
    FRoleModel findOneByUserId(String userId);
    
    /**
     * 查询当前用户能访问的所有的url
     * @param userId
     * @return
     */
    List<String> queryAllUrlByUserId(Integer userId);
    
    List<String> queryAllUrl();
}