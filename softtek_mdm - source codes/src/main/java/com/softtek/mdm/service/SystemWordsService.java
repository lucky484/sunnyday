package com.softtek.mdm.service;

import java.text.ParseException;
import java.util.List;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.WebsiteClassifyModel;
import com.softtek.mdm.web.http.ResultDTO;

public interface SystemWordsService {

    /**
     * 获取系统词库列表
     * @param orgId:机构id
     * @param name:词库名称
     * @param begin:开始页
     * @param num:分页大小
     * @return 返回系统词库列表
     */
    Page getSystemWordsList(Integer orgId,String name,Integer begin, Integer num);
    
    /**
     * 删除词库功能
     * @param request:request请求
     * @return 返回操作结果
     */
    ResultDTO deleteWords(Integer id) throws ParseException;
    
    
    /**
     * 获取网站分类列表
     * @return 
     */
    List<WebsiteClassifyModel> selectClassifyList();
    
	/**
	 * 查看系统词库名称是否存在
	 * @param orgId:机构Id
	 * @param id:系统词库id
	 * @param name:系统词库名称
	 * @return
	 */
	boolean exists(String name,Integer id,Integer orgId);
}