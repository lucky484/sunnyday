/**
 * 
 */
package com.hd.pfirs.dao2;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.Constant;

/**
 * @ClassName:
 * @Description: 常控人员
 * @author 
 * @date Dec 25, 2015 10:08:42 AM
 */
public interface ConstantDao {
	
	public Constant getConstant(String id);
    
	public List<Constant> queryConstantById(@Param(value = "list")List list,@Param(value = "xm")String xm,@Param(value = "xb")String xb,@Param(value = "sfzh")String sfzh);
}
