package com.hd.pfirs.dao2;

import com.hd.pfirs.model.T_QQFW_QGCK_ZP_Model;

/**
 * 辑控图片
 * @author curry.su
 *
 */
public interface T_QQFW_QGCK_ZP {

	/**
	 * 得到辑控图片
	 * @param idCardNo
	 * @return
	 */
	public T_QQFW_QGCK_ZP_Model getPhotoByIdCardNo(String idCardNo);
	
}
