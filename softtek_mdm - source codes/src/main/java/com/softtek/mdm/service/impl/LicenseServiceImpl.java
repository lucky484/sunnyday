
package com.softtek.mdm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.dao.LicenseDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.LicenseFileObject;
import com.softtek.mdm.model.LicenseInfoModel;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.status.LicenseStatus;
import com.softtek.mdm.util.LicenseUtil;
import com.softtek.mdm.util.RSAUtils;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 许可证证服务实现类 date: May 24, 2016 4:07:41 PM
 *
 * @author brave.chen
 */
@Service
public class LicenseServiceImpl implements LicenseService {
	/**
	 * 日志记录器
	 */
	private Logger logger = Logger.getLogger(LicenseServiceImpl.class);

	/**
	 * 许可证dao
	 */
	@Autowired
	private LicenseDao licenseDao;

	/**
	 * 用户dao
	 */
	@Autowired
	private UserDao userDao;
	
	/**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSourceService;

	/**
	 * license文件路径
	 */
	@Value("${license_file_path}")
	private String licenseFilePath;

	/**
	 * 本地机器码文件
	 */
	@Value("${machine_file_path}")
	private String machineFilePath;

	@Override
	public void updateLicenseInfo(LicenseInfoModel model) {
		licenseDao.updateLicenseInfo(model);
	}

	@Override
	public LicenseInfoModel queryLicenseInfo() {
		LicenseInfoModel licenseInfo = licenseDao.queryLicenseInfo();
		return licenseInfo;
	}

	@Override
	public int getGenereateMemberCount() {
		 LicenseFileObject licenseFileObject = getLicenseFileObject();
		 Integer count = licenseFileObject.getGenerateMembers();
		 return count == null ? 0 : count;
	}

	@Override
	public ResultDTO uploadLicenseFile(MultipartFile file) {

		ResultDTO dto = new ResultDTO();
		try
		{
			// 将license文件写入指定文件
			file.transferTo(new File(licenseFilePath));
			dto.message = messageSourceService.getMessage("web.admin.system.license.manager.uploadLicenseFile.resultdto.message.success", null,
					LocaleContextHolder.getLocale());
		}
		catch (Exception e)
		{
			logger.error("something excepiton happend, exception is " + e.getMessage());
			dto.type = BaseDTO.ERROR;
			dto.message = messageSourceService.getMessage("web.admin.system.license.manager.uploadLicenseFile.resultdto.message.failed", null,
					LocaleContextHolder.getLocale());
		}

		return dto;

	}

	@Override
	public Map<String, Object> generateMachineCode(String activeCode) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		LicenseInfoModel licenseInfoModel = queryLicenseInfo();

		// 如果激活码是同一个则不生成机器码
		if (null == licenseInfoModel || (null != licenseInfoModel && !licenseInfoModel.getActiveCode().equals(activeCode))) {
			String public_key = LicenseUtil.getPublicKey(activeCode);
			Map<String, Object> keyMap = new HashMap<String, Object>();
			String localPublicKey;
			String localPrivateKey;
			try {
				keyMap = RSAUtils.genKeyPair();
				localPublicKey = RSAUtils.getPublicKey(keyMap);
				localPrivateKey = RSAUtils.getPrivateKey(keyMap);
			} catch (Exception e) {
				returnMap.put("result", "failed");
				logger.error("generate private and public key failed, exception is " + e.getMessage());
				return returnMap;
			}

			LicenseInfoModel model = new LicenseInfoModel();
			model.setId(1);
			model.setActiveCode(activeCode);
			model.setPrivateKey(localPrivateKey);
			model.setMachineFilePath(machineFilePath);

			operateLicenseInfo(model);
			LicenseUtil.generateMachine(public_key, localPublicKey, machineFilePath);
		}

