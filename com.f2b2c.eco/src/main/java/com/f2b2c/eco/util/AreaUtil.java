package com.f2b2c.eco.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.f2b2c.eco.apimodel.Area;
import com.f2b2c.eco.apimodel.City;
import com.f2b2c.eco.apimodel.Province;
import com.f2b2c.eco.model.common.AreaModel;
import com.f2b2c.eco.model.common.CityModel;
import com.f2b2c.eco.model.common.ProvinceModel;
import com.f2b2c.eco.service.common.AreaService;

/**
 * 地域公共类方法
 * @author brave.chen
 *
 */
@Component
public class AreaUtil {
	/**
	 * 省份列表（包含城市和区域列表）
	 */
	public static List<ProvinceModel> provinceModels= null;
	
	/**
	 * 客户端省份列表
	 */
	public static List<Province> provinces = new ArrayList<Province>();
	
	/**
	 * 客户端城市列表map
	 */
	public static Map<Integer, List<City>> cityMap = new HashMap<Integer, List<City>>();
	
	/**
	 * 客户端区列表map
	 */
	public static Map<Integer, List<Area>> areaMap = new HashMap<Integer, List<Area>>();
	
	/**
	 * 区域服务类
	 */
	private static AreaService areaService;
	
	/**
	 * 获取地域信息列表
	 * @return 地域信息列表
	 */
	public static List<ProvinceModel> getProvinceModels()
	{
		if (CollectionUtils.isEmpty(provinceModels))
		{
			provinceModels = areaService.queryAll();
		}
		
		return provinceModels;
	}
	
	/**
	 * 获取所有省份列表（客户端）
	 * @return
	 */
	public static List<Province> getProvinces()
	{
		if (CollectionUtils.isEmpty(provinces))
		{
			List<ProvinceModel> provinceModels = getProvinceModels();
			for (ProvinceModel provinceModel : provinceModels)
			{
				Province province = getNewProvince(provinceModel);
				refreshCityMap(provinceModel);
				provinces.add(province);
			}
		}
		return provinces;
	}
	
	/**
	 * 根据省份code获取省份对应的城市列表（客户端）
	 * @param provinceCode 省份code
	 * @return 省份对应的城市列表
	 */
	public static List<City> getCitysByProvinceCode(Integer id)
	{
		if (CollectionUtils.isEmpty(provinces))
		{
			getProvinces();
		}
		
		return cityMap.get(id);
	}
	
	/**
	 * 根据城市code获取城市对应的区列表
	 * @param cityCode 城市code
	 * @return 城市对应的区列表
	 */
	public static List<Area> getAreaByCityCode(Integer id)
	{
		if (CollectionUtils.isEmpty(provinces))
		{
			getProvinces();
		}
		
		return areaMap.get(id);
	}
	
	private static void refreshCityMap(ProvinceModel provinceModel) {
		if (null != provinceModel)
		{
			List<CityModel> cityModels = provinceModel.getCityModelList();
			List<City> citys = getCityList(cityModels);
			cityMap.put(provinceModel.getId(), citys);
		}
	} 
	

	private static List<City> getCityList(List<CityModel> cityModels) {
		List<City> cityList = new ArrayList<City>();
		
		if (CollectionUtils.isNotEmpty(cityModels))
		{
			for (CityModel cityModel : cityModels)
			{
				refreshCityMap(cityModel);
				cityList.add(getNewCity(cityModel));
			}
		}
		
		return cityList;
	}
	
	private static void refreshCityMap(CityModel cityModel) {
		if (null != cityModel)
		{
			List<AreaModel> areaModels = cityModel.getAreaModelList();
			List<Area> areas = getAreaList(areaModels);
			areaMap.put(cityModel.getId(), areas);
		}
	}

	private static List<Area> getAreaList(List<AreaModel> areaModels) {
		List<Area> areas = new ArrayList<Area>();
		if (CollectionUtils.isNotEmpty(areaModels))
		{
			for (AreaModel areaModel : areaModels)
			{
				areas.add(getNewArea(areaModel));
			}
		}
		return areas;
	}

	private static Area getNewArea(AreaModel areaModel) {
		Area area = new Area();
		
		if (null != areaModel)
		{
			area.setCityCode(areaModel.getCityCode());
			area.setCode(areaModel.getCode());
			area.setId(areaModel.getId());
			area.setName(areaModel.getName());
		}
		
		return area;
	}

	private static City getNewCity(CityModel cityModel)
	{
		City city = new City();
		
		if (null != cityModel)
		{
			city.setCode(cityModel.getCode());
			city.setId(cityModel.getId());
			city.setName(cityModel.getName());
			city.setProvinceCode(cityModel.getProvinceCode());
		}
		
		return city;
	}

	private static Province getNewProvince(ProvinceModel provinceModel) {
		Province province = new Province();
		if (null != provinceModel)
		{
			province.setCode(provinceModel.getCode());
			province.setId(provinceModel.getId());
			province.setName(provinceModel.getName());
		}
		return province;
	}

	@Autowired
	public void setAreaService(AreaService areaService) {
		AreaUtil.areaService = areaService;
	}
	
	
}
