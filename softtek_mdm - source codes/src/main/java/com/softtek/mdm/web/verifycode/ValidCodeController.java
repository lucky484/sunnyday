package com.softtek.mdm.web.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author jing.liu
 *
 */
@Controller
public class ValidCodeController {
    
	private final int width = 74;    //定义图片的宽度
	private final int height = 28;   //定义图片的高度
	private final int code = 4;      //定义验证码的个数
	private final int xx = 15;
    private final int fontHeight = 18;
    private final int codeY = 20;
    
    //去掉I,1,O,0容易混淆的字母
    private final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    private Logger logger=LoggerFactory.getLogger(ValidCodeController.class);
    
    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/getCode",method=RequestMethod.GET)
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	try {
    		//定义图像buff
        	BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        	Graphics gd = buff.getGraphics();
        	
        	// 创建一个随机数生成器类
            Random random = new Random();
            // 将图像填充为白色
            gd.setColor(Color.white);
            gd.fillRect(0, 0, width, height);
            
            //创建字体，字体的大小根据图片的高度来定
            Font font = new Font("Arial",Font.PLAIN,fontHeight);
            //设置字体
            gd.setFont(font);
            //设置边框
            gd.setColor(null);
            gd.drawRect(0, 0, width-1, height-1);
            
            //sb用于保存随机产生 的验证码，以便用户登录后进行验证
            StringBuilder sb = new StringBuilder();
            int red = 0,green = 0,blue = 0;
            
            //生成随机产生的4位验证码
            for(int i=0;i<code;i++){
            //得到随机产生的验证码
              String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length-1)]);
              //产生随机的颜色来构造颜色值，这样输出的每位数字的颜色都不一样
              red = random.nextInt(255);
              green = random.nextInt(255);
              blue = random.nextInt(255);
              
              //将随机产生的验证码，绘制到图片中
              gd.setColor(new Color(red,green,blue));
              gd.drawString(code, (i+1) * xx,codeY);
              //将随机产生的验证码组合在一起
              sb.append(code);
            }
            gd.dispose();
            
            request.getSession().setAttribute("code_validate", sb.toString());
            
            //禁止图像缓存
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Exipres", 0);
            response.setContentType("image/jpeg");
            
            ServletOutputStream stream = response.getOutputStream();
            ImageIO.write(buff, "jpeg", stream);
            stream.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
}
