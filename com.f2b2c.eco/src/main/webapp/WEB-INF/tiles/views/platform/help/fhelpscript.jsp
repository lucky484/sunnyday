<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/bhelp/querypage" var="querypageUrl" />
<spring:url value="/farm/bhelp/delete" var="deleteUrl" />
<spring:url value="/farm/bhelp/update" var="updateUrl" />
<spring:url value="/farm/bhelp/show" var="showUrl" />
<spring:url value="/farm/bhelp/show-update" var="showupdateUrl"/>
<script>
	
	$(function() {
		$("#helpcenter").dataTable({
			"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
			"autoWidth": false,
			"searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort":false,
			"pageLength" : 10,
			"pagingType" : "full_numbers",
			"lengthChange": true,
			"serverSide" : true,
			"bDestroy" : true,
			"ajax" : {
				"url" : "${querypageUrl}",
				"type" : "get",
				"dataType" :"json",
				"dataSrc":"content",
				"data" : {},
			}, 
			"columns" : [
// 			{
// 				"data" : "id"
// 			}, 
			{
				"data" : "question"
			},
// 			{
// 				"data" : "answer"
// 			}, 
			{
				"data" : "type",
				"mRender": function(data,type,full){
					if (full.type !=null) {
						if(full.type == "0"){
							return "<div class='text-center'>购买咨询</div>"
						}else if(full.type == "1"){
							return "<div class='text-center'>支付问题</div>"
						}else if(full.type == "2"){
							return "<div class='text-center'>物流与售后</div>"
						}else if(full.type == "3"){
							return "<div class='text-center'>其它问题</div>"
						}else if(full.type == "4"){
							return "<div class='text-center'>问答详情</div>"
						}else{
							return " - "
						}
					}else{
						return " - "
					}
				}
			}, {
				"data" : "status",
				"mRender": function(data,type,full){
					if (full.type !=null) {
						if(full.type == "0"){
							if(full.status == "1"){
								return "<div class='text-center'>购买相关问题</div>"
							}else{
								return "<div class='text-center'> - </div>"
							}
						}else if(full.type == "1"){
							if(full.status == "1"){
								return "<div class='text-center'>支付相关</div>"
							} else if(full.status == "2"){
								return "<div class='text-center'>积分卡券使用</div>"
							} else if(full.status == "3"){
								return "<div class='text-center'>支付异常</div>"
							} else {
								return "<div class='text-center'> - </div>"								
							}
						}else if(full.type == "2"){
							if(full.status == "1"){
								return "<div class='text-center'>物流配送</div>"
							} else if(full.status == "2"){
								return "<div class='text-center'>售后咨询</div>"
							}else{
								return "<div class='text-center'> - </div>"
							}
						}else if(full.type == "3"){
							if(full.status == "1"){
								return "<div class='text-center'>帐号与密码</div>"
							} else{
								return "<div class='text-center'> - </div>"								
							}
						}else{
							return " - "
						}
					}else{
						return " - "
					}
				}
			},{
				"data" : "style",
				"mRender": function(data,type,full){
					if (full.style !=null) {
						if(full.style == "0"){
							return "<div class='text-center'>否</div>"
						}else if(full.style == "1"){
							return "<div class='text-center'>是</div>"
						}else{
							return " - "
						}
					}else{
						return " - "
					}
				}
			}],
			"columnDefs" : [
// 			{
// 				"targets" : 0,
// 				"render" : function(data, type, full) {
// 					return "<div class='text-center'>" + full.id + "</div>";
// 				}
// 			},
			{
				"targets" : 0,
				"render" : function(data, type, full) {
					if(full.question.length >= 40){
						return "<div class='hidden'>" + full.question + "</div>";
					} else {
						return "<div class=''>" + full.question + "</div>";
					}
				}
			},
			{
				"targets" : 1,
				"render" : function(data, type, full) {
					if(full.question.length >= 40){
						return "<div class='hidden'>" + full.answer + "</div>";
					} else {
						return "<div class=''>" + full.answer + "</div>";
					}
					
				}
			},
			{
				"targets" : 2,
				"render" : function(data, type, full) {
					return "<div class='text-center'>" + full.type + "</div>";
				}
			},
			{
				"targets" : 3,
				"render" : function(data, type, full) {
					return "<div class='text-center'>" + full.style + "</div>";
				}
			},
			{	
				"targets" : 4,
				"render" : function(data, type, full, row) {
					return  '<div class="text-center">'
							+'<a onclick="show('+full.id+');" href="javascript:void(0);" class="btn mini light-blue">查看 </a>'
							+'<a onclick="update('+full.id+');" href="javascript:void(0);" class="btn mini light-blue">修改 </a>'
							+'<a href="javascript:void(0)" onclick="del('+full.id+')" class="btn mini light-blue">删除 </a>'
							+'</div>';
				}
			}]
		});
	});

	function del(id) {
		 $.ajax({
			"dataType" : 'json',
			"type" : 'post',
			"data" : {
				"id" : id
			},
			"url" : '${deleteUrl}',
			"success" : function(data) {
				debugger
				if (data) {
					swal({
						title: "成功!",   
						text: "删除成功",   
						type: "info", 
					} , function(){window.location.reload()});
				}else{
					swal({
						title: "失败!",   
						text: "删除失败",   
						type: "info", 
					} , function(){window.location.reload()});
				}
			},
			"error" : function(data) {
				debugger
				alert(data.responseText);//错误内容
			}
		});
	}
	
	//根据修改帮助详情
	function update(id){
// 		layer.open({
// 			  type: 2,
// 			  title: '商品详情信息',
// 			  shadeClose: true,
// 			  area: ['950px', '500px'],
// 			  content: '${pageContext.request.contextPath}/farm/bhelp/show-update?id='+id
// 		}); 
		window.location.href= '${showupdateUrl}?id='+id
	}
	
	//根据id获取帮助详情
	function show(id){
		layer.open({
			  type: 2,
			  title: '查看帮助详情',
			  shadeClose: true,
			  area: ['950px', '600px'],
			  content: '${pageContext.request.contextPath}/farm/bhelp/show?id='+id
		}); 
	}
</script>