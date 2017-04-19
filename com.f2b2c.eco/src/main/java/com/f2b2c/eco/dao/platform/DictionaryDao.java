package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.model.common.Dictionary;

public interface DictionaryDao {

	String getProducer(String type);

	void setProducer(Dictionary dictionary);

}
