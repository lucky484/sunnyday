package com.f2b2c.eco.dao.platform;

import java.util.List;

import com.f2b2c.eco.model.platform.FOrderDiffenceImgModel;

/**
 * 差价凭证表
 * @author jane.hui
 *
 */
public interface FOrderDiffenceImgDao {
	
    /**
     * 删除
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入差价凭证表
     */
    int insert(FOrderDiffenceImgModel record);

    /**
     * 插入可选的差价凭证表
     */
    int insertSelective(FOrderDiffenceImgModel record);

    /**
     * 根据主键查询差价凭证表
     */
    FOrderDiffenceImgModel selectByPrimaryKey(String id);

    /**
     * 更新可选的差价凭证表
     */
    int updateByPrimaryKeySelective(FOrderDiffenceImgModel record);

    /**
     * 更新差价凭证表
     */
    int updateByPrimaryKey(FOrderDiffenceImgModel record);
    
    /**
     * 根据差价id获取图片List
     * @param differentId
     * @return
     */
    List<String> selectImgListByDifferentId(String differentId);
}