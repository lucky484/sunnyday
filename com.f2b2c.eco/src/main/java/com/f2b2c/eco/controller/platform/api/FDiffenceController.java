package com.f2b2c.eco.controller.platform.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FDiffenceModel;
import com.f2b2c.eco.service.platform.FDiffenceService;
import com.f2b2c.eco.util.CommonUtil;
import com.f2b2c.eco.util.FileUtil;
import com.f2b2c.eco.util.PropertiesUtil;

@Controller(value = "fdiffenceController")
@RequestMapping(value = "/api/fdiffence")
public class FDiffenceController {

	@Autowired
	private FDiffenceService fDiffenceService;

	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<List<String>> uploadImg(HttpServletRequest request) {
		DataToCResultModel<List<String>> dataToCResult = new DataToCResultModel<List<String>>();
		String imgList = request.getParameter("imgList");
		JSONArray array = JSONArray.fromObject(imgList);
		List<String> list = JSONArray.toList(array);
		List<String> pathList = new ArrayList<String>();
		BASE64Decoder decoder = new BASE64Decoder();
		if (list != null && list.size() > 0) {
			for (String s : list) {
				String path = "";
				try {
					path = FileUtil.copyTo(decoder.decodeBuffer(s),
							PropertiesUtil.getValue("fileSavePath"),
							"diffence", ".png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				pathList.add(path);
			}
		}
		dataToCResult.setData(pathList);
		dataToCResult.setMsg("success");
		dataToCResult.setStatus(200);
		return dataToCResult;

	}

	@RequestMapping(value = "/insertDiffence", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> insertDiffence(HttpServletRequest request) {
		DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
		String saleDiffence = request.getParameter("diffencePrice");
		if (saleDiffence != null && !saleDiffence.equals("")) {
			JSONObject json = JSONObject.fromObject(saleDiffence);
			FDiffenceModel fDiffence = new FDiffenceModel();
			fDiffence.setId(CommonUtil.generate32UUID());
			fDiffence.setOrderNo(json.getString("orderNo"));
			fDiffence.setDiffenceType(json.getInt("diffenceType"));
			fDiffence.setDiffenceCause(json.getInt("diffenceCause"));
			fDiffence.setDiffenceAmount(json.getInt("diffenceAmount"));
			fDiffence.setRemark(json.getString("remark"));
			if (fDiffence.getDiffenceType() == 1) {
				fDiffence.setStatus(1);
			} else {
				fDiffence.setStatus(2);
			}
			fDiffence.setCreatedTime(new Date());
			fDiffence.setUpdatedTime(new Date());
			int result = fDiffenceService.insert(fDiffence,
					json.getJSONArray("imgList"));
			if (result == 1) {
				dataToCResult.setStatus(200);
				dataToCResult.setMsg("success");
			} else {
				dataToCResult.setStatus(400);
				dataToCResult.setMsg("fail");
			}
		} else {
			dataToCResult.setStatus(400);
			dataToCResult.setMsg("提交参数不能为空");
		}
		return dataToCResult;

	}
}
