<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style> 
.ibox-tools-btn {
display: inline-block;
    float: right;
    position: relative;
    padding: 0px;
	margin-top: -8px !important;
}
</style> 
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
			<form id="editorForm" method="post">
				<div class="ibox-title">
					<h5>
						注册协议 <small> / 编辑协议 </small>
					</h5>
					<div class="ibox-tools-btn">
							<button class="btn-sm btn btn-primary" type="button"
								onclick="save();">保存</button>
					</div>
					<input type="hidden" id="id" name="id" value="${protocol.id }" />
					<input type="hidden" id="type" name="type" value="1" />
				</div>
				<div class="ibox-content">
					<section id="page-demo">
							<textarea id="editor" name="content" placeholder="这里输入内容" autofocus>${protocol.content }</textarea>
					</section>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
