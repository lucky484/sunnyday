package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.CustomerDao;
import com.softtek.pst.model.CustomerModel;
import com.softtek.pst.service.CustomerService;
import com.softtek.pst.util.Page;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public int addCustomer(CustomerModel customerModel) {
		return customerDao.addCustomer(customerModel);
	}

	@Override
	public int deleteCustomer(long customerId) {
		return customerDao.deleteCustomer(customerId);
	}

	@Override
	public int editCustomer(CustomerModel customerModel) {
		return customerDao.editCustomer(customerModel);
	}

	@Override
	public CustomerModel getCustomerById(long customerId) {
		return customerDao.getCustomerById(customerId);
	}

	@Override
	public int getCustomerNum(String search) {
		return customerDao.getCustomerNum(search);
	}

	@Override
	public Page<CustomerModel> getCustomer(Integer start, Integer length,
			String column, String dir, String search) {
		Page<CustomerModel> page = new Page<>();
		page.setData(customerDao.getCustomer(start, length, column, dir, search));
		int total = customerDao.getCustomerNum(search);
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public List<CustomerModel> getAllCustomer() {
		return customerDao.getAllCustomer();
	}
	
}
