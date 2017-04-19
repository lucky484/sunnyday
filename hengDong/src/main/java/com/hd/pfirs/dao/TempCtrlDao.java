package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameter;

import com.hd.pfirs.model.TempCtrl;

/**
 * 
 * @author curry.su
 *
 */
public interface TempCtrlDao {

	/**
	 * 插入临时辑控信息
	 * @param tempCtrl
	 */
	public void insertTempCtrl(TempCtrl tempCtrl);
	/**
	 * 查询临时辑控信息
	 * @param tempCtrl
	 */
	public List<TempCtrl> queryTempCtrl(@Param(value = "name")String name,@Param(value = "sex")String sex,@Param(value = "idCardNo")String idCardNo,@Param(value = "page")int page,@Param(value = "fys")int fys);
	
	/**
	 * 删除临时辑控信息
	 * @param tempCompCode
	 */
	public void delTempCtrl(long tempCompID);
	
	/**
	 * 得到总行数
	 * @return
	 */
	public int getCountTepCtrl(@Param(value = "name")String name,@Param(value = "sex")String sex,@Param(value = "idCardNo")String idCardNo);
	
	/**
	 * 更新状态
	 * @param status
	 * @param tempCompCode
	 */
	public void updateStatus(@Param(value = "status")String status,@Param(value = "tempCompID")long tempCompID);
	
	/**
	 * 得到主键
	 * @return
	 */
	public long getTempCompID();
}
