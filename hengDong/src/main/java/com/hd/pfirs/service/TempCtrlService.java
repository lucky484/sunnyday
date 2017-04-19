package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.TempCtrl;

/**
 * 
 * @author curry.su
 *
 */
public interface TempCtrlService {
	/**
	 * 插入临时辑控信息
	 * @param tempCtrl
	 */
	public void insertTempCtrl(TempCtrl tempCtrl);
	/**
	 * 查询临时辑控信息
	 * @param tempCtrl
	 */
	public List<TempCtrl> queryTempCtrl(String name,String sex,String idCardNo,int page,int fys);
	
	/**
	 * 删除临时辑控信息
	 * @param tempCompCode
	 */
	public void delTempCtrl(long tempCompID);
	
	/**
	 * 得到总行数
	 * @return
	 */
	public int getCountTepCtrl(String name,String sex,String idCardNo);
	
	/**
	 * 更新状态
	 * @param status
	 * @param tempCompCode
	 */
	public void updateStatus(String status,long tempCompID);
	
	/**
	 * 得到主键
	 * @return
	 */
	public long getTempCompID();
}
