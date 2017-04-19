<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="ibox float-e-margins">
          <div class="ibox float-e-margins">
                <div class="ibox-title"><h5>运费设置</h5></div>
                <div class="ibox-content freight_height">
                  <c:forEach items="${list}" var="var">
                      <div class="col-sm-3 input_margin_top"><span class="province_name">${var.provinceName}</span>
                      <input id="${var.provinceId},${var.id}" class="form-control input_width" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" value="${var.freight}" name="freightName" type="text"/><span class="freight_unit">&nbsp;元</span>
                      </div>
                   </c:forEach>
                   <div class="form-group" style="float:left;margin-top:30px;width:100%;">
                       <div class="col-sm-9" style="color:red">注:当省金额显示为0时,默认配送至该省时免运送费。</div>
                       <div class="col-sm-3">
                       <button class="btn btn-primary" type="button" onclick="modifyFreight()">运费设置</button>
                       </div>
                   </div>
                </div>
            </div>
        </div> 
    </div>
 </div>