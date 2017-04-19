package com.f2b.security.business.dao;


import com.f2b.security.domain.RewardIndex;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface RewardIndexDao {

    /**
     * id获取记录
     */
    public RewardIndex findModel(Long id);

    /**
     * 增加记录
     */
    public Integer add(RewardIndex model);

    /**
     * 修改记录
     */
    public Integer update(RewardIndex model);

    /**
     * 删除记录
     */
    public Integer delete(Long id);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
}
