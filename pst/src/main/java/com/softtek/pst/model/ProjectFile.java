/**
* @ClassName: ProjectFile
* @Description: 项目附件
* @author Jacob Shen
* @date Apr 15, 2016 4:49:08 PM
*/
package com.softtek.pst.model;

import java.util.Date;

public class ProjectFile {
	private long projectFileId;
	private String fileName;
	private String filePath;
	private String contentType;
	private long fileSize;
	private Date createDate;
	private Date updateDate;
	private long projectId;

	public ProjectFile() {
		super();
	}

	public ProjectFile(long projectFileId, String fileName, String filePath, String contentType, long fileSize, Date createDate, Date updateDate, long projectId) {
		super();
		this.projectFileId = projectFileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.projectId = projectId;
	}

	public long getProjectFileId() {
		return projectFileId;
	}

	public void setProjectFileId(long projectFileId) {
		this.projectFileId = projectFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

}
