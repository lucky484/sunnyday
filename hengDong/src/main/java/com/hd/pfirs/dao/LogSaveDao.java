package com.hd.pfirs.dao;

import com.hd.pfirs.model.FaceAndCardResult;

/**
 * 日志处理接口
 * @author brave.chen
 *
 */
public interface LogSaveDao
{
	void insertUserLoginLog(FaceAndCardResult faceAndCardResult);
}
