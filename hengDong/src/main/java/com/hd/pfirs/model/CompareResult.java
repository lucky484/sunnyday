package com.hd.pfirs.model;

public class CompareResult {
    
	private String compareBaseID;
	private String personID;
	private String similarity;
	private String photo;
	public String getCompareBaseID() {
		return compareBaseID;
	}
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
	}
	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	public String getSimilarity() {
		return similarity;
	}
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
