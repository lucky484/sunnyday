package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.model.platform.FUserModel;

import net.sf.json.JSONArray;

public interface FKindService {
   
    public List<BKindToCModel> queryAllKind();

    void queryKindsByParentId(Map<String, Object> map, FKindModel model);

    /**
     * 
     * Description:查询所有的种类
     *
     * @author Administrator
     * @return
     */
    List<FKindModel> queryAllFkindModel();

    /**
     * Description:分页查询相关种类
     * 
     * @return Page<FAuthCodeModel>
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 上午10:13:27
     */
    Page<FKindModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap, HttpServletRequest request);

    /**
     * 新增一个种类 <br/>
     * 是否显示在手机上默认为：0. 不显示
     * 
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午2:40:05
     */
    void insertFKindModel(FKindModel modal);

    /**
     * @param TODO
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午2:54:31
     */
    int deleteFKindModel(String id);

    /**
     * @param TODO
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午2:54:31
     */
    int deleteFKind(String id);
    
    /**
     * @param TODO
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午3:48:23
     */
    int updateFKindModel(FKindModel model);

    /**
     * 根据父节点查询其下所有子
     * 
     * @param TODO
     * @return List<FKindModel>
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月13日 下午1:25:25
     */
    Map<String, Object> queryChilds(Integer id);
    
    /**
     * 获取顶级水果分类
     * 
     * @return
     */
    List<FKindModel> selectParentKindByFruit();

	public JSONArray getKindsForJsTree();

	/**
     * 启用禁用分类
     */
	public int enable(Integer id, Integer enable, FUserModel user);

    public int updateFkindWeight(Integer id, Integer weight);
}