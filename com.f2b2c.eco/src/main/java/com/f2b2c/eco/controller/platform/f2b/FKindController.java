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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.platform.FKindService;
import com.f2b2c.eco.status.StorageStatus;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: FAuthorizeController
 * @Description: 授权码管理
 * @author ligang.yang@softtek.com
 * @date 2016年9月8日 下午5:02:28
 *
 */
@Controller(value = "fKindController")
@RequestMapping("/farm/admin/kind")
public class FKindController {
	/**
     * 日志工具类
     */
	private static final Logger logger = LoggerFactory.getLogger(FKindController.class);

	@Autowired
	private FKindService serv;
	
	@Value("${fileSavePath}")
	private String basePath;

	/*
     * 显示界面
     * 
     * @param request
     * 
     * @param response
     * 
     * @return 返回登录视图名称
     */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public final String page(HttpServletRequest request, HttpServletResponse response) {
		return "platform/main-kind";
	}

	/*
     * 显示面
     * 
     * @param request
     * 
     * @param response
     * 
     * @return 返回登录视图名称
     */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public final String addNewKind(FKindModel model, MultipartHttpServletRequest request) {

		Integer id = model.getId();
		String catalogValue = request.getParameter("catalogVal");
		if(StringUtils.isNotBlank(catalogValue)){
			model.setCatalog(catalogValue);
		}
		// --- start icon ---
		Object fileObj = request.getFile("file_upload2");
		MultipartFile file = null;
		if (fileObj instanceof MultipartFile) {
			file = (MultipartFile) fileObj;
		}
		if (file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String extName = FilenameUtils.getExtension(fileName);
			String fixFileName = System.currentTimeMillis() + "." + extName;
			Path path = Paths.get(basePath + "kind/");
			try {
                // 如果文件夹不存在则创建
				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}
				FileCopyUtils.copy(file.getBytes(), new FileOutputStream(path.toString() + "/" + fixFileName));
				model.setIconUrl("kind/" + fixFileName);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		// --- end icon ---
		if (id == null) {
			serv.insertFKindModel(model);
		} else {
			serv.updateFKindModel(model);
		}
		return "platform/main-kind";
	}

	/*
     * 显示登录界面
     * 
     * @param request
     * 
     * @param response
     * 
     * @return 返回登录视图名称
     */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public int delKind(String id) {
		 return serv.deleteFKindModel(id);
	}

	/**
     * 查询相关的2级分类产品
     * 
     * @return String
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月10日 下午2:49:14
     */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public final Map<String, Object> queryKindTree(@RequestParam("id") Integer id) {
		Map<String, Object> map = serv.queryChilds(id);
		return map;
	}

	
	@RequestMapping(value = "/btree/json", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getKindTreeJson(HttpServletRequest request) {
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
	public Page<FKindModel> queryDatatable(final Pageable pageable, HttpServletRequest request, HttpServletResponse response, Integer userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String parent_id = (String) request.getParameter("parent_id");
		paramMap.put("parent_id", parent_id);
		Page<FKindModel> pages = serv.findWithPagination(pageable, paramMap, request);
		return pages;
	}
	
	@RequestMapping(value = "/get-kinds-for-jstree")
	@ResponseBody
	public JSONArray getKindsForJsTree() {
		JSONArray j = serv.getKindsForJsTree();
		return j;
	}

	private JSONArray getMainKind(List<FKindModel> list) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("id", 1000);
		obj.put("nodeId", 1000);
        obj.put("text", "分类");
		obj.put("catalog", "");
        obj.put("level", 0);
		toJosnTree(list, array, obj);
		return array;
	}

	/**
	 * 
	 * @param list
	 * @param parent
	 * @param current
	 */
	private void toJosnTree(List<FKindModel> list, JSONArray parent, JSONObject current) {
		JSONObject child = new JSONObject();
		child.put("id", current.get("id"));
		child.put("nodeId", current.get("nodeId"));
		child.put("text", current.get("text"));
		child.put("catalog", current.get("catalog"));
        child.put("level", current.get("level"));
        List<FKindModel> childs = filterListById(list, (Integer) current.get("id"));// 查找子
		
		if (CollectionUtils.isNotEmpty(childs)) {
			JSONArray childArr = new JSONArray();
			
			for (FKindModel f : childs) {
				JSONObject fchild = new JSONObject();
				fchild.put("id", f.getId());
				fchild.put("nodeId", f.getId());
				fchild.put("text", f.getKindName());
				fchild.put("catalog", f.getCatalog());
                Integer level = (Integer) current.get("level");
                fchild.put("level", Integer.valueOf(level) + 1);
				toJosnTree(list, childArr, fchild);
			}
			child.put("nodes", childArr);
		}
		parent.add(child);
	}

	private List<FKindModel> filterListById(List<FKindModel> list, Integer id) {
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

	/*
     * 显示面
     * 
     * @param request
     * 
     * @param response
     * 
     * @return 返回登录视图名称
     */
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public final int enable(String id, String enable, HttpServletRequest request) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(enable)) {
			return serv.enable(Integer.valueOf(id), Integer.valueOf(enable), user);
		} else {
			return 0;
		}
	}

    /**
     * 设置平台分类权重
     */
    @RequestMapping(value = "/update-fkind-weight", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> updateFgoodsWeight(HttpServletRequest request, HttpSession session) {
        String id = request.getParameter("id");
        String weight = request.getParameter("weight");
        int size = serv.updateFkindWeight(Integer.valueOf(id), Integer.valueOf(weight));
        if (size > 0) {
            return new DataToCResultModel<Object>(200, "success", null);
        } else {
            return new DataToCResultModel<Object>(400, "erro", null);
        }
    }
}
