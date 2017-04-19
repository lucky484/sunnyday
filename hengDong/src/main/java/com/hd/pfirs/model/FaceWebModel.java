package com.hd.pfirs.model;

import java.util.Arrays;
import java.util.Date;

/**
 * 研判分析 人脸查询
 * @author curry.su
 *
 */
public class FaceWebModel {
	
	private Date collectTime;
	private String faceCode;
	private String collectTimeStr;
	private String collectSite;
	private byte[] collectPic;
	private String collectPicStr;
	private float similarity;
	private String isCompareFace;
	private String compareBaseID;
	private String compareBaseTyoe;
	private byte[] image;
	private String imageStr;
	private Long faceId;

	/**
	 * facecode
	 */
	private String personID;
	
	
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getCollectTimeStr() {
		return collectTimeStr;
	}
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}
	public String getCollectSite() {
		return collectSite;
	}
	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}
	public byte[] getCollectPic() {
		return collectPic;
	}
	public void setCollectPic(byte[] collectPic) {
		this.collectPic = collectPic;
	}
	public String getCollectPicStr() {
		return collectPicStr;
	}
	public void setCollectPicStr(String collectPicStr) {
		this.collectPicStr = collectPicStr;
	}
	public float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}
	
	
	public String getCompareBaseID() {
		return compareBaseID;
	}
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
	}
	public String getCompareBaseTyoe() {
		return compareBaseTyoe;
	}
	public void setCompareBaseTyoe(String compareBaseTyoe) {
		this.compareBaseTyoe = compareBaseTyoe;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getImageStr() {
		return imageStr;
	}
	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}
	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	public String getFaceCode() {
		return faceCode;
	}
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}
	public String getIsCompareFace() {
		return isCompareFace;
	}
	public void setIsCompareFace(String isCompareFace) {
		this.isCompareFace = isCompareFace;
	}
	public Long getFaceId() {
		return faceId;
	}
	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}
	
	
	
}
