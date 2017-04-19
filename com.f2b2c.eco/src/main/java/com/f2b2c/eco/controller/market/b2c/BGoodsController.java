package com.f2b2c.eco.controller.market.b2c;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.FileUtil;
import com.f2b2c.eco.util.PropertiesUtil;
import com.f2b2c.eco.util.SequenceUtil;


/**
 * 商品操作
 * 
 * @author jing.liu
 *
 */
@Controller("BGoodsController")
@RequestMapping(value = "/market/bgoods")
public class BGoodsController {
    /**
     * 日志记录器
     */
    private Logger logger=LoggerFactory.getLogger(BGoodsController.class);

    @Autowired
    private BGoodsService bGoodsService;

    @Autowired
    private BShopInfoService bShopInfoService;


    /**
     * 商品发布
     */
    @RequestMapping(value = "/publishgoods", method = RequestMethod.GET)
    public final String publishGoods(HttpServletRequest request, HttpServletResponse response) {
        return "market/publishGoods";
    }

    /**
     * 商品列表
     */
    @RequestMapping(value = "/bgoodslist", method = RequestMethod.GET)
    public final String bgoodslist(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "market/bgoodslist";
    }

    /**
     * 商品查看
     */
    @RequestMapping(value = "/bgoodsdetail", method = RequestMethod.GET)
    public final ModelAndView bgoodsdetail(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String id = request.getParameter("id");
        BGoodsModel bGoodsModel = bGoodsService.findBgoodsById(Integer.valueOf(id));
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> urllist = new ArrayList<>();
        String path = PropertiesUtil.getValue("path");
        if (bGoodsModel.getLogoUrl() != null) {
            String[] url = bGoodsModel.getLogoUrl().split("\\|");
            for (int i = 0; i < url.length; i++) {
                urllist.add(path + url[i]);
            }
		}
        map.put("urllist", urllist);
        map.put("data", bGoodsModel);
        return new ModelAndView("market/bgoodsdetail", map);
    }

    /**
     * 商品列表
     */
	@RequestMapping(value = "/bgoodsPage", method = RequestMethod.POST)
    @ResponseBody
    public final org.springframework.data.domain.Page<BGoodsModel> queryByPages(final Pageable pageable,
            HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
		if (user != null) {
			BShopInfoModel bShopInfoModel = bShopInfoService.findShopInfoByUserId(user.getId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shopId", bShopInfoModel.getId());
			paramMap.put("name", request.getParameter("name"));
			paramMap.put("kind", request.getParameter("kind"));
			paramMap.put("productPlace", request.getParameter("productPlace"));
			paramMap.put("status", request.getParameter("status"));
			org.springframework.data.domain.Page<BGoodsModel> pages = bGoodsService.findBgoodsByShopId(pageable,
					paramMap);
        return pages;
		} else {
			return null;
		}

    }

    /**
     * 添加商品图片
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/insert-bgoods-pic", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> insertBGoodsPic(MultipartHttpServletRequest request) {
        DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
        List<MultipartFile> files = request.getFiles("file");
        StringBuilder urls = new StringBuilder();
        for (MultipartFile file : files) {
            if (StringUtils.isEmpty(file.getOriginalFilename())) {
                continue;
            }
            try {
                String path = FileUtil.copy(file.getBytes(), PropertiesUtil.getValue("fileSavePath"), "bGoods",
                        FileUtil.getFileType(file.getOriginalFilename()));
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
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/insert-bgoods", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> insertBGoods(BGoodsModel bGoodsModel, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        try {
            BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
            // 店铺对象
            if (user != null) {
                BShopInfoModel bShopInfoModel = bShopInfoService.findShopInfoByUserId(user.getId());
                bGoodsModel.setShop(bShopInfoModel);
                String[] urls = bGoodsModel.getLogoUrl().split(",");
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
                String kindId = request.getParameter("kindId");
                FKindModel fKindModel = new FKindModel();
                fKindModel.setId(Integer.valueOf(kindId));
                // fKindModel.setId(2);
                bGoodsModel.setLogoUrl(picUrl.toString());
                String pricex = request.getParameter("pricex");
                bGoodsModel.setPrice(StringUtils.isEmpty(pricex) ? 0 : (int) (Double.valueOf(pricex) * 100));
                bGoodsModel.setKind(fKindModel);
                bGoodsModel.setUpdatedUser(user);
                SequenceUtil SequenceUtil = new SequenceUtil();
                // 生成商品编号
                bGoodsModel.setGoodsNo(SequenceUtil.nextId("G"));
                bGoodsService.insertBGoods(bGoodsModel);
                return new DataToCResultModel<Object>(200, "success", null);
            } else {
                return new DataToCResultModel<Object>(400, "请先登录", null);
            }
        } catch (Exception e) {
            return new DataToCResultModel<Object>(400, "新建商品失败", null);
        }

    }

    /**
     * 商品查看
     */
    @RequestMapping(value = "/bgoodsdetails", method = RequestMethod.POST)
    public Map<String, Object> bgoodsdetails(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        String id = request.getParameter("id");
        BGoodsModel bGoodsModel = bGoodsService.findBgoodsById(Integer.valueOf(id));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", bGoodsModel);
        return map;
    }

    /**
     * 商品更新版本页面
     */
    @RequestMapping(value = "/modifybgoodsurl", method = RequestMethod.GET)
    public final ModelAndView modifybgoodsurl(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        String id = request.getParameter("id");
        BGoodsModel bGoodsModel = bGoodsService.findBgoodsById(Integer.valueOf(id));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", bGoodsModel);
		map.put("path", PropertiesUtil.getValue("path"));
        return new ModelAndView("market/modifybgoods", map);
    }

    /**
     * 修改平台商品
     */
    @RequestMapping(value = "/modify-bgoods", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> modifybgoods(BGoodsModel bGoodsModel, HttpServletRequest request,
            HttpSession session) {
        try {
            BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
            String pricex = request.getParameter("pricex");
            bGoodsModel.setPrice(StringUtils.isEmpty(pricex) ? 0 : (int) (Double.valueOf(pricex) * 100));
            int size = bGoodsService.modifybgoods(bGoodsModel, user);
            if (size > 0) {
                return new DataToCResultModel<Object>(200, "success", null);
            } else {
                return new DataToCResultModel<Object>(400, "修改失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataToCResultModel<Object>(400, "修改失败", null);
        }
    }

    /**
     * 下架平台商品
     */
    @RequestMapping(value = "/next-shelf-bgoods", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> nextshelfbgoods(HttpServletRequest request,
            HttpSession session) {
        try {
            String id = request.getParameter("id");
            int size = bGoodsService.nextshelfbgoods(Integer.valueOf(id));
            if (size > 0) {
                return new DataToCResultModel<Object>(200, "success", null);
            } else {
                return new DataToCResultModel<Object>(400, "下架商品失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataToCResultModel<Object>(400, "下架商品失败", null);
        }
    }

    /**
     * 修改商品库存
     */
    @RequestMapping(value = "/modify-remain", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> modifyremain(BGoodsModel bGoodsModel, HttpServletRequest request,
            HttpSession session) {
        try {
            BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
            int size = bGoodsService.modifybgoodsRemain(bGoodsModel, user);
            if (size > 0) {
                return new DataToCResultModel<Object>(200, "success", null);
            } else {
                return new DataToCResultModel<Object>(400, "修改失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DataToCResultModel<Object>(400, "修改失败", null);
        }
    }
}