package com.f2b2c.eco.model.bean;

import java.util.List;

/**
 * 物流bean
 * @author jane.hui
 *
 */
public class CLogisticBean {

    /**
     * 电商ID
     */
    private String EBusinessID;
    
    /**
     * 物流公司编码
     */
    private String ShipperCode;
    
    /**
     * 返回状态
     */
    private String Success;
    
    /**
     * 运单号
     */
    private String LogisticCode;
    
    /**
     * 状态
     */
    private String State;
    
    /**
     * 跟踪记录list
     */
    private List<TracesBean> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String eBusinessID) {
        EBusinessID = eBusinessID;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> traces) {
        Traces = traces;
    }
}