package com.softtek.mdm.dao;

import java.util.List;
import com.softtek.mdm.model.Command;

/**
 * 操作命令dao
 * @author jane.hui
 *
 */
public interface CommandDao {
	
    /**
     * 根据主键删除操作命令表
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入操作命令表数据
     * @mbggenerated
     */
    int insert(Command record);

    /**
     * 插入可选的操作命令表数据
     * @mbggenerated
     */
    int insertSelective(Command record);

    /**
     * 根据主键查询操作命令表数据
     * @mbggenerated
     */
    Command selectByPrimaryKey(String id);

    /**
     * 更新可选的操作命令数据
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Command record);

    /**
     * 根据主键更新操作命令数据
     * @mbggenerated
     */
    int updateByPrimaryKey(Command record);
    
    /**
     * 根据odid查询
     * @param udid
     * @return
     */
    List<Command> selectCommandByUdid(String  udid);
    
    /**
     * 根据udid和doit状态以及类型查询Command状态
     * @param udid
     * @param doit
     * @param type
     * @return
     */
    List<Command> selectCommands(String udid,String doit,String type);

    /**
     * 插入或更新命令数据
     * @param command 指令命令
     */
	void mergeIntoCommand(Command command);
	
	/**
	 * 批量插入命令表
	 * @param list
	 * @return
	 */
	int insertBatchCommand(List<Command> list);

	/**
	 * 获取指令列表
	 * @return 返回指令列表
	 */
    List<String> selectCommandList();
}