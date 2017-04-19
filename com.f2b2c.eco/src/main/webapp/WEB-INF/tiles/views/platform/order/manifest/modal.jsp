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
<div class="modal inmodal" id="producerModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal_height">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h1>设置制单人</h1>
            </div>
            <div class="modal-body modal_find_body_height">
                <div class="form-group shop_name_height">
                    <div class="col-md-2">制单人:</div>
                    <div class="col-md-10">
                    	<input type="text" class="form-control" name="producer" id="producer"/>
                   </div>
                </div>
            </div>
            <div class="modal-footer">
            	<button class="btn-sm btn btn-primary" type="button" onclick="confirm();">确定</button>
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->