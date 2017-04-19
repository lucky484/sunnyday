package com.softtek.mdm.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.mdm.analyzer.Matrix;
import com.softtek.mdm.bean.WordsBean;
import com.softtek.mdm.dao.NetBehaviorLogDao;
import com.softtek.mdm.dao.SystemWordsDao;
import com.softtek.mdm.model.NetBehaviorLogInfoModel;
import com.softtek.mdm.util.GetMeta;
import jodd.util.StringUtil;

/**
 * 更新NetbehaviorLoginfo表
 * @author jane.hui
 *
 */
@Service("modifyNetbehaviorLoginfoJob")
public class ModifyNetbehaviorLoginfoJob {
    
    @Autowired
    private NetBehaviorLogDao netBehaviorLogDao;
    
    @Autowired
    private SystemWordsDao systemWordsDao;
    
    // 批量更新NetBehaviorLogInfo表的keywords和description和websitetype
    public void job(){
        List<NetBehaviorLogInfoModel> newList = new ArrayList<NetBehaviorLogInfoModel>();
        NetBehaviorLogInfoModel newNetBehaviorLogInfo = new NetBehaviorLogInfoModel();
        
        List<NetBehaviorLogInfoModel> list = netBehaviorLogDao.getNetBehaviorLog();
        if(CollectionUtils.isEmpty(list)){
        	return ;
        }
        int i = 0;
        for (NetBehaviorLogInfoModel net : list) {
            String keydesc = GetMeta.getKeywordsAndDescription(net.getSurfUrl());
            String keywords = "";
            String description = "";
            Integer websiteType = 0;
            if(StringUtil.isNotEmpty(keydesc)){
                String[] arr = keydesc.split("#");
                keywords = arr[0];
                description = arr[1];
                List<WordsBean> wordsList = systemWordsDao.getWordsList(net.getOrganizationId());
                if(CollectionUtils.isNotEmpty(wordsList)&&wordsList.size()>0){
                    websiteType= Matrix.getSystemWord(keydesc, description, wordsList);
                }
            }
            newNetBehaviorLogInfo.setId(net.getId());
            newNetBehaviorLogInfo.setHeadKeywords(keywords);
            newNetBehaviorLogInfo.setHeadDescription(description);
            newNetBehaviorLogInfo.setWebsiteType(websiteType);
            newList.add(newNetBehaviorLogInfo);
            if(++i==10){
                netBehaviorLogDao.updateNetBehaviorLogInfo(newList);
                newList.clear();
                i = 0;
            }
        }
    }
}
