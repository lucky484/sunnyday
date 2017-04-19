package com.softtek.mdm.service;

public interface BackupService {

	boolean backup(String autoBackup, String backupType, String backUp, String backupTime, String backupPath,
			String cronExpression,String instant);

}
