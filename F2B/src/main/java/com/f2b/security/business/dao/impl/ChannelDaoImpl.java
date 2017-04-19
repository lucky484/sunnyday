package com.f2b.security.business.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.f2b.security.business.dao.ChannelDao;
import com.f2b.security.domain.Channel;
import com.f2b.sugar.data.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/2.
 */
@Repository("ChannelDao")
public class ChannelDaoImpl implements ChannelDao{

    private final static Logger logger = LoggerFactory.getLogger(ChannelDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    // ******************************************************************************
    // ********************************* CRUD START *********************************
    // ******************************************************************************

    public Map<String, Object> getSearchCondition(Map<String, String> queryHash) {

        Map<String, Object> conditionHash = new HashMap<>();
        if (queryHash == null) {
            return conditionHash;
        }

        /*String String = queryHash.get("String");
		if (!StringUtils.isEmpty(String)) {
			conditionHash.put("String like ?{paramIndex} ", "%" + String + "%");
		}
		Integer Integer = TypeConvertUtils.StringToInteger(queryHash.get("Integer"));
		if (Integer != null && Integer > -1) {
			conditionHash.put("Integer = ?{paramIndex} ", Integer);
		}
		Date Date = TypeConvertUtils.StringToDate(queryHash.get("Date"));
		if (Date != null) {
			conditionHash.put("Date >= ?{paramIndex} ", Date);
		}*/

        return conditionHash;
    }


    @Override
    public Long totalRecord(Map<String, String> queryHash) {
        Map<String, Object> conditions = getSearchCondition(queryHash);
        TypedQuery<Long> typedQuery = QueryUtils.getTypedQueryByCondition("select count(ch) from Channel ch ", conditions, "", entityManager, Long.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Channel> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
        if (StringUtils.isEmpty(sqlOrder)) {
            sqlOrder = "order by ch.channelId desc ";
        }

        Map<String, Object> conditions = getSearchCondition(queryHash);
        TypedQuery<Channel> typedQuery = QueryUtils.getTypedQueryByCondition("select ch from Channel ch ", conditions, sqlOrder, entityManager, Channel.class);
        // 判断是否需要分页，并提交分页方法
        if (pageSize > 0 && pageNow > 0) {
            logger.debug("提交了分页查询信息，pageNow为[" + pageNow + "]，pageSize为[" + pageSize + "]");
            int minLimit = pageSize * (pageNow - 1);
            int maxLimit = pageSize;
            typedQuery.setFirstResult(minLimit).setMaxResults(maxLimit);
        }
        // 返回查询结果
        return typedQuery.getResultList();
    }

    @Override
    public Channel findModel(Long channelId) {
        return entityManager.find(Channel.class, channelId);
    }

    @Override
    @Transactional
    public Integer add(Channel model) {
        model.setEnterTime(new Date());
        entityManager.persist(model);
        logger.info("ChannelDaoImpl添加Channel成功！");
        return 1;
    }

    @Override
    @Transactional
    public Integer update(Channel model) {
        Channel existChannel = entityManager.find(Channel.class, model.getChannelId());
        existChannel.setEntrance(model.getEntrance());
        existChannel.setEnterTime(model.getEnterTime());
        existChannel.setOpenid(model.getOpenid());
        return 1;
    }

    @Override
    @Transactional
    public Integer delete(Long channelId) {
        Channel channel = entityManager.find(Channel.class, channelId);
        try {
            entityManager.remove(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
