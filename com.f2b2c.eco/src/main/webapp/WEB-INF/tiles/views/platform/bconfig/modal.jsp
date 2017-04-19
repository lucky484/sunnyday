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
<div class="modal inmodal" id="modifyProblemModal" tabindex="-1" role="dialog" aria-hidden="true">
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
<div class="modal inmodal" id="showProblemModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight modal_show">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <div>
                    <h5 style="float:left;">查看常见问题</h5>
                </div>
            </div>
            <form method="post" class="form-horizontal">
                   <div class="form-group">
                       <label class="col-sm-2 control-label">问题名称<span class="flower">*</span>：</label>
                       <div class="col-sm-9">
                           <input type="text" id="problem" name="problem" class="form-control title">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-sm-2 control-label">问题回答 <span class="flower">*</span>：</label>
                       <div class="col-sm-9">
                          <div class="row">
						    <script id="container" name="response" type="text/plain"></script>
					  		<script type="text/javascript">
					  			
							</script>
					   </div>
                       </div>
                   </div>
             </form>
        </div>
    </div>
</div>
<!-- modal end -->