/**
* @ClassName: AccessTokenDaoImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Aug 22, 2016 3:37:36 PM
*/
package com.f2b.security.business.dao.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.dao.RewardIndexDao;
import com.f2b.security.domain.RewardIndex;

/**
 * @author jacob.shen
 *
 */
@Repository("rewardIndexDao")
public class RewardIndexDaoImpl implements RewardIndexDao {

	private final static Logger logger = LoggerFactory.getLogger(RewardIndexDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public RewardIndex findModel(Long id) {
		return entityManager.find(RewardIndex.class, id);
	}

	@Override
	@Transactional
	public Integer add(RewardIndex model) {
		entityManager.persist(model);
		return 1;
	}

	@Override
	@Transactional
	public Integer update(RewardIndex model) {
		RewardIndex rewardIndex = entityManager.find(RewardIndex.class, model.getId());
		rewardIndex.setCurrentIndex(model.getCurrentIndex());
		rewardIndex.setTotalIndex(model.getTotalIndex());
		rewardIndex.setUpdateTime(new Date());
		return 1;
	}

	@Override
	@Transactional
	public Integer delete(Long id) {
		RewardIndex existStore = entityManager.find(RewardIndex.class, id);
		try {
			entityManager.remove(existStore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

}
