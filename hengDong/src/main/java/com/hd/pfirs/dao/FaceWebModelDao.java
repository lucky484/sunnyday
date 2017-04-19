package com.hd.pfirs.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.FaceWebModel;

/**
 * 
 * @author curry.su
 *
 */
public interface FaceWebModelDao {
	
	
	public FaceWebModel getFaceWebModelByFaceCode(@Param(value="faceCode")String faceCode,@Param(value="collectSite")String collectSite);
	
}
