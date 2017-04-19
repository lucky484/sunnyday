package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.RegionModel;
/**
 * 辖区管理
 * @author cliff.fan
 *
 */
public interface RegionService {
	//辖区管理的删除操作
	public int deleteRegion(Long regionId);
	//辖区管理的添加辖区操作
	public int addRegion(RegionModel region);
	//辖区管理的编辑操作
	public int editRegion(RegionModel region);
	
	public RegionModel getRegionInfo(Long regionid);

	public List<RegionModel> getRegion(int page,String regionCode, String regionName,
			 String regionLevel, String preRegionCode);

	public int getRegionCount(String regionCode, String regionName,
			 String regionLevel, String preRegionCode);
	
	public List<RegionModel> getReg();
}
