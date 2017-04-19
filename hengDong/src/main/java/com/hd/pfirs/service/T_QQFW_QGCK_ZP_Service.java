package com.hd.pfirs.service;


import com.hd.pfirs.model.T_QQFW_QGCK_ZP_Model;


public interface T_QQFW_QGCK_ZP_Service {

	/**
	 * 得到辑控图片
	 * @param idCardNo
	 * @return
	 */
	public T_QQFW_QGCK_ZP_Model getPhotoByIdCardNo(String idCardNo);
}
