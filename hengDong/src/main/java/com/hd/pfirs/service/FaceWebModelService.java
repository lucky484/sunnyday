package com.hd.pfirs.service;


import com.hd.pfirs.model.FaceWebModel;

/**
 * 
 * @author curry.su
 *
 */
public interface FaceWebModelService {
	
	public FaceWebModel getFaceWebModelByFaceCode(String faceCode,String collectSite);
}
