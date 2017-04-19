package com.softtek.mdm.analyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 字符串相似性匹配算法
 * @author jane.hui
 *
 */
public class Similarity {
    Map<Character, int[]> vectorMap = new HashMap<Character, int[]>();
    
    public Similarity(String s1,String s2){
        for(Character character1 : s1.toCharArray()){
            if(vectorMap.containsKey(character1)){
                vectorMap.get(character1)[0]++;
            } else {
                int[] tempArray = {1,0};
                vectorMap.put(character1, tempArray);
            }
        }
        
        for (Character character2 : s2.toCharArray()) {  
            if (vectorMap.containsKey(character2)) {  
                vectorMap.get(character2)[1]++;  
            } else {  
            	int[] tempArray = {0,1}; 
                vectorMap.put(character2, tempArray);  
            }  
        } 
    }
    
    // 求余弦相似度
    public double sim(){
        double result = 0;  
        result = pointMulti(vectorMap) / sqrtMulti(vectorMap);  
        return result;  
    }
    
    private double sqrtMulti(Map<Character, int[]> paramMap) {  
        double result = 0;  
        result = squares(paramMap);  
        result = Math.sqrt(result);  
        return result;  
    } 
    
    // 求平方和  
    private double squares(Map<Character, int[]> paramMap) {  
        double result1 = 0;  
        double result2 = 0;  
        Set<Character> keySet = paramMap.keySet();  
        for (Character character : keySet) {  
            int temp[] = paramMap.get(character);  
            result1 += (temp[0] * temp[0]);  
            result2 += (temp[1] * temp[1]);  
        }  
        return result1 * result2;  
    }
    
    // 点乘法  
    private double pointMulti(Map<Character, int[]> paramMap) {  
        double result = 0;  
        Set<Entry<Character, int[]>> entries=paramMap.entrySet();
        if(entries!=null){
        	 for (Entry<Character, int[]> entry : entries) {
            	 result += (entry.getValue()[0] * entry.getValue()[0]);
    		}
        }
        return result;  
    }
}