package com.hd.client.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SystemRuntimeUtil {

	private final static String localDiskName = "本地磁盘";
	private final static String removableDiskName = "可移动磁盘";
	private final static String enLocalDiskName = "Local Disk";
	private final static String enRemovableDiskName = "Removable Disk";

	public static BigDecimal getMemoryBit() throws SigarException {
		Sigar sigar = new Sigar();
		Mem mem = sigar.getMem();
		float total = mem.getTotal();
		float used = mem.getUsed();
		// 当前内存使用率

		return BigDecimal.valueOf(used / total * 100);
	}

	public static String getDiskSpace() throws SigarException {
		Sigar sigar = new Sigar();
		FileSystem[] fsArr = sigar.getFileSystemList();
		List fsList = new ArrayList();
		Long used = 0L;
		Long total = 0L;
		for (FileSystem fs : fsArr) {
			String diskName = fs.getDirName();
			if (diskName.startsWith(localDiskName) || diskName.startsWith(enLocalDiskName)) {
				used += sigar.getFileSystemUsage(fs.getDirName()).getAvail() / (1024 * 1024);
				total += sigar.getFileSystemUsage(fs.getDirName()).getTotal() / (1024 * 1024);
			}
		}
		return "剩余 " + used + "G / " + "共有 " + total + "G";
	}
}
