<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
<!-- modal start -->
<div class="modal inmodal" id="showDelivyGoodsModel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" >
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h3>填写物流信息</h3>
            </div>
            <div class="modal-body" style="height:300px;">
                <div class="col-md-12 form-group">
                    <div class="col-md-3">物流公司:</div>
                    <div class="col-md-9">
                       <input type="hidden" id="logisticCode"/>
                      <input type="text" class="form-control" onclick="getLogisticsInfo()" readonly="readonly" placeholder="请选择物流公司" id="logisticName"/>
                   </div>
                </div>
                <div class="col-md-12 form-group">
                    <div class="col-md-3">运单号:</div>
                    <div class="col-md-9">
                      <input type="text" class="form-control" placeholder="请输入运单号" id="logisticNumber"/>
                    </div>
               </div>
            </div>
            <div class="modal-footer" id="saveLogisticHtml">
            </div>
        </div>
    </div>
</div>
<!-- modal end -->

<!-- modal start -->
<div class="modal inmodal" id="chooseLogicCompay" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight" style="width:900px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h3>选择物流信息</h3>
            </div>
            <div class="modal-body" style="height:700px;">
                        <div class="row">
                            <div class="col-sm-2">
                           		 物流公司：
                            </div>
                             <div class="col-sm-7">
                           		 <input id="name" type="text" class="form-control" placeholder="请输入物流公司" />
                            </div>
                            <div class="col-sm-3">
                                <button class="btn-sm btn btn-primary" type="button" onclick="compaySearch();">查询</button>
                                <button class="btn-sm btn" type="button" onclick="compayReset();">清空</button>
                            </div>
                        </div>
                    <table id="CompanyTable" class="table table-striped  table-bordered">
                       <thead>
                         <tr>
                            <th>物流编号</th>
                            <th>物流公司</th>
                         </tr>
                       </thead>
                       <tbody>
                       </tbody>
                   </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="chooseCompayVal()"><span class="modal-oper">选择</span></button>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->