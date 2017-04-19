/**
 * Project Name:com.softtek.mdm File Name:NetBehaviorServiceImpl.java Package
 * Name:com.softtek.mdm.service.impl Date:Apr 12, 20165:03:02 PM Copyright (c)
 * 2016, brave.chen@softtek.com All Rights Reserved.【请根据具体情况修改模板】
 *
 */
package com.softtek.mdm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softtek.mdm.dao.AndroidPolicyListDao;
import com.softtek.mdm.dao.NetBehaviorDao;
import com.softtek.mdm.model.BlackWhiteListUrl;
import com.softtek.mdm.model.DeptNetBwListRelation;
import com.softtek.mdm.model.NetBehaviorBlackWhiteList;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.NetBehaviorService;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.Constant.NetBehaviorConstant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * Description: 类描述 date: Apr 12, 2016 5:03:02 PM
 *
 * @author brave.chen
 */
@Service("netBehaviorService")
@Transactional
public class NetBehaviorServiceImpl implements NetBehaviorService
{
    /**
     * 上网行为dao处理类
     */
    @Autowired
    private NetBehaviorDao netBehaviorDao;
    
    @Autowired
    private AndroidPolicyListDao androidPolicyListDao;
    
    /**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSourceService;
    
    /**
     * 设备服务类
     */
    @Autowired
    private DeviceService deviceService;

    /**
     * 新增黑白名单对象
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#addNetBehaviorBlackWhiteList(com.softtek.mdm.model.NetBehaviorBlackWhiteList)
     */
    @SuppressWarnings("unchecked")
	@Override
    public ResultDTO addNetBehaviorBlackWhiteList(Map<String, Object> paramsMap)
    {
		ResultDTO resultDto = new ResultDTO();
		Object netUrlStr = paramsMap.get("netUrlStr");
		
		NetBehaviorBlackWhiteList bwList = new NetBehaviorBlackWhiteList(paramsMap);

        // json字符串转换成javabean对象
		Gson gson = new Gson();
		List<BlackWhiteListUrl> bWListUrls = gson.fromJson((String) netUrlStr, new TypeToken<List<BlackWhiteListUrl>>()
		{
		}.getType());

		
		netBehaviorDao.addNetBehaviorBlackWhiteList(bwList);
		Integer id = bwList.getId();
		
		List<Integer> deptIdList  = (List<Integer>) paramsMap.get("deptIdList");
		
		List<DeptNetBwListRelation> relationList = new ArrayList<DeptNetBwListRelation>();
		if (CollectionUtils.isNotEmpty(deptIdList))
		{
			for (Integer dptId : deptIdList)
			{
				DeptNetBwListRelation relation = new DeptNetBwListRelation();
				relation.setOrgManagerId(dptId);
				relation.setNetbehavioirBwListId(id);
				relationList.add(relation);
			}
		}
		
		else
		{
			DeptNetBwListRelation relation = new DeptNetBwListRelation();
			relation.setOrgManagerId((Integer) paramsMap.get("organizationId"));
			relation.setNetbehavioirBwListId(id);
			relationList.add(relation);
		}
		
		if (CollectionUtils.isNotEmpty(relationList))
		{
			netBehaviorDao.addDeptBwListRelation(relationList);
		}
		
		bWListUrls = generateBWListUrls(id, bWListUrls);

		if (CollectionUtils.isNotEmpty(bWListUrls))
		{
			netBehaviorDao.addBlackWhiteListUrls(bWListUrls);
		}

		resultDto.type = BaseDTO.SUCCESS;
		resultDto.message = messageSourceService.getMessage("web.institution.savebwlist.resultdto.message.success", null,
				LocaleContextHolder.getLocale());
		return resultDto;
    }

     /**
     * 新增黑白名单监测url对象列表
     *
     * @see com.softtek.mdm.service.NetBehaviorService#addBlackWhiteListUrls(java.util.List)
     */
     @Override
     public void addBlackWhiteListUrls(List<BlackWhiteListUrl>
     blackWhiteListUrls)
     {
         netBehaviorDao.addBlackWhiteListUrls(blackWhiteListUrls);
     }

