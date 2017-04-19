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
<div class="modal inmodal" id="modifyShopModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h1>修改门店</h1>
            </div>
            <div class="modal-body modal_body_height">
                <div class="form-group shop_name_height">
                    <div class="col-md-2">店铺名称:</div>
                    <div class="col-md-10">
                    <input id="lnglat" name="lnglat" type="hidden"/>
                    <input type="text" class="form-control" placeholder="请输入门店名称" name="shopName" id="shopName"/>
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">手机号:</div>
                    <div class="col-md-10"><input type="text" class="form-control" placeholder="请输入手机号" name="phone" id="phone"/>
                   </div>
                </div>
                <div class="form-group remak_height">
                    <div class="col-md-2">店铺描述:</div>
                    <div class="col-md-10">
                    <textarea id="remark" name="remark" class="form-control" placeholder="请输入描述" cols="60" rows="5"></textarea>
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">店铺地址:</div>
                    <div class="col-md-10">
<!--                        <span  id="provinceHtml">
                       </span>
                       <span  id="cityHtml">
                       </span>
                       <span  id="areaHtml">
                       </span> -->
                       <div class="address_margin_top">
                                <!-- 将onchange="shop_change()"事件删除 by jane.hui -->
<!--                            <input type="text" class="form-control" placeholder="请输入门店地址" name="address" id="address" onchange="shop_change()"/>
 -->                           <input type="text" class="form-control" placeholder="请输入门店地址" name="address" id="address" readonly="true"/>
                       </div>
                    </div>
                </div>
                </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                <button id="modifyShop" type="button" class="btn btn-primary" onclick=""><span class="modal-oper">修改</span></button>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->

<!-- modal start -->
<div class="modal inmodal" id="findShopModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h1>查看门店</h1>
            </div>
            <div class="modal-body modal_find_body_height">
                <div class="form-group shop_name_height">
                    <div class="col-md-2">店铺名称:</div>
                    <div class="col-md-10"><input type="text" class="form-control" name="findShopName" id="findShopName" readonly="true"/>
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">手机号:</div>
                    <div class="col-md-10"><input type="text" class="form-control" name="findPhone" id="findPhone" readonly="true"/>
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">店老板:</div>
                    <div class="col-md-10"><input type="text" class="form-control" name="bUserName" id="bUserName" readonly="true"/>
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">专属顾问:</div>
                    <div class="col-md-10">
                    <input type="text" class="form-control" name="fUserName" id="fUserName" readonly="true"/>
                 </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">授权码:</div>
                    <div class="col-md-10">
                        <input type="text" class="form-control" name="authCode" id="authCode" readonly="true"/>
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">店铺地址:</div>
                    <div class="col-md-10">
                       <div class="address_margin_top">
                           <input type="text" class="form-control" name="findAddress" id="findAddress" readonly="true"/>
                       </div>
                    </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">店铺描述:</div>
                    <div class="col-md-10">
                    <textarea id="findRemark" name="findRemark" class="form-control" cols="60" rows="5" readonly="true"></textarea>
                   </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->