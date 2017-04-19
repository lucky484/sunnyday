package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.CustomerModel;

public interface CustomerDao {
	
	public int addCustomer(CustomerModel customerModel);
	
	public int deleteCustomer(long customerId);
	
	public int editCustomer(CustomerModel customerModel);
	
	public CustomerModel getCustomerById(long customerId);
	
	public int getCustomerNum(@Param(value = "search") String search);
	
	public List<CustomerModel> getCustomer(
			@Param(value = "start") Integer start,
			@Param(value = "length") Integer length,
			@Param(value = "col") String column,
			@Param(value = "dir") String dir,
			@Param(value = "search") String search);
	
	public List<CustomerModel> getAllCustomer();
	
}
