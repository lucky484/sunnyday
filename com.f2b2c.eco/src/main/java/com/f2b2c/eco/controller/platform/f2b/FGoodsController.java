package com.f2b2c.eco.controller.platform.f2b;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.common.ProvinceModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.common.AreaService;
import com.f2b2c.eco.service.platform.FGoodsService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.AreaUtil;
import com.f2b2c.eco.util.ConstantUtil.RoleType;
import com.f2b2c.eco.util.FileUtil;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.PropertiesUtil;
import com.f2b2c.eco.util.SequenceUtil;

/**
 * 平台商品管理
 * 
 * @author Jacob Shen
 *
 */
@Controller("FGoodsController")
@RequestMapping("/farm/goods")
public class FGoodsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FGoodsController.class);

	@Autowired
	private FGoodsService fGoodsService;
	
	@Autowired
	private AreaService areaService;

	/**
     * 商品发布页
     */
	@RequestMapping(value = "/add-fgoods", method = RequestMethod.GET)
	public final String fGoodsList(HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("path", PropertiesUtil.getValue("path"));
		List<ProvinceModel> provinces = AreaUtil.getProvinceModels();
		model.addAttribute("provinces", provinces);
		return "platform/add-product";
	}

	@RequestMapping(value = "/delete-fgoods-pic", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> deleteFGoodsPic(MultipartHttpServletRequest request) {
		String key = request.getParameter("key");
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
		dataToCResult.setData(key);
		return dataToCResult;
	}

	@RequestMapping(value = "/insert-fgoods-pic", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> insertFGoodsPic(MultipartHttpServletRequest request) {
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
		List<MultipartFile> files = request.getFiles("file");
		StringBuilder urls = new StringBuilder();
		for (MultipartFile file : files) {
			if (StringUtils.isEmpty(file.getOriginalFilename())) {
				continue;
			}
			try {
				String path = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "fGoods", FileUtil.getFileType(file.getOriginalFilename()));
				urls.append(path + "|");
			} catch (IOException e) {
				logger.error(e.getMessage());
				dataToCResult.setStatus(400);
				dataToCResult.setMsg("false");
				return dataToCResult;
			}
		}
        // 删除最后一个|分隔符
		if (urls.length() > 0) {
			urls.deleteCharAt(urls.length() - 1);
		}
		List<String> list = new ArrayList<>();
		list.add(urls.toString());
		list.add(PropertiesUtil.getValue("path") + urls.toString());
		dataToCResult.setStatus(200);
		dataToCResult.setMsg("true");
		dataToCResult.setData(list);
		return dataToCResult;
	}

	/**
     * 添加平台商品
     */
	@RequestMapping(value = "/insert-fgoods", method = RequestMethod.POST)
	public final String insertFGoods(FGoodsModel fGoods, HttpServletRequest request, HttpServletResponse response) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		String[] urls = request.getParameterValues("logoUrl");
		String pricex = request.getParameter("pricex");
		StringBuilder picUrl = new StringBuilder();
		for (String url : urls) {
			if (!StringUtils.isEmpty(url)) {
				picUrl.append(url + "|");
			}
		}
        // 删除最后一个|分隔符
		if (picUrl.length() > 0) {
			picUrl.deleteCharAt(picUrl.length() - 1);
		}
		fGoods.setPrice(StringUtils.isEmpty(pricex) ? 0 : (int) (Double.valueOf(pricex) * 100));
		fGoods.setLogoUrl(picUrl.toString());
		fGoods.setReleaseTime(new Date());
		fGoods.setIsCopy(0);
		fGoods.setVersion(0);
		fGoods.setReleaseUser(user);
		fGoods.setUpdatedUser(user);
		String goodsNo = SequenceUtil.nextId("F");
		if (StringUtils.isNotBlank(goodsNo)) {
			fGoods.setGoodsNo(goodsNo);
		}
	    fGoodsService.insertFGoods(fGoods);

		return "platform/fshoplist";
	}

	/**
     * 修改平台商品
     */
	@RequestMapping(value = "/update-fgoods", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> updateFGoods(FGoodsModel fGoods, MultipartHttpServletRequest request) {
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
        // 找到现有记录,并将其变成副本
		FGoodsModel existFGoods = fGoodsService.queryFGoodsInfoById(fGoods.getId());
		existFGoods.setIsCopy(1);
		fGoodsService.updateFGoods(existFGoods);
		List<MultipartFile> list = request.getFiles("picture");
        // 多个图片url，以|分割
		StringBuilder picUrl = new StringBuilder();
		for (MultipartFile file : list) {
			try {
				String path = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "fGoods", FileUtil.getFileType(file.getOriginalFilename()));
				picUrl.append(path + "|");
			} catch (IOException e) {
				logger.error(e.getMessage());
				dataToCResult.setStatus(400);
				dataToCResult.setMsg("false");
				return dataToCResult;
			}
		}
        // 删除最后一个|分隔符
		if (picUrl.length() > 0) {
			picUrl.deleteCharAt(picUrl.length() - 1);
		}
		String pricex = request.getParameter("pricex");
		fGoods.setPrice(StringUtils.isEmpty(pricex) ? 0 : (int) (Double.valueOf(pricex) * 100));
		fGoods.setLogoUrl(picUrl.toString());
		fGoods.setReleaseTime(new Date());
		fGoods.setIsCopy(0);
		fGoods.setVersion(existFGoods.getVersion() + 1);
		int result = fGoodsService.insertFGoods(fGoods);
		if (result == 1) {
            logger.info("修改平台商品成功");
			dataToCResult.setStatus(200);
			dataToCResult.setMsg("success");
			dataToCResult.setData(JsonUtil.parseToNoEmptyStr(fGoods));
		} else {
            logger.info("修改平台商品失败");
			dataToCResult.setStatus(400);
			dataToCResult.setMsg("false");
		}
		return dataToCResult;
	}

	/**
     * 查询所有商品
     */
	@RequestMapping(value = "/query-all-fgoods", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> queryAllFGoods(Integer page, Integer pageSize, HttpServletRequest request) {
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
		try {
			if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
                // 查询总记录条数
				int totalCount = fGoodsService.findAllFGoodsCount();
                // 分页对象构造
				Page p = new Page(totalCount, page, pageSize);
				List<FGoodsModel> rows = fGoodsService.queryAllFGoods(p.getStart(), pageSize);
				p.setRows(rows);
				dataToCResult.setStatus(200);
				dataToCResult.setMsg("success");
				dataToCResult.setData(JsonUtil.parseToNoEmptyStr(p));
			} else {
				dataToCResult.setStatus(400);
                dataToCResult.setMsg("请求参数不能为空");
			}
			return dataToCResult;
		} catch (Exception e) {
			dataToCResult.setStatus(400);
            dataToCResult.setMsg("获取商品数据失败");
			return dataToCResult;
		}
	}

	/**
     * 根据商品分类id查询商品列表(分页)
     * 
     * @param pageable
     *            //分页对象
     * @param kindId
     *            //类型Id
     * @return dataToCResult
     */
	/*
     * @SuppressWarnings("static-access")
     * 
     * @RequestMapping(value = "/query-bgoods-info-by-kindid", method =
     * RequestMethod.POST)
     * 
     * @ResponseBody public DataToCResultModel<Object>
     * queryBGoodsByKindId(Integer kindId, Integer page, Integer pageSize,
     * HttpServletRequest request) { DataToCResultModel<Object> dataToCResult =
     * new DataToCResultModel<Object>(); try { if (page != null &&
     * !page.equals("") && pageSize != null && !pageSize.equals("")) { //
     * 查询总记录条数 int totalCount =
     * fGoodsService.findAllBGoodsByKindIdCount(kindId); if (totalCount > 0) {
     * Page p = new Page(totalCount, page, Integer.valueOf(pageSize)); //
     * 根据商品类型,起始数字,每页尺寸 List<BGoodsToCModel> rows =
     * fGoodsService.queryBGoodsByKindId(kindId, p.getStart(), pageSize);
     * JsonUtil jsonUtil = new JsonUtil(); p.setRows(rows);
     * dataToCResult.setStatus(200); dataToCResult.setMsg("success");
     * p.setParams(null); dataToCResult.setData(jsonUtil.parseToNoEmptyStr(p));
     * } else { dataToCResult.setStatus(400); dataToCResult.setMsg("该类型没有商品"); }
     * } else { dataToCResult.setStatus(400); dataToCResult.setMsg("请求参数不能为空");
     * } return dataToCResult; } catch (Exception e) {
     * dataToCResult.setStatus(400); dataToCResult.setMsg("获取商品数据失败"); return
     * dataToCResult; } }
     */

	/**
     * 根据商品id查询商品介绍
     */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/query-fgoods-info-byid", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> queryFGoodsInfoById(String id, HttpServletRequest request) {
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				FGoodsModel fGoodsModel = fGoodsService.queryFGoodsInfoById(Integer.valueOf(id));
				dataToCResult.setStatus(200);
				dataToCResult.setMsg("success");
				dataToCResult.setData(JsonUtil.parseToNoEmptyStr(fGoodsModel));
			} else {
				dataToCResult.setStatus(400);
                dataToCResult.setMsg("请求参数不能为空");
			}
			return dataToCResult;
		} catch (Exception e) {
			dataToCResult.setStatus(400);
            dataToCResult.setMsg("获取商品数据失败");
			return dataToCResult;
		}
	}

	/**
     * 平台商品列表 页
     * 
     * @param request
     * @param response
     * @return 平台商品列表 页
     */
	@RequestMapping(value = "/fshoplist", method = RequestMethod.GET)
	public final String fShopList(HttpServletRequest request, HttpServletResponse response) {
		return "platform/fshoplist";
	}

	/**
     * 商品列表
     */
	@RequestMapping(value = "/fgoodsPage", method = RequestMethod.POST)
	@ResponseBody
	public final org.springframework.data.domain.Page<FGoodsModel> queryByPages(final Pageable pageable, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
//		List<Integer> areaListAll = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		if(!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID)){
            /*
             * if(user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)){ //省合伙人
             * List<Integer> list =
             * areaService.queryCityIdByProvinceId(user.getProvinceid());
             * //获取所有的市id List<Integer> areaList =
             * areaService.queryAreaIdByCityId(list);
             * areaListAll.add(user.getId()); areaListAll.addAll(list);
             * areaListAll.addAll(areaList); }else
             * if(user.getRoleId().equals(RoleType.CITY_ROLE_ID)){ List<Integer>
             * cityList = new ArrayList<Integer>(); cityList.add(user.getId());
             * List<Integer> areaList =
             * areaService.queryAreaIdByCityId(cityList);
             * areaListAll.add(user.getId()); areaListAll.addAll(areaList);
             * }else if(user.getRoleId().equals(RoleType.AREA_ROLE_ID)){
             * areaListAll.add(user.getId()); } userList =
             * fUserService.queryUserIdByAreaId(areaListAll);
             */
			userList.add(user.getId());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", pageable.getPageNumber());
		paramMap.put("offset", pageable.getPageSize());
		paramMap.put("name", request.getParameter("name"));
		paramMap.put("kind", request.getParameter("kind"));
		paramMap.put("productPlace", request.getParameter("productPlace"));
		paramMap.put("userList",userList);
		org.springframework.data.domain.Page<FGoodsModel> pages = fGoodsService.findFgoodsPages(pageable, paramMap);
		return pages;
	}

	/**
     * 商品查看
     */
	@RequestMapping(value = "/fgoodsdetail", method = RequestMethod.GET)
	public final ModelAndView bgoodsdetail(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String id = request.getParameter("id");
		FGoodsModel fGoodsModel = fGoodsService.findFgoodsById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> urllist = new ArrayList<>();
		String path = PropertiesUtil.getValue("path");
		if (fGoodsModel.getLogoUrl() != null) {
			String[] url = fGoodsModel.getLogoUrl().split("\\|");
			for (int i = 0; i < url.length; i++) {
				urllist.add(path + url[i]);
			}
		}
		;
		map.put("urllist", urllist);
		map.put("data", fGoodsModel);
		return new ModelAndView("platform/fgoods/details", map);
	}

	/**
     * 商品更新版本页面
     */
	@RequestMapping(value = "/modifyfgoodsurl", method = RequestMethod.GET)
	public final ModelAndView modifyfgoodsurl(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String id = request.getParameter("id");
		FGoodsModel fGoodsModel = fGoodsService.findFgoodsById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProvinceModel> provinces = AreaUtil.getProvinceModels();
		map.put("data", fGoodsModel);
		map.put("provinces", provinces);
		map.put("path", PropertiesUtil.getValue("path"));
		return new ModelAndView("platform/fgoods/modify", map);
	}

	/**
     * 修改平台商品
     */
	@RequestMapping(value = "/modifyfgoods", method = RequestMethod.POST)
	public final String modifybgoods(FGoodsModel fGoods, HttpServletRequest request, HttpSession session) {
		FUserModel user = (FUserModel) session.getAttribute(StorageStatus.USER_INSESSION.name());
		String[] urls = request.getParameterValues("logoUrl");
		String pricex = request.getParameter("pricex");
		fGoods.setPrice(StringUtils.isEmpty(pricex) ? 0 : (int) (Double.valueOf(pricex) * 100));
		StringBuilder picUrl = new StringBuilder();
		for (String url : urls) {
			if (!StringUtils.isEmpty(url)) {
				picUrl.append(url + "|");
			}
		}
        // 删除最后一个|分隔符
		if (picUrl.length() > 0) {
			picUrl.deleteCharAt(picUrl.length() - 1);
		}
		fGoods.setLogoUrl(picUrl.toString());
		fGoods.setReleaseTime(new Date());
		fGoods.setIsCopy(0);
		int size = fGoodsService.modifyfgoods(fGoods, user);
		return "platform/fshoplist";
	}

	/**
     * 下架平台商品
     */
	@RequestMapping(value = "/down-or-release", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> nextshelfbgoods(HttpServletRequest request, HttpSession session) {
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		int size = fGoodsService.downOrReleaseFgoods(Integer.valueOf(id), Integer.valueOf(status));
		return new DataToCResultModel<Object>(200, "success", null);
	}

    /**
     * 设置平台商品权重
     */
    @RequestMapping(value = "/update-fgoods-weight", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> updateFgoodsWeight(HttpServletRequest request, HttpSession session) {
        String id = request.getParameter("id");
        FGoodsModel model = fGoodsService.findFgoodsInfoById(Integer.valueOf(id));
        String weight = request.getParameter("weight");
        int size = fGoodsService.updateFgoodsWeight(model.getGoodsNo(), Integer.valueOf(weight));
        if (size > 0) {
            return new DataToCResultModel<Object>(200, "success", null);
        } else {
            return new DataToCResultModel<Object>(400, "erro", null);
        }
    }

}
