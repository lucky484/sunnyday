/*
 * @Title: PoiUtil.java
 * @Package f2b
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年6月17日 下午4:07:19
 * @version V1.0
 */
package com.f2b.sugar.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;  


/**
 * 
  * @ClassName: ExcelUtil
  * @Description: 导出excel
  * @author ligang.yang@softtek.com
  * @date 2016年6月17日 下午5:09:21
  *
 */
public class ExcelUtil {

	/**
	  * @param String title sheet名
	  * @param String[] headers 列名
      * @param Collection<T> dataset 集合 
      * @param OutputStream out 输出流
      * @param String pattern 日期格式
	  * @return void
	  * @throws 
	  * @author: ligang.yang@softtek.com  
	  * @version: 2016年6月17日 下午5:10:05
	  */
    @SuppressWarnings( { "unchecked", "deprecation" })  
	public static void exportToExcel(String fileName, String title, String[] headers, Collection<T> dataset,
			String pattern, HttpServletResponse response) {
		try {
			OutputStream out = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")+".xls");
			// 声明一个工作薄  
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格  
			HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节  
			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式  
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式  
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体  
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式  
			style.setFont(font);
			// 生成并设置另一个样式  
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.WHITE.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体  
			HSSFFont font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式  
			style2.setFont(font2);

			// 声明一个画图的顶级管理器  
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档  
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容  
			//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
			comment.setAuthor("admin");

			// 产生表格标题行  
			HSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}

			// 遍历集合数据，产生数据行  
			Iterator<T> it = dataset.iterator();
			int index = 0;
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				Object t = it.next(); //这里不要使用泛型强制转换  
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
				Field[] fields = t.getClass().getDeclaredFields();
				for (short i = 0; i < fields.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(style2);
					Field field = fields[i];
					String fieldName = field.getName();
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					System.out.println("-----------------fieldName: " + fieldName);
					try {
						Class tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						System.out.println("----------------value: " + value);
						// 判断值的类型后进行强制类型转换  
						String textValue = "";
						if (value == null || "".equals(value)) {
							value = "";
						} else if (value instanceof Integer) {
							int intValue = (Integer) value;
							cell.setCellValue(intValue);
						} else if (value instanceof Float) {
							float fValue = (Float) value;
							cell.setCellValue(fValue + "");
						} else if (value instanceof Double) {
							double dValue = (Double) value;
							cell.setCellValue(dValue + "");
						} else if (value instanceof Long) {
							long longValue = (Long) value;
							cell.setCellValue(longValue);
						}
						if (value instanceof Boolean) {
							boolean bValue = (Boolean) value;
							textValue = "男";
							if (!bValue) {
								textValue = "女";
							}
						} else if (value instanceof Date) {
							Date date = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
							textValue = sdf.format(date);
						} else {
							// 其它数据类型都当作字符串简单处理  
							textValue = value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
						if (textValue != null) {
							Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理  
								cell.setCellValue(Double.parseDouble(textValue));
							} else {
								HSSFRichTextString richString = new HSSFRichTextString(textValue);
								HSSFFont font3 = workbook.createFont();
								font3.setColor(HSSFColor.BLACK.index);
								richString.applyFont(font3);
								cell.setCellValue(richString);
							}
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} finally {
						// 清理资源  
					}
				}
			}

			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
    
    /*************************************************************************** 
     * @param fileName EXCEL文件名称 
     * @param listTitle EXCEL文件第一行列标题集合 
     * @param dataset EXCEL文件正文数据集合 
     * @return 
     */  
    public static String exportExcel2(String fileName,String[] Title, List<Object> dataset, String pattern, HttpServletResponse response) {  
     String result="系统提示：Excel文件导出成功！";    
     // 以下开始输出到EXCEL  
     try {      
      //定义输出流，以便打开保存对话框______________________begin  
      OutputStream os = response.getOutputStream();// 取得输出流        
      response.reset();// 清空输出流        
      response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")+".xls");  
   // 设定输出文件头        
      response.setContentType("application/msexcel");// 定义输出类型      
      //定义输出流，以便打开保存对话框_______________________end  
     
      /** **********创建工作簿************ */  
      WritableWorkbook workbook = Workbook.createWorkbook(os);  
     
      /** **********创建工作表************ */  
     
      WritableSheet sheet = workbook.createSheet("Sheet1", 0);  
     
      /** **********设置纵横打印（默认为纵打）、打印纸***************** */  
      jxl.SheetSettings sheetset = sheet.getSettings();  
      sheetset.setProtected(false);  
     
      /** ************设置单元格字体************** */  
      WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);  
      WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);  
     
      /** ************以下设置三种单元格样式，灵活备用************ */  
      // 用于标题居中  
      WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);  
      wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
      wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
      wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
      wcf_center.setWrap(false); // 文字是否换行  
        
      // 用于正文居左  
      WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);  
      wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条  
      wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
      wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐  
      wcf_left.setWrap(false); // 文字是否换行     
      
     
      /** ***************以下是EXCEL开头大标题，暂时省略********************* */  
      //sheet.mergeCells(0, 0, colWidth, 0);  
      //sheet.addCell(new Label(0, 0, "XX报表", wcf_center));  
      /** ***************以下是EXCEL第一行列标题********************* */  
      for (int i = 0; i < Title.length; i++) {  
       sheet.addCell(new Label(i, 0,Title[i],wcf_center));  
      }     
      /** ***************以下是EXCEL正文数据********************* */  
      Field[] fields=null; 
      
      CellView cellView = new CellView();  
      //cellView.setAutosize(true); //设置自动大小  
      int i=1;  
      for(Object obj:dataset){  
          fields=obj.getClass().getDeclaredFields();  
          int j=0;  
          for(Field v:fields){  
              v.setAccessible(true);  
              Object va=v.get(obj);  
              if(va==null){  
                  va="";  
              }  
              if (va instanceof Date) {
					Date date = (Date) va;
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);
					va = sdf.format(date);
				}
              //sheet.setColumnView(i, cellView);//根据内容自动设置列宽
              sheet.setColumnView(0, 8);//列宽写死，不同列宽度不同
              sheet.setColumnView(1, 25);
              sheet.setColumnView(2, 25);
              sheet.setColumnView(3, 9);
              sheet.setColumnView(4, 15);
              sheet.setColumnView(5, 9);
              sheet.setColumnView(6, 9);
              sheet.setColumnView(7, 40);
              sheet.setColumnView(8, 9);
              sheet.setColumnView(9, 9);
              sheet.setColumnView(10, 10);
              sheet.setColumnView(11, 24);
              sheet.setColumnView(12, 24);
              sheet.setColumnView(13, 30);
              sheet.addCell(new Label(j, i,va.toString(),wcf_left));  
              j++;  
          }  
          i++;  
      }  
      /** **********将以上缓存中的内容写到EXCEL文件中******** */  
      workbook.write();  
      /** *********关闭文件************* */  
      workbook.close();     
     
     } catch (Exception e) {  
      result="系统提示：Excel文件导出失败，原因："+ e.toString();  
      System.out.println(result);   
      e.printStackTrace();  
     }  
     return result;  
    }  
    
}  