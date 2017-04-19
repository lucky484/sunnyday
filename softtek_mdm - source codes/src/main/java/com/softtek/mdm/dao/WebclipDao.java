package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.Webclip;

/**
 * Webclip
 * @author jane.hui
 *
 */
public interface WebclipDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(Webclip record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(Webclip record);

    /**
     * 根据主键返回Webclip
     * @mbggenerated
     */
    Webclip selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Webclip record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(Webclip record);
    
    /**
     * 批量插入webclip
     * @param list
     * @return
     */
    int insertBatchWebClip(List<Webclip> list);
}