    /**
     * 根据黑白名单对象id删除黑白名单对象
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#delNetBehaviorBlackWhiteList(long)
     */
    @Override
    public ResultDTO delNetBehaviorBlackWhiteList(String bwListId)
    {
        ResultDTO result = new ResultDTO();
        
        if (StringUtils.isBlank(bwListId))
        {
            result.type = BaseDTO.ERROR;
            result.message = messageSourceService.getMessage(
					"web.institution.delbwlist.resultdto.message.failed", null,
					LocaleContextHolder.getLocale());
            return result;
        }
        
        // 删除部门和黑白名单的关联关系
        netBehaviorDao.deleteDeptBwListRelation(Integer.valueOf(bwListId));
        
        DataGridModel params = new DataGridModel();
		params.getParams().put("bwId", Integer.valueOf(bwListId));
		
		List<Integer> policyList = androidPolicyListDao.selectPolicyidListByBWID(params);
		
		if (CollectionUtils.isNotEmpty(policyList))
		{
			result.type = BaseDTO.ERROR;
			result.message = messageSourceService.getMessage(
					"web.institution.delbwlist.resultdto.message.failed.has.relatetion", null,
					LocaleContextHolder.getLocale());
            return result;
		}
		
        try
        {
            int count = netBehaviorDao.delNetBehaviorBlackWhiteList(bwListId);
            if (count == 0) 
            {
                result.type = BaseDTO.ERROR;
                result.message = messageSourceService.getMessage(
    					"web.institution.delbwlist.resultdto.message.failed", null,
    					LocaleContextHolder.getLocale());
                return result;
            } 
        }
        catch(Exception e)
        {
            result.type = BaseDTO.ERROR;
            result.message = messageSourceService.getMessage(
					"web.institution.delbwlist.resultdto.message.failed", null,
					LocaleContextHolder.getLocale());
            return result;
        }
        
        result.type = BaseDTO.SUCCESS;
        result.message = messageSourceService.getMessage(
				"web.institution.delbwlist.resultdto.message.success", null,
				LocaleContextHolder.getLocale());
        return result;
    }

    /**
     * 根据黑白名单id删除监测的url列表
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#delBlackWhiteListUrlsById(long)
     */
    @Override
    public void delBlackWhiteListUrlsById(String blackWhiteListId)
    {
        netBehaviorDao.delBlackWhiteListUrlsById(blackWhiteListId);
    }

    /**
     * 更新黑白名单对象
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#updateNetBehaviorBlackWhiteList(com.softtek.mdm.model.NetBehaviorBlackWhiteList)
     */
    @Override
    public void updateNetBehaviorBlackWhiteList(NetBehaviorBlackWhiteList netBehaviorBlackWhiteList)
    {
        netBehaviorDao.updateNetBehaviorBlackWhiteList(netBehaviorBlackWhiteList);
    }

    /**
     * 根据黑白名单类型查询所有的黑名单或者白名单
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#queryBlackWhiteListByType(int)
     */
    @Override
    public List<NetBehaviorBlackWhiteList> queryBwListWithCondition(Integer type, Integer organizationId)
    {
        List<NetBehaviorBlackWhiteList> bwLists = netBehaviorDao.queryBwListWithCondition(type, organizationId);
        return bwLists;
    }

    /**
     * 根据黑白名单id查询黑白名单对象信息
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#queryNetBehaviorBlackWhiteList(long)
     */
    @Override
    public NetBehaviorBlackWhiteList queryNetBehaviorBlackWhiteList(String bwListId)
    {
        NetBehaviorBlackWhiteList bwList = netBehaviorDao.queryNetBehaviorBlackWhiteList(bwListId);
        return bwList;
    }

    /**
     * 根据map参数查询分页对象
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#queryBlackWhiteListByParams(java.util.Map)
     */
	@Override
    public Page queryBlackWhiteListByParams(Map<String, Object> paramMap)
    {
    	Integer start = (Integer) paramMap.get(Constant.PageRelatedConstant.START_PAGE);
		Integer pageLength = (Integer) paramMap.get(Constant.PageRelatedConstant.PAGE_OFFSET);
		String type = (String) paramMap.get("type");
/*		List<Integer> deptIdList = (List<Integer>) paramMap.get("deptIdList");*/

		Integer bwListType = StringUtils.isEmpty(type) ? null : Integer.valueOf(type);

		start = start == null ? 0 : start;
		pageLength = pageLength == null ? 10 : pageLength;

		paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
		paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, pageLength);
		paramMap.put(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE, bwListType);
/*		paramMap.put("deptIdList", deptIdList);*/
		
		Integer count = netBehaviorDao.queryAllCountByParams(paramMap);
        List<NetBehaviorBlackWhiteList> list = (List<NetBehaviorBlackWhiteList>) netBehaviorDao
                .queryBlackWhiteListByPageParams(paramMap);
        
