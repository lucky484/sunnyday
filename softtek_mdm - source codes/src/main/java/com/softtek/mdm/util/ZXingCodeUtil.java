package com.softtek.mdm.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class ZXingCodeUtil {
	
	private static Logger logger=LoggerFactory.getLogger(ZXingCodeUtil.class);
	
	private static int codeWidth = 0;
	
	private static int codeHeight = 0;
	
	/**
	 * 
	 * @param content
	 * @return
	 */
	public static String createQrcode(String filePath,String content){
		codeHeight=codeWidth=200;
        return createCode(filePath,content,BarcodeFormat.QR_CODE);
    }
	
	/**
	 * 解析二维码
	 * @param filePath
	 * @return
	 */
	public static String extraQrcode(String filePath) {
        try {  
        	BufferedImage image = ImageIO.read(new File(filePath));  
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            return result.getText();
        } catch (IOException e) {  
        	logger.error(e.getMessage());
            return null;
        } catch (NotFoundException e) {  
        	logger.error(e.getMessage());
            return null;
        }  
	}
	
	/**
	 * 条形码
	 * @param content
	 * @return
	 */
	public static String createBarcode(String filePath,String content){
		codeWidth=100;
		codeHeight=30;
		return createCode(filePath,content,BarcodeFormat.EAN_13);
	}
	
	/**
	 * 生成png的码
	 * @param filePath
	 * @param content
	 * @param format
	 * @return
	 */
	public static String createCode(String filePath,String content,BarcodeFormat format){
        try {
            String qrcodeFormat = filePath.substring(filePath.lastIndexOf(".")+1).toLowerCase();
            HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, codeWidth, codeHeight, hints);

            File file=new File(filePath);
            Path path=file.toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat, path);
            return filePath;
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	 return null;
        }
	}
}
