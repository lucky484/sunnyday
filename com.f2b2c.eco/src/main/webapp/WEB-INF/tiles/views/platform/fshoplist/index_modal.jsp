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
<div class="modal inmodal" id="modifyGoodsWeightModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h1>设置权重</h1>
            </div>
            <div class="modal-body modal_body_height">
                <div class="form-group shop_name_height">
                    <div class="col-md-2">所在分类:</div>
                    <div class="col-md-10">
                    <input type="text" class="form-control" value="" id="kindName_modal" disabled="true"/>
                    <input type="hidden" value="" id="goodsId_modal">
                   </div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">权值:</div>
                    <div class="col-md-10">
                    <input type="number" class="form-control" placeholder="请输入1-9999的有效数字" name="weight_modal" id="weight_modal"/>
                   </div>
                </div>
                <div class="form-group ">
                	 权值为该商品在App对应商品分类中的排序
                </div>
                </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                <button id="modifyWeight" type="button" class="btn btn-primary" onclick="modifyWeight()"><span class="modal-oper">修改</span></button>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->
