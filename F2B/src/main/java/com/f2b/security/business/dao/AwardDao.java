package com.f2b.security.business.dao;


import java.util.List;
import java.util.Map;

import com.f2b.security.domain.Award;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface AwardDao {

    /**
     * 随机抽
     * @return
     */
    public String getOnePrize();


    /**
     * 一定抽到奖
     * @return
     */
    public String getOnePrize1();

    /**
     * 除了京东券的其他奖品
     */
    public String getOnePrize2();

    // ******************************************************************************
    // ********************************* CRUD START *********************************
    // ******************************************************************************

    /**
     * 获取总记录数
     */
    public Long totalRecord(Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<Award> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

    /**
     * id获取记录
     */
    public Award findModel(Long awardId);

    /**
     * 增加记录
     */
    public Integer add(Award model);

    /**
     * 修改记录
     */
    public Integer update(Award model);

    /**
     * 删除记录
     */
    public Integer delete(Long awardId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
}
