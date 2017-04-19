package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.bean.FkindBean;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.platform.FKindModel;

public interface FKindDao extends CrudMapper<FKindModel, Serializable>{
    
    public List<BKindToCModel> queryAllKind();
    
    public List<BKindToCModel> queryAllKindOnPhone();
    
    List<FKindModel> queryKindsByParentId(Integer id);
    List<BKindToCModel> queryAllKindsForTreeByParentId(Integer id);

    List<FKindModel> queryAllFKind();

    /**
     * 查询分页条数
     * 
     * @return int
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 上午10:20:57
     */
    int queryCountByCondition(Map<String, Object> paramMap);

    /**
     * 分页查询
     * 
     * @return List<FUserModel>
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月10日 下午1:42:44
     */
    List<FKindModel> findWithPagination(Map<String, Object> paramMap);

    /**
     * 根据主键删除kind
     * 
     * @return int
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午3:15:45
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 更形model， 如果不传入参数， 则不会更新相应字段
     * 
     * @return int
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午3:53:00
     */
    int updateByPrimaryKeySelective(FKindModel model);

    /**
     * 根据主键查相应的kind
     * 
     * @return FKindModel
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月13日 下午4:46:11
     */
    FKindModel selectByPrimaryKey(Integer id);

    public List<String> findChdKind(Integer id);
    
    /**
     * 前端获取一级分类
     * 
     * @param pId
     *            父id
     * @return 返回一级分类
     */
    List<BKindToCModel> queryFrontKindByPId(Integer pId);
    
    /**
     * 前端获取二级分类
     * 
     * @param pId
     *            父id
     * @return 返回二级分类
     */
    List<BKindToCModel> queryFrontSecondKindByPId(Integer pId);
    
    /**
     * 获取水果顶级分类
     * 
     * @return 返回水果顶级分类
     */
    List<FKindModel> selectParentKindByFruit();
    
    /**
     * 根据父id获取份额里id list
     * 
     * @param pId
     * @return
     */
    List<Integer> queryKindIdListByPId(Integer pId);
    
    /**
     * 获取F端的所有水果分类
     * 
     * @return
     */
    public List<FKindModel> getAllKinds();
    
    /**
     * 根据父分类或者所有的子分类
     * 
     * @param kindId
     * @return
     */
    public List<FKindModel> getSubKindByParentId(Integer kindId);

    public List<FKindModel> getAllKindByParentId(Integer kindId);
    
    List<BKindToCModel> queryAllKindTo();

    public String findcatalogById(Integer kindId);
    
    /**
     * 获取一级分类水果id
     * 
     * @return
     */
    Integer queryFirstCategoryId();
    
    /**
     * 根据父id获取子分类
     * 
     * @param pId:父分类
     * @return 返回子分类列表
     */
    List<FkindBean> queryFKindByPid(Integer pId);

    /**
     * 根据父级id获取过滤三级分类
     * 
     * @param pId:父分类
     * @return 返回子分类列表
     */
    List<FkindBean> queryFilterTCateById(Page page);
    
    public int enable(Map<String, Object> map);
    
    /**
     * 批量删除分类
     * 
     * @param list
     * @return
     */
    int deleteBatchFKindByIds(List<String> list);

    int updateFkindWeight(Integer id, Integer weight);

}