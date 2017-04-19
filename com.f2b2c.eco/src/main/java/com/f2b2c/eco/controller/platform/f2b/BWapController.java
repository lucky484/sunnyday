package com.f2b2c.eco.controller.platform.f2b;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.platform.BSliderWapService;
import com.f2b2c.eco.util.PropertiesUtil;


/**
 * 商品操作
 * 
 * @author jing.liu
 *
 */
@Controller("BWapController")
@RequestMapping(value = "/farm/bwap")
public class BWapController {
    /**
	 * 日志记录器
	 */
    private Logger logger=LoggerFactory.getLogger(BWapController.class);

	@Autowired
	BSliderWapService bSliderWapService;
	@Autowired
	BGoodsService bGoodsService;
	@Value("${fileSavePath}")
	private String basePath;
	
	/**
	 * b端公告
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public final String publishBnotice(HttpServletRequest request, HttpServletResponse response) {
		return "farm/bwap";
    }

	/**
	 * 查wap
	 * 
	 * @param pageable
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querypage", method = RequestMethod.GET)
	@ResponseBody
	public final Page<FSliderWapsModel> queryDatatable(final Pageable pageable, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Page<FSliderWapsModel> pages = bSliderWapService.findWithPagination(pageable, paramMap);
		List<FSliderWapsModel> list = pages.getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			FSliderWapsModel temp = null;
			String requsetPath = StringUtils.trimToEmpty(PropertiesUtil.getValue("path"));
			for (int i = 0; i < list.size(); i++) {
				temp = list.get(i);
				list.get(i).setWapImgUrl(requsetPath.endsWith("/") ? requsetPath + temp.getWapImgUrl()
						: requsetPath + "/" + temp.getWapImgUrl());
			}
		}
		return pages;
	}

	/**
	 * 修改页面-修改/新增功能
	 * 
	 * @param model
	 * @return 主页
	 */
	@RequestMapping(value = "/addorupdate", method = RequestMethod.POST)
	public final String addorupdate(FSliderWapsModel model, MultipartHttpServletRequest request) {
		Integer type = Integer.valueOf(request.getParameter("type"));

		if (type != null) {
			model.setType(type);
			String content = request.getParameter("contentStr");
			model.setContent(content);
		}

		Integer id = model.getId();
		Object fileObj = request.getFile("file");
		MultipartFile file = null;
		if (fileObj instanceof MultipartFile) {
			file = (MultipartFile) fileObj;
		}
		if (file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();// 获取文件名字
			String extName = FilenameUtils.getExtension(fileName);// 获取后缀
			String fixFileName = System.currentTimeMillis() + "." + extName;
			Path path = Paths.get(basePath + "bwap/");
			try {
				// 如果不存在文件夹，则创建
				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}
				FileCopyUtils.copy(file.getBytes(), new FileOutputStream(path.toString() + "/" + fixFileName));
				model.setWapImgUrl("bwap/" + fixFileName);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			if (id == null) {
				bSliderWapService.insert(model);
			}
		}
		if (id != null) {
			bSliderWapService.update(model);
		}
		return "farm/bwap";
	}

	/**
	 * 根据商品编号查询商品是否存在
	 * 
	 * @param goodsNo
	 * @return
	 */
	@RequestMapping(value = "/bgoods-no-istrue", method = RequestMethod.POST)
	@ResponseBody
	public boolean bgoogsNoIsTrue(String goodsNo) {
		boolean istrue = false;
		if(StringUtils.isNotBlank(goodsNo)){
			BGoodsModel model = bGoodsService.bgoogsNoIsTrue(goodsNo);
			if (model != null) {
				istrue = true;
			}
			return istrue;
		}else{
			return istrue;
		}

	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public final int del(String id) {
		return bSliderWapService.delete(id);
	}

	/**
	 * b端公告
	 */
	@RequestMapping(value = "/addpage", method = RequestMethod.GET)
	public final String addpage(Integer id, HttpServletRequest request, HttpServletResponse response) {
		if (id != null) {
			FSliderWapsModel model = bSliderWapService.querySliderWapById(id);
			String requsetPath = StringUtils.trimToEmpty(PropertiesUtil.getValue("path"));
			model.setWapImgUrl(requsetPath.endsWith("/") ? requsetPath + model.getWapImgUrl()
					: requsetPath + "/" + model.getWapImgUrl());
			if (model.getType() == 1) {
				model.setStatus(model.getContent());
				model.setContent("");
			}
			request.setAttribute("model", model);
		}
		return "farm/bwap/addpage";
	}
}