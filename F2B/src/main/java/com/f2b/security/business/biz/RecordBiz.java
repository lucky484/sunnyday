package com.f2b.security.business.biz;


import java.util.List;
import java.util.Map;

import com.f2b.security.domain.Record;
import com.f2b.security.domain.ShareRecord;

/**
 * Created by Administrator on 2016/3/24.
 */
@SuppressWarnings("unused")
public interface RecordBiz {
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
     * 发红包
     */
    public void payMoney(String openid,Integer award);
    
    /**
     * 发佣金
     * @param openid
     * @param award
     */
    public void payProfit(ShareRecord shareRecord);

    /**
     * 发红包
     */
    public boolean offerPrize(String openid,String award,String idFlag);

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
    public List<Record> findList();

    /**
     * 列表不分页
     */
    public List<Record> findList(Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<Record> findList(Integer pageNow, Integer pageSize);

    /**
     * 分页列表
     */
    public List<Record> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<Record> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

    /**
     * id获取记录
     */
    public Record findModel(Long recordId);

    /**
     * 增加或修改记录
     */
    public void addOrUpdate(Record model);

    /**
     * 删除记录
     */
    public void delete(Long recordId);

    // ******************************************************************************
    // ********************************** CRUD END **********************************
    // ******************************************************************************
    
    /**
     * 找到当前微信用户的所有订单的抽奖记录
     * @param openId
     * @param orderNos
     * @return
     */
    public List<Record> findByOpenId(String openId, List<String> orderNos);
    
    public Long findRecordNumByOrderNo(String orderNo);
}
