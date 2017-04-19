package com.softtek.mdm.util;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import jodd.util.StringUtil;

/**
 * 获取网页meta标签中的keywords和description
 * @author jane.hui
 *
 */
public class GetMeta {

    private static Logger logger = Logger.getLogger(DomainUtil.class);
    
    /**
     * 获取一级域名
     * @param url：网页url
     * @return 返回一级域名
     */
    public static String getWebUrl(String url){
        String domain = DomainUtil.getTopDomainWithoutSubdomain(url);
        return String.format("http://www.%s", domain);
    }
    
    /**
     * 获取标签中的keywords和description
     * @return 返回关键字和描述
     */
    public static String getKeywordsAndDescription(String url){
        Document doc = null;
        String desc = "";
        try {
            String tempUrl = getWebUrl(url);
            doc = Jsoup.connect(tempUrl).get();
            Elements metas = doc.head().select("meta");
            String keywords = "";
            String description = "";
            for(Element meta:metas){
                String content = meta.attr("content");
                if("keywords".equalsIgnoreCase(meta.attr("name"))||"keyword".equalsIgnoreCase(meta.attr("name"))){
                    keywords = content;
                }
                if("description".equalsIgnoreCase(meta.attr("name"))){
                    description = content;
                }
            }
            // 关键字
            if(StringUtil.isNotEmpty(keywords)){
                desc = keywords;
            };
            // 描述
            if(StringUtil.isNotEmpty(description)){
                if(StringUtil.isNotEmpty(desc)){
                    desc += String.format("#%s", description);
                } else {
                    desc = description;
                }
            }
        } catch (IOException e){
            logger.error("Connection URL error,url="+url+",error message:"+e.toString());
        }
        return desc;
    }
}