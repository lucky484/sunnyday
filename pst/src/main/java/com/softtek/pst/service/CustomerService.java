package com.softtek.pst.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.softtek.pst.model.CustomerModel;
import com.softtek.pst.util.Page;

@Service
public interface CustomerService {

	public int addCustomer(CustomerModel customerModel);

	public int deleteCustomer(long customerId);

	public int editCustomer(CustomerModel customerModel);

	public CustomerModel getCustomerById(long customerId);

	public int getCustomerNum(@Param(value = "search") String search);

	public Page<CustomerModel> getCustomer(
			@Param(value = "start") Integer start,
			@Param(value = "length") Integer length,
			@Param(value = "col") String column,
			@Param(value = "dir") String dir,
			@Param(value = "search") String search);
	
	public List<CustomerModel> getAllCustomer();
}
