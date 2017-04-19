package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.TokenUpdateInfo;

public interface TokenUpdateInfoDao {
    /**
     * 根据主键删除Token表字段
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入Token字段
     * @mbggenerated
     */
    int insert(TokenUpdateInfo record);

    /**
     * 插入可选Token
     * @mbggenerated
     */
    int insertSelective(TokenUpdateInfo record);

    /**
     * 根据主键查询Token表
     * @mbggenerated
     */
    TokenUpdateInfo selectByPrimaryKey(Integer id);

    /**
     * 更新可选的Token表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TokenUpdateInfo record);

    /**
     * 更新Token表
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TokenUpdateInfo record);

    /**
     * 更新Token表
     * @mbggenerated
     */
    int updateByPrimaryKey(TokenUpdateInfo record);
    
    /**
     * 根据udid获取Token信息
     * @param udid
     * @return
     */
    List<TokenUpdateInfo> selectTokenInfoByUdid(String udid);

	List<TokenUpdateInfo> selectTokenUpdateInfos(List<String> uidis);

	/**
	 * 根据用户list获取Token信息
	 */
	List<TokenUpdateInfo> selectTokenInfoByUserList(List<Integer> list);
	
	List<TokenUpdateInfo> queryTokenInfoByUdidList(List<String> list);
	
	TokenUpdateInfo queryIsProfileByIosUuid(String iosUuid);
	
	int deleteTokenUpdateInfoByUdid(String udid);
	
	int deleteTokenUpdateInfoByUuid(String iosUuid);
	
}