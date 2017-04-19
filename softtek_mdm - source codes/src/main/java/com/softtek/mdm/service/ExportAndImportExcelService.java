package com.softtek.mdm.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ExportAndImportExcelService {
	
	public Map<String, Object> importvirmember(InputStream ins, List<Integer> idlist, Map<String, Integer> map);

}
