/**
 * 
 */
package com.hd.pfirs.dao2;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.Temporary;

/**
 * @ClassName: TemporaryDao
 * @Description: 临控人员
 * @author light.chen
 * @date Dec 24, 2015 3:01:03 PM
 */
public interface TemporaryDao {
	
	public Temporary getTemporary(String id);
    
	public List<Temporary> queryTemporaryById(@Param(value = "list")List list,@Param(value = "bbkrxm")String bbkrxm,@Param(value = "bbkrxb")String bbkrxb,@Param(value = "bbkrzjhm")String bbkrzjhm);
}
