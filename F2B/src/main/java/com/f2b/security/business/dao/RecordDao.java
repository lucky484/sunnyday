package com.f2b.security.business.dao;


import java.util.List;
import java.util.Map;

import com.f2b.security.domain.Record;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface RecordDao {
    // ******************************************************************************
    // ********************************* CRUD START *********************************
    // ******************************************************************************

    /**
     * 获取除了5以外的前20条数据
     */
    public List<Record> findRecord();

    /**
     * 获取除了5以外的数据
     */
    public List<Record> findRecord(String openid);


    /**
     * 获取总记录数
     */
    public Long totalRecord(Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<Record> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

    /**
     * id获取记录
     */
    public Record findModel(Long recordId);

    /**
     * 增加记录
     */
    public Integer add(Record model);

    /**
     * 修改记录
     */
    public Integer update(Record model);

    /**
     * 删除记录
     */
    public Integer delete(Long recordId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
    
    public List<Record> findByOpenId(String openId, List<String> orderNos);
    public Long findRecordNumByOrderNo(String orderNo);
}
