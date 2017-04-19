package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.RegionDao;
import com.hd.pfirs.model.RegionModel;
import com.hd.pfirs.service.RegionService;
/**
 * 辖区管理
 * @author cliff.fan
 *
 */
@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDao regionDao;
	
	//辖区管理的删除操作
	@Override
	public int deleteRegion(Long regionId) {
		return regionDao.deleteRegion(regionId);
	}
	@Override
	public int editRegion(RegionModel region) {
		return regionDao.editRegion(region);
	}
	//辖区管理的添加辖区的功能
	@Override
	public int addRegion(RegionModel region) {
		return regionDao.addRegion(region);
	}

	@Override
	public List<RegionModel> getRegion(int page,String regionCode, String regionName,
			 String regionLevel, String preRegionCode) {
		return regionDao.getRegion(page,regionCode, regionName,  
				regionLevel, preRegionCode);
	}

	@Override
	public int getRegionCount(String regionCode, String regionName,
			  String regionLevel, String preRegionCode) {
		return regionDao.getRegionCount(regionCode, regionName,  
				regionLevel, preRegionCode);
	}
	@Override
	public RegionModel getRegionInfo(Long regionid) {
		return regionDao.getRegionInfo(regionid);
	}
	/**
	 * @Title: getReg
	 * @Description: TODO
	 * @return
	 * @see com.hd.pfirs.service.RegionService#getReg()
	 */
	@Override
	public List<RegionModel> getReg() {
		return regionDao.getReg();
	}


}
