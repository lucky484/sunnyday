package com.f2b2c.eco.controller.platform.f2b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.service.platform.FProtocolService;

@Controller("/fProtocolController")
@RequestMapping("/farm/protocol")
public class FProtocolController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(FProtocolController.class);
	
	@Autowired
	private FProtocolService fProtocolService;
	
}
