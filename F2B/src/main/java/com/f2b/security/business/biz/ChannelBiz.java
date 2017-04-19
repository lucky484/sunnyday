package com.f2b.security.business.biz;

import java.util.List;
import java.util.Map;

import com.f2b.security.domain.Channel;

/**
 * Created by Administrator on 2016/4/2.
 */
@SuppressWarnings("unused")
public interface ChannelBiz {


    // ******************************************************************************
    // ********************************* CRUD START *********************************
    // ******************************************************************************

    /**
     * 获取总记录数
     */
    public Long totalRecord();

    /**
     * 获取总记录数
     */
    public Long totalRecord(Map<String, String> queryHash);

    /**
     * 列表不分页
     */
    public List<Channel> findList();

    /**
     * 列表不分页
     */
    public List<Channel> findList(Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<Channel> findList(Integer pageNow, Integer pageSize);

    /**
     * 分页列表
     */
    public List<Channel> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<Channel> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

    /**
     * id获取记录
     */
    public Channel findModel(Long channelId);

    /**
     * 增加或修改记录
     */
    public void addOrUpdate(Channel model);

    /**
     * 删除记录
     */
    public void delete(Long channelId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
}
