package com.softtek.mdm.thread;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.softtek.mdm.analyzer.Matrix;
import com.softtek.mdm.bean.WordsBean;
import com.softtek.mdm.dao.SystemWordsDao;
import com.softtek.mdm.model.NetBehaviorLogInfoModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.NetBehaviorLogService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.GetMeta;
import com.softtek.mdm.util.SpringContextUtils;

import jodd.util.StringUtil;

/**
 * 操作上网行为日志线程 date: Apr 18, 2016 3:42:14 PM
 *
 * @author brave.chen
 */
public class OptNetBehaviorLogsThread implements Runnable
{

    /**
     * 用户service服务类
     */
    private UserService userService;

    /**
     * 组织机构服务类
     */
    private OrganizationService organizationService;

    /**
     * 结构服务类
     */
    private StructureService structureService;
    
    /**
     * 手机上网行为服务类
     */
    private NetBehaviorLogService netBehaviorLogService;
    
    /**
     * 国际化服务类
     */
    private static MessageSource messageSourceService;
    
    private static String tmpTitle;
    
    private SystemWordsDao systemWordsDao;

    /**
     * 上网行为日志对象列表
     */
    private List<NetBehaviorLogInfoModel> netBehaviorLogInfoList;

    public OptNetBehaviorLogsThread(List<NetBehaviorLogInfoModel> netBehaviorLogInfoList, UserService userService,
            OrganizationService organizationService, StructureService structureService,
            NetBehaviorLogService netBehaviorLogService,SystemWordsDao systemWordsDao)
    {
        this.netBehaviorLogInfoList = netBehaviorLogInfoList;
        this.userService = userService;
        this.organizationService = organizationService;
        this.structureService = structureService;
        this.netBehaviorLogService = netBehaviorLogService;
        this.systemWordsDao = systemWordsDao;
    }

    @Override
    public void run()
    {
        List<NetBehaviorLogInfoModel> modelList = new ArrayList<NetBehaviorLogInfoModel>();
        if (CollectionUtils.isNotEmpty(netBehaviorLogInfoList))
        {
            // 手机只有一个用户，所以只需要用其中一个去查询用户的相关信息即可
            UserModel useModel = null;
            for (NetBehaviorLogInfoModel model : netBehaviorLogInfoList)
            {
                if (null == useModel)
                {
                    useModel = userService.findOne(model.getUserId());
                    
                    Integer orgId = useModel.getOrganization().getId();
                    OrganizationModel organization = organizationService.findOne(orgId);

                    Integer structureId = useModel.getStructure().getId();
                    StructureModel structureModel = structureService.findOne(structureId);

                    useModel.setOrganization(organization);
                    useModel.setStructure(structureModel);
                }
                model.setUserName(useModel.getRealname());
                model.setSurfTime(CommUtil.stringToDate(model.getSurfTimeStr()));
                OrganizationModel organizationModel = useModel.getOrganization();
                StructureModel structureModel = useModel.getStructure();
                model.setDepartmentName(null == structureModel ? null : structureModel.getName());
                model.setDepartmentId(null == structureModel ? null : structureModel.getId());
                model.setOrganizationName(null == organizationModel ? null : organizationModel.getName());
                model.setOrganizationId(null == organizationModel ? null : organizationModel.getId());
                model.setUserType(4);
                model.setConetent(getContent(model));
                
                List<WordsBean> list = systemWordsDao.getWordsList(organizationModel.getId());
                if(list.size()>0){
                    if(StringUtils.isNotEmpty(model.getHeadKeywords())&&StringUtils.isNoneEmpty(model.getHeadDescription())){
                        // 赋值网站类型
                        Integer word = Matrix.getSystemWord(model.getHeadKeywords(), model.getHeadDescription(), list);
                        model.setWebsiteType(word);
                    } else {
                        String keywords = "";
                        String description = "";
                        String meta = GetMeta.getKeywordsAndDescription(model.getSurfUrl());
                        if(StringUtil.isNotEmpty(meta)&&meta.indexOf(Constant.SEPARATOR) != -1){
                            String[] arr = meta.split(Constant.SEPARATOR);
                            keywords = arr[0];
                            description = arr[1];
                            model.setWebsiteType(Matrix.getSystemWord(keywords, description, list));
                        } else if(StringUtil.isNotEmpty(meta)){
                            keywords = meta;
                            model.setWebsiteType(Matrix.getSystemWord(keywords, description, list));
                        }
                        else {
                            model.setWebsiteType(0);
                        }
                    }
                } else {// 如果系统词库没有数据，默认为其它
                    model.setWebsiteType(0);
                }
                modelList.add(model);
            }
            
            netBehaviorLogService.saveNetBehaviorLogInfo(modelList);
        }
    }

    /**
     * 获取上网内容
     *
     * @author brave.chen
     * @param model
     *            上网行为日志行为对象
     * @return 上网内容
     */
    private String getContent(NetBehaviorLogInfoModel model)
    {
        
        if (null == messageSourceService)
        {
            messageSourceService = (MessageSource) SpringContextUtils.getBean("messageSourceService");
        }
        
        if (StringUtils.isEmpty(tmpTitle))
        {
            tmpTitle = messageSourceService.getMessage("thread.surf.net.url.not.find", null , LocaleContextHolder.getLocale());
        }
        
        Object[] objs = new Object[3];
        objs[0] = model.getUserName();
        objs[1] = model.getSurfTimeStr();
        
        String content = "";
        if (StringUtils.isEmpty(model.getTitle()))
        {
            //contentBuffer.append("用户：{0}在{1}浏览了黑名单网页！");
            content = messageSourceService.getMessage("thread.surf.net.blacklist.url.log", objs, LocaleContextHolder.getLocale());
        }
        else if (tmpTitle.equals(model.getTitle()))
        {
            //contentBuffer.append("用户：{0}在{1}浏览了无法访问的地址！");
            content = messageSourceService.getMessage("thread.surf.net.no.access.url.log", objs, LocaleContextHolder.getLocale());
        }
        else
        {
            objs[2] = model.getTitle();
            //contentBuffer.append("用户：{0}在{1}浏览了{2}！");
            content = messageSourceService.getMessage("thread.surf.net.access.url.log", objs, LocaleContextHolder.getLocale());
        }
        
        return content;
    }
}