        Page page = new Page();
        page.setData(list);
        page.setRecordsFiltered(count);
        page.setRecordsTotal(count);
	
		
        return page;
    }

    
    /**
     * 根据名称获取黑白名单对象
     * 
     * @see com.softtek.mdm.service.NetBehaviorService#queryBwListByName(java.util.Map)
     */
	@Override
	public NetBehaviorBlackWhiteList queryBwListByName(String bWListName) {
		NetBehaviorBlackWhiteList bwList = netBehaviorDao.queryBwListByName(bWListName);
		return bwList;
	}
	
	@Override
	public ResultDTO updateBwList(Map<String, Object> paramMap)
	{
    	ResultDTO resultDto = new ResultDTO();
    	
    	Integer createUserId = (Integer) paramMap.get("createUserId");
        Object  netUrlStr = paramMap.get("netUrlStr");
        
        String createUserName = (String) paramMap.get("createUserName");
        
        Gson gson = new Gson();
        List<BlackWhiteListUrl> bWListUrls = gson.fromJson((String) netUrlStr, new TypeToken<List<BlackWhiteListUrl>>(){}.getType());
        NetBehaviorBlackWhiteList bwList = new NetBehaviorBlackWhiteList();
        String blackWhiteListId = (String) paramMap.get("blackWhiteListId");
        
        if (StringUtils.isEmpty(blackWhiteListId))
        {
        	 resultDto.type = BaseDTO.ERROR;
             resultDto.message = messageSourceService.getMessage("web.institution.updatebwlist.resultdto.message.id.not.exist", null, 
  					LocaleContextHolder.getLocale());
             return resultDto;
        }
        bwList.setId(Integer.valueOf(blackWhiteListId));

        bwList.setBlackWhiteName((String) paramMap.get(NetBehaviorConstant.BLACK_WHITE_LIST_NAME));
        bwList.setRemark((String) paramMap.get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_REMARK));
        bwList.setType((Integer) paramMap.get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE));
        
        bwList.setUpdateUserId(createUserId);
        bwList.setUpdateUserName(createUserName);
        bWListUrls = generateBWListUrls(Integer.valueOf(blackWhiteListId), bWListUrls);
        
        updateNetBehaviorBlackWhiteList(bwList);
        delBlackWhiteListUrlsById(blackWhiteListId);
        
        if (CollectionUtils.isNotEmpty(bWListUrls))
        {
        	addBlackWhiteListUrls(bWListUrls);
        }
        
        deviceService.jpushPoicyByBWId(Integer.valueOf(blackWhiteListId));

        resultDto.type = BaseDTO.SUCCESS;
        resultDto.message = messageSourceService.getMessage("web.institution.updatebwlist.resultdto.message.success", null, 
				LocaleContextHolder.getLocale());
        return resultDto;
    
	}
	
	/**
     * 生成黑白名单url列表
     *
     * @author brave.chen
     * @param blackWhiteListId
     *            黑白名单
     * @param bWListUrls2
     *            黑白名单过滤url名称和地址拼接字符串
     * @return 黑名单监听URL列表
     */
    private List<BlackWhiteListUrl> generateBWListUrls(Integer blackWhiteListId, List<BlackWhiteListUrl> bWListUrls)
	{
		if (CollectionUtils.isNotEmpty(bWListUrls))
		{
			for (BlackWhiteListUrl urlModel : bWListUrls)
			{
				urlModel.setBlackWhiteListId(blackWhiteListId);
			}
		}

		return bWListUrls;
	}

	@Override
	public NetBehaviorBlackWhiteList queryBwListByName(String bWListName, Integer orgId) {
		NetBehaviorBlackWhiteList bwList = netBehaviorDao.queryBwListByNameAndOrgId(bWListName,orgId);
		return bwList;
	}

    @Override
    public Map<String, Object> importExcel(InputStream ins, Integer filetype) {
        Map<String, Object> resultmap = new HashMap<String, Object>();
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(ins);
            ins.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = null;
        // 3.得到Excel工作表对象
        if (filetype == 03) {
            sheet = (HSSFSheet) wb.getSheetAt(0);
        }
        if (filetype == 07) {
            sheet = (XSSFSheet) wb.getSheetAt(0);
        }
        // Sheet sheet = wb.getSheetAt(0);
        // 总行数
        int trLength = sheet.getLastRowNum();
        // Excel所有用户名
        List<BlackWhiteListUrl> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        // 如果大于0行
        if (trLength > 0) {
            for (int i = 1; i <= trLength; i++) {
                // 得到Excel工作表的行
                Row row1 = sheet.getRow(i);
                int rownumber = i + 1;
                Object[] objects = { rownumber };
                // 得到Excel工作表指定行的单元格
                Cell name = row1.getCell(0);
                Cell url = row1.getCell(1); 

                if (name != null) {
                    name.setCellType(Cell.CELL_TYPE_STRING);
                }else{
                    // 第xx行名称不能为空
                    sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip1", objects, LocaleContextHolder.getLocale()));
                }
                if (url != null) {
                    url.setCellType(Cell.CELL_TYPE_STRING);
                }else{
                    // 第xx行网址不能为空
                    sb.append(messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip1", objects, LocaleContextHolder.getLocale()));
                }
                // 如果两个都填了 添加到list
                if (name != null && url != null) {
                    BlackWhiteListUrl model = new BlackWhiteListUrl();
                    model.setUrl(url.getStringCellValue().trim());
                    model.setUrlName(name.getStringCellValue().trim());
                    list.add(model);
                }
            }
            resultmap.put("list", list);
        } else {
            resultmap.put("resultnull", "1");
        }
        if (sb.length() > 0) {
            resultmap.put("messages", sb.toString());
        }

        if (resultmap.get("messages") == null && resultmap.get("resultnull") == null) {
            resultmap.put("success",
                    messageSourceService.getMessage("web.institution.exportandimportexcelserviceimpl.exceltip6", null,
                            LocaleContextHolder.getLocale()));
        }
        return resultmap;
    }
}
