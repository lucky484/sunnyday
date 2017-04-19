package com.softtek.mdm.abs;

import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.softtek.mdm.util.ExcelUtils;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;

/**
 * 导入excel抽象类
 * @author jane.hui
 *
 */
public abstract class AbstractImportExcel {
    /**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSourceService;
	
	/**
	 * 日志记录对象
	 */
	private static Logger logger = Logger.getLogger(AbstractImportExcel.class);
	
	/**
	 * 将sheet里的数据加入到list
	 * @param fileType：excel版本(03,07)
	 * @param files:要导入的文件
	 * @param rowCount:列数
	 * @param titles:标题(将excel每个标题以,分割组成字符串，注意最后一个标题也有)
	 * @return 
	 */
	public ResultDTO createWorksbook(Integer fileType,MultipartFile files,Integer rowCount,String[] titles){
		ResultDTO resultDTO = new ResultDTO();
		JsonArray jsonArray = new JsonArray();
		StringBuilder erromessages = new StringBuilder();
		try {
			InputStream ins = files.getInputStream();
			Workbook wb = null;
			wb = WorkbookFactory.create(ins);
			ins.close();
			Sheet sheet = ExcelUtils.getSheet(fileType, wb);
            if(sheet == null) {
            	resultDTO.type = BaseDTO.ERROR;
            	resultDTO.message = messageSourceService.getMessage("web.institution.namelistcontroller.excel.get.sheet.error", null,
						LocaleContextHolder.getLocale());
            	return resultDTO;
            }
			// 总行数
			int rowLength = sheet.getLastRowNum();
			if(rowLength==0){
	        	resultDTO.type = BaseDTO.ERROR;
	        	resultDTO.message = messageSourceService.getMessage("web.institution.namelistcontroller.excel.null.error.template.nodata", null,LocaleContextHolder.getLocale());
				return resultDTO;
			}
			Row rowtest = sheet.getRow(0);
			Cell cell = null;
			// 判断cell单元格是否为空以及是否和excel标题是否匹配
			for(int c = 0;c < rowCount; c++) {
				cell = rowtest.getCell(c);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(rowLength > 0) {
					if(cell != null) {
						String value = cell.getStringCellValue().trim();
						// 计算cell中的值与标题数组匹配度，如果有匹配累计加1
						int count = 0;
						for(int t = 0;t < titles.length; t++) {
							 if(value.equals(titles[t])) {
								 count++;
							 }
						}
						// cell中的值与titles数组中的值没有匹配项目
						if(count == 0) {
			            	resultDTO.type = BaseDTO.ERROR;
			            	resultDTO.message = messageSourceService.getMessage("web.institution.namelistcontroller.excel.cell.title.mismatching", null,LocaleContextHolder.getLocale());
			            	return resultDTO;
						}
					}
				}
			}
			StringBuilder rowErrMsg = null;
			if (rowLength > 0) {
				rowErrMsg = new StringBuilder();
 				JsonObject object = null;
				// 判断该行是否有数据(0:导入模板没数据  除0之外导入模板
				int tag = 0;
				// 针对每行上每个列是否有数据(0.无数据 1.有数据)
				int cellTag = 0;
				for (int i = 1; i <= rowLength; i++) {
					object = new JsonObject();
					// 得到Excel工作表的行
					Row row = sheet.getRow(i);
					if(row != null) {
						// 如果row不为空就代表行数有数据，需要在tag标识上加1
						tag += 1;
						for(int j = 0; j <= rowCount; j++) {
							cellTag = 0;
							Cell oldCell = row.getCell(j);
							String result = addCellDataToList(object,i,j,oldCell);
	                        if(!BaseDTO.SUCCESS.equals(result)) {//如果不返回success表示该列数据有误或者为空
	                        	erromessages.append(result);
	                        	rowErrMsg.append(result);
	                        } else { // 为success，表示该条数据无误
	                        	cellTag += 1;
	                        }
						}
						// 计算下每一行cellTag等于0，不为空则tag+=1表示导入模板有数据，如果cellTag=0表示该行每一列都有数据
						if(cellTag != 0) {
							tag += 1;
						}
						if(StringUtil.isBlank(rowErrMsg.toString())){
							jsonArray.add(object);
						} else {
							rowErrMsg = new StringBuilder();
						}
					} else {
						Object[] args = { i + 1 };
						String emptyTemplate = messageSourceService.getMessage("web.institution.namelistcontroller.excel.null.error.data", args, LocaleContextHolder.getLocale());
						rowErrMsg.append(emptyTemplate);
						erromessages.append(emptyTemplate);
					}
				}
				// 表示导入模板没有数据
				if(tag == 0){
		        	resultDTO.type = BaseDTO.ERROR;
		        	resultDTO.message = messageSourceService.getMessage("web.institution.namelistcontroller.excel.null.error.template.nodata", null,LocaleContextHolder.getLocale());
					return resultDTO;
				}
			}
		} catch (IOException e) {
        	resultDTO.type = BaseDTO.ERROR;
        	resultDTO.message = messageSourceService.getMessage("web.institution.namelistcontroller.excel.import.file.failed", null,LocaleContextHolder.getLocale());
			logger.error("import excel failed, message:" + e.getMessage());
			return resultDTO;
		} catch (InvalidFormatException e) {
        	resultDTO.type = BaseDTO.ERROR;
        	resultDTO.message = messageSourceService.getMessage("web.institution.namelistcontroller.excel.create.workbook.failed", null,LocaleContextHolder.getLocale());
			logger.error("create workbook failed, message:" + e.getMessage());
			return resultDTO;
		} 
		String message = erromessages.toString();
		if(StringUtil.isNotBlank(message)) {
			resultDTO.type = BaseDTO.ERROR;
		} else {
			resultDTO.type = BaseDTO.SUCCESS;
		}
		resultDTO.message = message;
		resultDTO.result = jsonArray.toString();
		return resultDTO;
	}
	
	/**
	 * 将单元格的数据加入到list中
	 * @param i:行数
	 * @param j:列数
	 * @param cValue:单元格中的数据
	 * @return
	 */
	public abstract String addCellDataToList(JsonObject object,int i, int j,Cell cell);
}