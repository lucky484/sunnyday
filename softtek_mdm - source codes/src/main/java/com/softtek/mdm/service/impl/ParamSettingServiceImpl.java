package com.softtek.mdm.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.dao.ParamSettingDao;
import com.softtek.mdm.model.EmailModelContent;
import com.softtek.mdm.model.EmailParamModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.service.ParamSettingService;

/**
 * 参数设置服务实现类
 * date: Jun 2, 2016 3:47:30 PM
 *
 * @author brave.chen
 */
@Service
public class ParamSettingServiceImpl implements ParamSettingService
{
	@Autowired
	private ParamSettingDao paramSettingDao;
	
	@Override
	public void addSysParamSetting(SystemParamSetModel systemParamSetModel)
	{
		paramSettingDao.addSysParamSetting(systemParamSetModel);
	}

	@Override
	public void updateSysParamSetting(SystemParamSetModel systemParamSetModel)
	{
		paramSettingDao.updateSysParamSetting(systemParamSetModel);
	}

	@Override
	public SystemParamSetModel querySysParamSetting()
	{
		return paramSettingDao.querySysParamSetting();
	}

	@Override
	public void updateMessageAdviceParam(EmailParamModel model)
	{
		paramSettingDao.updateMessageAdviceParam(model);
	}

	@Override
	public void updateEmailModelsContent(List<EmailModelContent> emailModelContents)
	{
		paramSettingDao.updateEmailModelsContent(emailModelContents);
	}

	@Override
	public List<EmailModelContent> queryEmailModels()
	{
		return paramSettingDao.queryEmailModels();
	}

	@Override
	public EmailParamModel queryEmailParams()
	{
		return paramSettingDao.queryEmailParams();
	}

	@Override
	public void saveMessageAdviceParam(EmailParamModel model)
	{
		paramSettingDao.saveMessageAdviceParam(model);
	}

	@Override
	public int queryLogoIsDefault() {
		int logoIsDefault;
		try {
			logoIsDefault = paramSettingDao.queryLogoIsDefault();
		} catch (Exception e) {
			logoIsDefault=1;
		}
		
		return logoIsDefault;
	}
	@Override
	public int queryCopyIsDefault() {
		return paramSettingDao.queryCopyIsDefault();
	}

	@Override
	public String queryDefaultCopyright() {
		return paramSettingDao.queryDefaultCopyright();
	}

	@Override
	public String queryCopyright() {
		return paramSettingDao.queryCopyright();
	}

	@Override
	public void modifystyle(MultipartFile files,ServletContext s1,String ischangelogo, String logopath, String copyright,String path) {
		Integer islogo=Integer.parseInt(ischangelogo);
		if(islogo==0){
		try {
			if(files!=null){
				BufferedImage bi =ImageIO.read(files.getInputStream());
				if(bi.getWidth()==150 && bi.getHeight()==83){
				//	String temp=s1.getRealPath("/");
//					Properties prop = PropertyUtil.getInstance().load("file");
					//等待服务器解决后修改代码。
					//以前是1.0是上传到项目中的resources/upload目录下
					//String path = request.getSession().getServletContext().getRealPath("resources" + File.separator + prop.getProperty("uploadPath") + File.separator);
					//String path = prop.getProperty("logopath");
					StringBuffer buffer = new StringBuffer();
					buffer.append(path);
					buffer.append("resources");
					buffer.append(File.separator);
					buffer.append("images");
					buffer.append(File.separator);
					buffer.append("newlogo.png");
					path = buffer.toString();
					File file=new File(path);
					if(file.exists()){
						file.delete();
						ImageIO.write(bi, "png", new File(path));  
					}else{
						ImageIO.write(bi, "png", new File(path));  
					}
					bi.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}

	@Override
	public int resetstyle() {
		return paramSettingDao.resetstyle();
	}
	
	@Override
	public EmailModelContent queryEmailModelContentByType(int type)
	{
		return paramSettingDao.queryEmailModelContentByType(type);
	}
}

