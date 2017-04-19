package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.BKindModel;

import net.sf.json.JSONArray;

public interface F2BbKindService {
   
    public List<BKindToCModel> queryAllKind();

	void queryKindsByParentId(Map<String, Object> map, BKindModel model);

    /**
     * 
     * Description:查询所有的种类
     *
     * @author Administrator
     * @return
     */
	List<BKindModel> queryAllBKindModel();

    /**
     * Description:分页查询相关种类
     * 
     * @return Page<FAuthCodeModel>
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 上午10:13:27
     */
	Page<BKindModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap, HttpServletRequest request);

    /**
     * 新增一个种类 <br/>
     * 是否显示在手机上默认为：0. 不显示
     * 
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午2:40:05
     */
	void insertBKindModel(BKindModel modal);

    /**
     * @param TODO
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午2:54:31
     */
    DataToCResultModel<Object> deleteBKindModel(String id);

    /**
     * @param TODO
     * @return void
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月12日 下午3:48:23
     */
	int updateBKindModel(BKindModel model);

    /**
     * 根据父节点查询其下所有子
     * 
     * @param TODO
     * @return List<BKindModel>
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
	List<BKindModel> selectParentKindByFruit();


	public JSONArray getKindsForJsTree();

	public List<BKindModel> getAllKinds();

	public List<BKindModel> getSubKindByParentId(Integer kindId);
}