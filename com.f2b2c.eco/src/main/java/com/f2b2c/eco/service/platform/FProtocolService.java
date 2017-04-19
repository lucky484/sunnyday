package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.platform.FProtocolModel;
/**
 * 注册+担保协议
 * @author mozzie.chu
 *
 */
public interface FProtocolService {

	/**
	 * 显示协议
	 */
	FProtocolModel queryFProtocolByType(String type);
	/**
	 * 填写协议
	 */
	int insert(FProtocolModel proModel);
	/**
	 * 填写协议
	 */
	int updateFProtocolContent(FProtocolModel proModel);

}
