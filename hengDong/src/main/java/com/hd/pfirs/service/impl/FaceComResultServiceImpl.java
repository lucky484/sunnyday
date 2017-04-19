package com.hd.pfirs.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao.FaceComResultDao;
import com.hd.pfirs.model.CompareResult;
import com.hd.pfirs.model.FaceComResult;
import com.hd.pfirs.service.FaceComResultService;
import com.hd.pfirs.util.CommUtil;

import sun.misc.BASE64Decoder;

@Service
public class FaceComResultServiceImpl implements FaceComResultService {

	@Autowired
	private FaceComResultDao faceComResultDao;

	@Transactional
	public void insertFaceComResult(String groupCode, String faceCode, String deviceCode, String featureID,
			List<CompareResult> list) throws IOException {
		int i = 1;
		FaceComResult faceComResult = new FaceComResult();
		if (list != null && list.size() > 0) {
			for (CompareResult compareResult : list) {
				faceComResult.setFaceCompRltID(CommUtil.getPrimaryKey() + Long.valueOf(i));
				faceComResult.setReceiveTime(new Date());
				faceComResult.setReceiveTimeStamp(String.valueOf(new Date().getTime()));
				faceComResult.setGroupCode(groupCode.equals("") && groupCode == null ? "" : groupCode);
				faceComResult.setFaceCode(faceCode.equals("") && faceCode == null ? "" : faceCode);
				faceComResult.setOrderNum(String.valueOf(i));
				faceComResult.setFeatureID(featureID.equals("") && featureID == null ? "" : featureID);
				faceComResult.setCtrlBaseID(compareResult.getPersonID());
				faceComResult.setDeleteStatus("0");
				faceComResult.setDeviceCode(deviceCode.equals("") && deviceCode == null ? "" : deviceCode);
				//System.out.println(compareResult.getPhoto());
				if (compareResult.getPhoto() != "") {
					byte[] comparePhoto = new BASE64Decoder().decodeBuffer(compareResult.getPhoto());
					faceComResult.setPhoto(comparePhoto);
				} else {
					faceComResult.setPhoto(null);
				}
				faceComResult.setSimilarity(Float.valueOf(compareResult.getSimilarity()) == null ? null
						: Float.valueOf(compareResult.getSimilarity()));
				faceComResult.setCompareBaseID(
						compareResult.getCompareBaseID().equals("") && compareResult.getCompareBaseID() == null ? ""
								: compareResult.getCompareBaseID());
				faceComResultDao.insertFaceComResult(faceComResult);
				i++;
				//			System.out.println("groupCode:"+groupCode \n);
			}
		}
	}

	@Override
	public FaceComResult getFaceBySimilarity(String deviceCode,String warningTime) {
		return faceComResultDao.getFaceBySimilarity(deviceCode,warningTime);
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public int queryFaceComWarnNum(String deviceCode) {
		return faceComResultDao.queryFaceComWarnNum(deviceCode);
	}

	@Override
	public void updateFlag(long faceCompRltID) {
		// TODO Auto-generated method stub
		faceComResultDao.updateFlag(faceCompRltID);
	}

	@Override
	public FaceComResult getLastFaceBySimilarity(String deviceCode,String warningTime) {
		// TODO Auto-generated method stub
		return faceComResultDao.getLastFaceBySimilarity(deviceCode,warningTime);
	}

	//	public FaceComResult getSimiliarity(String faceCode){
	//		return faceComResultDao.getSimiliarity(faceCode);
	//	}
	
	/**
	 * index 预警
	 * @param warningTime
	 * @return
	 */
	public List<FaceComResult> indexfaceWarningInfo(String warningTime){
		return faceComResultDao.indexfaceWarningInfo(warningTime);
	}
}
