<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!-- Modal start-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header b-b-l-none">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12">
						<section class="panel panel-default">
							<header class="panel-heading font-bold"><fmt:message key="tiles.institution.vtl.member.modal.excel.tip"/></header>
							<div class="panel-body">
								<spring:url value="/institution/vtl/save" var="modal_saveUrl" />
									<spring:url
										value="/resources/images/virtualMemberexcelmode.png"
										var="virtualMemberexcelmode"/>
									
										<label class="col-lg-12"><fmt:message key="tiles.institution.vtl.member.modal.excel.tip1" />
											</label>
											<spring:url value="/institution/vtl/getvirmodel" var="getVirModel" />
											<form action="${getVirModel}" method="get" id="getvirmodel">
											<div class="col-sm-12"><img class="col-sm-7" src="${virtualMemberexcelmode}" /> <div class="col-sm-5 exportvirtual">
											 <a href="javascript:void(0)" onclick="exportVirModel();" class="clear text-ellipsis"><strong
													class="block">
													<i class="fa fa-download fa-2x"></i>
												           <fmt:message key="tiles.institution.vtl.member.modal.excel.tip2"/></strong> 
												</a>
											 </div></div></form>
											 <spring:url value="/institution/excel/importvirmember" var="importVirMember" />
											 <form action="${importVirMember}" method="post" id="importVirMember" enctype="multipart/form-data">
									        <div class="col-sm-12 uploadvirmodel" >
									        <div class="col-sm-4"><a href="javascript:;" class="a-upload" >
									        <input type="file" name="file" id="file"><fmt:message key="tiles.institution.vtl.member.modal.excel.tip3"/></a>
									        </div><div class="col-sm-8">
									        <input type="text"  class="showFileName" disabled value="">
									        </div></div></form>
									        
										<div class="col-sm-12"><a class="fileerrorTip" style="color:red"></a></div>
										 <div class="col-lg-offset-2 col-lg-10">
										</div> 
							</div>
						</section>
						<div align="center">
						<input type="button" class="btn btn-md btn-primary"
											onclick="importVirMember()" id="disabledimportVirMember"	value="<fmt:message key="tiles.institution.vtl.member.modal.excel.tip4"/>"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->

<!-- Modal start-->
<div class="modal fade" id="delModalTree" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<fmt:message key="tip.del.title" />
				</h4>
			</div>
			<div class="modal-body">
				<h3 class="text-danger"></h3>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<fmt:message key="tip.del.cancel" />
				</button>
				<button type="button" class="btn btn-danger" id="delcol">
					<fmt:message key="tip.del.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->


<!-- Modal start-->
<div class="modal fade" id="loadingModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<spring:url value="/resources/images/loading.gif"
										var="loadingGif"/>
			<img class="col-sm-7" src="${loadingGif}" style="width: 200px; margin-left: 30%; margin-top: 30%;" />
		</div>
	</div>
</div>

<!-- Modal start-->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" data-backdrop="static">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
				   <fmt:message key="tiles.views.user.index.modal.success.title" /> 
				</h4>
			</div>
			
			<div class="col-sm-12"><h1 class="text-danger"></h1>
			<div  id="rownumbers"style="width:500px; height:100px; overflow:auto; border:2px solid #E5E5E5;"></div></div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="refresh()">
					<fmt:message key="tip.del.confirm" />
				</button>
			</div>
		</div>
	</div>
</div>
<!--Modal end-->