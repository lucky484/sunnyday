package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.FaceWebModelDao;
import com.hd.pfirs.model.FaceWebModel;
import com.hd.pfirs.service.FaceWebModelService;

@Service
public class FaceWebModelServiceImpl implements FaceWebModelService {

	@Autowired
	private FaceWebModelDao faceWebModelDao;
	@Override
	public FaceWebModel getFaceWebModelByFaceCode(String faceCode,String collectSite) {
		// TODO Auto-generated method stub
		return faceWebModelDao.getFaceWebModelByFaceCode(faceCode,collectSite);
	}

}
