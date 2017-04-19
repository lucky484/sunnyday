package com.f2b2c.eco.service.market;

import java.util.List;

import com.f2b2c.eco.model.market.CDeliveryAddressModel;

public interface CDeliveryAddressService {
	List<CDeliveryAddressModel> getUserDeliveryAddresses(Integer cUserId);

	CDeliveryAddressModel getDeliveryAddress(Integer id);
	CDeliveryAddressModel getDefaultDeliveryAddress(Integer cUserId);

	Integer insertDeliveryAddress(CDeliveryAddressModel address);

	Integer updateDeliveryAddress(CDeliveryAddressModel address);
}
