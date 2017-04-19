package com.hd.client.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hd.client.model.FaceInfoModel;
import com.hd.client.model.ParamSet;
import com.hd.client.service.FaceInfoService;
import com.hd.client.service.ParamSetService;
import com.hd.client.util.FaceInfoUtil;

/**
 * 
 * @author curry.su
 *
 */
@Controller
@RequestMapping("/faceInfo")
public class FaceInfoControl {
	private Logger logger = Logger.getLogger(FaceInfoControl.class);

	@Autowired
	private FaceInfoService faceInfoService;

	@Autowired
	private ParamSetService paramSetService;

	// @Autowired
	// private FaceAndIdcardService testService;

	@RequestMapping(value = "writeImage")
	public void writeImage(HttpServletRequest request) {
		ServletInputStream in_Image = null;
		OutputStream os = null;
		try {
			request.setCharacterEncoding("utf-8");
			in_Image = request.getInputStream();
			// 创建中间流，通过中间流写图片。写完重置，读取头信息。此过程原始流不变
			FilterInputStream is = new BufferedInputStream(in_Image);
			is.mark(1024000);
			BufferedImage image = ImageIO.read(is);
			String data = System.currentTimeMillis() + "";// 得到时间戳，测试图片是否转化为byte数组
			String path = "D://pic//" + data + ".jpg";
			os = new FileOutputStream(path);
			ImageIO.write(image, "JPEG", os);// 先下载图片，然后根据图片大小动态设置byte数组的大小
			os.close();
			is.reset();// 重置输入流，读取头信息
			BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
			String head = buffer.readLine();
			is.close();
			buffer.close();
			FaceInfoUtil faceInfoUtil = new FaceInfoUtil();
			FaceInfoModel faceInfoModel = new FaceInfoModel();

			// 解析头信息，得到faceinfo对象
			faceInfoModel = faceInfoUtil.getPersonByCPI(head);
			/**
			 * 如何解析头信息异常，为得到人脸信息，那么不保存信息
			 */
			if (faceInfoModel != null) {
				// 得到二进制图片
				byte[] image_Byte = faceInfoUtil.getByteByImage(new File(path));
				if (image_Byte == null) {
					long time = Long.valueOf(faceInfoModel.getCollectTimeStamp());
					Date d = new Date(time);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
					String date = sdf.format(d);
					logger.error(date + ": The " + faceInfoModel.getGroupCode() + " groupcode of " + date
							+ " acquisition of the picture is empty");
				}
				// 加载二进制图片
				faceInfoModel.setCollectPic(image_Byte);
				// 测试图片是否转化为二进制数组
				faceInfoUtil.getImageByByte(image_Byte, data);

				ParamSet model = new ParamSet();
				model = paramSetService.getParamSet();
				/**
				 * 如果参数信息未取到，打印错误信息
				 */
				if (model.getFaceDvNo() != null && model.getCollectSite() != null) {
					faceInfoModel.setFaceCode(model.getFaceDvNo() + "_" + System.currentTimeMillis());
					faceInfoModel.setCollectSite(model.getCollectSite());
					// 将faceinfo插入到数据库
					faceInfoService.addFaceInfo(faceInfoModel);
				} else {
					logger.error("No parameter information is obtained, please check the t_pr_paramset table!");
				}
			} else {
				logger.error("faceInfoModel is null!");
			}

		} catch (IOException e) {
			logger.error("The requested picture stream has a exception!");
		} finally {
			try {
				in_Image.close();
			} catch (IOException e) {
				logger.error("Requested picture stream was not successfully closed");
			}
		}
		// FaceInfoUtil test = new FaceInfoUtil();
		// test.getProperties();
	}

	@RequestMapping(value = "getfaceInfoModelList")
	public List<FaceInfoModel> getfaceInfoModelList() {
		List<FaceInfoModel> list = faceInfoService.getfaceInfoModelList();
		return list;
	}

	@RequestMapping(value = "getfaceInfo")
	public FaceInfoModel getfaceInfo() {
		FaceInfoModel faceInfo = faceInfoService.getFaceInfo();
		return faceInfo;
	}

	@RequestMapping(value = "updateFaceInfo")
	public void updateFaceInfo(String faceId, String relayFlag) {
		faceInfoService.updateFaceInfo(faceId, relayFlag);
	}

	// @RequestMapping(value = "/testPro")
	// public void testPro(HttpServletRequest request) {
	// Map<String, Object> resultMap = testService.queryFCInfo();
	// System.out.println(resultMap);
	// }
}
