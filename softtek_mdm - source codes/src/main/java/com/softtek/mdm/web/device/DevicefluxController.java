package com.softtek.mdm.web.device;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.DeviceAllInfoModel;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceFluxModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.service.DeviceFluxService;

/**
 * 与设备端连接处理基本信息
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/terminal/flux")
public class DevicefluxController {

	@Autowired
    private DeviceFluxService deviceFluxService;

    /**
     * 上报流量信息
     * 
     * @author josen.yang
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public DeviceResultModel<String> save(DeviceFluxModel model,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
        DeviceResultModel<String> deviceResult = new DeviceResultModel<String>();
		//String imsi="";
        if (model.getFlux() != null) {
            // 保存流量信息
            int number = deviceFluxService.saveSumFlux(model);
            if (number > 0) {
                // 如果保存成功 返回信息告诉客户端 此设备(用户)上一次登录是否异常、是否处理过
                DeviceFluxModel resultModel = deviceFluxService.findDeviceIsAbnormalAndIsReport(model);
                deviceResult.setStatus(200);
                deviceResult.setMsg("success");
                // 如果上一次登录的记录已经是 流量异常 并且没有处理 那就将那天的记录返回给客户端 否则返回null
                if (resultModel != null) {
                if (resultModel.getIsAbnormal() == 1 && resultModel.getIsReport() == 0) {
                        deviceResult.setData("abnormal");
                    } else {
                        deviceResult.setData("noabnormal");
                    }
                } else {
                    deviceResult.setData("noabnormal");
                }
            } else {
                deviceResult.setStatus(400);
            }
		}
		return deviceResult;
	}
	
    /**
     * 上报设备流量应用详情
     * 
     * @Description
     * @author josen.yang
     * @param deviceAllInfo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/upload-flux-detail", method = RequestMethod.POST)
    public DeviceResultModel<String> updaloadFluxDetail(@RequestBody DeviceAllInfoModel deviceAllInfo,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        DeviceResultModel<String> deviceResult = new DeviceResultModel<String>();
        // 获取基本信息
        DeviceBasicInfoModel basic = deviceAllInfo.getDeviceBasicInfo();
        // 获取应用信息
        List<DeviceAppInfoModel> deviceAppInfoList = deviceAllInfo.getDeviceAppInfoList();
        // ==========================================TEST
        /*
         * deviceAppInfoList = new ArrayList<DeviceAppInfoModel>(); for (int i =
         * 0; i < 10; i++) { DeviceAppInfoModel a = new DeviceAppInfoModel();
         * a.setApp_flux("10000"); a.setApp_version(String.valueOf(i));
         * a.setName("秋实测试应用"); deviceAppInfoList.add(a); } basic = new
         * DeviceBasicInfoModel(); basic.setSn("123123"); UserModel user = new
         * UserModel(); user.setId(81); basic.setUser(user);
         */
        // ===========================================
        Map<String, Object> map = new HashMap<>();
        map.put("deviceInfo", basic);
        map.put("appInfo", deviceAppInfoList);
        if (basic != null && basic.getSn() != null) {
            if (deviceAppInfoList != null && deviceAppInfoList.size() > 0) {
                DeviceFluxModel model = deviceFluxService.findPriKey(basic);
                Integer userFluxId = model.getId();
                map.put("userFluxId", userFluxId);
                int number = deviceFluxService.updaloadFluxDetail(map);
                if (number > 0) {
                    // 将流量异常设置为已经处理
                    deviceFluxService.updateIsReport(basic);
                    deviceResult.setStatus(200);
                    deviceResult.setMsg("双哥你成功了");
                }
            }
        }
        return deviceResult;
    }


}
