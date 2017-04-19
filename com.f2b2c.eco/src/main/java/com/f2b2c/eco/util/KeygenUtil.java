/*
 * @Title: KeygenUtil.java
 * @Package com.f2b2c.eco.util
 * @Description: 自动生成
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年9月7日 下午4:53:23
 * @version V1.0
 */
package com.f2b2c.eco.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description: 自动生成
 * @author ligang.yang@softtek.com
 * @date 2016年9月7日 下午4:53:23
 *
 */
public class KeygenUtil
{
	//这里去掉容易混淆的字符{1,l,L,i,I,0,o,O}
    public static String[] chars = new String[]
    { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
             "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    /**
     * Generate uuid
     * 
     * @return String
     * @author: ligang.yang@softtek.com
     * @version: 2016年9月8日 上午10:20:00
     */
    public static String generateKeygen()
    {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++)
        {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x36]);
        }
        return shortBuffer.toString();
    }

    /**
     * 批量生成
     */
    public static List<String> generateKeys(Integer num)
    {
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < num; i++)
        {
            keys.add(generateKeygen());
        }
        return keys;
    }

}
