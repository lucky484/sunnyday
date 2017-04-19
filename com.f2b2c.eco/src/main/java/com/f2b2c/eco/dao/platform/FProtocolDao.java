package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FAuthCodeModel;
import com.f2b2c.eco.model.platform.FProtocolModel;
/**
 * 注册+担保协议
 * @author mozzie.chu
 *
 */
public interface FProtocolDao extends CrudMapper<FProtocolModel,Serializable> {

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
