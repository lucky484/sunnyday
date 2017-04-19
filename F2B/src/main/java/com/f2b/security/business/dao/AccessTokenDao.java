package com.f2b.security.business.dao;


import com.f2b.security.domain.AccessToken;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface AccessTokenDao {

    /**
     * id获取记录
     */
    public AccessToken findModel(Long awardId);

    /**
     * 增加记录
     */
    public Integer add(AccessToken model);

    /**
     * 修改记录
     */
    public Integer update(AccessToken model);

    /**
     * 删除记录
     */
    public Integer delete(Long tokenId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
}
