package com.f2b2c.eco.service.market.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.CDeliveryAddressDao;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.service.market.CDeliveryAddressService;

@Service
public class CDeliveryAddressServiceImpl implements CDeliveryAddressService {

	@Autowired
	private CDeliveryAddressDao cDeliveryAddressDao;

	@Override
	public List<CDeliveryAddressModel> getUserDeliveryAddresses(Integer cUserId) {
		Page page = new Page();
		page.getParams().put("userId", cUserId);
		return cDeliveryAddressDao.selectDeliveryAddressByUserId(page);
	}

	@Override
	public Integer insertDeliveryAddress(CDeliveryAddressModel address) {
		return cDeliveryAddressDao.insertSelective(address);
	}

	@Override
	public CDeliveryAddressModel getDeliveryAddress(Integer id) {
		return cDeliveryAddressDao.select(id);
	}

	@Override
	public Integer updateDeliveryAddress(CDeliveryAddressModel address) {
		address.setUpdatedTime(new Date());
		return cDeliveryAddressDao.updateByPrimaryKeySelective(address);
	}

	@Override
	public CDeliveryAddressModel getDefaultDeliveryAddress(Integer cUserId) {
		return cDeliveryAddressDao.getDefaultDeliveryAddress(cUserId);
	}

}
