package com.f2b2c.eco.service.platform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FMenuDao;
import com.f2b2c.eco.dao.platform.FUserDao;
import com.f2b2c.eco.model.common.AreaModel;
import com.f2b2c.eco.model.common.CityModel;
import com.f2b2c.eco.model.common.ProvinceModel;
import com.f2b2c.eco.model.platform.FMenuModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.util.AreaUtil;

/**
 * 用户服务类
 * 
 * @author brave.chen
 *
 */
@Service
public class FUserServiceImpl implements FUserService {
	@Autowired
	private FUserDao fUserDao;
	@Autowired
	private FMenuDao fMenuDao;

	@Override
	public boolean checkIsExist(String accountName) {
		FUserModel fUserModel = fUserDao.queryUserByAccountName(accountName);

		// 用户存在
		if (null != fUserModel) {
			return true;
		}

		return false;
	}

	@Override
	public void saveFUserModel(FUserModel fUserModel) {
		// 保存用户对象
		fUserDao.insert(fUserModel);
	}

	@Override
	public void updateFUserModelInfo(FUserModel fUserModel) {
		fUserDao.update(fUserModel);
	}

	/**
	 * 删除用户（假删除，更新delted_time字段）
	 */
	@Override
	public void delete(Integer userId) {
		fUserDao.delete(userId);
	}

	@Override
	public Map<String, Object> queryParamsByUserId(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FUserModel fUserModel = fUserDao.select(userId);
		Integer areaId = null;
		if (null != fUserModel) {
			areaId = fUserModel.getAreaId();
		}

		List<ProvinceModel> provinces = AreaUtil.getProvinceModels();
		List<CityModel> cityModels = new ArrayList<CityModel>();
		List<AreaModel> areaModels = new ArrayList<AreaModel>();

		String selectedCityCode = null;
		String selectedCityName = null;
		String selectedProvinceCode = null;
		String selectedProvinceName = null;
		String selectAreadName = null;
		if (CollectionUtils.isNotEmpty(provinces)) {
			for (ProvinceModel provinceModel : provinces) {
				if (StringUtils.isNotEmpty(selectedCityCode)) {
					break;
				}
				cityModels = provinceModel.getCityModelList();
				selectedProvinceName = provinceModel.getName();
				if (CollectionUtils.isNotEmpty(cityModels)) {
					for (CityModel cityModel : cityModels) {
						if (StringUtils.isNotEmpty(selectedCityCode)) {
							break;
						}
						selectedProvinceCode = cityModel.getId().toString();
						selectedCityName = cityModel.getName();
						areaModels = cityModel.getAreaModelList();
						if (CollectionUtils.isNotEmpty(areaModels)) {
							for (AreaModel areaModel : areaModels) {
								if (areaModel.getId().equals(areaId)) {
									selectAreadName = areaModel.getName();
									selectedCityCode = areaModel.getId().toString();
									break;
								}
							}
						}

					}
				}
			}
		}

		map.put("provinces", provinces);
		map.put("cityModels", cityModels);
		map.put("areaModels", areaModels);
		map.put("fUserModel", fUserModel);
		map.put("selectedProvinceCode", selectedProvinceCode);
		map.put("selectedCityCode", selectedCityCode);
		map.put("selectedCityName", selectedCityName);
		map.put("selectedProvinceName", selectedProvinceName);
		map.put("selectAreadName", selectAreadName);

		// List<FRoleModel> fRoleModels = fRoleDao.findAll();
		// map.put("fUserModel", fUserModel);
		// map.put("fRoleModels", fRoleModels);
		return map;
	}

	public Page<FUserModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
		paramMap.put("start", pageable.getPageNumber());
		paramMap.put("offset", pageable.getPageSize());

		List<FUserModel> fUserModels = fUserDao.findWithPagination(paramMap);
		int total = fUserDao.queryCountByCondition(paramMap);

		Page<FUserModel> pages = new Pagination<FUserModel>(fUserModels, pageable, total);
		return pages;
	}

	@Override
	public FUserModel queryFUserModelById(Integer userId) {
		return fUserDao.queryUserById(userId);
	}

	@Override
	public FUserModel queryFUserModelByAccountName(String accountName) {
		return fUserDao.queryUserByAccountName(accountName);
	}

	@Override
	public FUserModel queryOne(FUserModel user) {
		if (null != user) {
			FUserModel fUser = fUserDao.findOne(user);
			return null != fUser ? fUser : null;
		}
		return null;
	}

	@Override
	public FUserModel findOneByAccount(String account) {
		return fUserDao.queryUserByAccountName(account);
	}

	@Override
	public List<FMenuModel> getAuthcationMenus(String userId) {
		return fMenuDao.queryMenuByUserId(Integer.valueOf(userId));
	}

	@Override
	public FUserModel findOneById(Integer id) {

		return fUserDao.findOneById(id);
	}

	@Override
	public FUserModel getCreateUserInfoByShopId(Integer shopId) {
		return fUserDao.getCreateUserInfoByShopId(shopId);
	}

	@Override
	public FUserModel getUserByAreaId(Integer areaId, String roleId) {
		
		Map<Object,Object> paramMap = new HashMap<Object,Object>();
		paramMap.put("areaId", areaId);
		paramMap.put("roleId",Integer.valueOf(roleId));
		return fUserDao.getUserByAreaId(paramMap);
	}

    /**
	 * 校验是否存在满足条件的合伙人
	 * 
	 * @param paramMap
	 * @return
	 */
    public Integer checkPartner(Map<String, Object> paramMap) {
        return fUserDao.checkPartner(paramMap);
    }

	@Override
	public Integer getCityIdByAreaId(Integer areaId) {
		
		return fUserDao.getCityIdByAreaId(areaId);
	}

	@Override
	public Integer getProvinceIdByAreaId(Integer areaId) {
		
		return fUserDao.getProvinceIdByAreaId(areaId);
	}

	@Override
	public FUserModel getUserByCityId(Integer cityId, String roleId) {
		
		Map<Object,Object> paramMap = new HashMap<Object,Object>();
		paramMap.put("cityId", cityId);
		paramMap.put("roleId",Integer.valueOf(roleId));
		return fUserDao.getUserByCityId(paramMap);
	}

	@Override
	public FUserModel getUserByProvinceId(Integer provinceId, String roleId) {
		
		Map<Object,Object> paramMap = new HashMap<Object,Object>();
		paramMap.put("provinceId", provinceId);
		paramMap.put("roleId",Integer.valueOf(roleId));
		return fUserDao.getUserByProvinceId(paramMap);
	}

	@Override
	public List<Integer> queryUserIdByAreaId(List<Integer> list) {
		
		return fUserDao.queryUserIdByAreaId(list);
	}

	@Override
	public List<Integer> queryUserId(List<Integer> list) {

		return fUserDao.queryUserId(list);
	}

}
