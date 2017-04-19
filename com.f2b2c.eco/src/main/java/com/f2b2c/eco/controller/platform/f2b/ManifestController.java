package com.f2b2c.eco.controller.platform.f2b;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.common.Dictionary;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.platform.BShopService;
import com.f2b2c.eco.service.platform.DictionaryService;
import com.f2b2c.eco.service.platform.FSalesOrderService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.ConstantUtil.RoleType;
import com.f2b2c.eco.util.DateUtil;

/**
 * 货单
 * 
 * @author color.wu
 *
 */
@Controller(value="fManifestController")
@RequestMapping(value="/farm/manifest")
public class ManifestController extends BaseController {

	@Autowired
	private FSalesOrderService fSalesOrderService;
	@Autowired
	private FUserService fUserService;
	@Autowired
	private BShopService bShopService;
	@Autowired
	private DictionaryService dictionaryService;
	
	private static final String DICTIONARY_TYPE = "producer";
	
	private static final Logger logger = LoggerFactory.getLogger(ManifestController.class);
	
	/**
	 * 显示出货单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"","/"},method=RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response){
		return "platform/manifest";
	}
	
	/**
	 * 
	 * @param pageable
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/page",method=RequestMethod.POST)
	public final Object page(Pageable pageable,HttpServletRequest request, HttpServletResponse response){
		String time = request.getParameter("time");
		String condition = request.getParameter("condition");
		String type = request.getParameter("type");
		Map<String, Object> map=new HashMap<>();
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		List<Integer> areaListAll = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		List<Integer> bUserList = new ArrayList<Integer>();
		if (!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID) && !user.getRoleId().equals(RoleType.FINANCE_ROLE_ID)) {
			if (user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)) { // 省合伙人
				// List<Integer> list =
				// areaService.queryCityIdByProvinceId(user.getProvinceid()); //
				// 获取所有的市id
//				List<Integer> areaList = areaService.queryAreaIdByCityId(list);
				areaListAll.add(user.getProvinceid());
//				areaListAll.addAll(list);
//				areaListAll.addAll(areaList);
			} else if (user.getRoleId().equals(RoleType.CITY_ROLE_ID)) {
//				List<Integer> cityList = new ArrayList<Integer>();
//				cityList.add(user.getCityid());
//				List<Integer> areaList = areaService.queryAreaIdByCityId(cityList);
				areaListAll.add(user.getCityid());
//				areaListAll.addAll(areaList);
			} else if (user.getRoleId().equals(RoleType.AREA_ROLE_ID)) {
				areaListAll.add(user.getAreaId());
			} else if (user.getRoleId().equals(RoleType.COUNSELOR_ROLE_ID)) {
				userList.add(user.getId());
			}
			if (areaListAll != null && areaListAll.size() > 0) {
				userList = fUserService.queryUserIdByAreaId(areaListAll);
			}
			if (userList != null && userList.size() > 0)
			bUserList = bShopService.queryBUserIdByList(userList);
		}
		map.put("pageable", pageable);
		map.put("bUserList", bUserList);
		map.put("condition", StringUtils.trimToNull(condition));
		map.put("time", StringUtils.trimToNull(time));
		map.put("type", Integer.parseInt(type) == -1 ? null:type);
		Page<FSalesOrderModel> page=fSalesOrderService.findWithPagination(map);
		return page;
	}
	
	/**
	 * 出货详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public final String detail(String oid,HttpServletRequest request, HttpServletResponse response,Model model){
		if(StringUtils.isBlank(oid)){
			return "redirect:/farm/manifest";
		}
		Map<String, Object> map=fSalesOrderService.getOrderInfo(oid);
		model.addAttribute("orderInfo", CollectionUtils.isEmpty(map)?null:map.get("order"));
		String producer = dictionaryService.getProducer(DICTIONARY_TYPE);
		if(StringUtils.isNotBlank(producer)){
			//配置的是制单人
			model.addAttribute("producer",producer);
		}else{
			//默认的是admin
			model.addAttribute("producer","admin");
		}
		return "platform/manifest/detail";
	}
	
	@RequestMapping(value="/getproducer",method=RequestMethod.GET)
	@ResponseBody
	public String getProducer(HttpServletRequest request,HttpServletResponse response){
		
		String producer = dictionaryService.getProducer(DICTIONARY_TYPE);
		return producer;
	}
	
	@RequestMapping(value="/setproducer",method=RequestMethod.POST)
	@ResponseBody
	public String setProducer(HttpServletRequest request,HttpServletResponse response){
		
		String producer = request.getParameter("producer");
		Dictionary dictionary = new Dictionary();
		dictionary.setName(DICTIONARY_TYPE);
		dictionary.setValue(producer);
		dictionaryService.setProducer(dictionary);
		return "success";
	}
	
	//批量导出
	@RequestMapping(value="/batchprint",method=RequestMethod.POST)
	@ResponseBody
	public String batchPrint(HttpServletRequest request,HttpServletResponse response){
		
		String oidArray = request.getParameter("oidArray");
		List<File> files = new ArrayList<File>();
		if(StringUtils.isNotBlank(oidArray)){
			String[] oids = StringUtils.split(oidArray, ",");
			for(int i=0;i<oids.length;i++){
				Map<String,Object> map = fSalesOrderService.getOrderInfo(oids[i]);
				FSalesOrderModel fSalesOrderModel = (FSalesOrderModel) map.get("order");
				if(fSalesOrderModel!=null){
					if(fSalesOrderModel.getfSalesOrderDetailsModel()!=null){
						//将数据写入excel中去
						if(fSalesOrderModel.getfSalesOrderDetailsModel().size() <= 12){
							File dest = WriteBatchDateToExcel(fSalesOrderModel,12*0+1,response);
							files.add(dest);
						}else{
							Integer size = fSalesOrderModel.getfSalesOrderDetailsModel().size();
							File dest = writeBatchToExcelAddRows(fSalesOrderModel,12*0+1,response,size);
							files.add(dest);
						}
					}
				}
			}
			//把所有的文件走导出的操作
			ZipOutputStream zos = null;
			try {
				zos = new ZipOutputStream(response.getOutputStream());
				zipFile(files, "", zos);
				response.setContentType("APPLICATION/OCTET-STREAM");  
				response.setHeader("Content-Disposition","attachment; filename="+ DateUtil.getyyyyMMddhhmmss()+".zip");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}finally{
				if(zos!=null){
					try {
						zos.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		return "success";
	}
	
	private void zipFile(List<File> files, String baseName, ZipOutputStream zos) {
		for (int i=0;i<files.size();i++) {  
		       File f=files.get(i);  
		       FileInputStream fis = null;
		       try {
				   zos.putNextEntry(new ZipEntry(baseName + f.getName()));
				   fis = new FileInputStream(f);     
			       byte[] buffer = new byte[1024];     
			       int r = 0;     
			       while ((r = fis.read(buffer)) != -1) {     
			           zos.write(buffer, 0, r);     
			       }     
			} catch (IOException e) {
				logger.error(e.getMessage());
			}finally{
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}     
		 }  
	}

	private File writeBatchToExcelAddRows(FSalesOrderModel fSalesOrderModel, int start, HttpServletResponse response,
			Integer size) {
		
		//读取excel的模板，将数据写入里面
		String path = ManifestController.class.getClassLoader().getResource("template/ordertemplate.xls").getPath();
		File template = new File(path);
		//读取excel模板  
		HSSFWorkbook wb = null;
		HSSFWorkbook newWb = null;
		//修改模板内容导出新模板  
		OutputStream out = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));  
			wb = new HSSFWorkbook(fs);  
			wb.setSheetName(0, fSalesOrderModel.getOrderId());//设置sheet的名称
			//读取了模板内所有sheet内容  
			HSSFSheet sheet = wb.getSheetAt(0);  
			//在workbook指定行之后添加行数
			for(int j=0;j<size-12;j++){
				sheet.shiftRows(19+j, sheet.getLastRowNum(), 1,true,false);
				HSSFRow newRow = sheet.createRow(19+j);
				copyRow(wb,newRow,sheet.getRow(18));
				CellRangeAddress cra=new CellRangeAddress(19+j, 19+j, 2, 4);        
		        //在sheet里增加合并单元格  
		        sheet.addMergedRegion(cra);
			}
			out = new FileOutputStream(new File("D:/"+fSalesOrderModel.getOrderId()+".xls"));
			wb.write(out);
			//再次读入新的模板
			POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream("D:/"+fSalesOrderModel.getOrderId()+".xls"));
			newWb = new HSSFWorkbook(fis); 
			newWb.setSheetName(0, fSalesOrderModel.getOrderId());//设置sheet的名称
			//读取了模板内所有sheet内容  
			HSSFSheet newSheet = newWb.getSheetAt(0);
			//在相应的单元格进行赋值  
			HSSFCell cell = newSheet.getRow(3).getCell(1);  
			cell.setCellValue(fSalesOrderModel.getOrderId());  
			HSSFCell cell1 = newSheet.getRow(4).getCell(1);  
			cell1.setCellValue(fSalesOrderModel.getShopName());  
			HSSFCell cell2 = newSheet.getRow(3).getCell(3);  
			cell2.setCellValue(fSalesOrderModel.getUser().getPhone());  
			HSSFCell cell3 = newSheet.getRow(3).getCell(8);  
			cell3.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getCreatedTime()));  
			HSSFCell cell4 = newSheet.getRow(5).getCell(1);
			HSSFCellStyle cellStyle = newWb.createCellStyle();       
			cellStyle.setWrapText(true); 
			HSSFFont font = newWb.createFont();   
			font.setFontHeightInPoints((short)9);  
			font.setFontName("宋体");
			cellStyle.setFont(font);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(fSalesOrderModel.getReceiverAddress());  
			HSSFCell cell5 = newSheet.getRow(4).getCell(3);  
			cell5.setCellValue(fSalesOrderModel.getUser().getRealName());  
			HSSFCell cell6 = newSheet.getRow(4).getCell(8);  
			cell6.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getSendTime())); 
			//填入数据
			FSalesOrderDetailsModel detail = null;
			HSSFCellStyle cellStyle3 = newWb.createCellStyle();  
			HSSFFont font2 = newWb.createFont();   
			font2.setFontHeightInPoints((short)9);  
			font2.setFontName("宋体");
            cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle3.setFont(font2);
			for(int i=0;i<fSalesOrderModel.getfSalesOrderDetailsModel().size();i++){
				detail = fSalesOrderModel.getfSalesOrderDetailsModel().get(i);
				HSSFRow row = newSheet.getRow(i+7);//创建所需的行数  
				row.getCell(0).setCellValue(i+start);
				HSSFCellStyle cellStyle2 = newWb.createCellStyle();
				HSSFCell cellName = row.getCell(1);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setWrapText(true); 
				cellStyle2.setFont(font);
				cellName.setCellStyle(cellStyle2);
				cellName.setCellValue(detail.getGoodsName());
            	row.getCell(2).setCellValue(detail.getGoods()==null?"":detail.getGoods().getSpec());
            	row.getCell(5).setCellValue(detail.getGoodsQty());
            	row.getCell(6).setCellValue("箱");
            	HSSFCell cellPrice = row.getCell(7);
            	cellPrice.setCellValue(Double.valueOf(detail.getGoods().getPrice())/100);
            	cellPrice.setCellStyle(cellStyle3);
            	HSSFCell cellTotal = row.getCell(8);
            	Double total = Double.valueOf(detail.getPrice()*detail.getGoodsQty())/100;
            	cellTotal.setCellValue(total==null?0.0:total);
            	cellTotal.setCellStyle(cellStyle3);
            	row.getCell(9).setCellValue("");
			}
			HSSFCell cell7 = newSheet.getRow(19+(size-12)).getCell(5);  
			cell7.setCellValue(fSalesOrderModel.getGoodsCount()); 
			HSSFCell cell8 = newSheet.getRow(19+(size-12)).getCell(8);  
			cell8.setCellValue(Double.valueOf(fSalesOrderModel.getTotal())/100); 
			cell8.setCellStyle(cellStyle3);
			HSSFCell cell9 = newSheet.getRow(21+(size-12)).getCell(1);  
			cell9.setCellValue(dictionaryService.getProducer(DICTIONARY_TYPE));
			//2016-10-25 导出之后通过流下载
			String fileName = fSalesOrderModel.getOrderId() + ".xls";  
            out = new FileOutputStream(new File("D:/"+fileName));
            newWb.write(out);
            return new File("D:/"+fileName);
			//导出成功之后调用jacob插件将excel打印出来
			//printExcel("D:/export.xls");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return null;
	}
	
	private File WriteBatchDateToExcel(FSalesOrderModel fSalesOrderModel, int start, 
			HttpServletResponse response) {
			//读取excel的模板，将数据写入里面
			String path = ManifestController.class.getClassLoader().getResource("template/ordertemplate.xls").getPath();
			File template = new File(path);
			//读取excel模板  
			HSSFWorkbook wb = null;
			//修改模板内容导出新模板  
			FileOutputStream out = null;
			try {
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));  
				wb = new HSSFWorkbook(fs);  
				wb.setSheetName(0, fSalesOrderModel.getOrderId());//设置sheet的名称
				//读取了模板内所有sheet内容  
				HSSFSheet sheet = wb.getSheetAt(0);  
				//在相应的单元格进行赋值  
				HSSFCell cell = sheet.getRow(3).getCell(1);  
				cell.setCellValue(fSalesOrderModel.getOrderId());  
				HSSFCell cell1 = sheet.getRow(4).getCell(1);  
				cell1.setCellValue(fSalesOrderModel.getShopName());  
				HSSFCell cell2 = sheet.getRow(3).getCell(3);  
				cell2.setCellValue(fSalesOrderModel.getUser().getPhone());  
				HSSFCell cell3 = sheet.getRow(3).getCell(8);  
				cell3.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getCreatedTime()));  
				HSSFCell cell4 = sheet.getRow(5).getCell(1);
				HSSFCellStyle cellStyle = wb.createCellStyle();       
				cellStyle.setWrapText(true); 
				HSSFFont font = wb.createFont();   
				font.setFontHeightInPoints((short)9);  
				font.setFontName("宋体");
				cellStyle.setFont(font);
				cell4.setCellStyle(cellStyle);
				cell4.setCellValue(fSalesOrderModel.getReceiverAddress());  
				HSSFCell cell5 = sheet.getRow(4).getCell(3);  
				cell5.setCellValue(fSalesOrderModel.getUser().getRealName());  
				HSSFCell cell6 = sheet.getRow(4).getCell(8);  
				cell6.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getSendTime())); 
				//将表格中的数据填入
				FSalesOrderDetailsModel detail = null;
				HSSFCellStyle cellStyle3 = wb.createCellStyle();  
				HSSFFont font2 = wb.createFont();   
				font2.setFontHeightInPoints((short)9);  
				font2.setFontName("宋体");
	            cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	            cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
	            cellStyle3.setFont(font2);
				for(int i=0;i<fSalesOrderModel.getfSalesOrderDetailsModel().size();i++){
					detail = fSalesOrderModel.getfSalesOrderDetailsModel().get(i);
					HSSFRow row = sheet.getRow(i+7);//创建所需的行数  
					row.getCell(0).setCellValue(i+start);
	            	row.getCell(1).setCellValue(detail.getGoodsName());
	            	row.getCell(2).setCellValue(detail.getGoods()==null?"":detail.getGoods().getSpec());
	            	row.getCell(5).setCellValue(detail.getGoodsQty());
	            	row.getCell(6).setCellValue("箱");
	            	HSSFCell cellPrice = row.getCell(7);
	            	cellPrice.setCellValue(Double.valueOf(detail.getGoods().getPrice())/100);
	            	cellPrice.setCellStyle(cellStyle3);
	            	HSSFCell cellTotal = row.getCell(8);
	            	Double total = Double.valueOf(detail.getPrice()*detail.getGoodsQty())/100;
	            	cellTotal.setCellValue(total==null?0.0:total);
	            	cellTotal.setCellStyle(cellStyle3);
	            	row.getCell(9).setCellValue("");
				}
				HSSFCell cell7 = sheet.getRow(19).getCell(5);  
				cell7.setCellValue(fSalesOrderModel.getGoodsCount()); 
				HSSFCell cell8 = sheet.getRow(19).getCell(8);  
				cell8.setCellValue(Double.valueOf(fSalesOrderModel.getTotal())/100); 
				cell8.setCellStyle(cellStyle3);
				HSSFCell cell9 = sheet.getRow(21).getCell(1);  
				cell9.setCellValue(dictionaryService.getProducer(DICTIONARY_TYPE)); 
				//2016-10-25 导出之后通过流下载
	            String fileName = fSalesOrderModel.getOrderId() + ".xls";  
	            out = new FileOutputStream(new File("D:/"+fileName));
	            wb.write(out);
	            return new File("D:/"+fileName);
				//导出成功之后调用jacob插件将excel打印出来
				//printExcel("D:/export.xls");
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}finally{
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
			return null;
	}

	//单个导出
	@RequestMapping(value="/print",method=RequestMethod.POST)
	@ResponseBody
	public String print(HttpServletRequest request,HttpServletResponse response){
		
		String oid = request.getParameter("oid");
		Map<String, Object> map=fSalesOrderService.getOrderInfo(oid);
		if(map!=null && map.size()>0){
			FSalesOrderModel fSalesOrderModel = (FSalesOrderModel) map.get("order");
			//将数据写入模板excel中，注意当数据超过12条时需要分为多张发票
			if(fSalesOrderModel!=null){
				if(fSalesOrderModel.getfSalesOrderDetailsModel()!=null){
					if(fSalesOrderModel.getfSalesOrderDetailsModel().size() <= 12){
						//当数据 小于等于12条时直接写入excel并导出打印
						writeDateToExcel(fSalesOrderModel,12*0+1,response);
					}else{
						//计算出可以分出的票张数
						/*Integer size = fSalesOrderModel.getfSalesOrderDetailsModel().size();
						Double total = Double.valueOf(size);
						Double page = Double.valueOf(12);
						//计算出来总共的页数
						int count = Double.valueOf(Math.ceil(total/page)).intValue();
						List<FSalesOrderModel> totalLists = new LinkedList<FSalesOrderModel>();
						Map<Object, Object> paramMap = new HashMap<Object,Object>();
						for(int i=0;i<count;i++){
							FSalesOrderModel orderList = new FSalesOrderModel();
							if(i == count - 1){
								paramMap.put("pageNumber", (12*i));
								paramMap.put("pageSize", Double.valueOf(total-(i)*12).intValue());
								paramMap.put("orderId", oid);
								orderList = fSalesOrderService.getTotalOrders(paramMap);
								totalLists.add(orderList);
							}else{
								paramMap.put("pageNumber", (12*i));
								paramMap.put("pageSize", 12);
								paramMap.put("orderId", oid);
								orderList = fSalesOrderService.getTotalOrders(paramMap);
								totalLists.add(orderList);
							}
						}
						//这里把数据都取出来了，进行写入excel并进行导出打印的操作
						if(totalLists!=null && totalLists.size()>0){
							for(int i=0;i<totalLists.size();i++){
								//WriteDateToExcel(totalLists.get(i),12*i+1);
							}
						}*/
						//获取数据中所有的行数
						Integer size = fSalesOrderModel.getfSalesOrderDetailsModel().size();
						writeToExcelAddRows(fSalesOrderModel,12*0+1,response,size);
					}
				}
			}
		}
		return "success";
	}
	
	private void copyRow(HSSFWorkbook wb, HSSFRow targetRow,HSSFRow sourceRow) {  
	    for (int i = 0; i < sourceRow.getLastCellNum(); i++) {  
	        HSSFCell sourceCell = sourceRow.getCell(i);  
	        HSSFCell targetCell = targetRow.createCell(i);  
	        if(sourceCell!=null){
	        	HSSFCellStyle sourceStyle = sourceCell.getCellStyle(); 
	        	HSSFFont sourceFont = sourceStyle.getFont(wb);  
		        sourceStyle.setFont(sourceFont);  
		        targetCell.setCellStyle(sourceStyle);  
		        targetCell.setCellType(sourceCell.getCellType());  
	        }
	    }  
	}  
	
	
	//将数据写入excel动态增加列
	private void writeToExcelAddRows(FSalesOrderModel fSalesOrderModel, int start, HttpServletResponse response,Integer size) {
		//读取excel的模板，将数据写入里面
		String path = ManifestController.class.getClassLoader().getResource("template/ordertemplate.xls").getPath();
		File template = new File(path);
		//读取excel模板  
		HSSFWorkbook wb = null;
		HSSFWorkbook newWb = null;
		//修改模板内容导出新模板  
		OutputStream out = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));  
			wb = new HSSFWorkbook(fs);  
			wb.setSheetName(0, fSalesOrderModel.getOrderId());//设置sheet的名称
			//读取了模板内所有sheet内容  
			HSSFSheet sheet = wb.getSheetAt(0);  
			//在workbook指定行之后添加行数
			for(int j=0;j<size-12;j++){
				sheet.shiftRows(19+j, sheet.getLastRowNum(), 1,true,false);
				HSSFRow newRow = sheet.createRow(19+j);
				copyRow(wb,newRow,sheet.getRow(18));
				CellRangeAddress cra=new CellRangeAddress(19+j, 19+j, 2, 4);        
		        //在sheet里增加合并单元格  
		        sheet.addMergedRegion(cra);
			}
			out = new FileOutputStream(new File("D:/"+fSalesOrderModel.getOrderId()+".xls"));
			wb.write(out);
			//再次读入新的模板
			POIFSFileSystem fis = new POIFSFileSystem(new FileInputStream("D:/"+fSalesOrderModel.getOrderId()+".xls"));
			newWb = new HSSFWorkbook(fis); 
			newWb.setSheetName(0, fSalesOrderModel.getOrderId());//设置sheet的名称
			//读取了模板内所有sheet内容  
			HSSFSheet newSheet = newWb.getSheetAt(0);  
			//在相应的单元格进行赋值  
			HSSFCell cell = newSheet.getRow(3).getCell(1);  
			cell.setCellValue(fSalesOrderModel.getOrderId());  
			HSSFCell cell1 = newSheet.getRow(4).getCell(1);  
			cell1.setCellValue(fSalesOrderModel.getShopName());  
			HSSFCell cell2 = newSheet.getRow(3).getCell(3);  
			cell2.setCellValue(fSalesOrderModel.getUser().getPhone());  
			HSSFCell cell3 = newSheet.getRow(3).getCell(8);  
			cell3.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getCreatedTime()));  
			HSSFCell cell4 = newSheet.getRow(5).getCell(1);
			HSSFCellStyle cellStyle = newWb.createCellStyle();       
			cellStyle.setWrapText(true); 
			HSSFFont font = newWb.createFont();   
			font.setFontHeightInPoints((short)9);  
			font.setFontName("宋体");
			cellStyle.setFont(font);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(fSalesOrderModel.getReceiverAddress());  
			HSSFCell cell5 = newSheet.getRow(4).getCell(3);  
			cell5.setCellValue(fSalesOrderModel.getUser().getRealName());  
			HSSFCell cell6 = newSheet.getRow(4).getCell(8);  
			cell6.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getSendTime())); 
			//填入数据
			FSalesOrderDetailsModel detail = null;
			HSSFCellStyle cellStyle3 = newWb.createCellStyle();  
			HSSFFont font2 = wb.createFont();   
			font2.setFontHeightInPoints((short)9);  
			font2.setFontName("宋体");
            cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle3.setFont(font2);
			for(int i=0;i<fSalesOrderModel.getfSalesOrderDetailsModel().size();i++){
				detail = fSalesOrderModel.getfSalesOrderDetailsModel().get(i);
				HSSFRow row = newSheet.getRow(i+7);//创建所需的行数  
				row.getCell(0).setCellValue(i+start);
				HSSFCellStyle cellStyle2 = newWb.createCellStyle();
				HSSFCell cellName = row.getCell(1);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setWrapText(true); 
				cellStyle2.setFont(font);
				cellName.setCellStyle(cellStyle2);
				cellName.setCellValue(detail.getGoodsName());
            	row.getCell(2).setCellValue(detail.getGoods()==null?"":detail.getGoods().getSpec());
            	row.getCell(5).setCellValue(detail.getGoodsQty());
            	row.getCell(6).setCellValue("箱");
            	HSSFCell cellPrice = row.getCell(7);
            	cellPrice.setCellValue(Double.valueOf(detail.getGoods().getPrice())/100);
            	cellPrice.setCellStyle(cellStyle3);
            	HSSFCell cellTotal = row.getCell(8);
            	Double total = Double.valueOf(detail.getPrice()*detail.getGoodsQty())/100;
            	cellTotal.setCellValue(total==null?0.0:total);
            	cellTotal.setCellStyle(cellStyle3);
            	row.getCell(9).setCellValue("");
			}
			HSSFCell cell7 = newSheet.getRow(19+(size-12)).getCell(5);  
			cell7.setCellValue(fSalesOrderModel.getGoodsCount()); 
			HSSFCell cell8 = newSheet.getRow(19+(size-12)).getCell(8);  
			cell8.setCellValue(Double.valueOf(fSalesOrderModel.getTotal())/100); 
			cell8.setCellStyle(cellStyle3);
			HSSFCell cell9 = newSheet.getRow(21+(size-12)).getCell(1);  
			cell9.setCellValue(dictionaryService.getProducer(DICTIONARY_TYPE));
			//2016-10-25 导出之后通过流下载
            String fileName = fSalesOrderModel.getOrderId() + ".xls";  
            String headStr = "attachment; filename=\"" + fileName + "\"";  
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", headStr);  
            out = response.getOutputStream();  
            newWb.write(out);
			//导出成功之后调用jacob插件将excel打印出来
			//printExcel("D:/export.xls");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	//将数据写入到excel中，单个文件写入并下载
	private void writeDateToExcel(FSalesOrderModel fSalesOrderModel,int start,HttpServletResponse response) {
		//读取excel的模板，将数据写入里面
		String path = ManifestController.class.getClassLoader().getResource("template/ordertemplate.xls").getPath();
		File template = new File(path);
		//读取excel模板  
		HSSFWorkbook wb = null;
		//修改模板内容导出新模板  
		OutputStream out = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));  
			wb = new HSSFWorkbook(fs);  
			wb.setSheetName(0, fSalesOrderModel.getOrderId());//设置sheet的名称
			//读取了模板内所有sheet内容  
			HSSFSheet sheet = wb.getSheetAt(0);  
			//在相应的单元格进行赋值  
			HSSFCell cell = sheet.getRow(3).getCell(1);  
			cell.setCellValue(fSalesOrderModel.getOrderId());  
			HSSFCell cell1 = sheet.getRow(4).getCell(1);  
			cell1.setCellValue(fSalesOrderModel.getShopName());  
			HSSFCell cell2 = sheet.getRow(3).getCell(3);  
			cell2.setCellValue(fSalesOrderModel.getUser().getPhone());  
			HSSFCell cell3 = sheet.getRow(3).getCell(8);  
			cell3.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getCreatedTime()));  
			HSSFCell cell4 = sheet.getRow(5).getCell(1);
			HSSFCellStyle cellStyle = wb.createCellStyle();       
			cellStyle.setWrapText(true); 
			HSSFFont font = wb.createFont();   
			font.setFontHeightInPoints((short)9);  
			font.setFontName("宋体");
			cellStyle.setFont(font);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(fSalesOrderModel.getReceiverAddress());  
			HSSFCell cell5 = sheet.getRow(4).getCell(3);  
			cell5.setCellValue(fSalesOrderModel.getUser().getRealName());  
			HSSFCell cell6 = sheet.getRow(4).getCell(8);  
			cell6.setCellValue(DateUtil.getyyyyMMdd(fSalesOrderModel.getSendTime())); 
			//将表格中的数据填入
			FSalesOrderDetailsModel detail = null;
			HSSFCellStyle cellStyle2 = wb.createCellStyle();  
			HSSFFont font2 = wb.createFont();   
			font2.setFontHeightInPoints((short)9);  
			font2.setFontName("宋体");
            cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle2.setFont(font2);
            cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
			for(int i=0;i<fSalesOrderModel.getfSalesOrderDetailsModel().size();i++){
				detail = fSalesOrderModel.getfSalesOrderDetailsModel().get(i);
				HSSFRow row = sheet.getRow(i+7);//创建所需的行数  
            	row.getCell(0).setCellValue(i+start);
            	row.getCell(1).setCellValue(detail.getGoodsName());
            	row.getCell(2).setCellValue(detail.getGoods()==null?"":detail.getGoods().getSpec());
            	row.getCell(5).setCellValue(detail.getGoodsQty());
            	row.getCell(6).setCellValue("箱");
            	HSSFCell cellPrice = row.getCell(7);
            	cellPrice.setCellValue(Double.valueOf(detail.getGoods().getPrice())/100);
            	cellPrice.setCellStyle(cellStyle2);
            	HSSFCell cellTotal = row.getCell(8);
            	Double total = Double.valueOf(detail.getPrice()*detail.getGoodsQty())/100;
            	cellTotal.setCellValue(total==null?0.0:total);
            	cellTotal.setCellStyle(cellStyle2);
            	row.getCell(9).setCellValue("");
			}
			HSSFCell cell7 = sheet.getRow(19).getCell(5);  
			cell7.setCellValue(fSalesOrderModel.getGoodsCount()); 
			HSSFCell cell8 = sheet.getRow(19).getCell(8);  
			cell8.setCellValue(Double.valueOf(fSalesOrderModel.getTotal())/100); 
			cell8.setCellStyle(cellStyle2);
			HSSFCell cell9 = sheet.getRow(21).getCell(1);  
			cell9.setCellValue(dictionaryService.getProducer(DICTIONARY_TYPE)); 
			//2016-10-25 导出之后通过流下载
            String fileName = fSalesOrderModel.getOrderId() + ".xls";  
            String headStr = "attachment; filename=\"" + fileName + "\"";  
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", headStr);  
            out = response.getOutputStream();  
            wb.write(out);
			//导出成功之后调用jacob插件将excel打印出来
			//printExcel("D:/export.xls");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/*private void printExcel(String path) {
		ComThread.InitSTA();
		ActiveXComponent xl = new ActiveXComponent("Excel.Application"); 
		try{
			//new Variant(false)  打印时是否显示文档       false不显示   
			Dispatch.put(xl, "Visible", new Variant(false)); 
	        Dispatch workbooks = xl.getProperty("Workbooks").toDispatch(); 
	        //打开文档 
            Dispatch excel=Dispatch.call(workbooks,"Open",path).toDispatch(); 
            Dispatch.get(excel,"PrintOut"); 
            //Dispatch.call(excel, "save");
            Dispatch.call(excel,  "Close" ,  new  Variant(true)); 
            excel=null;
		}catch (Exception e){
			logger.error(e.getMessage());
		}finally{ 
			xl.invoke("Quit", new Variant[] {});
            xl=null;
			ComThread.Release();
			//将原来的excel删除掉
		}
	}*/
}

























