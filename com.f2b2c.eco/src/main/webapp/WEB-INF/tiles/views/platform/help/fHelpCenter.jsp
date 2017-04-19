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
				<div class="ibox-title">
					<h5>帮助中心</h5>
				</div>

				<div class="ibox-content">
					<table class="table table-striped table-bordered table-hover dataTables-logistics" id="helpcenter">
						<thead>
							<tr>
<!-- 								<th class="text-center" width="5%">ID</th> -->
								<th class="text-center" width="35%">问题</th>
<!-- 								<th class="text-center" width="35%">解决方案</th> -->
								<th class="text-center" width="6%">帮助类型</th>
								<th class="text-center" width="6%">问题类型</th>
								<th class="text-center" width="7%">是否热点问题</th>
								<th class="text-center" width="12%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
			</div>
		</div>
	</div>
</div>



