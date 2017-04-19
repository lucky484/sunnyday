package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.hd.pfirs.model.IdCardWebModel;

public interface IdCardWebService {

	public List<IdCardWebModel> getIdCardWebModelList(int page,
			String collectSite,int fys);

	public int getIdCardWebModelCount(String collectSite);

	public List<IdCardWebModel> getIdCardWebModelListByidCardNo(int page,
			String idCardNo, String collectSite,int fys);

	public int getIdCardWebModelCountByidCardNo(String idCardNo,
			String collectSite);
}
