<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/shop/index" var="shopIndex" />
<spring:url value="/farm/shop/upload" var="shopUploadUrl" />
<spring:url value="/resources/platform/js/plugins/simditor/image/image.png" var="defaultImgUrl" />
<script type="text/javascript">
	
	//添加店铺的方法
	function add(){
		//首先进行前端的验证，验证通过之后再验证授权码，如果授权码不对，则进行提示，之后再进行保存的操作
		var authCode = $("input[name='authCode']").val();
		$.ajax({
			"dataType": 'json',
	        "type": "get",
	        "data": {"authCode":$.trim(authCode)},
            "url":"${pageContext.request.contextPath}/farm/shop/validauthcode",
	        "success": function(result){
        		if(result.type == "error"){
        			 swal({
        			        title: "授权码不存在",
        			        type: "warning"
        			    });
				} else if (result.type == "warning") {
					 swal({
					        title: "授权码已被使用",
					        type: "warning"
					    });
				} else {
					//提交数据到后台
					var postData = $("#addForm").serialize();
							$.ajax({
								"dataType" : 'json',
								"type" : "post",
								"data" : postData,
								"url" : "${pageContext.request.contextPath}/farm/shop/add",
								"success" : function(result) {
									if(result.type == "error"){
										swal({
				        			        title: "添加店铺失败",
				        			        type: "warning"
				        			    });
									}else{
										window.location.href = "${shopIndex}";
									}
								}
							});
					}
				}
			});
		}

	//初始化加载simditor编辑器
	$(function() {
		var editor = new Simditor({
			textarea : $("#editor"),
			placeholder : '请在这里输入内容...',
			imageButton : [ 'upload'],
			defaultImage:"${defaultImgUrl}",
			upload : {
				url : '${shopUploadUrl}',
				params : null,
				fileKey : 'files',
				connectionCount : 3,
				leaveConfirm : '正在上传文件'
			}
		});
	});
</script>




