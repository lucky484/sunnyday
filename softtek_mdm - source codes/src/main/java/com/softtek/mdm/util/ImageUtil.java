package com.softtek.mdm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class ImageUtil {

	private static Logger logger = Logger.getLogger(ImageUtil.class);
	
	public static String getUploadFilevVideoPath(HttpServletRequest request) throws IllegalStateException, IOException{
	   //创建一个通用的多部分解析器  
       CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
       //判断 request 是否有文件上传,即多部分请求  
       if(multipartResolver.isMultipart(request)){  
           //转换成多部分request    
           MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
           //取得request中的所有文件名  
           Iterator<String> iter = multiRequest.getFileNames();  
           while(iter.hasNext()){  
               //记录上传过程起始时的时间，用来计算上传时间  
               //取得上传文件  
               MultipartFile file = multiRequest.getFile(iter.next());  
               if(file != null){  
                   //取得当前上传文件的文件名称  
                   String myFileName = file.getName();  
                   
                   //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                   if(myFileName.trim().length()>0){  
                       //重命名上传后的文件名  
                       //定义上传路径  
                   	String imageName=getImageName(file.getOriginalFilename());
                       String path = CommUtil.FILE_SAVE_PATH + imageName;  
                       File localFile = new File(path);  
                       file.transferTo(localFile);  
                       return imageName;
                   }  
               }  
           }  
       }
       return ""; 	
	}
	
	public static String getUploadFilePath(HttpServletRequest request,String savePath) throws IllegalStateException, IOException{
	     //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim().length()>0){  
                        //重命名上传后的文件名  
                        //定义上传路径  
                    	String fileName=getImageName(file.getOriginalFilename());
                        File localFile = new File(savePath + fileName);  
                        file.transferTo(localFile);  
                        return fileName;
                    }  
                }  
            }  
        }
        return "";
	}
	
	public static String getUploadAPKFilePath(HttpServletRequest request,Integer id) throws Exception{
	     //创建一个通用的多部分解析器  
       CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
       //判断 request 是否有文件上传,即多部分请求  
       if(multipartResolver.isMultipart(request)){  
           //转换成多部分request    
           MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
           //取得request中的所有文件名  
           Iterator<String> iter = multiRequest.getFileNames();  
           while(iter.hasNext()){  
               //记录上传过程起始时的时间，用来计算上传时间  
               //取得上传文件  
               MultipartFile file = multiRequest.getFile(iter.next());  
               if(file != null){  
                   //取得当前上传文件的文件名称  
                   String myFileName = file.getOriginalFilename();  
                   //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                   if(myFileName.trim().length()>0){  
                       // 上传到正式目录
                	   String path = "";
                       String imageName=getImageName(myFileName);
            		   path = CommUtil.TEMP_PATH +imageName;
                	   // 将临时目录APK上传到对应目录下
                	   File localFile = new File(path);  
                	   file.transferTo(localFile); 
                	   return String.format("%s,%s", imageName,myFileName);
                   }  
               }  
           } 
       }
       return "";
	}
	/**
	 * 获取图片服务器名称
	 * @param sourcePictrueName
	 * @return
	 */
	private static String getImageName(String sourcePictrueName){
		//扩展名
		int weizhi= sourcePictrueName.lastIndexOf(".");
		String extensionName= sourcePictrueName.substring(weizhi);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMss");  
		Date now=new Date();
		Random r = new Random();
		String rannum  = String.valueOf(r.nextInt(100));
		
		String newImageName=sdf.format(now)+rannum+extensionName;
		return newImageName;
	}
	
	public static String uploadFile(HttpServletRequest request,String savePath) throws IOException{
		String filename = "";
		FileOutputStream out  = null;
		InputStream in = null;
		try {
			//创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建一个文件上传解析器 
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置上传文件名的编码
			upload.setHeaderEncoding("UTF-8");
			//判断提交上来的数据是否是上传表单的数据
			if(!ServletFileUpload.isMultipartContent(request)){
				return null;
			}
			//使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个from表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					 filename = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
				}else{//如果fileitem中封装的是上传文件
					//得到上传的文件名称
					 filename = item.getName();
					  if(StringUtils.isEmpty(filename)){
						  continue;
						 }
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
				    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					  filename = filename.substring(filename.lastIndexOf("\\")+1);
					  //获取item中的上传文件的输入流
					  in = item.getInputStream();
					//创建一个文件输出流
					  out = new FileOutputStream(savePath + "\\" + filename);
					//创建一个缓冲区
					  byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					  int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					  while((len=in.read(buffer))>0){
						  //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						  out.write(buffer, 0, len);
					  }
					//关闭输入流
					  in.close();
					//关闭输出流
					  out.close();
					//删除处理文件上传时生成的临时文件
					  item.delete();
					  }
			}
		} catch (Exception e) {
			logger.error("upload file failed,error message is:"+e.getMessage());
		} finally {
			//关闭输入流
			  in.close();
			//关闭输出流
			  out.close();
		}
		return filename;
	}
}
