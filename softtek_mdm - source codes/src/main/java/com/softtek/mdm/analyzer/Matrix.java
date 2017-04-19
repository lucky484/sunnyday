package com.softtek.mdm.analyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;
import com.softtek.mdm.bean.WordsBean;

/**
 * 矩阵工具类
 * @author jane.hui
 *
 */
public class Matrix {
    
    
   /* public static void main(String[] args) {
        String keywords = "淘宝,掏宝,网上购物,C2C,在线交易,交易市场,网上交易,交易市场,网上买,网上卖,购物网站,团购,网上贸易,安全购物,电子商务,放心买,供应,买卖信息,网店,一口价,拍卖,网上开店,网络购物,打折,免费开店,网购,频道,店铺";
        String description = "淘宝网 - 亚洲较大的网上交易平台，提供各类服饰、美容、家居、数码、话费/点卡充值… 数亿优质商品，同时提供担保交易(先收货后付款)等安全交易保障服务，并由商家提供退货承诺、破损补寄等消费者保障服务，让你安心享受网上购物乐趣！";
        List<WordsBean> list = new ArrayList<WordsBean>();
        WordsBean wordsBean = new WordsBean();
        wordsBean.setId(1);
        wordsBean.setName("资讯");
        wordsBean.setType(1);
        list.add(wordsBean);
        
        wordsBean = new WordsBean();
        wordsBean.setId(2);
        wordsBean.setName("新闻");
        wordsBean.setType(2);
        list.add(wordsBean);
        
        wordsBean = new WordsBean();
        wordsBean.setId(3);
        wordsBean.setName("视频");
        wordsBean.setType(3);
        list.add(wordsBean);
        
        wordsBean = new WordsBean();
        wordsBean.setId(4);
        wordsBean.setName("娱乐");
        wordsBean.setType(4);
        list.add(wordsBean);
        
        wordsBean = new WordsBean();
        wordsBean.setId(5);
        wordsBean.setName("门户");
        wordsBean.setType(5);
        list.add(wordsBean);
        
        wordsBean = new WordsBean();
        wordsBean.setId(6);
        wordsBean.setName("游戏");
        wordsBean.setType(6);
        list.add(wordsBean);
        
        wordsBean = new WordsBean();
        wordsBean.setId(7);
        wordsBean.setName("购物");
        wordsBean.setType(7);
        list.add(wordsBean);
        System.out.println(getSystemWord(keywords,description,list));
    }*/
    
    /**
     * 获取系统词库id
     * @param keywords:关键字
     * @param description:描述
     * @param list:系统词库列表
     * @return 返回系统词库外键
     */
    public static Integer getSystemWord(String keywords,String description,List<WordsBean> list){
        int[][] result = getMatrix(keywords, description, list); 
        Integer index = getMax(result);
        return getIndex((int)index, list);
    }
    
    /**
     * 根据索引获取系统中的关键词
     * @param index:索引
     * @param systemWordsList:系统词库
     * @return 返回系统词库对应的值
     */
    private static Integer getIndex(int index,List<WordsBean> systemWordsList){
        if(index<systemWordsList.size()){
        	return systemWordsList.get(index).getType();
        }
        // 表示其它
        return 0;
    }
    
    /**
     * 获取矩阵中最大的数值(一位列向量)
     * @param result
     * @return
     */
    private static Integer getMax(int[][] result){
    	// 假定二维数组中的第一个为最大的值
        int max = 0;
        // 返回最大矩阵中的索引
        int maxIndex = 0;
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].length;j++){
                if(result[i][j] > max){ //如果该数组中还有比最大值都大的,那么它就是最大的
                    max = result[i][j];
                    maxIndex = j;
                }
            }
        }
        return maxIndex;
    }
    
    /**
     * 两个矩阵相加(用二八定律相乘)省略系统举证
     * @param matrix1:关键词矩阵
     * @param matrix2:描述矩阵
     * @return 返回相加后的结果
     */
    private static int [][] add(int[][] matrix1,int[][] matrix2){
        int[][] result = new int[1][matrix1[0].length];
        for(int i=0;i<matrix1.length;i++){
            for(int j=0;j<matrix1[i].length;j++){
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }
    
    /**
     * 用IKAnalyzer分析解析keywords与descripton
     * @param keywords:html中的meta -> keywords
     * @param description:html中的meta -> description
     * @param systemWordsList:系统列表
     * @return 返回矩阵
     */
    private static int[][] getMatrix(String keywords,String description,List<WordsBean> systemWordsList){
        int [][] keywordsMatrix = new int[1][];
        int[][] descMatrix = new int[1][];
        
        // 创建IKAnalyzer中文分词对象
        IKAnalyzer analyzer = new IKAnalyzer();
        analyzer.setUseSmart(true);
        // 计算html中的meta->keywords的词与系统词库的相似读(余弦定义)
        
        List<String> keywordList = printAnalysisResult(analyzer,keywords);
        // 计算用余弦定义得到的相似度值设置到矩阵中
        compute(keywordList,systemWordsList,keywordsMatrix,0);
        
        analyzer = new IKAnalyzer();
        analyzer.setUseSmart(true);
        
        // 计算html中的meta->description的词与系统词库的相似度(余弦定义)
        List<String> descriptionList = printAnalysisResult(analyzer,description);
       
        // 计算用余弦定义得到的相似度值设置到矩阵中
        compute(descriptionList,systemWordsList,descMatrix,1);
        
        return add(keywordsMatrix, descMatrix);
    }
    
    /**
     * 用余弦定义原来去计算系统词库与keywords或者description相似度
     * @param keywordList:html标签meta->keyword列表
     * @param descriptionList:html标签meta->descrtion列表
     * @param systemWordsList:系统词库列表
     * @param wordMatrix：将系统词语与meta标签计算形似度的值设置到矩阵中
     * @param tag:标识(tag=0标识->矩阵中第一维存keywords与系统词库的相似度值，tag=1标识 ->矩阵中的第二维存description与系统词库的相似度)
     * @return 不返回
     */
    private static void compute(List<String> list,List<WordsBean> systemWordsList,int [][] wordMatrix,int tag){
        if(CollectionUtils.isEmpty(systemWordsList)){
        	return ;
        }
    	int i = 0;
        boolean result = false;
        // 巴莱多定义的系数或者二八定律的系数，如果tag=0系数为8，如果tag=1系数为2
        int paredo = tag==0?8:2;
        wordMatrix[0] = new int[systemWordsList.size()];
        
        // 循环系统词库
        for (WordsBean s1 : systemWordsList) {
            result = false;
            // 循环网络爬虫的的关键字列表
            for (String s2 : list) {
                Similarity similarity = new Similarity(s1.getName(), s2);  
                double sim = similarity.sim();
                // 利用余弦定理去判断系统词库与关键字的形似度，如果为1.0，则代表是相似的
                if(((int)sim)==1){
                    result = true;
                    break;
                }
            }
            // 如果系统词库与关键字的词一样，则将设置为1，如果没有则为0
            
            wordMatrix[0][i++] =result? paredo:0;
        }
    }
    /**
     * 打印出给定分词器的分词结
     * @param analyzer:分析
     * @param keyWord：关键语
     */
    private static List<String>  printAnalysisResult(Analyzer analyzer,String keyWord){
    	List<String> list = new ArrayList<String>();
    	try {
        	TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(keyWord));
            tokenStream.addAttribute(CharTermAttribute.class);
            while(tokenStream.incrementToken()){
                CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
                list.add(charTermAttribute.toString());
            }
        } catch (IOException e) {
            return null;
        }
        return list;
    }
}