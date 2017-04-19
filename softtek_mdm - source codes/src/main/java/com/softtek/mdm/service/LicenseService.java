package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.model.LicenseFileObject;
import com.softtek.mdm.model.LicenseInfoModel;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 
 * 许可证服务类
 * date: May 24, 2016 4:04:49 PM
 *
 * @author brave.chen
 */
public interface LicenseService
{
	/**
	 * 更新license信息
	 *
	 * @author brave.chen
	 * @param model
	 */
	void updateLicenseInfo(LicenseInfoModel model);
	
	/**
	 * 查询license信息
	 * @author brave.chen
	 * @return license对象信息
	 */
	LicenseInfoModel queryLicenseInfo();
	
	/**
	 * 获取分配的人数
	 *
	 * @author brave.chen
	 * @return 分配的总人数
	 */
	int getGenereateMemberCount();
	
	/**
	 * 上传license文件
	 * @author brave.chen
	 * @param file 上传文件对象
	 * @return 操作结果
	 */
	ResultDTO uploadLicenseFile(MultipartFile file);
	
	/**
	 * 生成机器码
	 * @author brave.chen
	 * @param activeCode 激活码
	 * @return map对象
	 */
	Map<String, Object> generateMachineCode(String activeCode);

	/**
	 * 解析license信息
	 *
	 * @author brave.chen
	 * @return license信息对象map
	 */
	Map<String, Object> generateLicenseInfo();
	
	/**
	 * 获取机器码文件路径
	 * @author brave.chen
	 */
	String getMachineCodeFilePath();

	/**
	 * 
	 * 校验license信息
	 *
	 * @author brave.chen
	 * @param activeCode 激活码
	 * @param cpuId cpu号
	 * @param mac mac地址
	 * @return 校验结果map
	 */
	Map<String, Object> checkLicense(String activeCode, String cpuId, String mac);
	
	/**
	 * 是否过期 
	 *
	 * @author brave.chen
	 * @param obj license对象信息
	 * @return true过期 false不过期
	 */
	boolean isBeyondDate(LicenseFileObject obj);
	
	/**
	 * 是否超过最大人数限制
	 *
	 * @author brave.chen
	 * @param obj license对象信息
	 * @return true超过 false不超过
	 */
	boolean isBeyondGenereateMember(LicenseFileObject obj);

	/**
	 * 添加license记录
	 * @author brave.chen
	 * @param model license对象模型
	 */
	void addLicenseInfoModel(LicenseInfoModel model);
	
	/**
	 * 操作license记录
	 * @author brave.chen
	 * @param model license对象模型
	 */
	void operateLicenseInfo(LicenseInfoModel model);
	
	/**
	 * 是否超过机构限制人数
	 * @author brave.chen
	 * @param obj 许可证对象
	 * @param orgIds 组织机构id列表
	 * @return true超过，false没超过
	 */
	boolean isBeyondOrganizationLimit(LicenseFileObject obj, List<Integer> orgIds);
	
	int checkLicense(List<Integer> orgIds);
	
	/**
	 * 校验机构下能否激活activeNum数量的用户
	 *
	 * @author brave.chen
	 * @param activeNum 待激活的用户数量
	 * @param orgId 机构ID
	 * @return 机构下能否激活activeNum数量的用户（true:表示未超过，false表示超过）
	 */
	boolean isActiveNumBeyondOrgLimit(int activeNum, int orgId);

	/**
	 * 获取许可证文件的字节数组
	 *
	 * @author brave.chen
	 * @return 字节数组
	 */
	byte[] getLicenseFileBytes();
	
	/**
	 * 当前日期距离licese授权结束日期还剩余多少天
	 * 如果剩余天数大于通知天数则返回-1
	 * 否则返回剩余天数
	 * @return
	 */
	int diffEndTimeAndInformDays();
}

