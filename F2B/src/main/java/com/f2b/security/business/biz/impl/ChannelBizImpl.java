package com.f2b.security.business.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.ChannelBiz;
import com.f2b.security.business.dao.ChannelDao;
import com.f2b.security.domain.Channel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/2.
 */
@Service("ChannelBiz")
public class ChannelBizImpl implements ChannelBiz {

    private Logger logger= LoggerFactory.getLogger(ChannelBizImpl.class);

    @Autowired
    private ChannelDao channelDao;

    @Override
    public Long totalRecord() {
        return this.totalRecord(null);
    }

    @Override
    public Long totalRecord(Map<String, String> queryHash) {
        return channelDao.totalRecord(queryHash);
    }

    @Override
    public List<Channel> findList() {
        return this.findList(0, 0, null);
    }

    @Override
    public List<Channel> findList(Map<String, String> queryHash) {
        return this.findList(0, 0, queryHash);
    }

    @Override
    public List<Channel> findList(Integer pageNow, Integer pageSize) {
        return this.findList(pageNow, pageSize, null);
    }

    @Override
    public List<Channel> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash) {
        return this.findList(pageNow, pageSize, "", queryHash);
    }

    @Override
    public List<Channel> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
        return channelDao.findList(pageNow, pageSize, sqlOrder, queryHash);
    }

    @Override
    public Channel findModel(Long channelId) {
        return channelDao.findModel(channelId);
    }

    @Override
    @Transactional
    public void addOrUpdate(Channel model) {
        if (model.getChannelId() != null && model.getChannelId() > 0) {
            logger.info("当前需修改的Channel对象channelId为[" + model.getChannelId() + "]");
            channelDao.update(model);
        } else {
            logger.info("ChannelBizImpl添加Channel！");
            channelDao.add(model);
        }
    }

    @Override
    @Transactional
    public void delete(Long channelId) {
        channelDao.delete(channelId);
    }
}
