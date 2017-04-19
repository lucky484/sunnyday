package com.f2b.security.business.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.f2b.security.business.dao.AwardDao;
import com.f2b.security.domain.Award;
import com.f2b.sugar.data.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created by Administrator on 2016/3/24.
 */
@Repository("AwardDao")
public class AwardDaoImpl implements AwardDao {

    private final static Logger logger = LoggerFactory.getLogger(AwardDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    Random random = new Random();

    public Map<String, Object> getSearchCondition(Map<String, String> queryHash) {
        Map<String, Object> conditionHash = new HashMap<>();
        if (queryHash == null) {
            return conditionHash;
        }

        String awards = queryHash.get("awards");
        if (!org.apache.commons.lang.StringUtils.isEmpty(awards)) {
            conditionHash.put("awards = ?{paramIndex} ",awards);
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
    public String getOnePrize() {
        List<Award> awardses = entityManager.createQuery("select l from Award l where l.number > 0").getResultList();

        //奖池空了
        if (CollectionUtils.isEmpty(awardses)) {
            return "5";
        }

        Award awards = awardses.get(random.nextInt(awardses.size()));
        /**
         * 修改奖品数量
         */
        awards.setNumber(awards.getNumber()-1);
        return awards.getAwards();


    }

    @Override
    public String getOnePrize1() {
        List<Award> awardses = entityManager.createQuery("select aw from Award aw where aw.number > 0 and aw.awards <> :awards ").setParameter("awards","5").getResultList();

        //奖池空了
        if (CollectionUtils.isEmpty(awardses)) {
            return "5";
        }

        Award awards = awardses.get(random.nextInt(awardses.size()));
        /**
         * 修改奖品数量
         */
        awards.setNumber(awards.getNumber()-1);
        return awards.getAwards();
    }

    @Override
    public String getOnePrize2() {
        List<Award> awardses = entityManager.createQuery("select aw from Award aw where aw.number > 0 and aw.awards <> :awards and aw.awards <> :awards1 ").setParameter("awards","3").setParameter("awards1","5").getResultList();

        //奖池空了
        if (CollectionUtils.isEmpty(awardses)) {
            return "5";
        }

        Award awards = awardses.get(random.nextInt(awardses.size()));
        /**
         * 修改奖品数量
         */
        awards.setNumber(awards.getNumber()-1);
        return awards.getAwards();
    }

    @Override
    public Long totalRecord(Map<String, String> queryHash) {
        Map<String, Object> conditions = getSearchCondition(queryHash);
        TypedQuery<Long> typedQuery = QueryUtils.getTypedQueryByCondition("select count(aw) from Award aw ", conditions, "", entityManager, Long.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Award> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
        if (StringUtils.isEmpty(sqlOrder)) {
            sqlOrder = "order by aw.awardId desc ";
        }

        Map<String, Object> conditions = getSearchCondition(queryHash);
        TypedQuery<Award> typedQuery = QueryUtils.getTypedQueryByCondition("select aw from Award aw ", conditions, sqlOrder, entityManager, Award.class);
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
    @Transactional
    public Award findModel(Long awardId) {
        return entityManager.find(Award.class, awardId);
    }

    @Override
    @Transactional
    public Integer add(Award model) {
        model.setCreateDate(new Date());
        entityManager.persist(model);
        return 1;
    }

    @Override
    @Transactional
    public Integer update(Award model) {
        Award award= entityManager.find(Award.class, model.getAwardId());
        award.setAwards(model.getAwards());
        award.setNumber(model.getNumber());
        award.setUpdateDate(new Date());
        return 1;
    }

    @Override
    @Transactional
    public Integer delete(Long awardId) {
        Award existStore = entityManager.find(Award.class, awardId);
        try {
            entityManager.remove(existStore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
