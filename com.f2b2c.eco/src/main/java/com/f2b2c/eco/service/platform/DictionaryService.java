package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.common.Dictionary;

public interface DictionaryService {

	String getProducer(String type);

	void setProducer(Dictionary dictionary);

}
