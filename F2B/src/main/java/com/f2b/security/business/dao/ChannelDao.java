package com.f2b.security.business.dao;

import java.util.List;
import java.util.Map;

import com.f2b.security.domain.Channel;

/**
 * Created by Administrator on 2016/4/2.
 */
public interface ChannelDao {

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
    public List<Channel> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

    /**
     * id获取记录
     */
    public Channel findModel(Long channelId);

    /**
     * 增加记录
     */
    public Integer add(Channel model);

    /**
     * 修改记录
     */
    public Integer update(Channel model);

    /**
     * 删除记录
     */
    public Integer delete(Long channelId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
}
