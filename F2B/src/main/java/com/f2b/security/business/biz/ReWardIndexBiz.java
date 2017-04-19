package com.f2b.security.business.biz;

import com.f2b.security.domain.RewardIndex;

/**
 * Created by Administrator on 2016/3/24.
 */
@SuppressWarnings("unused")
public interface ReWardIndexBiz {
    // ******************************************************************************
    // ********************************* CRUD START *********************************
    // ******************************************************************************

    /**
     * id获取记录
     */
    public RewardIndex findModel(Long recordId);

    /**
     * 增加或修改记录
     */
    public void addOrUpdate(RewardIndex model);

    /**
     * 删除记录
     */
    public void delete(Long recordId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
}
