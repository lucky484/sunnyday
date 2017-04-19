package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FAuthCodeModel;

public interface FAuthService {
    /**
     * 
     * Description:插入生成的一组授权码
     *
     * @author Administrator
     * @param userId
     * @return
     */
     int insertAuthCodes(Integer num, Integer userId);

    List<FAuthCodeModel> queryAuthCodesByFUserId(Integer userId);

    /**
     * Description:查询授权码列表
     * 
     * @return Page<FAuthCodeModel>
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月10日 下午1:39:00
     */
    Page<FAuthCodeModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);

    /**
     * 验证授权码的正确性
     * @param authCode
     * @param userId
     * @return
     */
	FAuthCodeModel getAuthCodeWithUserId(String authCode, Integer userId);
	/**
	 * 创建店铺成功之后需要把该授权码的状态改为已用
	 * @param authCode
	 * @return
	 */
	int updateAuthCode(String authCode);
	
	FAuthCodeModel queryCodeIsUser(String code);
}
