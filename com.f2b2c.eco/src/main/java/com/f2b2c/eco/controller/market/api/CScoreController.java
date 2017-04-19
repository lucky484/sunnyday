package com.f2b2c.eco.controller.market.api;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.f2b2c.eco.model.market.CUserScoreRecordModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.service.market.BScoreService;
import com.f2b2c.eco.util.PageUtil;
import jodd.util.StringUtil;

/**
 * 积分管理
 * @author jane.hui
 *
 */
@Controller(value="cScoreController")
@RequestMapping(value="/api/score") 
public class CScoreController {
	
	@Autowired
	private BScoreService bScoreService;
	
    /**
     * 根据用户id获取我的帐户余额
     * @param userId:用户id
     * @return 返回我的帐户余额值
     */
    @RequestMapping(value = "/getMyScore", method = RequestMethod.GET)
    @ResponseBody
    public DataToCResultModel<Integer> getMyBalance(HttpServletRequest request){
        DataToCResultModel<Integer> result = new DataToCResultModel<Integer>();
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(userId)){
            Integer score = bScoreService.getMyScore(Integer.valueOf(userId));
            result.setMsg("获取我的帐户余额操作成功");
            result.setStatus(200);
            result.setData(score);
        } else {
            result.setMsg("获取我的帐户余额操作失败");
            result.setStatus(400);
        }
        return result;
    }
    
    /**
     * 积分变动记录
     * @return
     */
    @RequestMapping(value = "/scoreRecord", method = RequestMethod.GET)
    @ResponseBody
    public DataToCResultModel<List<CUserScoreRecordModel>> selectBalanceRecordByUserId(HttpServletRequest request,HttpServletResponse response){
        DataToCResultModel<List<CUserScoreRecordModel>> result = new DataToCResultModel<List<CUserScoreRecordModel>>();
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        Integer intPage = Integer.valueOf(page);
        Integer intPageSize = Integer.valueOf(pageSize);
        Integer start = PageUtil.getStart(intPage, intPageSize);
        Integer length = intPageSize;
        if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)){
            result.setMsg("获取充值记录信息操作成功");
            result.setStatus(200);
            result.setData(bScoreService.scoreRecord(userId,start,length));
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
            return result;
        }
        return result;
    }
}
