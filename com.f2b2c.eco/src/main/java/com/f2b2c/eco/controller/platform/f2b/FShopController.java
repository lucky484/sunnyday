/*
 * @Title: FAuthorizeController.java
 * @Package com.f2b2c.eco.controller.platform.f2b
 * @Description: 授权码管理
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年9月8日 下午5:02:28
 * @version V1.0
 */
package com.f2b2c.eco.controller.platform.f2b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.service.platform.FKindService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: FAuthorizeController
 * @Description: 授权码管理
 * @author ligang.yang@softtek.com
 * @date 2016年9月8日 下午5:02:28
 *
 */
@Controller("fshopController")
@RequestMapping("/farm/admin/fshop")
public class FShopController
{

    @Autowired
    private FKindService serv;

    /* 显示登录界面
     * 
     * @param request
     * @param response
     * @return 返回登录视图名称
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public final String page(HttpServletRequest request, HttpServletResponse response)
    {
        return "platform/main-kind";
    }


    /* 显示登录界面
     * 
     * @param request
     * @param response
     * @return 返回登录视图名称
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public final String addNewKind(FKindModel model)
    {
        //        Integer id = model.getId();
        //        if (id == null)
        //        {
        //            serv.insertFKindModel(model);
        //        }
        //        else
        //        {
        //            serv.updateFKindModel(model);
        //        }
        return "platform/add-product";
    }

    /* 显示登录界面
     * 
     * @param request
     * @param response
     * @return 返回登录视图名称
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public final int delKind(String id)
    {
        return serv.deleteFKind(id);
    }


    @RequestMapping(value = "/btree/json", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getKindTreeJson(HttpServletRequest request)
    {
        List<FKindModel> list = serv.queryAllFkindModel();
        JSONArray array = getMainKind(list);
        return array;
    }

    /**
     * 查询相关的2级分类产品
     * 
     * @return String
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月10日 下午2:49:14
     */
    @RequestMapping(value = "/datatable/json", method = RequestMethod.GET)
    @ResponseBody
    public Page<FKindModel> queryDatatable(final Pageable pageable, HttpServletRequest request,
            HttpServletResponse response, Integer userId)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String parent_id = (String) request.getParameter("parent_id");
        paramMap.put("parent_id", parent_id);
        Page<FKindModel> pages = serv.findWithPagination(pageable, paramMap, request);
        return pages;
    }

    private JSONArray getMainKind(List<FKindModel> list)
    {
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("id", 1000);
        obj.put("nodeId", 1000);
        obj.put("text", "分类");
        obj.put("catalog", "");
        toJosnTree(list, array, obj);
        return array;
    }
    
    private void toJosnTree(List<FKindModel> list, JSONArray parent, JSONObject current)
    {
        JSONObject child = new JSONObject();
        child.put("id", current.get("id"));
        child.put("nodeId", current.get("nodeId"));
        child.put("text", current.get("text"));
        child.put("catalog", current.get("catalog"));
        List<FKindModel> childs = filterListById(list, (Integer) current.get("id"));// 查找子
        if (CollectionUtils.isNotEmpty(childs))
        {
            JSONArray childArr = new JSONArray();
            for (FKindModel f : childs)
            {
                JSONObject fchild = new JSONObject();
                fchild.put("id", f.getId());
                fchild.put("nodeId", f.getId());
                fchild.put("text", f.getKindName());
                fchild.put("catalog", f.getCatalog());
                toJosnTree(list, childArr, fchild);
            }
            child.put("nodes", childArr);
        }
        parent.add(child);
    }

    private List<FKindModel> filterListById(List<FKindModel> list, Integer id)
    {
    	List<FKindModel> filterList=new ArrayList<FKindModel>();
		if(CollectionUtils.isEmpty(list)){
		      return filterList;
		}

		for(int i=0;i<list.size();i++){
		      if(list.get(i).getParent()!=null&&id.compareTo(list.get(i).getParent().getId())==0){
		            filterList.add(list.get(i));
		      }
		}
		return filterList;
    }
}
