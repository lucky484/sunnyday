package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IdCardWebModel;

public interface IdCardWebDao {
	
	public List<IdCardWebModel> getIdCardWebModelList(@Param(value = "page")int page,@Param(value = "collectSite")String collectSite,@Param(value="fys")int fys);
	
	public int getIdCardWebModelCount(@Param(value = "collectSite")String collectSite);
	
	public List<IdCardWebModel> getIdCardWebModelListByidCardNo(@Param(value = "page")int page,@Param(value = "idCardNo")String idCardNo,@Param(value = "collectSite")String collectSite,@Param(value="fys")int fys);
	
	public int getIdCardWebModelCountByidCardNo(@Param(value = "idCardNo")String idCardNo,@Param(value = "collectSite")String collectSite);
	
}
