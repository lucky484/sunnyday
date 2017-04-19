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
<div class="modal inmodal" id="checkOrderMoney" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h1>补差价详情</h1>
            </div>
            <div class="modal-body modal_find_body_height">
                <div class="form-group shop_name_height">
                    <div class="col-md-2">订单号:</div>
                    <div class="col-md-10" id="findOrderNo"></div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">用户:</div>
                    <div class="col-md-10" id="differenceUser"></div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">类型:</div>
                    <div class="col-md-10" id="differenceType"></div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">差价金额:</div>
                    <div class="col-md-10" id="differenceMoney"></div>
                </div>
                <div id="differentHtml" class="form-group shop_name_height">
                    <div class="col-md-2">状态:</div>
                    <div class="col-md-10" id="differenceStatus"></div>
                </div>
                <div class="form-group shop_name_height">
                    <div class="col-md-2">差价原因:</div>
                    <div class="col-md-10" id="differenceCause"></div>
                </div>
                <div class="form-group remak_height" >
                    <div class="col-md-2">凭证:</div>
                    <div class="col-md-10" id="differenceImgs"></div>
                </div>
                <div class="form-group remak_height">
                    <div class="col-md-2">留言:</div>
                    <div class="col-md-10">
                    <textarea id="findRemark" name="findRemark" class="form-control" cols="60" rows="5" readonly="true"></textarea>
                   </div>
                </div>
            </div>
            <div class="modal-footer" id="buttonHtml">
            </div>
        </div>
    </div>
</div>

<!-- modal start -->
<div class="modal inmodal" id="findImgList" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" onclick="imgClose()"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h1>查看大图</h1>
            </div>
            <div class="modal-body modal_find_body_height">
               <img id="imgSrc" src="" width="730px" height="653px"/>
            </div>
            <div class="modal-footer" id="buttonHtml">
              <button type="button" class="btn btn-white" onclick="imgClose()">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->