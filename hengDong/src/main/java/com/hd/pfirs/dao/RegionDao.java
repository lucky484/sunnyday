package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.RegionModel;
import com.hd.pfirs.model.User;
/**
 * 辖区管理
 * @author cliff.fan
 *
 */
public interface RegionDao {
   
	public int deleteRegion(Long regionId);

	public int addRegion(RegionModel region);

	public int editRegion(RegionModel region);
	
	public RegionModel getRegionInfo(Long regionid);

	public List<RegionModel> getRegion(
			@Param(value = "page")int page,
			@Param(value = "regionCode") String regionCode,
			@Param(value = "regionName") String regionName,
			@Param(value = "regionLevel") String regionLevel,
			@Param(value = "preRegionCode") String preRegionCode);

	public int getRegionCount(
			@Param(value = "regionCode") String regionCode,
			@Param(value = "regionName") String regionName,
			@Param(value = "regionLevel") String regionLevel,
			@Param(value = "preRegionCode") String preRegionCode);
	
	public List<RegionModel> getReg();
}
