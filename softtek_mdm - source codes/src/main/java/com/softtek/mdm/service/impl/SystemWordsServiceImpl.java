package com.softtek.mdm.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.softtek.mdm.dao.SystemWordsDao;
import com.softtek.mdm.dao.WebsiteClassifyDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.WebsiteClassifyModel;
import com.softtek.mdm.service.SystemWordsService;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;

/**
 * 词库管理Service
 * @author jane.hui
 *
 */
@Service("systemWordsService")
public class SystemWordsServiceImpl implements SystemWordsService{

    @Autowired
    private SystemWordsDao systemWordsDao;
    
    @Autowired
    private MessageSource messageSourceService;
    
    @Autowired
    private WebsiteClassifyDao websiteClassifyDao;
    
    /**
     * 获取系统词库列表数据
     * @param orgId:机构id
     * @param name:系统名称
     * @param begin:开始页
     * @param num:分页大小
     * @return 获取分页列表数据
     */
    @Override
    public Page getSystemWordsList(Integer orgId, String name, Integer begin, Integer num) {
        Page p = new Page();
        DataGridModel params = new DataGridModel();
        params.setBegin(begin);
        params.setNum(num);
        params.getParams().put("orgId", orgId);
        if(StringUtil.isNotBlank(name)){
            params.getParams().put("name", name);
        }
        int total = systemWordsDao.getSystemWordsSize(params);
        p.setData(systemWordsDao.getSystemWordsList(params));
        p.setRecordsTotal(total);
        p.setRecordsFiltered(total);
        return p;
    }

    /**
     * 删除词库功能
     * @param request:请求
     * @return 返回删除操作结果
     */
    @Override
    public ResultDTO deleteWords(Integer id) throws ParseException {
        ResultDTO result = new ResultDTO();
        int size = systemWordsDao.deleteSystemWordsById(id);
        if(size == 0){
            result.type = BaseDTO.ERROR;
            result.message = messageSourceService.getMessage("web.institution.system.words.delwords.failed.lable",null, LocaleContextHolder.getLocale());
            return result;
        }
        result.type = BaseDTO.SUCCESS;
        result.message = messageSourceService.getMessage("web.institution.system.words.delwords.success.lable",null, LocaleContextHolder.getLocale());
        return result;
    }

    /**
     * 获取网站列表
     */
    @Override
    public List<WebsiteClassifyModel> selectClassifyList() {
        return websiteClassifyDao.selectClassifyList();
    }

    /**
     * 判断系统词库名称是否存在
     * @param name：名称
     * @param id:主键
     * @param orgId:机构id
     * @return 判断是否存在
     */
    @Override
    public boolean exists(String name, Integer id, Integer orgId) {
        // 新增功能(判断如何存在一个系统词库名称一样，则提示已存在)
        DataGridModel objParams = new DataGridModel();
        objParams.getParams().put("orgId", orgId);
        objParams.getParams().put("name", name);
        if(id==null||Constant.EMPTY_STR.equals(String.valueOf(id))){
            Integer existSize = systemWordsDao.selectWordsyByName(objParams);
            if(existSize>0){
                return true;
            }
        } else {
            objParams.getParams().put("id", Integer.valueOf(String.valueOf(id)));
            Integer existSize = systemWordsDao.selectWordsyByName(objParams);
            // 编辑功能(判断如果存在一个以上系统词库名称一样，则提示已存在，因为本身在数据库存在一条名称一样)
            if(existSize>0){
                return true;
            }
        }
        return false;
    }
}