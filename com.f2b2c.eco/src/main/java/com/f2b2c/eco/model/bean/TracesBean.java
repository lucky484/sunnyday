package com.f2b2c.eco.model.bean;

import java.util.Date;

/**
 * 跟踪记录bean
 * @author jane.hui
 *
 */
public class TracesBean {

    /**
     * 描述
     */
    private String AcceptStation;
    
    /**
     * 时间
     */
    private String AcceptTime;

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }
}
