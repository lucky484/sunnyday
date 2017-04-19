package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.AuthenticateInfo;

public interface AuthenticateInfoDao {
	
    /**
     * 根据主键删除认证表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入认证表
     * @mbggenerated
     */
    int insert(AuthenticateInfo record);

    /**
     * 插入可选的认证表
     * @mbggenerated
     */
    int insertSelective(AuthenticateInfo record);

    /**
     * 根据主键查询认证表
     * @mbggenerated
     */
    AuthenticateInfo selectByPrimaryKey(Integer id);

    /**
     * 更新可选的认证表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthenticateInfo record);

    /**
     * 更新认证表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthenticateInfo record);
    
    /**
     * 插入可选的认证表
     * @mbggenerated
     */
    int insertSelectiveAuthenticate(AuthenticateInfo record);
    
    /**
     * 根据udid获取认证信息
     * @param udid
     * @return
     */
    List<AuthenticateInfo> selectAuthenticateInfoByUdid(String udid);
    
    int deleteAuthenticateInfoByUdid(String udid);
}