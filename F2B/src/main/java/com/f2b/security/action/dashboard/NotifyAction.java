package com.f2b.security.action.dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @ClassName: NotifyAction
 * @Description: 用户微信支付之支付成功之后的回调。
 * @author ligang.yang@softtek.com
 * @date 2016年8月18日 上午10:33:49
 *
 */
@Controller
@RequestMapping("wxpay/pay-3") // 表示支付成功后的通知
public class NotifyAction
{


    /**
     * sb为微信返回的xml
     */
    @RequestMapping("notify")
    @ResponseBody
    public String getAwardListPage(HttpServletRequest request, HttpServletResponse response)
    {

        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        }
        catch (IOException e1)
        {
            // TODO logger covert to bufferReader error
            e1.printStackTrace();
        }

        String line = null;
        StringBuilder sb = new StringBuilder();

        try
        {
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
            // TODO logger read and append string error
            e.printStackTrace();
        }
        return sb.toString();
    }

}
