package com.hd.client.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class TransMap2Bean {
	public static void transMap2Bean2(Map<String, Object> map, Object obj) {
		if (map == null || obj == null)
			return;

		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
