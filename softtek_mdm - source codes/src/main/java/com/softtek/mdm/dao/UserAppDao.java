package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.UserApp;
import com.softtek.mdm.util.DataGridModel;

public interface UserAppDao {
	
    /**
     * 根据主键删除绑定
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增用户绑定数据
     * @mbggenerated
     */
    int insert(UserApp record);

    /**
     * 新增可选的用户绑定数据
     * @mbggenerated
     */
    int insertSelective(UserApp record);

    /**
     * 根据主键查询用户绑定model
     *
     * @mbggenerated
     */
    UserApp selectByPrimaryKey(Integer id);

    /**
     * 更新可选的用户绑定数据
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserApp record);

    /**
     * 更新可选的用户绑定数据
     * @mbggenerated
     */
    int updateByPrimaryKey(UserApp record);
    
    /**
     * 删除用户应用绑定关系
     * @param userId
     * @return
     */
    int removeUserAppBinding(DataGridModel params);
    
    /**
     * 根据用户id和应用id确定是否存在绑定关系 
     * @param params
     * @return
     */
    int selectUserAppSizeById(DataGridModel params);
    
    /**
     * 根据应用id获取用户
     * @param userId
     * @return
     */
    List<Integer> selectUserIdsByAppId(Integer appId);
}