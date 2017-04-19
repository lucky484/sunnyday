package com.softtek.mdm.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MatrixToImageWriter {
     
	   private static final int BLACK = 0xFF000000;
	   
	   private static final int WHITE = 0xFFFFFFFF;
	   
	   public MatrixToImageWriter() {  
		   
	    } 
	   public static BufferedImage toBufferedImage(BitMatrix matrix){
		   int width = matrix.getWidth();
		   int height = matrix.getHeight();
		   BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		   for(int x=0;x<width;x++){
			   for(int y=0;y<height;y++){
				   image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			   }
		   }
		   return image;
	   }
	   public static void writeToFile(BitMatrix matrix,String format,File file) throws IOException{
		   BufferedImage image = toBufferedImage(matrix);
		   if(!ImageIO.write(image,format,file)){
			   throw new IOException("Could not write an image of format " + format + " to " + file);
		   }
	   }
	   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException{
		   BufferedImage image = toBufferedImage(matrix); 
		   if (!ImageIO.write(image, format, stream)) {  
	            throw new IOException("Could not write an image of format " + format);
	        }  
	   }
		public void encode(String contents, File file, BarcodeFormat format, int width, int height, Map<EncodeHintType,Integer> hints) {
	         try {
	        	 hints.put(EncodeHintType.MARGIN,1);
	             BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height,hints);
	             writeToFile(bitMatrix, "png", file);
	           } catch (Exception e) {
	        	   e.printStackTrace();
	           }
     }
}