		returnMap.put("machineFile", machineFilePath);
		return returnMap;
	}

	@Override
	public void operateLicenseInfo(LicenseInfoModel model)
	{
		LicenseInfoModel licenseInfoModel = queryLicenseInfo();
		if (null == licenseInfoModel)
		{
			addLicenseInfoModel(model);
		}
		else
		{
			updateLicenseInfo(model);
		}
	}

	@Override
	public Map<String, Object> generateLicenseInfo() {

		LicenseFileObject licenseFileObject = getLicenseFileObject();
		Map<String, Object> map = generateLicenseMap(licenseFileObject);
		LicenseInfoModel licenseInfoModel = queryLicenseInfo();
		if (null != licenseInfoModel) {
			map.put("activeCodeTop", licenseInfoModel.getActiveCode());
			map.put("adviceDaysTop", licenseInfoModel.getAdviceDays());
			map.put("machineFilePathTop", licenseInfoModel.getMachineFilePath());
		}

		return map;
	}

	@Override
	public String getMachineCodeFilePath() {
		return machineFilePath;
	}

	private Map<String, Object> generateLicenseMap(LicenseFileObject obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != obj) {
			Integer totalDay = null;
			String startTime = obj.getStartDate();
			String endTime = obj.getEndDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDdate;
			Date endDdate;
			try {
				startDdate = formatter.parse(startTime);
				endDdate = formatter.parse(endTime);

				Calendar cal = Calendar.getInstance();
				cal.setTime(startDdate);
				long sTime = cal.getTimeInMillis();
				cal.setTime(endDdate);
				long eTime = cal.getTimeInMillis();
				long btTime = (eTime - sTime) / (1000 * 3600 * 24);
				totalDay = Integer.valueOf(String.valueOf(btTime));
				map.put("totalDay", totalDay);

				// TODO:这儿暂时用本地时间，后续改成系统时间
				Date date = new Date();
				String today = formatter.format(date);
				Date todayDate = formatter.parse(today);
				cal.setTime(todayDate);
				long todayTime = cal.getTimeInMillis();

				long remamiDay = (eTime - (todayTime > sTime ? todayTime : sTime)) / (1000 * 3600 * 24) - 1;
				map.put("remainDay", Integer.valueOf(String.valueOf(remamiDay)));

			} catch (ParseException e) {
				logger.error("Parse time exception, error message is " + e.getMessage());
				return map;
			}

			Integer totalUser = obj.getGenerateMembers();

			map.put("totalMembers", totalUser);
			map.put("usedMembers", userDao.getActiveUserCount());
			map.put("versionType", obj.getVersionType());
		}

		return map;
	}

	@Override
	public boolean isBeyondOrganizationLimit(LicenseFileObject obj, List<Integer> orgIds) {
		int result = licenseDao.queryOrgsLimit(orgIds);

		// 查询结果为负数表示超过限制
		if (result >= 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isBeyondDate(LicenseFileObject obj) {
		boolean isBeyondDate = false;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if (null != obj) {
			String endTime = obj.getEndDate();
			Date endDdate;
			try {
				endDdate = formatter.parse(endTime);
			} catch (ParseException e) {
				logger.error("Parse date error, error message is " + e.getMessage());
				return true;
			}

			Date todayDate = new Date();

			if (endDdate.getTime() <= todayDate.getTime()) {
				isBeyondDate = true;
			}
		} else {
			isBeyondDate = true;
		}

		return isBeyondDate;
	}

	@Override
	public boolean isBeyondGenereateMember(LicenseFileObject obj) {
		if (null != obj) {
			Integer total = obj.getGenerateMembers();

			int used = userDao.getActiveUserCount();

			if (used >= total) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Map<String, Object> checkLicense(String activeCode, String cpuId, String mac) {
		Map<String, Object> map = new HashMap<String, Object>();
		LicenseFileObject obj = getLicenseFileObject();
		if (null != obj) {
			String activeCodeTmp = obj.getActiveCode();
//			String cpuIdTmp = obj.getCpuId();
//			String macTmp = obj.getMac();

//			if (StringUtils.equals(activeCode, activeCodeTmp) && StringUtils.equals(cpuId, cpuIdTmp)
//					&& StringUtils.equals(mac, macTmp)) {
//				map = generateLicenseMap(obj);
//			}
			
			if (StringUtils.equals(activeCode, activeCodeTmp)) {
				map = generateLicenseMap(obj);
			}

		}

		return map;
	}

	@Override
	public void addLicenseInfoModel(LicenseInfoModel model) {

		licenseDao.addLicenseInfoModel(model);

	}

	/**
	 * 检查license是否过期或者用户超出限制
	 * 
	 * @param orgIds
	 * @return
	 */
	@Override
	public int checkLicense(List<Integer> orgIds) {
		LicenseFileObject licenseFileObject = this.getLicenseFileObject();
		if (licenseFileObject == null) {
			return LicenseStatus.FILE_NOTEXISTS.getDisplayValue();
		}
		if (this.isBeyondDate(licenseFileObject)) {
			return LicenseStatus.BEYOND_DATE.getDisplayValue();
		}
		if (this.isBeyondGenereateMember(licenseFileObject)) {
			return LicenseStatus.BEYOND_COUNT.getDisplayValue();
		}
		if (CollectionUtils.isNotEmpty(orgIds)) {
			if (this.isBeyondOrganizationLimit(licenseFileObject, orgIds)) {
				return LicenseStatus.BEYOND_COUNT.getDisplayValue();
			}
		}
		return LicenseStatus.BEYONG_NONE.getDisplayValue();

	}

	@Override
	public boolean isActiveNumBeyondOrgLimit(int activeNum, int orgId) {
		List<Integer> orgIds = new ArrayList<Integer>();
		orgIds.add(orgId);
		int remain = licenseDao.queryOrgsLimit(orgIds);
		if (activeNum <= remain) {
			return false;
		}
		return true;
	}

	@Override
	public byte[] getLicenseFileBytes()
	{
		InputStream in = null;
		byte [] bt = null;
		try
		{
			File file = new File(licenseFilePath);
			in = new FileInputStream(file);
			int len = (int) file.length();
			bt = new byte[len];
			in.read(bt, 0, len);
			
		}
		catch (FileNotFoundException e)
		{
			logger.warn("File is not exist!Exception message is " + e.getMessage());
		}
		catch (IOException e)
		{
			logger.error("IO Excepiton!Exception message is " + e.getMessage());
		}
		finally
		{
			if (null != in)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					logger.error("IO closed failed!Exception message is " + e.getMessage());
				}
			}
		}
		return bt;
	}
	
	private LicenseFileObject getLicenseFileObject()
	{
		LicenseFileObject licenseFileObject = null;
		LicenseInfoModel licenseInfoModel = queryLicenseInfo();
		if (null != licenseInfoModel && StringUtils.isNotEmpty(licenseInfoModel.getPrivateKey()))
		{
			String privateKey = licenseInfoModel.getPrivateKey();
			licenseFileObject = LicenseUtil.decodeLicneseFile(licenseInfoModel.getLicenceFileBytes(), privateKey);
		}

		return licenseFileObject;
	}

	@Override
	public int diffEndTimeAndInformDays() {
		LicenseInfoModel licenseInfo = queryLicenseInfo();
		LicenseFileObject licenseFileObject=getLicenseFileObject();
		if(null!=licenseFileObject&&null!=licenseInfo){
			int days=getTimeDifference(licenseFileObject.getEndDate());
			 if(days<=licenseInfo.getAdviceDays()){
				 return days;
			 }
		}
		return -1;
	}
	
	private Integer getTimeDifference(String time) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = time;
        String nowDate = date.format(new Date());
        Date now = null;
        Date end = null ;
        try {
            now = date.parse(nowDate);
            end = date.parse(endTime);
        } catch (ParseException e) {
            e.getMessage();
            return -1;
        }
        return Days.daysBetween(new DateTime(now), new DateTime(end)).getDays();
    }
}
