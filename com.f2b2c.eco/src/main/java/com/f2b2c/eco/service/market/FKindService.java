package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FKindModel;

public interface FKindService {

	public List<BKindToCModel> queryAllKind();

	public List<String> findChdKind(Integer id);

	DataToCResultModel<Map<String, Object>> queryFirstKind(Integer pId);

	List<BKindToCModel> getSubKindList(Integer pId);

	public List<FKindModel> getAllKinds();

	public List<FKindModel> getSubKindByParentId(Integer kindId);
	
	List<BKindToCModel> queryAllKindTo();

    // 根据ID查询是否是水果分类
	public String findcatalogById(Integer kindId);

    // 根据分类Id查询子分类
	public List<BKindToCModel> queryKindListByKindId(Integer kindId);

    String findIsCommissionById(Integer id);

}
