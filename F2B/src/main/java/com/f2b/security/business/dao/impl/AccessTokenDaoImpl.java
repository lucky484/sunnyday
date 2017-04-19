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

import com.f2b.security.business.dao.AccessTokenDao;
import com.f2b.security.domain.AccessToken;

/**
 * @author jacob.shen
 *
 */
@Repository("accessTokenDao")
public class AccessTokenDaoImpl implements AccessTokenDao{

	 private final static Logger logger = LoggerFactory.getLogger(AccessTokenDaoImpl.class);
	    @PersistenceContext
	    private EntityManager entityManager;

	    @Override
	    @Transactional
	    public AccessToken findModel(Long AccessTokenId) {
	        return entityManager.find(AccessToken.class, AccessTokenId);
	    }

	    @Override
	    @Transactional
	    public Integer add(AccessToken model) {
	        entityManager.persist(model);
	        return 1;
	    }

	    @Override
	    @Transactional
	    public Integer update(AccessToken model) {
	        AccessToken AccessToken= entityManager.find(AccessToken.class, model.getTokenId());
	        if(AccessToken==null) {
	        	model.setTokenId(null);
	        	add(model);
	        } else {
	        	AccessToken.setExpiresIn(model.getExpiresIn());
	        	AccessToken.setToken(model.getToken());
	        	AccessToken.setUpdateTime(new Date());
	        }
	        return 1;
	    }

	    @Override
	    @Transactional
	    public Integer delete(Long AccessTokenId) {
	        AccessToken existStore = entityManager.find(AccessToken.class, AccessTokenId);
	        try {
	            entityManager.remove(existStore);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return 1;
	    }

}
