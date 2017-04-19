<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>结算参数设置</h5>
                        <button class="btn btn-primary btn-sm pull-right m-t-n-xs" onclick="modify(this);">保持设置</button>
                    </div>
                    <div class="ibox-content col-sm-12">
                        <div class="ibox float-e-margins">
                        <div class="ibox-content" style="padding-bottom: 0px !important;">
                            <h3>各级别区域代理分配比例</h3>
                            <p>省级代理分比</p>
                            <div class="row m-b-lg">
                                <div class="col-sm-12">
                                    <div class="slider noUi-target noUi-ltr noUi-horizontal noUi-background" id="province"></div>
                                    <span class="example-val"><span id="province-span">0.00</span>%</span>
                                </div>
                            </div>
                            <p>市级代理分比</p>
                            <div class="row m-b-lg">
                                <div class="col-sm-12">
                                    <div class="slider noUi-target noUi-ltr noUi-horizontal noUi-background" id="city"></div>
                                    <span class="example-val" ><span id="city-span">0.00</span>%</span>
                                </div>
                            </div>
                            <p>区级代理分比</p>
                            <div class="row m-b-lg">
                                <div class="col-sm-12">
                                    <div class="slider noUi-target noUi-ltr noUi-horizontal noUi-background" id="area"></div>
                                    <span class="example-val" ><span id="area-span">0.00</span>%</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <div class="ibox-content col-sm-12">
                        <div class="ibox float-e-margins">
                        <div class="ibox-content">
                            <h3>非水果提成比例</h3>
                            <p>从非水果销售额中提成比例</p>
                            <div class="row m-b-lg">
                                <div class="col-sm-12">
                                    <div class="slider noUi-target noUi-ltr noUi-horizontal noUi-background" id="notfruit"></div>
                                    <span class="example-val"><span id="notfruit-span">0.00</span>%</span>
                                </div>
                            </div>
                            <p>平台和消费者的分成比例</p>
                            <div class="row m-b-lg">
                                <div class="col-sm-12">
                                    <div class="slider noUi-target noUi-ltr noUi-horizontal noUi-background" id="platformVsCustomer"></div>
                                    <span class="example-val">平台：<span id="platform-span">0.00</span>%</span>
                                    <span class="example-val pull-right">消费者:<span id="customer-span">100.00</span>%</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>