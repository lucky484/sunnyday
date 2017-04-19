package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.T_QQFW_QGCK_ZP;
import com.hd.pfirs.model.T_QQFW_QGCK_ZP_Model;
import com.hd.pfirs.service.T_QQFW_QGCK_ZP_Service;

@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class T_QQFW_QGCK_ZP_ServiceImpl implements T_QQFW_QGCK_ZP_Service {

	@Autowired
	private T_QQFW_QGCK_ZP t_qqfw_qgck_zp;
	
	@Override
	public T_QQFW_QGCK_ZP_Model getPhotoByIdCardNo(String idCardNo) {
		// TODO Auto-generated method stub
		return t_qqfw_qgck_zp.getPhotoByIdCardNo(idCardNo);
	}

}
