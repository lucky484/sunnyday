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
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.BKindModel;
import com.f2b2c.eco.service.platform.F2BbKindService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("bKindControllerManagement")
@RequestMapping("/farm/bkind")
public class BKindController extends BaseController{

	/**
     * 日志工具类
     */
	private static final Logger logger = LoggerFactory.getLogger(FKindController.class);

	@Autowired
	private F2BbKindService serv;
	
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
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public final String page(HttpServletRequest request, HttpServletResponse response) {
		return "platform/bkind";
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
	public final String addNewKind(BKindModel model, MultipartHttpServletRequest request) {

		Integer id = model.getId();
		String catalogValue = request.getParameter("catalogVal");
		if (StringUtils.isNotBlank(catalogValue)) {
			model.setCatalog(catalogValue);
		}
		// --- start icon ---
		Object fileObj = request.getFile("file_upload2");
		Object fileObj3 = request.getFile("file_upload3");
		MultipartFile file = null;
		if (fileObj instanceof MultipartFile) {
			file = (MultipartFile) fileObj;
		}
        // 插入icon
		if (file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String extName = FilenameUtils.getExtension(fileName);
			Random r = new Random();
			String fixFileName = System.currentTimeMillis() + "icon." + extName;
			Path path = Paths.get(basePath + "bkind/");
			try {
                // 如果文件夹不存在则创建
				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}
				FileCopyUtils.copy(file.getBytes(), new FileOutputStream(path.toString() + "/" + fixFileName));
				model.setIconUrl("bkind/" + fixFileName);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
        // 保存分类广告图
		MultipartFile newFile = null;
		if (fileObj3 instanceof MultipartFile) {
			newFile = (MultipartFile) fileObj3;
		}
        // 插入icon
		if (newFile != null && !newFile.isEmpty()) {
			String fileName = newFile.getOriginalFilename();
			String extName = FilenameUtils.getExtension(fileName);
			String fixFileName = System.currentTimeMillis() + "pic." + extName;
			Path path = Paths.get(basePath + "bkind/");
			try {
                // 如果文件夹不存在则创建
				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}
				FileCopyUtils.copy(newFile.getBytes(), new FileOutputStream(path.toString() + "/" + fixFileName));
				model.setPicUrl("bkind/" + fixFileName);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

        if (model.getIsCommission() == null) {
            model.setIsCommission(1);
        }
		// --- end icon ---
		if (id == null) {
			serv.insertBKindModel(model);
		} else {
			serv.updateBKindModel(model);
		}
		return "platform/bkind";
	}

	/**
     * 删除分类
     * 
     * @param id
     * @return
     */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
    public DataToCResultModel<Object> delKind(String id) {
		return serv.deleteBKindModel(id);
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
		List<BKindModel> list = serv.queryAllBKindModel();
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
	public Page<BKindModel> queryDatatable(final Pageable pageable, HttpServletRequest request,
			HttpServletResponse response, Integer userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String parent_id = (String) request.getParameter("parent_id");
		paramMap.put("parent_id", parent_id);
		Page<BKindModel> pages = serv.findWithPagination(pageable, paramMap, request);
		return pages;
	}

	@RequestMapping(value = "/get-kinds-for-jstree")
	@ResponseBody
	public JSONArray getKindsForJsTree() {
		JSONArray j = serv.getKindsForJsTree();
		return j;
	}

	private JSONArray getMainKind(List<BKindModel> list) {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("id", -1);
		obj.put("nodeId", -1);
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
	private void toJosnTree(List<BKindModel> list, JSONArray parent, JSONObject current) {
		JSONObject child = new JSONObject();
		child.put("id", current.get("id"));
		child.put("nodeId", current.get("nodeId"));
		child.put("text", current.get("text"));
		child.put("catalog", current.get("catalog"));
		child.put("level", current.get("level"));
        List<BKindModel> childs = filterListById(list, (Integer) current.get("id"));// 查找子

		if (CollectionUtils.isNotEmpty(childs)) {
			JSONArray childArr = new JSONArray();

			for (BKindModel f : childs) {
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

	private List<BKindModel> filterListById(List<BKindModel> list, Integer id) {
		List<BKindModel> filterList = new ArrayList<BKindModel>();
		if (CollectionUtils.isEmpty(list)) {
			return filterList;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getParent() != null && id.compareTo(list.get(i).getParent().getId()) == 0) {
				filterList.add(list.get(i));
			}
		}
		return filterList;
	}
}
